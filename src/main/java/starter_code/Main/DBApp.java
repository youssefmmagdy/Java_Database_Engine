package starter_code.Main;

import starter_code.BTree.BTree;
import starter_code.Exception.DBAppException;
import starter_code.Serialization.Deserialize;
import starter_code.Serialization.Serialize;

import java.io.*;
import java.util.*;

import static starter_code.Serialization.Deserialize.DeserializeTable;


/** * @author Wael Abouelsaadat */


public class DBApp {
	Iterator table = null;
	private Vector<Table> tables;

	public DBApp( ) throws IOException, ClassNotFoundException {
		init();
		tables = new Vector<Table>();
		for(String s : getTableNames("metadata.csv")){
			File f = new File("starter_code/"+s+"/"+s+".class");
			if(f.exists()&& !f.isDirectory()){
				Table t = Deserialize.DeserializeTable(s);
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
			lines.append("Table Name"+','+ "Column Name"+','+ "Column Type"+','+ "ClusteringKey"+','+ "IndexName"+','+"IndexType");
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



	public void createTable(String strTableName,
							String strClusteringKeyColumn,
							Hashtable<String,String> htblColNameType) throws IOException, DBAppException {
		File f = new File("starter_code/"+strTableName);
		if(!f.exists()) {
			for (Map.Entry<String, String> entry : htblColNameType.entrySet()) {
				String value = entry.getValue();if(!value.equals("java.lang.String")&&!(value.equals("java.lang.double")|value.equals("java.lang.Double"))&&!value.equals("java.lang.Integer")){
					throw new DBAppException(value + " is an invalid data type");
				}}
			Table newt = new Table(strTableName);
			String folderPath = "starter_code/"+strTableName;
			File folder = new File(folderPath);
			folder.mkdirs();
			tables.add(newt);
			Serialize.Serializethis(strTableName, newt, strTableName);
			FileWriter fileWriter = new FileWriter("metadata.csv", true);
			StringBuilder line = new StringBuilder();
			line.append("\n");
			line.append(strTableName).append(',').append(strClusteringKeyColumn).append(',').append(htblColNameType.get(strClusteringKeyColumn)).append(',').append("True").append(',').append("null").append(',').append("null");
			line.append("\n");
			for (Map.Entry<String, String> entry : htblColNameType.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();
				if (!key.equals(strClusteringKeyColumn)) {
					line.append(strTableName).append(',').append(key).append(',').append(value).append(',').append("False").append(',').append("null").append(',').append("null");
					line.append("\n");
				}
			}
			fileWriter.write(line.toString());
			fileWriter.close();
		}else{
			throw new DBAppException(strTableName + " Table Already Exists");
		}
	}


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
							Table deserializedTable = Deserialize.DeserializeTable(strTableName);

							BTree tree = new BTree( strTableName, strColName,strIndexName);

							if(!deserializedTable.getTreesNames().contains(strIndexName))
								deserializedTable.addTreeName(strIndexName);


							for (String pageName : deserializedTable.getPageNames()) {
								Page deserializedPage = Deserialize.DeserializePage(pageName,strTableName);
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

	public boolean fileExists(File Location){
		return (Location.exists() && !Location.isDirectory());
	}
	public boolean sizeCheck(int col1, int col2){
		return (col1 == col2);
	}
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
						String place = strTableName + " " + strTableName + K.get(0);
						deserializedTree.insert((Comparable) htblColNameValue.get(x),place);
						deserializedTree.shiftRowsInTree();
					}
					Serialize.Serializethis(deserializedTree.getTreeName(),deserializedTree, strTableName);
				}
			}
		}
	}

	public void insertIntoTable(String strTableName,
								Hashtable<String, Object> htblColNameValue) throws DBAppException {
		if (searchMetadata(strTableName)) {
			Vector column = columnNameReader(strTableName);
			File f = new File("starter_code/" + strTableName + "/" + strTableName + ".class");
			if (fileExists(f)) {
				if (sizeCheck(htblColNameValue.size(), column.size())) {
					if (tableDataTypeCheck(column, htblColNameValue)) {
						String clusteringKey = clusteringKey_ID(column);
						if (!clusteringKey.isEmpty()) {
							Object Id = htblColNameValue.get(clusteringKey);
							//System.out.println(Id);
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
						throw new DBAppException("Invalid Input");
					}
				} else {
					throw new DBAppException("Invalid Input");
				}
			} else {
				throw new DBAppException("Invalid Input");
			}
		} else {
			throw new DBAppException("Table Not Found");
		}
	}
	private static boolean isDataTypeMatching2(Object value, String colType) {
		String valueType = value.getClass().getName();
		if (Objects.equals(colType, "java.lang.double") && valueType.equals("java.lang.Double")){
			return true;
		}else {
			return colType.equals(valueType);
		}
	}


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
			Table table = Deserialize.DeserializeTable(strTableName);
			if(table.getPageNames().isEmpty())
				throw new DBAppException("Table is Empty");
			Vector<Vector<String>> column = columnNameReader(strTableName);

			Vector<String> indexedColumns =  Table.getIndexedColumns(columnNameReader(strTableName));
			Record record;
			if (indexedColumns.contains(getPrimaryKey(strTableName))) {
				String clusteringKey = getPrimaryKey(strTableName);
				BTree tree = Deserialize.DeserializeTree(Table.getTreeName(columnNameReader(strTableName), clusteringKey), strTableName);
				String s = (String) tree.search(strClusteringKeyValue);
				System.out.println(tree);
				if(s == null)
					throw new DBAppException("Record not found in Tree");
				Page page = Deserialize.DeserializePage(Table.getPageName(s), strTableName);
				record = binarySearchPage(strTableName, page, strClusteringKeyValue);
				if (record == null)
					throw new DBAppException("Record Does Not Exist");
				tree.delete(strClusteringKeyValue);
//				tree.insert((Comparable) htblColNameValue.get(clusteringKey), strTableName+" "+getPageName(s));
			}
			else {
				record = binarySearch(strTableName, strClusteringKeyValue);
				System.out.println(Deserialize.DeserializeTable(strTableName));
				System.out.println(1);
				if (record == null) {
					throw new DBAppException("Record Doesn't Exist");
				}
			}
			Page i = Deserialize.DeserializePage(record.getPageName(), strTableName);
			int index = getRecordNumber(i, record);
			if (index == -1)
				throw new DBAppException("Record Not Found");
			deleteFromTable(strTableName,record.getHm());
			for (Vector<String> d : column) {
				if (record.getHm().containsKey(d.get(0)) && htblColNameValue.containsKey(d.get(0)))
					record.getHm().put(d.get(0), htblColNameValue.get(d.get(0)));
			}
			insertIntoTable(strTableName,record.getHm());

//			i.getTuples().set(index, record);
			table.update(i, getPrimaryKey(strTableName));
			Serialize.Serializethis(i.getName(), i, strTableName);
		}
		else {
			throw new DBAppException("Table Not Found");
		}
	}
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
	public static Record binarySearch(String tableName , Object pk) throws DBAppException, IOException, ClassNotFoundException {
		System.out.println(Deserialize.DeserializeTable(tableName)+""+pk);
		try{
			Table table = Deserialize.DeserializeTable(tableName);
			int n = table.getPageNames().size();
			int l = 0 , r = n-1;
			Vector<Object> maxes = table.getMaxOfPages();
			Vector<Object> mines = table.getminOfPages();
			System.out.println(maxes+" "+mines);
			Vector<String> pageNames = table.getPageNames();
			while(l<=r){
				int mid = (l + r) / 2;
				if(((Comparable) pk).compareTo(maxes.get(mid)) <= 0 && ((Comparable) pk).compareTo(mines.get(mid)) >= 0 ){
					Page page = Deserialize.DeserializePage(pageNames.get(mid),tableName);
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
	public void deleteFromTable(String strTableName, Hashtable<String, Object> htblColNameValue) throws DBAppException, IOException, ClassNotFoundException {

		if (searchMetadata(strTableName)) {
			Table deserializedTable = Deserialize.DeserializeTable(strTableName);
			if(deserializedTable.getPageNames().isEmpty())
				throw new DBAppException("Table is Empty");
			deserializedTable.deleteRows(htblColNameValue);
			System.out.println("Deletion completed successfully.");
		} else {
			throw new DBAppException("Table Not Found");
		}
	}




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

	public Iterator selectFromTable(SQLTerm[] arrSQLTerms,
									String[]  strarrOperators) throws DBAppException, IOException, ClassNotFoundException {
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
		// useless line but ill keep it for reference -> Iterator operators = Arrays.stream(strarrOperators).iterator();
		// idk what to do with this--> bplustree tree = new bplustree(deserializedTable.getNumberOfRows());
		//		By Doing this  -->  BTree tree = new BTree(tableName, columnName, indexName);

		for (Table v: tables) {
			if (v.getTableName().equals(arrSQLTerms[0]._strTableName) && searchMetadata(arrSQLTerms[0]._strTableName)) {
				String output = "";
				flag = true;
				table = new Table("Query", v.getPageNames(), 0);
				/*for (int i = 0; i < arrSQLTerms.length; i++) {
					for (int j = 0 ; j<strarrOperators.length ; j++){
					while (!ANDflag || !XORflag || !ORflag) {
						for (j = i; j < strarrOperators.length; j++) {
						}

						if (i==arrSQLTerms.length-1){
							if (!ORflag && ANDflag && XORflag){
								ORflag = true;
							}
							if (!XORflag&&ANDflag){
								XORflag = true;
								i = 0;
							}
							if (!ANDflag){
								i = 0;
								ANDflag = true;
							}
							break;
						}
					}*/
				if (strarrOperators.length == 0){
					for (String pageName : v.getPageNames()) {
						Page page = Deserialize.DeserializePage(pageName,v.getTableName());
						Vector<Record> tuples = page.getTuples();


						for (Record record : tuples) {
							Hashtable<String, Object> ht = record.getHm();
							for (Map.Entry<String, Object> entry : ht.entrySet()) {
								String key = entry.getKey();
								Object value = entry.getValue();


								switch (arrSQLTerms[0]._strOperator) {
									case "=":
										if (value.equals(arrSQLTerms[0]._objValue) && key.equalsIgnoreCase(arrSQLTerms[0]._strColumnName)) {
											insertRecord((Table) table,ht,output);
										}
										break;
									case ">":
										if ((value instanceof Double || value instanceof Integer) && (key.equalsIgnoreCase(arrSQLTerms[0]._strColumnName))) {
											if (arrSQLTerms[0]._objValue instanceof Integer || arrSQLTerms[0]._objValue instanceof Double) {
												if ((Double) value > (Double) arrSQLTerms[0]._objValue) {
													insertRecord((Table) table,ht,output);
												}
												break;
											} else {
												throw new DBAppException("Invalid data type in arrSQLTerms");
											}
										}
										else if(key.equalsIgnoreCase(arrSQLTerms[0]._strColumnName)){
											if (arrSQLTerms[0]._objValue instanceof Integer || arrSQLTerms[0]._objValue instanceof Double){
												throw new DBAppException("Invalid data type in arrSQLTerms");
											}
											if (((Comparable)value).compareTo(arrSQLTerms[0]._objValue)>0 && key.equalsIgnoreCase(arrSQLTerms[0]._strColumnName)){
												insertRecord((Table) table,ht,output);
											}
										}
										break;
									case ">=":
										if ((value instanceof Double || value instanceof Integer) && (key.equalsIgnoreCase(arrSQLTerms[0]._strColumnName))) {
											if (arrSQLTerms[0]._objValue instanceof Integer || arrSQLTerms[0]._objValue instanceof Double) {
												if ((Double) value >= (Double) arrSQLTerms[0]._objValue) {
													insertRecord((Table) table,ht,output);
												}
												break;
											} else {
												throw new DBAppException("Invalid data type in arrSQLTerms");
											}
										}
										else if(key.equalsIgnoreCase(arrSQLTerms[0]._strColumnName)){
											if (arrSQLTerms[0]._objValue instanceof Integer || arrSQLTerms[0]._objValue instanceof Double){
												throw new DBAppException("Invalid data type in arrSQLTerms");
											}
											if (((Comparable)value).compareTo(arrSQLTerms[0]._objValue)>=0 && key.equalsIgnoreCase(arrSQLTerms[0]._strColumnName)){
												insertRecord((Table) table,ht,output);
											}
										}
										break;
									case "<":
										if ((value instanceof Double || value instanceof Integer) && (key.equalsIgnoreCase(arrSQLTerms[0]._strColumnName))) {
											if (arrSQLTerms[0]._objValue instanceof Integer || arrSQLTerms[0]._objValue instanceof Double) {
												if ((Double) value < (Double) arrSQLTerms[0]._objValue) {
													insertRecord((Table) table,ht,output);
												}
												break;
											} else {
												throw new DBAppException("Invalid data type in arrSQLTerms");
											}
										}
										else if(key.equalsIgnoreCase(arrSQLTerms[0]._strColumnName)){
											if (arrSQLTerms[0]._objValue instanceof Integer || arrSQLTerms[0]._objValue instanceof Double){
												throw new DBAppException("Invalid data type in arrSQLTerms");
											}
											if (((Comparable)value).compareTo(arrSQLTerms[0]._objValue)<0 && key.equalsIgnoreCase(arrSQLTerms[0]._strColumnName)){
												insertRecord((Table) table,ht,output);
											}
										}
										break;
									case "<=":
										if ((value instanceof Double || value instanceof Integer) && (key.equalsIgnoreCase(arrSQLTerms[0]._strColumnName))) {
											if (arrSQLTerms[0]._objValue instanceof Integer || arrSQLTerms[0]._objValue instanceof Double) {
												if ((Double) value <= (Double) arrSQLTerms[0]._objValue) {
													insertRecord((Table) table,ht,output);
												}
												break;
											} else {
												throw new DBAppException("Invalid data type in arrSQLTerms");
											}
										}
										else if(key.equalsIgnoreCase(arrSQLTerms[0]._strColumnName)){
											if (arrSQLTerms[0]._objValue instanceof Integer || arrSQLTerms[0]._objValue instanceof Double){
												throw new DBAppException("Invalid data type in arrSQLTerms");
											}
											if (((Comparable)value).compareTo(arrSQLTerms[0]._objValue)<=0 && key.equalsIgnoreCase(arrSQLTerms[0]._strColumnName)){
												insertRecord((Table) table,ht,output);
											}
										}
										break;
									case "!=":
										if (!value.equals(arrSQLTerms[0]._objValue) && key.equalsIgnoreCase(arrSQLTerms[0]._strColumnName)) {
											insertRecord((Table) table,ht,output);
										}
										break;
									default:
										throw new DBAppException("Error: Invalid operator.");
								}
							}
							output+="\n";
						}
					}
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


	public void ExecuteQuery(SQLTerm[] arrSQLTerms, Table table, String output, String operator,String[] strarrOperators) throws IOException, ClassNotFoundException, DBAppException {
		boolean TrueFlag = false;
		boolean ANDFlag = true;
		boolean XORExists = false;
		boolean ORFlag = false;
		int counter = 0;

		for (int k = 0; k<strarrOperators.length;k++){
			if (strarrOperators[k].equalsIgnoreCase("AND")){
				ANDFlag = false;
			}
		}
		for (int k = 0; k<strarrOperators.length;k++){
			if (strarrOperators[k].equalsIgnoreCase("XOR")){
				XORExists = true;
			}
		}
		for (int k = 0; k<strarrOperators.length;k++){
			if (strarrOperators[k].equalsIgnoreCase("OR")){
				ORFlag = true;
			}
		}
		for (int i = 0; i < arrSQLTerms.length-1; i++) {
			for (int j = 0; j < strarrOperators.length; j++) {
				for (String pageName : table.getPageNames()) {
					Page page = Deserialize.DeserializePage(pageName, table.getTableName());
					Vector<Record> tuples = page.getTuples();
					for (Record record : tuples) {
						Hashtable<String, Object> ht = record.getHm();
						for (Map.Entry<String, Object> entry : ht.entrySet()) {
							String key = entry.getKey();
							Object value = entry.getValue();
							if (key.equalsIgnoreCase(arrSQLTerms[i]._strColumnName)) {
								if (checkValidity(value, key, arrSQLTerms, i)) {
									if (strarrOperators[0].equalsIgnoreCase("OR")) {
										//if strarroperator(0) is OR
										switch (arrSQLTerms[i]._strOperator) {
											case "=":
												if (value.equals(arrSQLTerms[i]._objValue)) {
													TrueFlag = true;
													if (strarrOperators[i].equalsIgnoreCase("OR")) {
														insertRecord(table, ht, output);
														break;
													} else if (strarrOperators[i].equalsIgnoreCase("AND")) {
														ANDFlag = true;
													} else if (strarrOperators[i].equalsIgnoreCase("XOR") && TrueFlag) {
														counter++;
													} else {
														throw new DBAppException("Invalid operator in strarroperators.");
													}
												} else {
													if ((strarrOperators[i].equalsIgnoreCase("AND"))) {
														break;
													}
													TrueFlag = false;
													break; //break from outer switch
												}
											case ">":
												if (operator.equalsIgnoreCase("and")) {
													if (((Comparable) value).compareTo(arrSQLTerms[i]._objValue) > 0) {
														insertRecord(table, ht, output);
													}
												}
												break;
											case ">=":
												if (((Comparable) value).compareTo(arrSQLTerms[i]._objValue) >= 0) {
													insertRecord(table, ht, output);
												}
												break;
											case "<":
												if (((Comparable) value).compareTo(arrSQLTerms[i]._objValue) < 0) {
													insertRecord(table, ht, output);
												}
												break;
											case "<=":
												if (((Comparable) value).compareTo(arrSQLTerms[i]._objValue) <= 0) {
													insertRecord(table, ht, output);
												}
												break;
											case "!=":
												if (!value.equals(arrSQLTerms[i]._objValue)) {
													insertRecord(table, ht, output);
												}
												break;
											default:
												throw new DBAppException("Error: Invalid operator.");
										}
									} else if (strarrOperators[0].equalsIgnoreCase("AND")) {
										//code for AND as first operator.
									}
									else{
										switch (arrSQLTerms[i]._strOperator) {
											case "=":
												if (value.equals(arrSQLTerms[i]._objValue)) {
													counter++;
												}
												break;
											case ">":
													if (((Comparable) value).compareTo(arrSQLTerms[i]._objValue) > 0) {
														counter++;
													}
												break;
											case ">=":
												if (((Comparable) value).compareTo(arrSQLTerms[i]._objValue) >= 0) {
													counter++;
												}
												break;
											case "<":
												if (((Comparable) value).compareTo(arrSQLTerms[i]._objValue) < 0) {
													counter++;
												}
												break;
											case "<=":
												if (((Comparable) value).compareTo(arrSQLTerms[i]._objValue) <= 0) {
													counter++;
												}
												break;
											case "!=":
												if (!value.equals(arrSQLTerms[i]._objValue)) {
													counter++;
												}
												break;
											default:
												throw new DBAppException("Error: Invalid operator.");
										}
									}
								}
							}
							output += "\n";
						}
					}
				}
			}
		}
	}
	public void insertRecord(Table table, Hashtable<String, Object> ht, String output) throws DBAppException, IOException, ClassNotFoundException {
		Hashtable htblColNameValue = new Hashtable();
		for (Map.Entry<String, Object> entry2 : ht.entrySet()){
			String OutKey = entry2.getKey();
			Object OutValue = entry2.getValue();
			htblColNameValue.put(OutKey, OutValue);
			output += htblColNameValue;
			insertIntoTable(String.valueOf(table), htblColNameValue);
		}
	}

	public boolean checkValidity(Object value, String key, SQLTerm[] arrSQLTerms, int i){
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

	public boolean checker(boolean key, boolean key1, boolean valid, boolean valid1){
		return true;
	}

	public static boolean searchForClusteringKey(Table table, SQLTerm[] arrSQLTerms,int i) throws ClassNotFoundException, IOException {
		for (String pageName : table.getPageNames()) {
			Page page = Deserialize.DeserializePage(pageName,table.getTableName());
			Vector<Record> tuples = page.getTuples();


			for (Record record : tuples) {
				Hashtable<String, Object> ht = record.getHm();
				for (Map.Entry<String, Object> entry : ht.entrySet()) {
					String key = entry.getKey();
					Object value = entry.getValue();


					if (key.equals(getPrimaryKey(String.valueOf((table))))) {
						return true;
					}
				}
			}
		}
		return false;
	}

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
	public static void main( String[] args ){

		try{
			DBApp dbApp = new DBApp();
			String strTableName = "Student";
			String st = "java.lang.String";
			String in = "java.lang.Integer";
			String dou = "java.lang.double";
//
//			Hashtable htblColNameType = new Hashtable();
//			htblColNameType.put("id", st);
//			htblColNameType.put("name", st);
//			htblColNameType.put("gpa", dou);
//			dbApp.createTable( strTableName, "id", htblColNameType );
//			dbApp.createIndex( strTableName, "id", "nameIndex" );


//			Hashtable htblColNameValue = new Hashtable();
//			htblColNameValue.put("id", "a1155");
//			htblColNameValue.put("name", "Moksha");
//			htblColNameValue.put("gpa",0.69);
//			dbApp.insertIntoTable( strTableName , htblColNameValue );


//			dbApp.updateTable("Student", "1", htblColNameValue);

//			dbApp.deleteFromTable(strTableName, htblColNameValue);

//			System.out.println(Deserialize.DeserializeTable(strTableName));
//			BTree tree = Deserialize.DeserializeTree("nameIndex", "Student");
//			System.out.println(tree);
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


			//		SQLTerm[] arrSQLTerms;
			//		arrSQLTerms = new SQLTerm[2];
			//		arrSQLTerms[0]._strTableName =  "Student";
			//		arrSQLTerms[0]._strColumnName=  "name";
			//		arrSQLTerms[0]._strOperator  =  "=";
			//		arrSQLTerms[0]._objValue     =  "John Noor";

			//		arrSQLTerms[1]._strTableName =  "Student";
			//		arrSQLTerms[1]._strColumnName=  "gpa";
			//		arrSQLTerms[1]._strOperator  =  "=";
			//		arrSQLTerms[1]._objValue     =  1.5 ;
			//		arrSQLTerms[1]._objValue     =  new Double( 1.5 );

			//		String[] strarrOperators = new String[1];
			//		strarrOperators[0] = "OR";
			// select * from Student where name = "John Noor" or gpa = 1.5;
			//		Iterator resultSet = dbApp.selectFromTable(arrSQLTerms , strarrOperators);

			// Perform deletion
			//		dbApp.deleteFromTable(strTableName, htblColNameValue);

			// Print the table after deletion

		}
		catch(Exception exp){
			exp.printStackTrace( );
		}
	}
}