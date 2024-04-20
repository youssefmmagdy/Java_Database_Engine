package starter_code.Main;

import java.io.IOException;
import java.io.Serializable;
import java.util.Hashtable;
import java.util.Vector;


public class Page implements Serializable{
    private static final long serialVersionUID = -558553967080514790L;
    private String tableName;
    private String name;
    private Vector<Record> tuples;
    private Object max,min;
    private boolean isNullbefore;
    private int pageNumber;
    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }


    public boolean isNullbefore() {
        return isNullbefore;
    }

    public void setNullbefore(boolean nullbefore) {
        isNullbefore = nullbefore;
    }

    public Page(String tableName, String x) {
        name = x;
        tuples = new Vector<>();
        this.tableName = tableName;

        StringBuilder r = new StringBuilder();
        for(int i = tableName.length();i<name.length();i++){
            r.append(name.charAt(i));
        }
        pageNumber = Integer.parseInt(""+r);
    }

    public String toString(){
        return "" + getTuples();
    }

    public String getName() {
        return name;
    }
    public void add(Hashtable<String, Object> ht) throws IOException, ClassNotFoundException {
        Record r = new Record(name, tableName);
        r.add(ht);
        tuples.add(r);
    }

    public Vector<Record> getTuples(){
        return tuples;
    }

    public Object getMax() {
        return max;
    }


    /**
     @param max: puts the max clustering key in the page
     * **/
    public void setMax(Object max) {
        if(this.max == null)
            this.max = max;
        if(max instanceof Integer){
            Integer x = (Integer)max;
            this.max = Math.max(x,(Integer) this.max);
        }
        if(max instanceof Double){
            Double x = (Double) max;
            this.max = Math.max(x,(Double) this.max);
        }
        if(max instanceof String){
            String x = (String)max;
            this.max = x.compareTo((String) this.max) > 0 ? x : this.max;
        }
    }

    public Object getMin() {
        return min;
    }
    /**
     @param min: puts the min clustering key in the page
      * **/
    public void setMin(Object min) {
        if(this.min == null)
            this.min = min;
        if(min instanceof Integer){
            Integer x = (Integer)min;
            this.min = Math.min(x,(Integer) this.min);
        }
        if(min instanceof Double){
            Double x = (Double) min;
            this.min = Math.min(x,(Double) this.min);
        }
        if(min instanceof String){
            String x = (String)min;
            this.min = x.compareTo((String) this.min) < 0 ? x : this.min;
        }


    }

    public int getNumberOfRows(){
        int n=0;
        for(Record r : tuples){
            if(r.isNull){
                n++;
            };
        }

        return tuples.size()-n;

    }
    public int getNumberofRowsraw(){
        return tuples.size();
    }


    public boolean isEmpty() {
        for(Record record : getTuples()){
            if(!record.isNull){
                return false;
            }
        }
        return true;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }


}