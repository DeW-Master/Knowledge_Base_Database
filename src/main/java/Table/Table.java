package Table;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * the panda like dataframe, use for control annotation and datbase
 */
public class Table {

    /**
     * table name
     */
    public String name;
    /**
     * column name
     */
    public ArrayList<String> title = new ArrayList<>();
    /**
     * 2D matrix for store data
     */
    public ArrayList<ArrayList<String>> content = new ArrayList<>();
    /**
     * column counter
     */
    public int column;
    /**
     * to limit the column size
     */
    public static int[] columnMaxLengths = new int[20];

    public Table(String name) {
        this.name = name;
    }

    public void createColumn(String str) {
        String[] columnName = str.split(",");
        this.column = columnName.length;
        for (int i = 0; i < column; i++) {
            columnName[i] = columnName[i].replaceAll("\\s", "");
            columnMaxLengths[i] = columnName[i].length();
            this.title.add(columnName[i]);
        }
    }

    public void addRow(String str) {
        String[] records = str.split(",");
        ArrayList<String> row = new ArrayList<>();
        for (int i = 0; i < column; i++) {
            records[i] = records[i].replaceAll("\\s", "");
            columnMaxLengths[i] = Math.max(columnMaxLengths[i], records[i].length());
            row.add(records[i]);
        }
        content.add(row);
    }


    public void print() {
        //title
        System.out.println("---------------" + name + "---------------");
        //columnName
        for (int i = 0; i < column; i++) {
            System.out.printf("%" + (columnMaxLengths[i] + 2) + "s", title.get(i));
        }
        System.out.println();
        //content
        Iterator<ArrayList<String>> iterator = content.iterator();
        ArrayList<String> row = new ArrayList<>();
        while (iterator.hasNext()) {
            row = iterator.next();
            for (int i = 0; i < column; i++) {
                System.out.printf("%" + (columnMaxLengths[i] + 2) + "s", row.get(i));
            }
            System.out.println();
        }
    }
}
