package starter_code.Main;

import starter_code.BTree.BTree;
import starter_code.Exception.DBAppException;
import starter_code.Serialization.Deserialize;
import starter_code.Serialization.Deserialize.*;
import starter_code.Serialization.Serialize.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import starter_code.Main.Record.*;
import static starter_code.Main.DBApp.*;
import static starter_code.Main.Table.*;
import static starter_code.Serialization.Deserialize.*;

public class awel3alanedeef {

//    public Iterator selectFromTable(SQLTerm[] arrSQLTerms, String[]  strarrOperators) throws DBAppException, IOException, ClassNotFoundException {
//        Vector<Record>[] vectors = new Vector[arrSQLTerms.length];
//        Vector k = columnNameReader(arrSQLTerms[0]._strTableName);
//        String primaryKey = clusteringKey_ID(k);
//        for(int i = 0; i < arrSQLTerms.length; i++) {
//            Vector<Record> recordlist = new Vector<>();
//            if(Objects.equals(arrSQLTerms[i]._strColumnName, primaryKey)){
//            }else if (hasBinaryTree(k, arrSQLTerms[i]._strColumnName)){
//                String str = arrSQLTerms[i]._strColumnName+"Index";
//            }else{
//            }
//            vectors[i] = recordlist;
//        }
//
//        return null;
//    }
//    public static String clusteringKey_ID(Vector column){
//        String clusteringKey = "";
//        for (int i = 0; i < column.size(); i++) {
//            Vector K = (Vector) column.get(i);
//            if (K.get(4).equals("True")) {
//                clusteringKey = (String) K.get(0);
//            }
//        }
//        return clusteringKey;
//    }
//    public static boolean hasBinaryTree(Vector column, String collumName){
//        for (int i = 0; i < column.size(); i++) {
//            Vector K = (Vector) column.get(i);
//            if (!K.get(5).equals("null") && K.get(0).equals(collumName)) {
//                return true;
//            }
//        }
//        return false;
//    }
//    public static Vector<Vector<String>> columnNameReader (String strTableName){
//        String csvFile = "metadata.csv";
//
//        Vector<Vector<String>> column = new Vector<>();
//        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                String[] values = line.split(",");
//                if (values[0].equals(strTableName)) {
//
//                    Vector<String> add = new Vector<>();
//                    add.add(values[1]);
//                    add.add(values[2]);
//                    add.add(values[4]);
//                    add.add(values[5]);
//                    add.add(values[3]);
//                    column.add(add);
//
//                }
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        return column;
//    }




