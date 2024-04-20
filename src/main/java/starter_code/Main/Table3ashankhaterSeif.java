package starter_code.Main;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

public class Table3ashankhaterSeif implements Iterator {
    Vector<Hashtable<String, Object>> record = new Vector<>();
    public Table3ashankhaterSeif() {

    }

    @Override
    public String toString() {
        StringBuffer str = new StringBuffer();
        for(Hashtable<String, Object> ht : record) {
            str.append(ht).append(' ');
        }
        return  str.toString();
    }

    public Vector<Hashtable<String, Object>> getRecord() {
        return record;
    }

    public void addRecord(Hashtable<String, Object> htrecord) {
        this.record.add(htrecord);
    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public Object next() {
        return null;
    }
}
