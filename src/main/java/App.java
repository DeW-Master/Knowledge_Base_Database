import DataBase.DataBase;
import Table.*;

import java.util.Scanner;

/**
 * the driver
 */
public class App {
    public static void main(String[] args) {

        System.out.println("====== Welcome to Annotation Log System ======");
        System.out.println("Loading Data....");
        DataBase dataBase = new DataBase("src/main/resources");

        while (true) {
            Scanner s = new Scanner(System.in);
            System.out.println("----- Please Input your query ----- ");
            String query = s.nextLine();
            System.out.println("||annotation Type selection||");
            System.out.println("||1.bag                    ||");
            System.out.println("||2.probability            ||");
            System.out.println("||3.certainty              ||");
            System.out.println("||4.polynomial             ||");
            System.out.println("||5.normal output(SQL ver) ||");

            String type = s.nextLine();
            if (query.equals("exit"))
                break;
            Engine l_annoEngine = new Engine(Integer.valueOf(type), dataBase);
            try {
                Table res = l_annoEngine.executeQuery(query);
                res.print();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
