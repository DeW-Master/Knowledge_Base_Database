import DataBase.DataBase;
import Table.Table;
import Table.TablePrinter;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        DataBase dataBase = new DataBase("/Users/zouhaochen/6591 peoject example/database");

        while(true){
            Scanner s = new Scanner(System.in);
            System.out.println("Input your query: ");
            String query = s.nextLine();
            System.out.println("Input type (1.bag 2.probability 3.certainty 4.polynomial 5.normal)");
            String type = s.nextLine();
            if (query.equals("exit"))
                break;
            Formula formula = new Formula(Integer.valueOf(type), dataBase);
            try {
                Table res = formula.calculate(query);
                TablePrinter.print(res);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
