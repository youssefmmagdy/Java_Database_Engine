package starter_code.Main;

import java.io.IOException;
import java.io.Serializable;
import java.util.Hashtable;
import java.util.Vector;

public class Page implements Serializable{

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
        Record r = new Record(name);
        r.add(ht);
        tuples.add(r);
    }

    public Vector<Record> getTuples(){
        return tuples;
    }

    public Object getMax() {
        return max;
    }

    public void setMax(Object max) {
        this.max = max;
    }

    public Object getMin() {
        return min;
    }

    public void setMin(Object min) {
        this.min = min;
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