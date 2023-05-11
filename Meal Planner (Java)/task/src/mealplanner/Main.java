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
                    System.out.println("Which category do you want to print (breakfast, lunch, dinner)?");
                    userInput = scanner.nextLine();

                    while (!listMeal.checkTypeMealToShow(userInput)) {
                        userInput = scanner.nextLine();
                    }

                    switch (userInput) {
                        case "breakfast" -> {
                            listMeal.showBreakfastMeals();
                        }
                        case "lunch" -> {
                            listMeal.showLunchMeals();
                        }
                        case "dinner" -> {
                            listMeal.showDinnerMeals();
                        }
                    }

                }
                case "plan" -> {
                    if (!listMeal.isPlanEmpty()) {
                        listMeal.planWeekMealsFromScratch(scanner);
                    }
                }
                case "exit" -> {
                    System.out.println("Bye!");
                    exit = true;
                }
            }
        }
    }


    public static void askUser() {
        System.out.println("What would you like to do (add, show, plan, exit)?");
    }

    public static void accessDatabase() throws SQLException {
        String DB_URL = "jdbc:postgresql://localhost/meals_db";
        String USER = "postgres";
        String PASS = "1111";

        Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
        connection.setAutoCommit(true);
        Statement statement = connection.createStatement();
        Statement statement1 = connection.createStatement();

        statement.execute("""
                CREATE TABLE IF NOT EXISTS meals(
                category varchar(1000),
                meal varchar(1000),
                meal_id integer PRIMARY KEY);""");

        statement.execute("""
                CREATE TABLE IF NOT EXISTS ingredients(
                ingredient varchar(1000),
                ingredient_id integer,
                meal_id integer PRIMARY KEY);""");

        statement.execute("""
                CREATE TABLE IF NOT EXISTS plan(
                meal varchar(1000),
                category varchar(1000),
                meal_id integer);""");
    }
}