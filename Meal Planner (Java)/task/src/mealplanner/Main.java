package mealplanner;

import java.util.Scanner;
import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        //Initializing Objects and Variables
        Scanner scanner = new Scanner(System.in);
        ListMeal listMeal = new ListMeal();
        boolean exit = false;
        accessDatabase();

        while (!exit) {
            askUser();
            String userInput = scanner.nextLine();

            switch (userInput) {
                case "add" -> {
                    listMeal.addMeal(scanner);
                }
                case "show" -> {
//                    if (listMeal.size() <= 0) {
//                        System.out.println("No meals saved. Add a meal first.");
//                    } else {
//                        listMeal.printMeals();
//                    }
                    listMeal.showMeals();
                }
                case "exit" -> {
                    System.out.println("Bye!");
                    exit = true;
                }
            }
        }
    }


    public static void askUser() {
        System.out.println("What would you like to do (add, show, exit)?");
    }

    public static void accessDatabase() throws SQLException {
        final String DB_URL = "jdbc:postgresql:meals_db";
        final String USER = "postgres";
        final String PASS = "1111";

        Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
        connection.setAutoCommit(true);
        Statement statement = connection.createStatement();
        Statement statement1 = connection.createStatement();

        statement.execute("""
                CREATE TABLE IF NOT EXISTS meals(
                category varchar(1000),
                meal varchar(1000),
                meal_id integer);""");

        statement.execute("""
                CREATE TABLE IF NOT EXISTS ingredients(
                ingredient varchar(1000),
                ingredient_id integer,
                meal_id integer);""");
    }
}