package starter_code.BTree;

import starter_code.Main.Page;
import starter_code.Main.Record;
import starter_code.Main.Table;
import starter_code.Serialization.Deserialize;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.Vector;

/**
 * A B+ tree Since the structures and behaviors between internal node and
 * external node are different, so there are two different classes for each kind
 * of node.
 *
 * @param < TKey > the data type of the key
 * @param < TValue > the data type of the value
 */
public class BTree<TKey extends Comparable<TKey>, TValue> implements Serializable {
    /**
     * @uml.property name="root"
     * @uml.associationEnd multiplicity="(1 1)"
     */
    private BTreeNode<TKey> root;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    private int size;
    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    /**
     * @uml.property name="tableName"
     */
    private String tableName;

    public String getTreeName() {
        return treeName;
    }

    public void setTreeName(String indexName) {
        this.treeName = indexName;
    }

    private String treeName;
    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    private String columnName;

    public BTree(String table, String column,String index) {
        this.root = new BTreeLeafNode<TKey, TValue>();
        tableName = table;
        columnName = column;
        treeName = index;
    }

    /**
     * Insert a new key and its associated value into the B+ tree.
     */
    public void insert(TKey key, TValue value) {
        BTreeLeafNode<TKey, TValue> leaf = this.findLeafNodeShouldContainKey(key);
        leaf.insertKey(key, value);

        if (leaf.isOverflow()) {
            BTreeNode<TKey> n = leaf.dealOverflow();
            if (n != null)
                this.root = n;
        }
        size++;
    }

    /**
     * Search a key value on the tree and return its associated value.
     */
    public TValue search(TKey key) {
        BTreeLeafNode<TKey, TValue> leaf = this.findLeafNodeShouldContainKey(key);

        int index = leaf.search(key);
        return (index == -1) ? null : leaf.getValue(index);
    }

    /**
     * Delete a key and its associated value from the tree.
     */
    public void delete(TKey key) {
        BTreeLeafNode<TKey, TValue> leaf = this.findLeafNodeShouldContainKey(key);

        if (leaf.delete(key) && leaf.isUnderflow()) {
            BTreeNode<TKey> n = leaf.dealUnderflow();
            if (n != null)
                this.root = n;
        }
        size--;
    }

    /**
     * Search the leaf node which should contain the specified key
     */
    @SuppressWarnings("unchecked")
    private BTreeLeafNode<TKey, TValue> findLeafNodeShouldContainKey(TKey key) {
        BTreeNode<TKey> node = this.root;
        while (node.getNodeType() == TreeNodeType.InnerNode) {
            node = ((BTreeInnerNode<TKey>) node).getChild(node.search(key));
        }

        return (BTreeLeafNode<TKey, TValue>) node;
    }

    public void print() {
        ArrayList<BTreeNode> upper = new ArrayList<>();
        ArrayList<BTreeNode> lower = new ArrayList<>();

        upper.add(root);
        while (!upper.isEmpty()) {
            BTreeNode cur = upper.get(0);
            if (cur instanceof BTreeInnerNode) {
                ArrayList<BTreeNode> children = ((BTreeInnerNode) cur).getChildren();
                for (int i = 0; i < children.size(); i++) {
                    BTreeNode child = children.get(i);
                    if (child != null)
                        lower.add(child);
                }
            }
            System.out.println(cur.toString() + " ");
            upper.remove(0);
            if (upper.isEmpty()) {
                System.out.println("\n");
                upper = lower;
                lower = new ArrayList<>();
            }
        }
    }

    public String toString(){
        StringBuilder sb = new StringBuilder("# ");
        try {
            Table table = Deserialize.DeserializeTable(getTableName());
            BTree tmpTree = new BTree(getTableName(),getColumnName(),getTreeName());
            Vector<Object> tmp = new Vector<>();
            for(String pageName : table.getPageNames()){
                Page page = Deserialize.DeserializePage(pageName,getTableName());
                for(Record record : page.getTuples()){
                    String s = (String) search((TKey) record.getHm().get(getColumnName()));
                    if(s != null) {
                        sb.append(record.getHm().get(getColumnName()) + " ");
                        tmp.add(record.getHm().get(getColumnName()));
                        tmpTree.delete((TKey) record.getHm().get(getColumnName()));
                        tmpTree.insert((TKey) record.getHm().get(getColumnName()), getTableName() + " " + pageName);
                    }
                }
            }
            while(tmpTree.getSize() > 0){
                this.insert((TKey) tmp.remove(0), (TValue) tmpTree.search((TKey) tmp.remove(0)));
                tmpTree.delete((TKey) tmp.remove(0));

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        sb.append('#');
        return sb.toString();
    }

    public BTreeLeafNode getSmallest() {
        return this.root.getSmallest();
    }

    public String commit() {
        return this.root.commit();
    }

    //    When shifting rows in Table, this shifts based on Page Number and Row Number
    public void shiftRowsInTree() throws IOException, ClassNotFoundException {
        Table deserializedTable = Deserialize.DeserializeTable(getTableName());

        int rowNumber = 1;
        for(String pageName : deserializedTable.getPageNames()){
            Page deserializedPage = Deserialize.DeserializePage(pageName,tableName);
            for(Record record : deserializedPage.getTuples()){
                for (Map.Entry<String, Object> entry : record.getHm().entrySet()) {
                    if(entry.getKey().equals(this.getColumnName())){
                        delete((TKey) entry.getValue());
                        insert((TKey) entry.getValue(), (TValue) (tableName + " " + deserializedPage.getName()));
                        break;
                    }
                }
                rowNumber++;
            }
        }
    }



    public static void main(String[] args) throws IOException {
        BTree tree = new BTree("Student","gpa","gpaIndex");
        tree.insert("3.2","Student 1 1");
        tree.insert("3.2","Student 1 2");
        tree.delete("3.2");
        System.out.println(tree);
    }
}