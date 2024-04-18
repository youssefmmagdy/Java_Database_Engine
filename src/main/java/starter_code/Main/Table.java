package starter_code.Main;

import starter_code.BTree.BTree;
import starter_code.Exception.DBAppException;
import starter_code.Serialization.Deserialize;
import starter_code.Serialization.Serialize;

import java.io.*;
import java.util.*;
import static starter_code.Main.DBApp.*;

public class Table implements Serializable,Iterator<Table> {
  private String tableName;

  public void setPageNames(Vector<String> pageNames) {
    this.pageNames = pageNames;
  }

  public Vector<Object> getMaxOfPages() {
    return maxOfPages;
  }

  public void setMaxOfPages(Integer index, Object o) {
    this.maxOfPages.set(index, o);
  }

  private Vector<Object> maxOfPages;

  public Vector<Object> getminOfPages() {
    return minOfPages;
  }

  public void setMinOfPages(Integer index, Object o) {
    this.minOfPages.set(index, o);
  }

  private Vector<Object> minOfPages;
  private Vector<String> pageNames;
  private Vector<String> treesNames;
  private int numberOfPages;
  private int numberOfRows;


  public Table(String x) throws IOException {

    tableName = x;
    pageNames = new Vector<>();
    treesNames = new Vector<>();
    numberOfPages=0;
    maxOfPages = new Vector<>();
    minOfPages = new Vector<>();
  }



  public Table(String x, Vector<String> pageNames, int numberOfPages) {
  }

  public int getNumberOfRows() {
    return numberOfRows;
  }

  public Vector<String> getTreesNames(){
    return treesNames;
  }
  public void setTreesNames(Vector<String> newTrees){
    treesNames = newTrees;
  }
  public String toString(){
    StringBuilder sb = new StringBuilder("{\n");
    for(String pageName : getPageNames()){
      try {
        sb.append(Deserialize.DeserializePage(pageName,getTableName()) + "\n");
      } catch (IOException e) {
        System.out.println(1);
        throw new RuntimeException(e);
      } catch (ClassNotFoundException e) {
        System.out.println(2);
        throw new RuntimeException(e);
      }
    }
    sb.append("}");
    return sb.toString();
  }
  public void addTreeName(String treeName){ treesNames.add(treeName);}

  public void setNumberOfRows(int numberOfRows) {
    this.numberOfRows = numberOfRows;
  }



  public String getTableName() {
    return tableName;
  }

  public Vector<String> getPageNames() {
    return pageNames;
  }

  public int getNumberOfPages(){
    return numberOfPages;
  }





  public boolean inRange(Object o, Page p) throws IOException, ClassNotFoundException {
    Object max = p.getMax();
    Object min = p.getMin();
    if(((Comparable) min).compareTo(o) <= 0 && ((Comparable) max).compareTo(o) >= 0){
      return true;
    }
    return false;
  }
  public boolean lowerthanmin(Object o, Page p) throws IOException, ClassNotFoundException {
    Object min = p.getMin();
    if(((Comparable) min).compareTo(o) > 0){
      return true;
    }
    return false;
  }
  public boolean biggerthanmax(Object o, Page p) throws IOException, ClassNotFoundException {
    Object max = p.getMax();
    if(((Comparable) max).compareTo(o) < 0){
      return true;
    }
    return false;
  }
  public void insertmiddle(Hashtable<String, Object> ht, Page p, String primary) {
    Object Key = ht.get(primary);
    for(Record r : p.getTuples()){
      if (!r.isNull) {
        Object o = r.getHm().get(primary);
        if (((Comparable) o).compareTo(Key) > 0) {
          int in = p.getTuples().indexOf(r);
          if (p.getTuples().get(in - 1).isNull) {
            p.getTuples().get(in - 1).setHm(ht);
            p.getTuples().get(in - 1).unsetNull();
          } else {
            Record re = new Record(p.getName());
            re.setHm(ht);
            p.getTuples().insertElementAt(re, in);
          }
          break;
        }
      }
    }

  }


  public void insertbefore(Hashtable<String, Object> ht, Page p, String primary){
    Vector<Record> v = p.getTuples();
    if(v.get(0).isNull) {
      v.get(0).setHm(ht);
      v.get(0).unsetNull();
    }else{
      Record r = new Record(p.getName());
      r.setHm(ht);
      v.insertElementAt(r,0);
    }
    p.setMin(ht.get(primary));
    int n = pageNames.indexOf(p.getName());
    setMinOfPages(n,p.getMin());
  }


