package starter_code.Serialization;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Serialize {

    // x is the Object Name (Table | Page | BPlusTree)
    // y is the Object itself
    public static void Serializethis(String x, Object y, String tableName) throws IOException {
        FileOutputStream fileOut = new FileOutputStream("starter_code/"+tableName+"/"+x+".class");
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(y);
        out.close();
        fileOut.close();
     }

    public static void main(String [] args) throws IOException {





        }
    }
