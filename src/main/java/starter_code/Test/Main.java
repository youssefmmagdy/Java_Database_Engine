package starter_code.Test;

import java.io.*;
import java.util.*;

public class Main{
    private static boolean isDataTypeMatching2(Object value, String colType) {
        String valueType = value.getClass().getName();
        return colType.equals(valueType);
    }
    public static void main(String[] args) throws IOException{


    }

}