  public void add(Hashtable<String, Object> ht) throws IOException, ClassNotFoundException {
    Vector column = columnNameReader(tableName);
    String primary = clusteringKey_ID(column);

    Properties properties = new Properties();
    FileInputStream fileInputStream = new FileInputStream("starter_code/resources/DBApp.config");
    properties.load(fileInputStream);
    int maxRowsCount = Integer.parseInt(properties.getProperty("MaximumRowsCountinPage"));




    if(pageNames.size() != 0){
      for (String s : pageNames) {
        Page p = Deserialize.DeserializePage(s, tableName);
        boolean in = inRange(ht.get(primary), p);
        boolean bg = biggerthanmax(ht.get(primary), p);
        boolean lo = lowerthanmin(ht.get(primary), p);

        if (p.getName().equals(pageNames.get(pageNames.size() - 1))) {
          if (bg) {
            if (p.getNumberofRowsraw() < maxRowsCount) {
              p.add(ht);
              update(p, primary);
              numberOfRows++;
              numberOfRowsraw++;
              Serialize.Serializethis(s, p, tableName);
            } else {
              classicInsert(ht, primary);
            }
            break;
          }
        }



        if (in) {
          if (p.getNumberofRowsraw() < maxRowsCount) {
            insertmiddle(ht, p, primary);
            Serialize.Serializethis(p.getName(),p,tableName);
            break;
          } else {
            insertmiddleshift(ht, p, primary);
            Serialize.Serializethis(p.getName(),p,tableName);
            break;
          }
        } else if (lo) {
          if((!this.pageNames.contains(tableName+(p.getPageNumber()-1)))&&p.getPageNumber()-1 > 0){
            String pagename = tableName + (p.getPageNumber()-1);
            Page y = new Page(tableName, pagename);
            int n = pageNames.indexOf(p.getName());
            this.pageNames.insertElementAt(pagename,n);
            y.add(ht);
            y.setMax(ht.get(primary));
            y.setMin(ht.get(primary));
            n = pageNames.indexOf(y.getName());
            this.maxOfPages.insertElementAt(y.getMax(),n);
            this.minOfPages.insertElementAt(y.getMin(),n);
            Serialize.Serializethis(pagename, y, tableName);
            break;
          }else{
            if (p.getNumberofRowsraw() < maxRowsCount) {
              insertbefore(ht, p, primary);
              Serialize.Serializethis(p.getName(),p,tableName);
              break;
            } else {
              insertbeforeshift(ht, p, primary);
              Serialize.Serializethis(p.getName(),p,tableName);
              break;
            }
          }
        }
      }
    }
    else{
      classicInsert(ht,primary);
    }
    getRecord(ht, primary);
    Serialize.Serializethis(tableName,this,tableName);

  }
  public void insertmiddleshift(Hashtable<String, Object> ht, Page p, String primary) throws IOException, ClassNotFoundException {
    Properties properties = new Properties();
    FileInputStream fileInputStream = new FileInputStream("starter_code/resources/DBApp.config");
    properties.load(fileInputStream);
    int maxRowsCount = Integer.parseInt(properties.getProperty("MaximumRowsCountinPage"));
    insertmiddle(ht, p, primary);
    Record r = p.getTuples().get(p.getTuples().size() - 1);
    if(!r.isNull){

      Record tbi = p.getTuples().get(p.getTuples().size() - 1);
      p.getTuples().remove(tbi);

      update(p, primary);

      File f = new File("starter_code/" + tableName + "/" + tableName + (p.getPageNumber()+1)+".class");
      if(!f.exists()){
        classicInsert(tbi.getHm(), primary);
      }else {
        boolean flag = true;
        while (flag) {
          String s = tableName + p.getPageNumber() + 1;
          Page d = Deserialize.DeserializePage(s, tableName);
          if (d.getNumberofRowsraw() < maxRowsCount) {
            insertbefore(r.getHm(), d, primary);
            flag = false;
          } else {
            insertbeforeshift(r.getHm(), d, primary);
          }
        }
      }
    }else{
      p.getTuples().remove(p.getTuples().size() - 1);
    }

  }
  public void insertbeforeshift(Hashtable<String, Object> ht, Page p, String primary) throws IOException, ClassNotFoundException {
    Properties properties = new Properties();
    FileInputStream fileInputStream = new FileInputStream("starter_code/resources/DBApp.config");
    properties.load(fileInputStream);
    int maxRowsCount = Integer.parseInt(properties.getProperty("MaximumRowsCountinPage"));


    insertbefore(ht, p, primary);
    Record r = p.getTuples().get(p.getTuples().size() - 1);
    if(!r.isNull){
      p.getTuples().remove(p.getTuples().size() - 1);

      update(p, primary);

      File f = new File("starter_code/" + tableName + "/" + tableName + (p.getPageNumber()+1)+".class");
      if(!f.exists()){
        classicInsert(r.getHm(), primary);
      }else {
        String s = tableName + (p.getPageNumber() + 1);
        Page d = Deserialize.DeserializePage(s, tableName);
        if (d.getNumberofRowsraw() < maxRowsCount) {
          insertbefore(r.getHm(), d, primary);
          Serialize.Serializethis(s,d,tableName);
        } else {
          insertbeforeshift(r.getHm(), d, primary);
        }
      }
    }else{
      p.getTuples().remove(p.getTuples().size() - 1);
    }

  }

