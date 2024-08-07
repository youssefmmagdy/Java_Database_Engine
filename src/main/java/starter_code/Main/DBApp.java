package starter_code.Main;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import starter_code.BTree.BTree;
import starter_code.Exception.DBAppException;
import starter_code.Main.starter_code.Main.SQLLexer;
import starter_code.Main.starter_code.Main.SQLParser;
import starter_code.Serialization.Deserialize;
import starter_code.Serialization.Serialize;

import javax.xml.crypto.dsig.dom.DOMValidateContext;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static org.antlr.v4.runtime.CharStreams.fromFileName;
import static org.antlr.v4.runtime.CharStreams.fromString;
import static starter_code.Main.Table.*;
import static starter_code.Main.Table.getPageName;
import static starter_code.Serialization.Deserialize.*;
import static starter_code.Serialization.Deserialize.DeserializeTree;


/** * @author Wael Abouelsaadat */


public class DBApp {
	private Vector<Table> tables;

	public DBApp( ) throws IOException, ClassNotFoundException {
		init();
		tables = new Vector<Table>();
		for(String s : getTableNames("metadata.csv")){
			File f = new File("starter_code/"+s+"/"+s+".class");
			if(f.exists()&& !f.isDirectory()){
				Table t = DeserializeTable(s);
				tables.add(t);
				Serialize.Serializethis(s, t, s);}
		}
	}
	public static List<String> getTableNames(String x) throws IOException {
		List<String> tn = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(x))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] values = line.split(",");
				if (values.length > 0) {
					tn.add(values[0]);
				}
			}
		}
		return new ArrayList<>(new HashSet<>(tn));
	}

	// this does whatever initialization you would like
	// or leave it empty if there is no code you want to
	// execute at application startup

	public void init( ) throws IOException{

		File f = new File("metadata.csv");
		if(!f.exists() && !f.isDirectory()) {
			FileWriter fileWriter = new FileWriter("metadata.csv", true);
			StringBuilder lines = new StringBuilder();
			lines.append("Table Name"+','+ "Column Name"+','+ "Column Type"+','+ "ClusteringKey"+','+ "IndexName"+','+"IndexType"+'\n');
			fileWriter.write(lines.toString());
			fileWriter.close();
		}

	}


	// following method creates one table only
	// strClusteringKeyColumn is the name of the column that will be the primary
	// key and the clustering column as well. The data type of that column will
	// be passed in htblColNameType
	// htblColNameValue will have the column name as key and the data
	// type as value


/**
 @param htblColNameType: attributes of the table
 @param strTableName: Name of the table to be created
 @param strClusteringKeyColumn: Primary key of the table
 **/
	public void createTable(String strTableName,
							String strClusteringKeyColumn,
							Hashtable<String,String> htblColNameType) throws DBAppException {
		File f = new File("starter_code/"+strTableName);
		if(!f.exists()) {

			boolean fag = true;
			for (Map.Entry<String, String> entry : htblColNameType.entrySet()) {
				String value = entry.getValue();
				String key = entry.getKey();
				if (key.equals(strClusteringKeyColumn)) {fag = false;}

				if(!value.equals("java.lang.String")&&!(value.equals("java.lang.double")|value.equals("java.lang.Double"))&&!value.equals("java.lang.Integer")){
					throw new DBAppException(value + " is an invalid data type");
				}
			}
			if(fag){
				throw new DBAppException("No Clustering Key Inserted");
			}
			try{
			Table newt = new Table(strTableName);
				String folderPath = "starter_code/"+strTableName;
				File folder = new File(folderPath);
				folder.mkdirs();
				tables.add(newt);
				Serialize.Serializethis(strTableName, newt, strTableName);
				FileWriter fileWriter = new FileWriter("metadata.csv", true);
				StringBuilder line = new StringBuilder();
				line.append("\n");
				for (Map.Entry<String, String> entry : htblColNameType.entrySet()) {
					String key = entry.getKey();
					String value = entry.getValue();
					if (!key.equals(strClusteringKeyColumn)) {
						line.append(strTableName).append(',').append(key).append(',').append(value).append(',').append("False").append(',').append("null").append(',').append("null");
						line.append("\n");
					}else{
						line.append(strTableName).append(',').append(key).append(',').append(value).append(',').append("True").append(',').append("null").append(',').append("null");
						line.append("\n");
					}
				}
				fileWriter.write(line.toString());
				fileWriter.close();

		} catch (IOException e) {
			throw new DBAppException("Failed to create table");
		}
		}else{
			throw new DBAppException(strTableName + " Table Already Exists");
		}
	}

	/**
	 @param strTableName: Name of the table the index will be created on
	 @param strColName: Name of column the index will be made on
	 @param strIndexName: Name of the index to be created
	 **/
	// following method creates a B+tree index
	public void createIndex(String   strTableName,
							String   strColName,
							String   strIndexName) throws DBAppException {
		if(!searchMetadata(strTableName)){
			throw new DBAppException("Table Not Found");
		}
		File f1 = new File("starter_code/"+strTableName+"/"+strIndexName+".class");
		if(!f1.exists()) {
			String csvFile = "metadata.csv";
			try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
				String line;
				Vector<String[]> lines = new Vector<>();

				while ((line = reader.readLine()) != null)
					lines.add(line.split(","));

				File file = new File(csvFile);
				file.delete();

				FileWriter fileWriter = new FileWriter(csvFile);

				for (int i = 0; i < lines.size(); i++) {
					String[] values = lines.get(i);

					if (values[0].equals(strTableName) && values[1].equals(strColName)) {
						values[4] = strIndexName;
						values[5] = "B+tree";

						File f = new File(strTableName + strIndexName + ".class");
						if (!f.exists()) {
							Table deserializedTable = DeserializeTable(strTableName);

							BTree tree = new BTree( strTableName, strColName,strIndexName);

							if(!deserializedTable.getTreesNames().contains(strIndexName))
								deserializedTable.addTreeName(strIndexName);


							for (String pageName : deserializedTable.getPageNames()) {
								Page deserializedPage = DeserializePage(pageName,strTableName);
								Vector<Record> tuples = deserializedPage.getTuples();
								for (Record r : tuples) {
									for (Map.Entry<String, Object> entry : r.getHm().entrySet()) {
										if (entry.getKey().equals(strColName)) {
											tree.insert((Comparable) entry.getValue(), strTableName + " " + deserializedPage.getName());
										}
									}

								}

							}
							tree.print();

							Serialize.Serializethis(strIndexName, tree, strTableName);
							Serialize.Serializethis(strTableName, deserializedTable, strTableName);
						}
					}
					StringBuilder sb = new StringBuilder();
					for (int k = 0; k < values.length; k++) {
						if (k != values.length - 1)
							sb.append(values[k]).append(",");
					}
					sb.append(values[values.length - 1]);
					sb.append("\n");
					fileWriter.write(sb.toString());
				}
				fileWriter.close();
			}catch (IOException e) {
				throw new RuntimeException(e);
			} catch (ClassNotFoundException e) {
				throw new RuntimeException(e);
			}

		}else{
			throw new DBAppException(strIndexName + "Already Exists");
		}

	}


	//         following method inserts one row only.
