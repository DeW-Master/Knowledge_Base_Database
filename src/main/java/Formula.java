import DataBase.DataBase;
import Table.Table;

import java.util.Stack;

public class Formula {

    public int type;
    public DataBase dataBase;

    /**
     * project -> @
     * selecte -> #
     * join -> *
     * union -> +
     * @param formula
     * @return
     */
    private String formate(String formula) {
        return formula.replaceAll("project", "@")
                .replaceAll("select", "#")
                .replaceAll("join", "*")
                .replaceAll("union", "+");
    }

    private String getTableName(int start, String formula) {
        int end = -1;
        for (int i = start ; i < formula.length() ; i ++){
            if (formula.charAt(i) == ')'){
                end = i;
                break;
            }
        }
        return formula.substring(start, end);
    }

    public Table calculate(String formula) throws Exception {
        Stack<String> operation = new Stack<>();
        Stack<Table> result = new Stack<>();

        formula = formate(formula);

        for (int i = 0 ; i < formula.length() ; i ++){
            String op = formula.substring(i, i + 1);

            if (op.equals("(")){
                operation.push(op);

            } else if (op.equals("@")){
                String p = getProjectOrSelect(i, formula);
                i = i + p.length() - 1;
                operation.push(p);

            } else if (op.equals("#")){
                String s = getProjectOrSelect(i, formula);
                i = i + s.length() - 1;
                operation.push(s);

            } else if (op.equals("*")){
                operation.push("*");

            } else if (op.equals("+")){
                operation.push("+");

            } else if (op.equals(")")){
                String operator1 = operation.pop();
                String operator2 = operation.pop();
                Table res;

                if (operator1.equals("(")){
                    if (operator2.contains("@") || operator2.contains("#")){
                        Table t1 = result.pop();
                        res = executeUnaryOp(operator2, t1);
                    } else {
                        Table t1 = result.pop();
                        Table t2 = result.pop();
                        res = executeBiOp(operator2, t1, t2);
                    }
                } else {
                    if (operator1.contains("@") || operator2.contains("#")){
                        Table t1 = result.pop();
                        res = executeUnaryOp(operator1, t1);
                    } else {
                        Table t1 = result.pop();
                        Table t2 = result.pop();
                        res = executeBiOp(operator1, t1, t2);
                    }
                }
                result.push(res);

            } else if(op.equals(" ")){
                continue;
            } else {
                String tableName = getTableName(i, formula);
                i = i + tableName.length() - 1;
                Table table = dataBase.database.get(tableName);
                result.push(table);
            }

        }
        if (operation.size() != 0){
            String op = operation.pop();
            if (op.contains("@") || op.contains("#")){
                Table table = result.pop();
                result.push(executeUnaryOp(op, table));
            } else{
                Table table1 = result.pop();
                Table table2 = result.pop();
                result.push(executeBiOp(op, table1, table2));
            }
        }
        return result.pop();
    }

    private String getProjectOrSelect(int start, String formula) {
        int end = -1;
        for (int i = start + 1 ; i < formula.length() ; i ++){
            if (formula.charAt(i) == '>' && formula.charAt(i + 1) == '('){
                end = i;
                break;
            }
        }
        return formula.substring(start, end + 1);
    }

    private Table executeUnaryOp(String operator2, Table para) throws Exception {
        Table res = new Table("");
        if (operator2.contains("@")) {
            String columns = operator2.replaceAll("@","")
                    .replaceAll("<","")
                    .replaceAll(">","");
            res = new Operation().projectForAll(columns, para, Integer.toString(this.type));
        } else if(operator2.contains("#")){
            String conditions = operator2.replaceAll("#","");
            conditions = conditions.substring(1,conditions.length() - 1);

            res = new Operation().selectForAll(conditions, para);
        }
        return res;
    }

    private Table executeBiOp(String operator2, Table para1, Table para2) throws Exception {
        if (operator2.contains("*")){
            return new Operation().joinForAll(para1, para2,Integer.toString(this.type));
        } else if (operator2.contains("+")){
            return new Operation().unionForAll(para1, para2, Integer.toString(this.type));
        } else{
            return new Table("");
        }
    }
}
