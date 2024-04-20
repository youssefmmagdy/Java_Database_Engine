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
import static starter_code.Serialization.Deserialize.DeserializePage;
import static starter_code.Serialization.Deserialize.DeserializeTree;

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
            Table deserializedTable = Deserialize.DeserializeTable(arrSQLTerms[0]._strTableName);
            if (deserializedTable.getPageNames().isEmpty())
                throw new DBAppException("Table is Empty");
        }
        boolean flag = false;
        boolean XORflag = true;
        boolean ANDflag = true;
        boolean ORflag = true;
        boolean XORExists = false;
        //Stack<SQLTerm[]> s = new Stack<>();
        for (int k = 0; k<strarrOperators.length;k++){
            if (strarrOperators[k].equalsIgnoreCase("AND")){
                ANDflag = false;
            }
        }
        for (int k = 0; k<strarrOperators.length;k++){
            if (strarrOperators[k].equalsIgnoreCase("XOR")){
                XORflag = false;
                XORExists = true;
            }
        }
        for (int k = 0; k<strarrOperators.length;k++){
            if (strarrOperators[k].equalsIgnoreCase("OR")){
                ORflag = false;
            }
        }
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
                } else {

                    Vector<String> indexedColumns = getIndexedColumns(columnNameReader(v.getTableName()));

                    boolean flag1 = false, flag2 = false;
                    // checks on Primary Keys & Indexed

                    for (int i = 0; i < arrSQLTerms.length; i++) {
                        flag1 = true;
                        Vector<Record> records = new Vector<>();

                        if (arrSQLTerms[i]._strColumnName.equals(getPrimaryKey(v.getTableName())) && indexedColumns.contains(arrSQLTerms[i]._strColumnName)) {
                            String treeName = getTreeName(columnNameReader(v.getTableName()), arrSQLTerms[i]._strColumnName);
                            BTree tree = DeserializeTree(treeName, v.getTableName());

                            switch (arrSQLTerms[i]._strOperator) {
                                case "=":
                                    String s = (String) tree.search((Comparable) arrSQLTerms[i]._objValue);
                                    Page page = DeserializePage(getPageName(s), v.getTableName());
                                    Record record = binarySearchPage(v.getTableName(), page, arrSQLTerms[i]._objValue);
                                    records.add(record);
                                    break;
                                case "<=":
                                    break;
                                case ">=":
                                    break;
                                case "<":
                                    break;
                                case ">":
                                    break;
                            }

                            String s = (String) tree.search((Comparable) arrSQLTerms[i]._objValue);
                            Page page = DeserializePage(getPageName(s), v.getTableName());
                            Record record = binarySearchPage(v.getTableName(), page, arrSQLTerms[i]._objValue);
                            records.add(record);

                            vectors[i] = records;
                            break;
                        }


                    }
                    if (!flag1) {
                        // checks on Primary Keys (Not Indexed)
                        for (int i = 0; i < arrSQLTerms.length; i++) {
                            flag2 = true;
                            Vector<Record> records = new Vector<>();
                            if (arrSQLTerms[i]._strColumnName.equals(getPrimaryKey(v.getTableName())) && !indexedColumns.contains(arrSQLTerms[i]._strColumnName)) {


                                switch (arrSQLTerms[i]._strOperator) {
                                    case "=":
                                        Record record = binarySearch(v.getTableName(), arrSQLTerms[i]._objValue);
                                        records.add(record);
                                        ;
                                        break;
                                    case "<=":
                                        ;
                                        break;
                                    case ">=":
                                        ;
                                        break;
                                    case "<":
                                        ;
                                        break;
                                    case ">":
                                        ;
                                        break;
                                }
                                vectors[i] = records;
                                break;
                            }

                        }
                    } else if (!flag1 && !flag2) {
                        // checks on Indexed Only
                        for (int i = 0; i < arrSQLTerms.length; i++) {
                            Vector<Record> records = new Vector<>();
                            if (!arrSQLTerms[i].equals(getPrimaryKey(v.getTableName())) && indexedColumns.contains(arrSQLTerms[i]._strColumnName)) {
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
                            vectors[i] = records;
                        }
                    } else {
//						vectors[i] = all records
                    }
                }
            }
        if (flag){
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

    public static Object[] removeFromArray(Object[] array, int tbr){
        Object[] newobj = new Object[array.length-1];
        for(int i = 0; i < array.length-1; i++){
            if(i!=tbr){
                newobj[i] = array[i];
            }
        }
        return newobj;
    }

    public Vector<String> Solver (Vector<String>[] operands, String[] op) throws DBAppException {

    if((operands.length-op.length)!=1){
        throw new DBAppException("Difference should be 1");
    }
    for (int i = 0; i < op.length; i++) {

        if(op[i].equals("AND")){
            Vector<String> bf = operands[i];
            Vector<String> af = operands[i+1];
            int length = Math.min(af.size(), bf.size());
            for(int j = 0; j < length; j++){
                for(int k =0; k < af.size(); k++){

                }
            }
        }

    }
        return null;
    }


}
