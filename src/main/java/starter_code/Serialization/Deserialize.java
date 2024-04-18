package starter_code.Serialization;

import starter_code.BTree.BTree;
import starter_code.Main.Page;
import starter_code.Main.Table;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Deserialize {

    public static Table DeserializeTable(String x) throws IOException, ClassNotFoundException {
        FileInputStream fileIn = new FileInputStream("starter_code/"+x+"/"+x+".class");
        ObjectInputStream in = new ObjectInputStream(fileIn);
        Table i = (Table) in.readObject();
        in.close();fileIn.close();
        return i;
    }

    public static Page DeserializePage(String pageName , String tableName) throws IOException, ClassNotFoundException {
        FileInputStream fileIn = new FileInputStream("starter_code/"+tableName+"/"+pageName+".class");
        ObjectInputStream in = new ObjectInputStream(fileIn);
        Page i = (Page) in.readObject();
        in.close();fileIn.close();
        return i;
    }

    public static BTree DeserializeTree(String treeName,String tableName) throws IOException, ClassNotFoundException {
        FileInputStream fileIn = new FileInputStream("starter_code/"+tableName+"/"+treeName+".class");
        ObjectInputStream in = new ObjectInputStream(fileIn);
        BTree i = (BTree) in.readObject();
        in.close();fileIn.close();
        return i;
    }



    public static void main(String [] args) throws IOException, ClassNotFoundException {
        Page e = null;
        FileInputStream fileIn = new FileInputStream("Page.class");
        ObjectInputStream in = new ObjectInputStream(fileIn);
        e = (Page) in.readObject();
        in.close();fileIn.close();
        System.out.println(e.getName());


    }
}