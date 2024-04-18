import starter_code.Exception.DBAppException;
import starter_code.Main.DBApp;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;

public class myVisitor extends SQLBaseVisitor{

    Vector<String> names = new Vector<String>();
    Vector<String> types = new Vector<String>();
    String tableName;
    String primaryKey;
    String indexName;
    Vector<String> operators = new Vector<>();
    Vector<Object> values = new Vector<>();
    Vector<String> between = new Vector<>();
    Vector<Object> updateValues = new Vector<>();
    Vector<Hashtable<String, Object>> tbi = new Vector<>();
    String s;


    public Object visitName(SQLParser.NameContext ctx) {
        tableName= stringActuate(ctx.getText());
        return visitChildren(ctx);
    }
    public Object visitAttrname(SQLParser.AttrnameContext ctx) {
        if(names.size()==0){primaryKey = ctx.getText();}
        names.add(stringActuate(ctx.getText()));
        return visitChildren(ctx);
    }
    public Object visitIndexname(SQLParser.IndexnameContext ctx) {
        indexName= stringActuate(ctx.getText());

        return visitChildren(ctx);
    }

    public Object visitValue(SQLParser.ValueContext ctx) {
            s=ctx.getText();
            Object value = ctx.getText();
            if (mayParse((String) value) != null) {
                value = mayParse((String) value);
            } else if (mayParse2((String) value) != null) {
                value = mayParse2((String) value);
            }else{
                value = stringActuate(ctx.getText());
            }
            values.add(value);

        return visitChildren(ctx);
    }
    public Object visitUpdatevalue(SQLParser.UpdatevalueContext ctx) {
        for (int i=0; i<ctx.getChildCount(); i++) {
            Object value = ctx.getChild(i).getText();
            if (mayParse((String) value) != null) {
                value = mayParse((String) value);
            } else if (mayParse2((String) value) != null) {
                value = mayParse2((String) value);
            }else{
                value = stringActuate(ctx.getChild(i).getText());
            }
            updateValues.add(value);
        }
        return visitChildren(ctx);
    }
    public Object visitCattrname(SQLParser.CattrnameContext ctx) {
        primaryKey = stringActuate(ctx.getText());
        return visitChildren(ctx);
    }
    public Object visitType(SQLParser.TypeContext ctx) {
            if(ctx.getText().equals("INT")) {types.add("java.lang.Integer");}
            if(ctx.getText().equals("STRING")) {types.add("java.lang.String");}
            if(ctx.getText().equals("DOUBLE")) {types.add("java.lang.Double");}
        return visitChildren(ctx);
    }
    public Object visitWrapper(SQLParser.WrapperContext ctx) {
        if((ctx.getChildCount()/2+1) != names.size()){
            throw new RuntimeException(ctx.getText() + " does not match " + names);
        }
        Hashtable<String, Object> inserted = new Hashtable<>();
        Vector<Object> vec = new Vector<>();
        for(int i = 0; i < ctx.getChildCount(); i+=2){
                Object value = ctx.getChild(i).getText();
                if (mayParse((String) value) != null) {
                    value = mayParse((String) value);
                } else if (mayParse2((String) value) != null) {
                    value = mayParse2((String) value);
                }else{
                    value = stringActuate(ctx.getChild(i).getText());
                }
                vec.add(value);

        }
        for(int i = 0; i < vec.size(); i++){
                inserted.put(names.get(i), vec.get(i));
        }
        tbi.add(inserted);
       return visitChildren(ctx); 
    }
    public Object visitOper(SQLParser.OperContext ctx) {
        for (Object val : ctx.children) {
            operators.add(val.toString());
        }
        return visitChildren(ctx);
    }
    public Object visitInbetweenoperand(SQLParser.InbetweenoperandContext ctx) {
        for (Object val : ctx.children) {
            between.add(val.toString());
        }
        return visitChildren(ctx);
    }
    public Object visitClosercreate(SQLParser.ClosercreateContext ctx) {
        Hashtable<String,String> tbu = new Hashtable<String,String>();
        for(int i = 0; i < names.size(); i++){
            if(i==0){
                primaryKey= names.get(i);
            }
            tbu.put(names.get(i), types.get(i));
        }

        DBApp db = null;
        try {
            db = new DBApp();
            db.createTable(tableName, primaryKey, tbu);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (DBAppException e) {
            throw new RuntimeException(e);
        }


        return visitChildren(ctx);
    }
    public Object visitCloserindex(SQLParser.CloserindexContext ctx) {



        DBApp db = null;
        try {
            db = new DBApp();
            db.createIndex(tableName, names.getFirst(), indexName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (DBAppException e) {
            throw new RuntimeException(e);
        }




        return visitChildren(ctx);
    }
    public Object visitCloserinsert(SQLParser.CloserinsertContext ctx) {


        for(Hashtable<String, Object> ht : tbi){
            DBApp db = null;
            try {
                db = new DBApp();
                db.insertIntoTable(tableName, ht);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (DBAppException e) {
                throw new RuntimeException(e);
            }

        }
        return visitChildren(ctx);
    }
    public Object visitCloserupdate(SQLParser.CloserupdateContext ctx) {
        Hashtable<String, Object> updated = new Hashtable<>();
        for(int i = 0; i < names.size(); i++){
            updated.put(names.get(i), updateValues.get(i));
        }

        DBApp db = null;
        try {
            db = new DBApp();
            db.updateTable(tableName, s, updated);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (DBAppException e) {
            throw new RuntimeException(e);
        }

        return visitChildren(ctx);
    }
    public Object visitCloserdelete(SQLParser.CloserdeleteContext ctx) {
        Hashtable<String, Object> deleted = new Hashtable<>();
        for(int i = 0; i < names.size(); i++){
            deleted.put(names.get(i), updateValues.get(i));
        }


        DBApp db = null;
        try {
            db = new DBApp();
            db.deleteFromTable(tableName, deleted);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (DBAppException e) {
            throw new RuntimeException(e);
        }

        return visitChildren(ctx);
    }
    public Object visitCloserselect(SQLParser.CloserselectContext ctx) {
        System.out.println(names);
        System.out.println(values);
        System.out.println(operators);
        System.out.println(between);
//selectFromTable(SQLTerm[] arrSQLTerms, between)

//        DBApp db = null;
//        try {
//            db = new DBApp();
//            db.selectFromTable(SQLTerm[] arrSQLTerms, between);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        } catch (DBAppException e) {
//            throw new RuntimeException(e);
//        }
        return visitChildren(ctx);
    }
    public static String stringActuate(String str) {
        return  str.substring(1, str.length() - 1);
    }
    public static Integer mayParse(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return null;
        }
    }
    public static Double mayParse2(String str) {
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}

