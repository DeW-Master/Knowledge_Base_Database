package Table;

import java.util.ArrayList;

public class Table {

    public String name;
    public ArrayList<String> title = new ArrayList<>();
    public ArrayList<ArrayList<String>> content = new ArrayList<>();
    public Integer column;

    public Table(String name) {
        this.name = name;
    }

    public void createTitle(String str){
        String[] titles = str.split(",");
        this.column = titles.length;

        for (String t :
                titles) {
            t = t.replaceAll("\\s","");
            this.title.add(t);
        }
    }

    public void addNewRow(String str) {
        String[] records = str.split(",");
        ArrayList<String> row = new ArrayList<>();
        for (String record :
                records) {
            record = record.replaceAll("\\s","");
            row.add(record);
        }
        content.add(row);
    }

    public void addNewColumn() {
        //TODO: using for adding annotation
    }
}