//	 htblColNameValue must include a value for the primary key
	/**
	 @param Location: is the location the search will be on
	 **/
	public boolean fileExists(File Location){
		return (Location.exists() && !Location.isDirectory());
	}
	public boolean sizeCheck(int col1, int col2){
		return (col1 == col2);
	}
	/**
	 @param column:a vector that contains the data of the columns from the metadata
	 @param htblColNameValue: the input to be inserted from insertintotable method
	 **/
	public boolean tableDataTypeCheck(Vector column, Hashtable<String,Object> htblColNameValue){
		boolean Flag = true;
		for (int i = 0; i < column.size(); i++) {
			Vector E = (Vector) column.get(i);
			String x = (String) E.get(0);
			Object o = htblColNameValue.get(x);

			if (!isDataTypeMatching2(o, (String) E.get(1))) {
				Flag = false;
			}
		}
		return Flag;
	}


	/**
	 @param column:a vector that contains the data of the columns from the metadata
	 @return a string that contains the name of the clusteringkey
	 **/
	public static String clusteringKey_ID(Vector column){
		String clusteringKey = "";
		for (int i = 0; i < column.size(); i++) {
			Vector K = (Vector) column.get(i);
			if (K.get(4).equals("True")) {
				clusteringKey = (String) K.get(0);
			}
		}
		return clusteringKey;
	}


	/**
	 @param tab: refrence pointer to the table to be inserted in
	 @param column:a vector that contains the data of the columns from the metadata
	 @param strTableName: The name of the table to be inserted in
	 @param htblColNameValue the record to be inserted into table
	 **/
	public void addBPlussTree(Table tab, Vector column, String strTableName, String Key, Hashtable<String,Object> htblColNameValue) throws IOException, ClassNotFoundException {
		Vector<String> treeNames = tab.getTreesNames();
		for (int i = 0; i < column.size(); i++){
			Vector E = (Vector) column.get(i);
			String x= (String) E.get(0);
			if (E.contains("B+tree")){
				for (String treeName : treeNames) {
					BTree deserializedTree = Deserialize.DeserializeTree(treeName,strTableName);
					deserializedTree.print();
					if (deserializedTree.getColumnName().equals(x)) {
						Vector K = tab.getRecord(htblColNameValue,Key);
//						if(K != null){
						String place = strTableName + " " + strTableName + K.get(0);
						deserializedTree.insert((Comparable) htblColNameValue.get(x),place);
						deserializedTree.shiftRowsInTree();
//						}
					}
					Serialize.Serializethis(deserializedTree.getTreeName(),deserializedTree, strTableName);
				}
			}
		}
	}
	/**
	 @param column:
	 @param htblColNameValue:
	 @return if the name of the column name in insert methood matches the one of those in the metadata
	 **/
	public boolean comparecolumname(Vector column,Hashtable<String, Object> htblColNameValue){
		for (int i = 0; i < column.size(); i++) {
			Vector c= (Vector) column.get(i);
			if (!htblColNameValue.containsKey(c.get(0))){
				return false;
			}
		}
		return true;
	}
	/**
	 @param strTableName: Name of the table the index will be created on
	 @param htblColNameValue: The values to be inserted
	 **/

	public void insertIntoTable(String strTableName,
								Hashtable<String, Object> htblColNameValue) throws DBAppException {
		if (searchMetadata(strTableName)) {
			Vector column = columnNameReader(strTableName);
			File f = new File("starter_code/" + strTableName + "/" + strTableName + ".class");
			if (fileExists(f)) {
				if(comparecolumname(column,htblColNameValue)) {
					if (sizeCheck(htblColNameValue.size(), column.size())) {
						if (tableDataTypeCheck(column, htblColNameValue)) {
							String clusteringKey = clusteringKey_ID(column);
							if (!clusteringKey.isEmpty()) {
								Object Id = htblColNameValue.get(clusteringKey);
								try {
									if (binarySearch(strTableName, Id) == null) {
										Table tab = null;
										try {
											tab = DeserializeTable(strTableName);
										} catch (IOException | ClassNotFoundException e) {
											throw new DBAppException("Deserialized Table Not found");
										}
										try {
											tab.add(htblColNameValue);
										} catch (IOException | ClassNotFoundException e) {
											throw new DBAppException("Unable to add to table");
										}
										try {
											addBPlussTree(tab, column, strTableName, clusteringKey, htblColNameValue);
										} catch (IOException | ClassNotFoundException e) {
											throw new DBAppException("Unable to add to B+ Tree");
										}
									} else {
										throw new DBAppException("Duplicate insert Found");
									}
								} catch (IOException | ClassNotFoundException e) {
									throw new DBAppException("Failed to search for duplicate record");
								}
							} else {
								throw new DBAppException("No clustering key found");
							}
						} else {
							throw new DBAppException("Invalid Types");
						}
					} else {
						throw new DBAppException("Sizes do not match");
					}
				}else {
					throw new DBAppException("Mismatch in type found");
				}
			} else {
				throw new DBAppException("Invalid Input");
			}
		} else {
			throw new DBAppException("Table Not Found");
		}
	}

	/**
	 @param value: the object's type will be checked on
	@param colType: the expected type that value should be
	 **/
	private static boolean isDataTypeMatching2(Object value, String colType) {
		String valueType = value.getClass().getName();
		if (Objects.equals(colType, "java.lang.double") && valueType.equals("java.lang.Double")){
			return true;
		}else {
			return colType.equals(valueType);
		}
	}

	/**
	 @param strTableName: the table to be checked whether its in the metadata
	 **/
	//check wheather a given table name is in the csv file
	public static boolean searchMetadata(String strTableName) {
		String csvFile = "metadata.csv";
		try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] values = line.split(",");
				if (values[0].equals(strTableName)) {
					return true;
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return false;
	}

	/**
	 @param strTableName: table name
	 @return: the column names and data
	 **/
	//reads the collums from a given table name from csv
	public static Vector<Vector<String>> columnNameReader (String strTableName){
		String csvFile = "metadata.csv";

		Vector<Vector<String>> column = new Vector<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] values = line.split(",");
				if (values[0].equals(strTableName)) {

					Vector<String> add = new Vector<>();
					add.add(values[1]);
					add.add(values[2]);
					add.add(values[4]);
					add.add(values[5]);
					add.add(values[3]);
					column.add(add);

				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return column;
	}

	/**
	 	@param tableName: the name of the table
	 @param page: pointer to the page to be searched in
	 @param pk: the primary key
	 @return : the record that is the result of the primary key
	 **/
	public static Record binarySearchPage(String tableName, Page page, Object pk) throws IOException, ClassNotFoundException {
		int i = 0, j = page.getNumberofRowsraw();
		while(i <= j){
			int index = (i + j) / 2;
			if(!(page.getTuples().get(index).isNull)
					&& ((Comparable)pk).compareTo(page.getTuples().get(index).getHm().get(getPrimaryKey(tableName))) == 0){
				return page.getTuples().get(index);
			}else if(((Comparable)pk).compareTo(page.getTuples().get(index).getHm().get(getPrimaryKey(tableName))) > 0){
				i = index + 1;
			}else{
				j = index - 1;
			}
		}
		return null;
	}

	// following method 87s one row only
	// htblColNameValue holds the key and new value
	// htblColNameValue will not include clustering key as column name
	// strClusteringKeyValue is the value to look for to find the row to update.

	/**
	 @param strTableName: the name of the table
	 @param strClusteringKeyValue: the value of the primary key
	 @param htblColNameValue: the updated record to be used to modify the original record
	 **/
	public void updateTable(String strTableName,
							String strClusteringKeyValue,
							Hashtable<String,Object> htblColNameValue) throws DBAppException, IOException, ClassNotFoundException {

		String dataType = null;

		try {
			int intValue = Integer.parseInt(strClusteringKeyValue);
			dataType = "integer";
		} catch (NumberFormatException e) {
			// Not an integer
		}

		if (dataType == null) {
			try {
				double doubleValue = Double.parseDouble(strClusteringKeyValue);
				dataType = "double";
			} catch (NumberFormatException e) {
				// Not a double either
			}
		}

		if (dataType == null) {
			dataType = "string";  // It's a string if it's not an integer or double
		}
		if (searchMetadata(strTableName)) {
			Table table = DeserializeTable(strTableName);
			if(table.getPageNames().isEmpty())
				throw new DBAppException("Table is Empty");
			Vector<Vector<String>> column = columnNameReader(strTableName);

			Vector<String> indexedColumns =  Table.getIndexedColumns(columnNameReader(strTableName));
			Record record;
			if (indexedColumns.contains(getPrimaryKey(strTableName))) {
				String clusteringKey = getPrimaryKey(strTableName);
				BTree tree = Deserialize.DeserializeTree(Table.getTreeName(columnNameReader(strTableName), clusteringKey), strTableName);
				String s = (String) tree.search(strClusteringKeyValue);
				if(s == null)
					throw new DBAppException("Record not found in Tree");
				Page page = DeserializePage(Table.getPageName(s), strTableName);
				record = binarySearchPage(strTableName, page, strClusteringKeyValue);
				if (record == null)
					throw new DBAppException("Record Does Not Exist");
				tree.delete(strClusteringKeyValue);
//				tree.insert((Comparable) htblColNameValue.get(clusteringKey), strTableName+" "+getPageName(s));
			}
			else {
				record = binarySearch(strTableName, strClusteringKeyValue);
				if (record == null) {
					throw new DBAppException("Record Doesn't Exist");
				}
			}
			Page i = DeserializePage(record.getPageName(), strTableName);
			int index = getRecordNumber(i, record);
			if (index == -1)
				throw new DBAppException("Record Not Found");
			table.deleteRows(record.getHm(), false);
			Record old = record;
			for (Vector<String> d : column) {
				if (record.getHm().containsKey(d.get(0)) && htblColNameValue.containsKey(d.get(0)))
					record.getHm().put(d.get(0), htblColNameValue.get(d.get(0)));
			}

			try {
				insertIntoTable(strTableName, record.getHm());
			}catch(DBAppException e){
				insertIntoTable(strTableName, old.getHm());
			}

//			i.getTuples().set(index, record);
			table.update(i, getPrimaryKey(strTableName));

			i = DeserializePage(record.getPageName(), strTableName);
			Serialize.Serializethis(i.getName(), i, strTableName);
		}
		else {
			throw new DBAppException("Table Not Found");
		}
	}

	/**
	 @param  page: pointer to a page
	 @param record : pointer to a record
	 @return the record number
	  **/
	public static int getRecordNumber(Page page, Record record){
		int i = 0, j = page.getNumberofRowsraw();
		Object pk = record.getHm().get(getPrimaryKey(page.getTableName()));
		while(i <= j){
			int index = (i + j) / 2;
			if(((Comparable)pk).compareTo(page.getTuples().get(index).getHm().get(getPrimaryKey(page.getTableName()))) == 0){
				return index;
			}else if(((Comparable)pk).compareTo(page.getTuples().get(index).getHm().get(getPrimaryKey(page.getTableName()))) > 0){
				i = index + 1;
			}else{
				j = index - 1;
			}
		}
		return -1;
	}


	/**
	 @param str: a string that may contain an integer
	 @return the result of the possibility of conversion to int
	 * **/
	public static Integer mayParse(String str) {
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException e) {
			return null;
		}
	}
	/**
	 @param str: a string that may contain a double
	 @return the result of the possibility of conversion to double
	  **/
	public static Double mayParse2(String str) {
		try {
			return Double.parseDouble(str);
		} catch (NumberFormatException e) {
			return null;
		}
	}




	public static Record binarySearch(String tableName , Object pk) throws DBAppException, IOException, ClassNotFoundException {
		try{
			Table table = DeserializeTable(tableName);
			int n = table.getPageNames().size();
			int l = 0 , r = n-1;
			Vector<Object> maxes = table.getMaxOfPages();
			Vector<Object> mines = table.getminOfPages();
			Vector<String> pageNames = table.getPageNames();
			while(l<=r){
				int mid = (l + r) / 2;


				if (mayParse(""+ pk) != null) {
					pk = mayParse(""+ pk);
				} else if (mayParse2(""+ pk) != null) {
					pk = mayParse2(""+ pk);
				}

				if(((Comparable) pk).compareTo(maxes.get(mid)) <= 0 && ((Comparable) pk).compareTo(mines.get(mid)) >= 0 ){
					Page page = DeserializePage(pageNames.get(mid),tableName);
					return binarySearchPage(tableName,page,pk);
//					int i = 0, j = page.getNumberofRowsraw();
//					while(i <= j){
//						int index = (i + j) / 2;
//
//						if(((Comparable)pk).compareTo(page.getTuples().get(index).getHm().get(getPrimaryKey(tableName))) == 0){
//							return page.getTuples().get(index);
//						}else if(((Comparable)pk).compareTo(page.getTuples().get(index).getHm().get(getPrimaryKey(tableName))) > 0){
//							i = index + 1;
//						}else{
//							j = index - 1;
//						}
//					}

				}else if(((Comparable) pk).compareTo(maxes.get(mid)) > 0){
					l = mid + 1;
				}else{
					r = mid - 1;
				}

			}
		}catch(Exception e){
			throw new DBAppException("Table Not Found to make Binary Search");
		}
		return null;
	}

	// following method could be used to delete one or more rows.
	// htblColNameValue holds the key and value. This will be used in search
	// to identify which rows/tuples to delete.
	// htblColNameValue entries are ANDED together

	/**
	 @param strTableName: the name of the table
	 @param htblColNameValue: the combination of the values to be deleted
	 * **/
	public void deleteFromTable(String strTableName, Hashtable<String, Object> htblColNameValue) throws DBAppException {
		if (searchMetadata(strTableName)) {
			Vector column = columnNameReader(strTableName);

			Table deserializedTable = null;
			try {
				deserializedTable = DeserializeTable(strTableName);
			} catch (IOException | ClassNotFoundException e) {
				throw new DBAppException("Could not deserialize Table");
			}
			if (deserializedTable.getPageNames().isEmpty())
				throw new DBAppException("Table is Empty");
			try {
				deserializedTable.deleteRows(htblColNameValue,true);
			} catch (IOException | ClassNotFoundException e) {
				throw new DBAppException("Table Not Found");
			}
			System.out.println("Deletion completed successfully.");

		} else {
			throw new DBAppException("Table Not Found");
		}
	}



	/**
	 @param value: the object's type will be checked on
	 @param colType: the expected type that value should be
	 **/
	// Method to check if the data type of value matches the expected data type for a column
	private boolean isDataTypeMatching(Object value, String colType) {
		String valueType = value.getClass().getName();
		// Convert shorthand data type names to their full class names
		switch (colType) {
			case "Integer":
				colType = "java.lang.Integer";
				break;
			case "String":
				colType = "java.lang.String";
				break;
			case "Double":
				colType = "java.lang.Double";
				break;
		}
		// Check if the actual data type matches the expected data type
		return colType.equals(valueType);
	}



	/**
	 @param arrSQLTerms : Array of query conditions.
	 @param strarrOperators : Operators between conditions.
	 @return : The Query to be returned.
	 **/
	public Iterator selectFromTable(SQLTerm[] arrSQLTerms, String[]  strarrOperators) throws DBAppException, IOException, ClassNotFoundException {
		if(arrSQLTerms.length > 0) {
			Table deserializedTable = DeserializeTable(arrSQLTerms[0]._strTableName);
			if (deserializedTable.getPageNames().isEmpty())
				throw new DBAppException("Table is Empty");
		}
		boolean flag = false;
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
			} else
			{
				Vector<String> indexedColumns = getIndexedColumns(columnNameReader(v.getTableName()));


				// checks on Primary Keys & Indexed

				for(int i = 0;i < arrSQLTerms.length;i++) {

					Vector<Record> records = new Vector<>();

					if(vectors[i] == null && arrSQLTerms[i]._strColumnName.equals(getPrimaryKey(v.getTableName())) && indexedColumns.contains(arrSQLTerms[i]._strColumnName)){

						String treeName = getTreeName(columnNameReader(v.getTableName()), arrSQLTerms[i]._strColumnName);
						BTree tree = DeserializeTree(treeName, v.getTableName());

						switch (arrSQLTerms[i]._strOperator){
							case "=":
								String s = (String) tree.search((Comparable) arrSQLTerms[i]._objValue);
								Page page = DeserializePage(getPageName(s) , v.getTableName());
								Record record = binarySearchPage(v.getTableName(),page,arrSQLTerms[i]._objValue);
								records.add(record);
								;break;
							case "<=": ;break;
							case ">=": ;break;
							case "<": ;break;
							case ">": ;break;
						}

						String s = (String) tree.search((Comparable) arrSQLTerms[i]._objValue);
						Page page = DeserializePage(getPageName(s) , v.getTableName());
						Record record = binarySearchPage(v.getTableName(),page,arrSQLTerms[i]._objValue);
						records.add(record);

						vectors[i] = records;
						break;
					}


				}

				// checks on Primary Keys (Not Indexed)
				for(int i = 0;i < arrSQLTerms.length;i++) {
					Vector<Record> records = new Vector<>();
					if(vectors[i] == null && arrSQLTerms[i]._strColumnName.equals(getPrimaryKey(v.getTableName())) && ! indexedColumns.contains(arrSQLTerms[i]._strColumnName)){

						Vector<Record> records1 = new Vector<>();
						switch (arrSQLTerms[i]._strOperator){
							case "=":
								Record record = binarySearch(v.getTableName(),arrSQLTerms[i]._objValue);
								records.add(record);
								;break;
							case "<=":
								records1 = linearSearchTable(DeserializeTable(v.getTableName()),arrSQLTerms[i],2);
								while(!records1.isEmpty()){
									records.add(records1.remove(0));
								}
								break;
							case ">=":
								records1 = linearSearchTable(DeserializeTable(v.getTableName()),arrSQLTerms[i],3);
								while(!records1.isEmpty()){
									records.add(records1.remove(0));
								}
								break;
							case "<":
								records1 = linearSearchTable(DeserializeTable(v.getTableName()),arrSQLTerms[i],4);
								while(!records1.isEmpty()){
									records.add(records1.remove(0));
								}
								break;
							case ">":
								records1 = linearSearchTable(DeserializeTable(v.getTableName()),arrSQLTerms[i],5);
								while(!records1.isEmpty()){
									records.add(records1.remove(0));
								}
								;
								break;
						}
						vectors[i] = records;
						break;
					}

				}


				// checks on Indexed Only
				for (int i = 0; i < arrSQLTerms.length; i++) {
					Vector<Record> records = new Vector<>();
					if (vectors[i] == null && !arrSQLTerms[i].equals(getPrimaryKey(v.getTableName())) && indexedColumns.contains(arrSQLTerms[i]._strColumnName)) {
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

				}


				for(int i = 0;i < arrSQLTerms.length;i++) {
					if(vectors[i] == null)
						vectors[i] = linearSearchTable2(DeserializeTable(v.getTableName()), arrSQLTerms[i]);

				}
				Vector<Record> records = Solver(vectors, strarrOperators);
				for(int i=0;i<records.size();i++){
					((Table3ashankhaterSeif) table).addRecord(records.get(i));
				}
				return table;


			}
		}
		if (flag){
			table = (Iterator) Solver(vectors,strarrOperators);
			System.out.println("done");
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

	public static Vector<Record>[] removeFromArray(Vector<Record>[] array, int tbr){
		Vector<Record>[] newobj = new Vector[array.length];
		for(int i = 0; i < array.length; i++){
			if(i!=tbr){
				newobj[i] = array[i];
			}
		}
//        Vector<Record>[] newnewobj = new Vector[array.length-1];
//        for(int i = 0; i < array.length-1; i++){
//            if(!(newobj[i]==null)){
//                newnewobj[i] = newobj[i];
//            }
//        }
		return newobj;
	}
	public static String[] removeFromArray2(String[] array, int tbr){
		String[] newobj = new String[array.length];
		for(int i = 0; i < array.length; i++){
			if(i!=tbr){
				newobj[i] = array[i];
			}
		}
//     String[] newnewobj = new String[array.length-1];
//        for(int i = 0; i < array.length-1; i++){
//            if(!(newobj[i]==null)){
//                newnewobj[i] = newobj[i];
//            }
//        }
		return newobj;
	}



	public static Vector<Record> Solver (Vector<Record>[] operands, String[] op) throws DBAppException {

		if((operands.length-op.length)!=1){
			throw new DBAppException("Difference should be 1");
		}
		Vector<Vector<Record>> vecquick = new Vector<>();
		for(int i =0; i<operands.length;i++){
			vecquick.add(operands[i]);
		}
		for (int i = 0; i < op.length; i++) {
			Vector<Record> LastmanStanding = new Vector<>();
			if(!(op[i]==null)&&op[i].equals("AND")){

				Vector<Record> bf = vecquick.get(i);
				Vector<Record> af = vecquick.get(i+1);
				int length = Math.min(af.size(), bf.size());

				for(int j = 0; j < length; j++){


					if(bf.size()==length){
						for(int k =0; k < af.size(); k++){
							if ((vecquick.get(i).get(j).getHm().get(getPrimaryKey(operands[0].get(0).getTableName())))
									.equals(vecquick.get(i+1).get(k).getHm().get(getPrimaryKey(operands[0].get(0).getTableName())))) {
								LastmanStanding.add(vecquick.get(i).get(j));
							}
						}
					}else {
						for (int k = 0; k < bf.size(); k++) {
							if ((operands[i + 1].get(j).getHm().get(getPrimaryKey(operands[0].get(j).getTableName())))
									.equals(vecquick.get(i).get(k).getHm().get(getPrimaryKey(operands[0].get(j).getTableName())))) {

								LastmanStanding.add(vecquick.get(i+1).get(j));
							}
						}
					}
				}
				operands[i] = LastmanStanding;

				op = removeFromArray2(op,i);
			}

		}
		boolean empty = true;
		for (Object ob : op) {
			if (ob != null) {
				empty = false;
				break;
			}
		}
		if (empty) {return operands[0];}
		for (int i = 0; i < op.length; i++) {
			if(!(op[i]==null)&&op[i].equals("OR")){

				Vector<Record> bf = vecquick.get(i);
				Vector<Record> af = vecquick.get(i+1);
				bf.addAll(af);
				LinkedHashSet<Record> hashSet = new LinkedHashSet<Record>(bf);
				bf.clear();
				bf.addAll(hashSet);




				op =  removeFromArray2(op,i);
			}

		}
		empty = true;
		for (Object ob : op) {
			if (ob != null) {
				empty = false;
				break;
			}
		}
		if (empty) {return operands[0];}
		for (int i = 0; i < op.length; i++) {
			Vector<Record> LastmanStanding = new Vector<>();
			if(!(op[i]==null)&&op[i].equals("XOR")){

				Vector<Record> bf = vecquick.get(i);
				Vector<Record> af = vecquick.get(i+1);
				int length = Math.min(af.size(), bf.size());

				for(int j = 0; j < length; j++){


					if(bf.size()==length){
						for(int k =0; k < af.size(); k++){
							if ((vecquick.get(i).get(j).getHm().get(getPrimaryKey(operands[0].get(0).getTableName())))
									.equals(vecquick.get(i+1).get(k).getHm().get(getPrimaryKey(operands[0].get(0).getTableName())))) {
								LastmanStanding.add(vecquick.get(i).get(j));
							}
						}
					}else {
						for (int k = 0; k < bf.size(); k++) {
							if ((operands[i + 1].get(j).getHm().get(getPrimaryKey(operands[0].get(j).getTableName())))
									.equals(vecquick.get(i).get(k).getHm().get(getPrimaryKey(operands[0].get(j).getTableName())))) {

								LastmanStanding.add(vecquick.get(i+1).get(j));
							}
						}
					}
				}
				Vector<Record> r =  vecquick.get(i);
				r.removeAll(LastmanStanding);
				operands[i] = r;
				vecquick.set(i,r);
				op = removeFromArray2(op,i);
			}

		}
		return operands[0];
	}
	public static Vector<Record> linearSearchTable(Table table, SQLTerm term, int x) throws IOException, ClassNotFoundException {
		Vector<Record> records = new Vector<>();
		for(String pageName : table.getPageNames()) {
			Page page = DeserializePage(pageName,table.getTableName());
			for (Record record : page.getTuples()) {
				Object obj = record.getHm().get(term._strColumnName);
				String y= term._strOperator;
				//TODO: replace x with y and remove the number from method signature
				switch (x) {
					case 1:
						if (((Comparable) obj).compareTo(term._objValue) == 0) {
							if(!records.contains(record))
								records.add(record);
						}
						;
						break;
					case 2:
						if (((Comparable) obj).compareTo(term._objValue) <= 0) {
							if(!records.contains(record))
								records.add(record);
						}
						;
						break;
					case 3:
						if (((Comparable) obj).compareTo(term._objValue) >= 0) {
							if(!records.contains(record))
								records.add(record);
						}
						;
						break;
					case 4:
						if (((Comparable) obj).compareTo(term._objValue) < 0) {
							if(!records.contains(record))
								records.add(record);
						}
						;
						break;
					case 5:
						if (((Comparable) obj).compareTo(term._objValue) > 0) {
							if(!records.contains(record))
								records.add(record);
						}
						;
						break;
				}
			}
		}
		return records;
	}
	public static Vector<Record> linearSearchTable2(Table table, SQLTerm term) throws IOException, ClassNotFoundException {
		Vector<Record> records = new Vector<>();
		for(String pageName : table.getPageNames()) {
			Page page = DeserializePage(pageName,table.getTableName());
			for (Record record : page.getTuples()) {
				Object obj = record.getHm().get(term._strColumnName);
				String y= term._strOperator;
				//TODO: replace x with y and remove the number from method signature
				switch (y) {
					case "=":
						if (((Comparable) obj).compareTo(term._objValue) == 0) {
							if(!records.contains(record))
								records.add(record);
						}
						;
						break;
					case "<=":
						if (((Comparable) obj).compareTo(term._objValue) <= 0) {
							if(!records.contains(record))
								records.add(record);
						}
						;
						break;
					case ">=":
						if (((Comparable) obj).compareTo(term._objValue) >= 0) {
							if(!records.contains(record))
								records.add(record);
						}
						;
						break;
					case "<":
						if (((Comparable) obj).compareTo(term._objValue) < 0) {
							if(!records.contains(record))
								records.add(record);
						}
						;
						break;
					case ">":
						if (((Comparable) obj).compareTo(term._objValue) > 0) {
							if(!records.contains(record))
								records.add(record);
						}
						;
						break;
				}
			}
		}
		return records;
	}
	/**
	 @param arrSQLTerms: Array of conditions
	 @param table: Query to be returned.
	 @param strarrOperators: Array of operators
	 @param t: Original table to traverse on.
	 **/


	/**
	 @param value: value of the key
	 @param arrSQLTerms: array of conditions
	 @param i: index of the condition
	 @return : True if valid data type. False if invalid, i.e name<24
	 **/
	public boolean checkValidity(Object value, SQLTerm[] arrSQLTerms, int i){
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

	/**
	 @param strTableName: the name of the table
	 @return : the name of the primary key
	 * **/
	public static String getPrimaryKey(String strTableName) {
		String csvFile = "metadata.csv";
		try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] values = line.split(",");
				if (values[0].equals(strTableName) && values[3].equals("True")) {
					return values[1];
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return null;
	}

	// below method returns Iterator with result set if passed
// strbufSQL is a select, otherwise returns null.

	/**
	 @param strbufSQL :
	 @return :
	 * **/
	public Iterator parseSQL(StringBuffer strbufSQL) throws DBAppException{
		new myVisitor().visit(new SQLParser(new CommonTokenStream(new SQLLexer(fromString(strbufSQL.toString())))).parse());
        return null;
		}

	public static void main( String[] args ){

		try{
			DBApp dbApp = new DBApp();
			dbApp.init();
//			String strTableName = "Student";
//			String st = "java.lang.String";
//			String in = "java.lang.Integer";
//			String dou = "java.lang.double";
//			StringBuffer sqlBuffer = new StringBuffer();
//			sqlBuffer.append("CREATE TABLE \"Student\" (\"id\" INT PRIMARY KEY, \"name\" STRING, \"gpa\" DOUBLE);");
//			dbApp.parseSQL(sqlBuffer);
//			sqlBuffer.delete(0,sqlBuffer.length());
//			sqlBuffer.append("CREATE INDEX \"nameIndex\" ON \"Student\" (\"name\");");
//			dbApp.parseSQL(sqlBuffer);
//			sqlBuffer.delete(0,sqlBuffer.length());
//			sqlBuffer.append("INSERT INTO \"Student\" (\"id\", \"name\", \"gpa\") VALUES (3,\"Ahmed\", 0.69), (2,\"Youssef\",0.77), (1, \"Omar\", 0.99);");
//			dbApp.parseSQL(sqlBuffer);
//			sqlBuffer.delete(0,sqlBuffer.length());
//			sqlBuffer.append("UPDATE \"Student\" SET \"gpa\" = 0.42 ,\"name\"=\"ZZ\" WHERE \"id\" = 1;");
//			dbApp.parseSQL(sqlBuffer);
//			sqlBuffer.delete(0,sqlBuffer.length());
//			sqlBuffer.append("DELETE FROM \"Student\";");
//			dbApp.parseSQL(sqlBuffer);
//			sqlBuffer.delete(0,sqlBuffer.length());
//			sqlBuffer.append("SELECT * FROM \"Student\" WHERE \"gpa\" = 50.6 AND \"id\" = 4;");
//			dbApp.parseSQL(sqlBuffer);

//			Hashtable htblColNameType = new Hashtable();
//			htblColNameType.put("id", st);
//			htblColNameType.put("name", st);
//			htblColNameType.put("gpa", dou);
//			dbApp.createTable( strTableName, "name", htblColNameType );
//			dbApp.createIndex( strTableName, "id", "idIndex" );


//			for(int i=0;i<201;i++){
//			Hashtable htblColNameValue = new Hashtable();
//			htblColNameValue.put("id", i);
//			htblColNameValue.put("name", "Ahmed");
//			htblColNameValue.put("gpa",i+0.6);
//			dbApp.insertIntoTable( "Student" , htblColNameValue );
//			}

//			Hashtable htblColNameValue = new Hashtable();
//			htblColNameValue.put("id", "a15");
//			htblColNameValue.put("name", "K0");
//			htblColNameValue.put("gpa",0.7);
			//htblColNameValue.put("sss",0.69);
//			dbApp.insertIntoTable( strTableName , htblColNameValue );


//			dbApp.updateTable("Student", "K10", htblColNameValue);

//			dbApp.deleteFromTable(strTableName, htblColNameValue);
//			System.out.println(Deserialize.DeserializeTable("Student"));
//			System.out.println(Deserialize.DeserializeTable("Student").getminOfPages()+" minimums");
//			System.out.println(Deserialize.DeserializeTable("Student"));
//			BTree tree = Deserialize.DeserializeTree("nameIndex", "Student");
//			System.out.println(tree);
//			tree.print();

			//		htblColNameValue.clear();
			//		htblColNameValue.put("id", new Integer( 453455 ));
			//		htblColNameValue.put("name", new String("Ahmed Noor" ) );
			//		htblColNameValue.put("gpa", new Double( 0.95 ) );
			//		dbApp.insertIntoTable( strTableName , htblColNameValue );

			//		htblColNameValue.clear( );
			//		htblColNameValue.put("id", new Integer( 5674567 ));
			//		htblColNameValue.put("name", new String("Dalia Noor" ) );
			//		htblColNameValue.put("gpa", new Double( 1.25 ) );
			//		dbApp.insertIntoTable( strTableName , htblColNameValue );

			//		htblColNameValue.clear( );
			//		htblColNameValue.put("id", new Integer( 23498 ));
			//		htblColNameValue.put("name", new String("John Noor" ) );
			//		htblColNameValue.put("gpa", new Double( 1.5 ) );
			//		dbApp.insertIntoTable( strTableName , htblColNameValue );

			//		htblColNameValue.clear( );
			//		htblColNameValue.put("id", new Integer( 78452 ));
			//		htblColNameValue.put("name", new String("Zaky Noor" ) );
			//		htblColNameValue.put("gpa", new Double( 0.88 ) );
			//		dbApp.insertIntoTable( strTableName , htblColNameValue );


			SQLTerm[] arrSQLTerms;
			arrSQLTerms = new SQLTerm[2];
			SQLTerm sql = new SQLTerm();
			SQLTerm sql2 = new SQLTerm();
			SQLTerm sql3 = new SQLTerm();
			arrSQLTerms[0] = sql;
			arrSQLTerms[1] = sql2;
//			arrSQLTerms[2] = sql3;
			arrSQLTerms[0]._strTableName =  "Student";
			arrSQLTerms[0]._strColumnName=  "gpa";
			arrSQLTerms[0]._strOperator  =  "<";
			arrSQLTerms[0]._objValue     =   69.6;

			arrSQLTerms[1]._strTableName =  "Student";
			arrSQLTerms[1]._strColumnName=  "id";
			arrSQLTerms[1]._strOperator  =  "<";
			arrSQLTerms[1]._objValue     =   60 ;

//			arrSQLTerms[2]._strTableName =  "Student";
//			arrSQLTerms[2]._strColumnName=  "gpa";
//			arrSQLTerms[2]._strOperator  =  "=";
//			arrSQLTerms[2]._objValue     =   1.6 ;


			String[] strarrOperators = new String[1];
			strarrOperators[0] = "XOR";
//			strarrOperators[1] = "OR";
//			 select * from Student where name = "John Noor" or gpa = 1.5;
					Iterator resultSet = dbApp.selectFromTable(arrSQLTerms , strarrOperators);
					System.out.println(resultSet.toString());
			// Perform deletion
			//		dbApp.deleteFromTable(strTableName, htblColNameValue);

			// Print the table after deletion

		}
		catch(Exception exp){
			exp.printStackTrace( );
		}
	}
}