    public Iterator selectFromTable(SQLTerm[] arrSQLTerms, String[]  strarrOperators) throws DBAppException, IOException, ClassNotFoundException {
        if(arrSQLTerms.length > 0) {
            Table deserializedTable = DeserializeTable(arrSQLTerms[0]._strTableName);
            if (deserializedTable.getPageNames().isEmpty())
                throw new DBAppException("Table is Empty");
        }
        boolean flag = false;
        Table v = new Table("Student");
        // useless line but ill keep it for reference -> Iterator operators = Arrays.stream(strarrOperators).iterator();
        // idk what to do with this--> bplustree tree = new bplustree(deserializedTable.getNumberOfRows());
        //		By Doing this  -->  BTree tree = new BTree(tableName, columnName, indexName);
        Vector<Record>[] vectors = new Vector[arrSQLTerms.length];
        Iterator table = new Table3ashankhaterSeif();
            if (v.getTableName().equals(arrSQLTerms[0]._strTableName) && searchMetadata(arrSQLTerms[0]._strTableName)) {
                flag = true;
                table = new Table3ashankhaterSeif();

                if (strarrOperators.length == 0) {
                    for (String pageName : v.getPageNames()) {
                        Page page = DeserializePage(pageName, v.getTableName());
                        Vector<Record> tuples = page.getTuples();

                        for (Record record : tuples) {
                            Hashtable<String, Object> ht = record.getHm();
                            for (Map.Entry<String, Object> entry : ht.entrySet()) {
                                String key = entry.getKey();
                                Object value = entry.getValue();

                                if (key.equalsIgnoreCase(arrSQLTerms[0]._strColumnName)) {
                                    if (checkValidity2(value, arrSQLTerms, 0)) {
                                        switch (arrSQLTerms[0]._strOperator) {
                                            case "=":
                                                if (value.equals(arrSQLTerms[0]._objValue)) {
                                                    Vector<Record> vec = new Vector<>();
                                                    Record r = v.getRecordValue(ht);
                                                    vec.add(r);
                                                    vectors[0] = vec;
                                                }
                                                break;
                                            case ">":
                                                if (((Comparable) value).compareTo(arrSQLTerms[0]._objValue) > 0) {
                                                    Vector<Record> vec = new Vector<>();
                                                    Record r = v.getRecordValue(ht);
                                                    vec.add(r);
                                                    vectors[0] = vec;
                                                }
                                                break;
                                            case ">=":
                                                if (((Comparable) value).compareTo(arrSQLTerms[0]._objValue) >= 0) {
                                                    Vector<Record> vec = new Vector<>();
                                                    Record r = v.getRecordValue(ht);
                                                    vec.add(r);
                                                    vectors[0] = vec;
                                                }
                                                break;
                                            case "<":
                                                if (((Comparable) value).compareTo(arrSQLTerms[0]._objValue) < 0) {
                                                    Vector<Record> vec = new Vector<>();
                                                    Record r = v.getRecordValue(ht);
                                                    vec.add(r);
                                                    vectors[0] = vec;
                                                }
                                                break;
                                            case "<=":
                                                if (((Comparable) value).compareTo(arrSQLTerms[0]._objValue) <= 0) {
                                                    Vector<Record> vec = new Vector<>();
                                                    Record r = v.getRecordValue(ht);
                                                    vec.add(r);
                                                    vectors[0] = vec;
                                                }
                                                break;
                                            case "!=":
                                                if (!value.equals(arrSQLTerms[0]._objValue)) {
                                                    Vector<Record> vec = new Vector<>();
                                                    Record r = v.getRecordValue(ht);
                                                    vec.add(r);
                                                    vectors[0] = vec;
                                                }
                                                break;
                                            default:
                                                throw new DBAppException("Error: Invalid operator.");
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else
                {
                    Vector<String> indexedColumns = getIndexedColumns(columnNameReader(v.getTableName()));


                    // checks on Primary Keys & Indexed

                    for(int i = 0;i < arrSQLTerms.length;i++) {

                        Vector<Record> records = new Vector<>();

                        if(vectors[i] == null && arrSQLTerms[i]._strColumnName.equals(getPrimaryKey(v.getTableName())) && indexedColumns.contains(arrSQLTerms[i]._strColumnName)){

                            String treeName = getTreeName(columnNameReader(v.getTableName()), arrSQLTerms[i]._strColumnName);
                            BTree tree = DeserializeTree(treeName, v.getTableName());

                            switch (arrSQLTerms[i]._strOperator){
                                case "=":
                                    String s = (String) tree.search((Comparable) arrSQLTerms[i]._objValue);
                                    Page page = DeserializePage(getPageName(s) , v.getTableName());
                                    Record record = binarySearchPage(v.getTableName(),page,arrSQLTerms[i]._objValue);
                                    records.add(record);
                                    ;break;
                                case "<=": ;break;
                                case ">=": ;break;
                                case "<": ;break;
                                case ">": ;break;
                            }

                            String s = (String) tree.search((Comparable) arrSQLTerms[i]._objValue);
                            Page page = DeserializePage(getPageName(s) , v.getTableName());
                            Record record = binarySearchPage(v.getTableName(),page,arrSQLTerms[i]._objValue);
                            records.add(record);

                            vectors[i] = records;
                            break;
                        }


                    }

                        // checks on Primary Keys (Not Indexed)
                        for(int i = 0;i < arrSQLTerms.length;i++) {
                            Vector<Record> records = new Vector<>();
                            if(vectors[i] == null && arrSQLTerms[i]._strColumnName.equals(getPrimaryKey(v.getTableName())) && ! indexedColumns.contains(arrSQLTerms[i]._strColumnName)){

                                Vector<Record> records1 = new Vector<>();
                                switch (arrSQLTerms[i]._strOperator){
                                    case "=":
                                        Record record = binarySearch(v.getTableName(),arrSQLTerms[i]._objValue);
                                        records.add(record);
                                        ;break;
                                    case "<=":
                                        records1 = linearSearchTable(DeserializeTable(v.getTableName()),arrSQLTerms[i],2);
                                        while(!records1.isEmpty()){
                                            records.add(records1.remove(0));
                                        }
                                        break;
                                    case ">=":
                                        records1 = linearSearchTable(DeserializeTable(v.getTableName()),arrSQLTerms[i],3);
                                        while(!records1.isEmpty()){
                                            records.add(records1.remove(0));
                                        }
                                        break;
                                    case "<":
                                        records1 = linearSearchTable(DeserializeTable(v.getTableName()),arrSQLTerms[i],4);
                                        while(!records1.isEmpty()){
                                            records.add(records1.remove(0));
                                        }
                                        break;
                                    case ">":
                                        records1 = linearSearchTable(DeserializeTable(v.getTableName()),arrSQLTerms[i],5);
                                        while(!records1.isEmpty()){
                                            records.add(records1.remove(0));
                                        }
                                        ;
                                        break;
                                }
                                vectors[i] = records;
                                break;
                            }

                        }


                        // checks on Indexed Only
                        for (int i = 0; i < arrSQLTerms.length; i++) {
                            Vector<Record> records = new Vector<>();
                            if (vectors[i] == null && !arrSQLTerms[i].equals(getPrimaryKey(v.getTableName())) && indexedColumns.contains(arrSQLTerms[i]._strColumnName)) {
                                String treeName = getTreeName(columnNameReader(v.getTableName()), arrSQLTerms[i]._strColumnName);
                                BTree tree = DeserializeTree(treeName, v.getTableName());

                                String s = (String) tree.search((Comparable) arrSQLTerms[i]._objValue);
                                BTree tmp = new BTree<>(v.getTableName(), tree.getColumnName(), tree.getTreeName());
                                while (s != null) {
                                    int x = 0;
                                    switch (arrSQLTerms[i]._strOperator) {
                                        case "=":
                                            x = 1;
                                            break;
                                        case "<=":
                                            x = 2;
                                            break;
                                        case ">=":
                                            x = 3;
                                            break;
                                        case "<":
                                            x = 4;
                                            break;
                                        case ">":
                                            x = 5;
                                            break;
                                    }
                                    Page page = DeserializePage(getPageName(s), v.getTableName());
                                    Vector<Record> records1 = linearSearch(page, arrSQLTerms[i], x);
                                    while (records1.size() > 0) {
                                        records.add(records1.remove(0));
                                    }

                                    tmp.insert((Comparable) arrSQLTerms[i]._objValue, s);
                                    s = (String) tree.search((Comparable) arrSQLTerms[i]._objValue);
                                }

                                while (tmp.getSize() > 0) {
                                    tree.insert(strarrOperators[i], tmp.search(strarrOperators[i]));
                                    tmp.delete(strarrOperators[i]);
                                }

                            }

                        }


                        for(int i = 0;i < arrSQLTerms.length;i++) {
                            if(vectors[i] == null)
                                vectors[i] = linearSearchTable2(DeserializeTable(v.getTableName()), arrSQLTerms[i]);

                        }
                        Vector<Record> records = Solver(vectors, strarrOperators);
                        for(int i=0;i<records.size();i++){
                                ((Table3ashankhaterSeif) table).addRecord(records.get(i));
                        }
                        return table;


                }
            }
        if (flag){
            table = (Iterator) Solver(vectors,strarrOperators);
            System.out.println("done");
            return table;

        }
        else{
            throw new DBAppException("Table not found.");
        }
    }

    public static Vector<Record> linearSearch(Page page, SQLTerm term, int x){
        Vector<Record> records = new Vector<>();
        for(Record record : page.getTuples()){
            Object obj = record.getHm().get(term._strColumnName);
            switch (x){
                case 1:
                    if(((Comparable)obj).compareTo(term._objValue) == 0){
                        records.add(record);
                    }
                    ;break;
                case 2:
                    if(((Comparable)obj).compareTo(term._objValue) <= 0){
                        records.add(record);
                    }
                    ;break;
                case 3:
                    if(((Comparable)obj).compareTo(term._objValue) >= 0){
                        records.add(record);
                    }
                    ;break;
                case 4:
                    if(((Comparable)obj).compareTo(term._objValue) < 0){
                        records.add(record);
                    }
                    ;break;
                case 5:
                    if(((Comparable)obj).compareTo(term._objValue) > 0){
                        records.add(record);
                    }
                    ;break;
            }
        }
        return records;
    }

    public boolean checkValidity2(Object value, SQLTerm[] arrSQLTerms, int i){
        if (value instanceof Integer || value instanceof Double){
            if (arrSQLTerms[i]._objValue instanceof Integer || arrSQLTerms[i]._objValue instanceof Double){
                return true;
            }
            else{
                return false;
            }
        }
        else{
            if (arrSQLTerms[i]._objValue instanceof Integer || arrSQLTerms[i]._objValue instanceof Double){
                return false;
            }
            else{
                return true;
            }
        }
    }

    public static Vector<Record>[] removeFromArray(Vector<Record>[] array, int tbr){
        Vector<Record>[] newobj = new Vector[array.length];
        for(int i = 0; i < array.length; i++){
            if(i!=tbr){
                newobj[i] = array[i];
            }
        }
//        Vector<Record>[] newnewobj = new Vector[array.length-1];
//        for(int i = 0; i < array.length-1; i++){
//            if(!(newobj[i]==null)){
//                newnewobj[i] = newobj[i];
//            }
//        }
        return newobj;
    }
 public static String[] removeFromArray2(String[] array, int tbr){
     String[] newobj = new String[array.length];
        for(int i = 0; i < array.length; i++){
            if(i!=tbr){
                newobj[i] = array[i];
            }
        }
//     String[] newnewobj = new String[array.length-1];
//        for(int i = 0; i < array.length-1; i++){
//            if(!(newobj[i]==null)){
//                newnewobj[i] = newobj[i];
//            }
//        }
        return newobj;
    }



    public static Vector<Record> Solver (Vector<Record>[] operands, String[] op) throws DBAppException {

    if((operands.length-op.length)!=1){
        throw new DBAppException("Difference should be 1");
    }
    Vector<Vector<Record>> vecquick = new Vector<>();
    for(int i =0; i<operands.length;i++){
        vecquick.add(operands[i]);
    }
    for (int i = 0; i < op.length; i++) {
        Vector<Record> LastmanStanding = new Vector<>();
        if(!(op[i]==null)&&op[i].equals("AND")){

            Vector<Record> bf = vecquick.get(i);
            Vector<Record> af = vecquick.get(i+1);
            int length = Math.min(af.size(), bf.size());

            for(int j = 0; j < length; j++){


                if(bf.size()==length){
                for(int k =0; k < af.size(); k++){
                    if ((vecquick.get(i).get(j).getHm().get(getPrimaryKey(operands[0].get(0).getTableName())))
                            .equals(vecquick.get(i+1).get(k).getHm().get(getPrimaryKey(operands[0].get(0).getTableName())))) {
                        LastmanStanding.add(vecquick.get(i).get(j));
                    }
                }
                }else {
                    for (int k = 0; k < bf.size(); k++) {
                        if ((operands[i + 1].get(j).getHm().get(getPrimaryKey(operands[0].get(j).getTableName())))
                                .equals(vecquick.get(i).get(k).getHm().get(getPrimaryKey(operands[0].get(j).getTableName())))) {

                            LastmanStanding.add(vecquick.get(i+1).get(j));
                        }
                    }
                }
            }
            operands[i] = LastmanStanding;

            op = removeFromArray2(op,i);
        }

    }
        boolean empty = true;
        for (Object ob : op) {
            if (ob != null) {
                empty = false;
                break;
            }
        }
            if (empty) {return operands[0];}
        for (int i = 0; i < op.length; i++) {
            if(!(op[i]==null)&&op[i].equals("OR")){

                Vector<Record> bf = vecquick.get(i);
                Vector<Record> af = vecquick.get(i+1);
                bf.addAll(af);
                LinkedHashSet<Record> hashSet = new LinkedHashSet<Record>(bf);
                bf.clear();
                bf.addAll(hashSet);




                op =  removeFromArray2(op,i);
            }

        }
        empty = true;
        for (Object ob : op) {
            if (ob != null) {
                empty = false;
                break;
            }
        }
        if (empty) {return operands[0];}
        for (int i = 0; i < op.length; i++) {
            Vector<Record> LastmanStanding = new Vector<>();
            if(!(op[i]==null)&&op[i].equals("XOR")){

                Vector<Record> bf = vecquick.get(i);
                Vector<Record> af = vecquick.get(i+1);
                int length = Math.min(af.size(), bf.size());

                for(int j = 0; j < length; j++){


                    if(bf.size()==length){
                        for(int k =0; k < af.size(); k++){
                            if ((vecquick.get(i).get(j).getHm().get(getPrimaryKey(operands[0].get(0).getTableName())))
                                    .equals(vecquick.get(i+1).get(k).getHm().get(getPrimaryKey(operands[0].get(0).getTableName())))) {
                                LastmanStanding.add(vecquick.get(i).get(j));
                            }
                        }
                    }else {
                        for (int k = 0; k < bf.size(); k++) {
                            if ((operands[i + 1].get(j).getHm().get(getPrimaryKey(operands[0].get(j).getTableName())))
                                    .equals(vecquick.get(i).get(k).getHm().get(getPrimaryKey(operands[0].get(j).getTableName())))) {

                                LastmanStanding.add(vecquick.get(i+1).get(j));
                            }
                        }
                    }
                }
                      Vector<Record> r =  vecquick.get(i);
                      r.removeAll(LastmanStanding);
                      operands[i] = r;
                      vecquick.set(i,r);
                      op = removeFromArray2(op,i);
            }

        }
        return operands[0];
    }
    public static Vector<Record> linearSearchTable(Table table, SQLTerm term, int x) throws IOException, ClassNotFoundException {
        Vector<Record> records = new Vector<>();
        for(String pageName : table.getPageNames()) {
            Page page = DeserializePage(pageName,table.getTableName());
            for (Record record : page.getTuples()) {
                Object obj = record.getHm().get(term._strColumnName);
                String y= term._strOperator;
                //TODO: replace x with y and remove the number from method signature
                switch (x) {
                    case 1:
                        if (((Comparable) obj).compareTo(term._objValue) == 0) {
                            if(!records.contains(record))
                                records.add(record);
                        }
                        ;
                        break;
                    case 2:
                        if (((Comparable) obj).compareTo(term._objValue) <= 0) {
                            if(!records.contains(record))
                                records.add(record);
                        }
                        ;
                        break;
                    case 3:
                        if (((Comparable) obj).compareTo(term._objValue) >= 0) {
                            if(!records.contains(record))
                                records.add(record);
                        }
                        ;
                        break;
                    case 4:
                        if (((Comparable) obj).compareTo(term._objValue) < 0) {
                            if(!records.contains(record))
                                records.add(record);
                        }
                        ;
                        break;
                    case 5:
                        if (((Comparable) obj).compareTo(term._objValue) > 0) {
                            if(!records.contains(record))
                                records.add(record);
                        }
                        ;
                        break;
                }
            }
        }
        return records;
    }
    public static Vector<Record> linearSearchTable2(Table table, SQLTerm term) throws IOException, ClassNotFoundException {
        Vector<Record> records = new Vector<>();
        for(String pageName : table.getPageNames()) {
            Page page = DeserializePage(pageName,table.getTableName());
            for (Record record : page.getTuples()) {
                Object obj = record.getHm().get(term._strColumnName);
                String y= term._strOperator;
                //TODO: replace x with y and remove the number from method signature
                switch (y) {
                    case "=":
                        if (((Comparable) obj).compareTo(term._objValue) == 0) {
                            if(!records.contains(record))
                                records.add(record);
                        }
                        ;
                        break;
                    case "<=":
                        if (((Comparable) obj).compareTo(term._objValue) <= 0) {
                            if(!records.contains(record))
                                records.add(record);
                        }
                        ;
                        break;
                    case ">=":
                        if (((Comparable) obj).compareTo(term._objValue) >= 0) {
                            if(!records.contains(record))
                                records.add(record);
                        }
                        ;
                        break;
                    case "<":
                        if (((Comparable) obj).compareTo(term._objValue) < 0) {
                            if(!records.contains(record))
                                records.add(record);
                        }
                        ;
                        break;
                    case ">":
                        if (((Comparable) obj).compareTo(term._objValue) > 0) {
                            if(!records.contains(record))
                                records.add(record);
                        }
                        ;
                        break;
                }
            }
        }
        return records;
    }

public static void main(String[] args) throws IOException, ClassNotFoundException, DBAppException {
        DBApp dbApp = new DBApp();
        SQLTerm[] arrSQLTerms;
        arrSQLTerms = new SQLTerm[3];
        SQLTerm sql = new SQLTerm();
        SQLTerm sql2 = new SQLTerm();
        SQLTerm sql3 = new SQLTerm();
        arrSQLTerms[0] = sql;
        arrSQLTerms[1] = sql2;
        arrSQLTerms[2] = sql3;
        arrSQLTerms[0]._strTableName =  "Student";
        arrSQLTerms[0]._strColumnName=  "gpa";
        arrSQLTerms[0]._strOperator  =  "<";
        arrSQLTerms[0]._objValue     =   77.6;

        arrSQLTerms[1]._strTableName =  "Student";
        arrSQLTerms[1]._strColumnName=  "id";
        arrSQLTerms[1]._strOperator  =  ">";
        arrSQLTerms[1]._objValue     =   70 ;

        arrSQLTerms[2]._strTableName =  "Student";
        arrSQLTerms[2]._strColumnName=  "gpa";
        arrSQLTerms[2]._strOperator  =  "=";
        arrSQLTerms[2]._objValue     =   1.6 ;

        awel3alanedeef thi = new awel3alanedeef();

        String[] strarrOperators = new String[2];
        strarrOperators[0] = "AND";
        strarrOperators[1] = "OR";
        Iterator resultSet = thi.selectFromTable(arrSQLTerms , strarrOperators);
        System.out.println(resultSet.toString());

}

}