  private void classicInsert(Hashtable<String, Object> ht, String primary) throws IOException, ClassNotFoundException {
    numberOfPages++;
    numberOfRows++;
    numberOfRowsraw++;
    String pagename = tableName + numberOfPages;
    Page y = new Page(tableName, pagename);
    pageNames.add(pagename);
    y.add(ht);
    y.setMax(ht.get(primary));
    y.setMin(ht.get(primary));
    this.maxOfPages.add(y.getMax());
    this.minOfPages.add(y.getMin());
    Serialize.Serializethis(pagename, y, tableName);
  }


  public int getNumberOfRowsraw() {
    return numberOfRowsraw;
  }

  public void setNumberOfRowsraw(int numberOfRowsraw) {
    this.numberOfRowsraw = numberOfRowsraw;
  }

  private int numberOfRowsraw;

  public static Vector<String> getIndexedColumns(Vector<Vector<String>> column){
    Vector<String> result = new Vector<>();
    for (Vector<String> d : column)
      if(d.get(3).equals("B+tree"))
        result.add(d.get(0));

    return result;
  }
  public static String getPageName(String s){
    int i = 0;
    StringBuilder sb = new StringBuilder();
    while(s.charAt(i++) != ' ');
    while(i < s.length()){
      sb.append(s.charAt(i++));
    }
    return sb.toString();
  }
  public static String getTreeName(Vector<Vector<String>> column, String columnName){
    for(Vector<String> d : column){
      if(d.get(0).equals(columnName))
        return d.get(2);
    }
    return null;
  }

  public void deleteRows(Hashtable<String, Object> htblColNameValue) throws IOException, ClassNotFoundException, DBAppException {

    if(htblColNameValue.size() == 0){
      while(pageNames.size() > 0){
        File pageFile = new File("starter_code/" + tableName + "/" + pageNames.get(0) + ".class");
        pageFile.delete();
        pageNames.remove(pageNames.get(0));
      }
      treesNames.clear();
      maxOfPages.clear();
      minOfPages.clear();
      numberOfRows = 0;
      numberOfPages = 0;
      return;
    }

    Vector<String> indexedColumns =  getIndexedColumns(columnNameReader(tableName));
    boolean f = false;
    for (Map.Entry<String, Object> entry : htblColNameValue.entrySet()) {
      if (indexedColumns.contains(entry.getKey())) {
        f = true;
        BTree tree = Deserialize.DeserializeTree(getTreeName(columnNameReader(tableName), entry.getKey()), tableName);
        String s = (String) tree.search((Comparable) entry.getValue());
        if(s == null)
          throw new DBAppException("Record Not Found");
        Page page = Deserialize.DeserializePage(getPageName(s), tableName);
        for (Record record : page.getTuples()) {
          boolean flag = false;
          for (Map.Entry<String, Object> entry1 : htblColNameValue.entrySet()) {
            if (!record.getHm().containsKey(entry1.getKey())
                    || !record.getHm().get(entry1.getKey()).equals(htblColNameValue.get(entry1.getKey()))) {
              flag = true;
              break;
            }
          }
          if (!flag) {
            for (Map.Entry<String, Object> entry1 : htblColNameValue.entrySet()) {
              if(indexedColumns.contains(entry1.getKey())){
                tree = Deserialize.DeserializeTree(getTreeName(columnNameReader(tableName), entry1.getKey()), tableName);
                tree.delete((Comparable) entry1.getValue());
                Serialize.Serializethis(tree.getTreeName(), tree, tableName);
              }
            }
            record.setNull();
            update(page, getPrimaryKey(tableName));
            numberOfRows--;

            if (page.getNumberOfRows() == 0) {
              numberOfRowsraw -= page.getNumberofRowsraw();
              int n = pageNames.indexOf(page.getName());
              this.maxOfPages.remove(n);
              this.minOfPages.remove(n);
              pageNames.remove(record.getPageName());
              File pageFile = new File("starter_code/" + tableName + "/" + record.getPageName() + ".class");
              pageFile.delete();
            } else {
              Serialize.Serializethis(record.getPageName(), page, tableName);
            }
            Serialize.Serializethis(tableName, this, tableName);
          }
        }


      }
    }

    if(!f) {
      if (htblColNameValue.containsKey(getPrimaryKey(this.tableName))) {
        Record record = binarySearch(tableName, htblColNameValue.get(getPrimaryKey(tableName)));
        if (record == null)
          throw new DBAppException("Record Doesn't Exist");
        for (Map.Entry<String, Object> entry : htblColNameValue.entrySet()) {
          if (!record.getHm().containsKey(entry.getKey())
                  || !record.getHm().get(entry.getKey()).equals(htblColNameValue.get(entry.getKey()))) {
            record = null;
            break;
          }
        }
        if (record == null)
          throw new DBAppException("Record Doesn't Exist");

        record.setNull();
        Page i = Deserialize.DeserializePage(record.getPageName(), tableName);
        i.getTuples().get(getRecordNumber(i, record)).setNull();
        update(i, getPrimaryKey(tableName));

        numberOfRows--;

        if (i.getNumberOfRows() == 0) {
          int n = pageNames.indexOf(i.getName());
          this.maxOfPages.remove(n);
          this.minOfPages.remove(n);
          pageNames.remove(record.getPageName());
          File pageFile = new File("starter_code/" + tableName + "/" + record.getPageName() + ".class");
          pageFile.delete();
        } else {
          Serialize.Serializethis(record.getPageName(), i, tableName);
        }
        Serialize.Serializethis(tableName, this, tableName);
      } else {

        Vector<String> clonedpages = (Vector<String>) pageNames.clone();
        for (String pageName : clonedpages) {
          Page deserializedPage = Deserialize.DeserializePage(pageName, tableName);
          Vector<Record> tuples = deserializedPage.getTuples();
          boolean pageModified = false;
          Iterator<Record> iterator = tuples.iterator();
          while (iterator.hasNext()) {
            Record tuple = iterator.next();
            boolean match = true;
            for (String colName : htblColNameValue.keySet()) {
              Object colValue = htblColNameValue.get(colName);
              if (!tuple.getHm().containsKey(colName) || !tuple.getHm().get(colName).equals(colValue)) {
                match = false;
                break;
              }
            }

            if (match) {
              tuple.setNull();
              update(deserializedPage, getPrimaryKey(tableName));
//            iterator.remove();

              numberOfRows--;
              pageModified = true;
            }
          }

          if (deserializedPage.getNumberOfRows() == 0) {
            int n = pageNames.indexOf(deserializedPage.getName());
            maxOfPages.remove(n);
            minOfPages.remove(n);
            pageNames.remove(pageName);
            numberOfRowsraw--;
            File pageFile = new File("starter_code/" + tableName + "/" + pageName + ".class");
            pageFile.delete();

          } else if (pageModified) {
            Serialize.Serializethis(pageName, deserializedPage, tableName);
          }
          Serialize.Serializethis(tableName, this, tableName);
        }
      }
    }
  }
  
  

