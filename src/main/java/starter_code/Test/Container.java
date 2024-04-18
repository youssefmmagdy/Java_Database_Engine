package starter_code.Test;

import java.io.*;
import java.util.*;

public class Container implements Serializable{

    List<String> filenames = new LinkedList<String>();
    public void listFilesForFolder(final File folder) {
        for (final File fileEntry : folder.listFiles()) {
                if(fileEntry.getName().contains(".ser"))
                    filenames.add(fileEntry.getName());
            }
    }

    public static void main(String[] args){
        Container c= new Container();
        final File folder = new File("/Database_Engine/");
        c.listFilesForFolder(folder);
        System.out.print(c.filenames.get(0));

    }
}
