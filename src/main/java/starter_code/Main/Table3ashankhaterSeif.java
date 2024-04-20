package starter_code.Main;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

public class Table3ashankhaterSeif implements Iterator {
    Vector<Record> record = new Vector<>();
    public Table3ashankhaterSeif() {

    }

    public void setRecord(Vector<Record> record) {
        this.record = record;
    }

    @Override
    public String toString() {
        StringBuffer str = new StringBuffer();
        for(Record r : record) {
            str.append(r.toString()).append(' ');
        }
        return  str.toString();
    }

    public Vector<Record> getRecord() {
        return record;
    }

    public void addRecord(Record htrecord) {
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