  @Override
  public boolean hasNext() {
    return false;
  }

  @Override
  public Table next() {
    return null;
  }

  public void update(Page p, String primary){
    int n = pageNames.indexOf(p.getName());
    for(int i = 1;i<p.getTuples().size();i++){
      Record re = p.getTuples().get(p.getTuples().size() - i);
      if(!re.isNull){
        p.setMax(re.getHm().get(primary));
        setMaxOfPages(n,p.getMax());
        break;
      }
    }
    for(int i = 1;i<p.getTuples().size();i++){
      Record re = p.getTuples().get(i);
      if(!re.isNull){
        p.setMin(re.getHm().get(primary));
        setMinOfPages(n,p.getMin());
        break;
      }
    }
  }

  public Vector<Integer> getRecord(Hashtable ht, String primary) throws IOException, ClassNotFoundException {
    Object comparator = ht.get(primary);
    for(String s : pageNames){
      Page p = Deserialize.DeserializePage(s, tableName);
      boolean in = inRange(comparator,p);
      if(in){
        int place = p.getTuples().indexOf(comparator);
        Vector<Integer> v = new Vector<Integer>(2);
        v.add(p.getPageNumber());
        v.add(place+1);
        return v;
      }
    }
    return null;
  }

  public Record getRecordValue(Object Id) throws IOException, ClassNotFoundException {
    Record Returned = null;
    for(String pagename : pageNames) {
      Page K = Deserialize.DeserializePage(pagename,tableName);
      for (int j = 0; j < K.getNumberOfRows();j++){
        Vector<Record> M = K.getTuples();
        for (int k = 0; k < M.size(); k++){
          Record ComparedTo = M.get(k);
          System.out.println(ComparedTo + " "+Id);
          if (ComparedTo.hm.contains(Id)){
            Returned = M.get(k);
          }
        }
      }

    }
    return Returned;
  }

  public static void main(String[] args) throws IOException, ClassNotFoundException, DBAppException {

    System.out.println(getIndexedColumns(columnNameReader("Student")));

  }

}