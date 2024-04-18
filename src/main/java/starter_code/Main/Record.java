package starter_code.Main;

import java.io.Serializable;
import java.util.*;

import static starter_code.Main.DBApp.getPrimaryKey;

public class Record implements Serializable,Comparable<Record> {
    Hashtable<String, Object> hm;
    String tableName;
    String pageName;
    boolean isNull;

    public Record (String pageName){
        this.pageName = pageName;
        hm = new Hashtable<>();
        isNull=false;
    }
    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }


    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }




    public void add (Hashtable<String, Object> ht){
        hm = ht;
    }

    public Object getHm(String x) {
        return hm.get(x);
    }

    public Hashtable<String, Object> getHm() {
        return hm;
    }

    public void setHm(Hashtable<String, Object> hm) {
        this.hm = hm;
    }

    @Override
    public String toString() {
        return "" + getHm()+(isNull ? ".IsNull" : "");
    }


    public static void main (String[] args){

    }

    @Override
    public int compareTo(Record record) {
        String pk = getPrimaryKey(tableName);
        Object val1 = "";

        for (Map.Entry<String, Object> entry : record.getHm().entrySet())
            if (entry.getKey().equals(pk)) {
                val1 = entry.getValue();
                break;
            }

        for (Map.Entry<String, Object> entry : this.getHm().entrySet()) {
            if (entry.getKey().equals(pk)) {
                if (entry.getValue() instanceof Comparable) {
                    return ((Comparable) entry.getValue()).compareTo(val1);
                }

//                if (entry.getValue() instanceof String) {
//                    return ((String) entry.getValue()).compareTo((String) val1);
//                }
//                if(entry.getValue() instanceof Integer){
//                    return ((Integer) entry.getValue()).compareTo((Integer) val1);
//                }
//                if(entry.getValue() instanceof Double){
//                    return ((Double) entry.getValue()).compareTo((Double)val1);
//                }
            }
        }
        return -1;
    }

    public void setNull(){
        isNull = true;
        //hm = null;
    }
    public void unsetNull(){
        isNull = false;
    }
}