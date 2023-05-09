package mealplanner;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.sql.*;

public class ListMeal {
    private Map<String, Meal> listMeal;

    public ListMeal() {
        this.listMeal = new LinkedHashMap<>();
    }

    public void addMeal(Scanner scanner) throws SQLException, ClassNotFoundException {
        //Can refactor this whole block of code with a do-while loop.
        System.out.println("Which meal do you want to add (breakfast, lunch, dinner)?");
        String mealType = scanner.nextLine();

        while (!checkTypeMeal(mealType)) {
            mealType = scanner.nextLine();
        }

        System.out.println("Input the meal's name:");
        String mealName = scanner.nextLine();

        while (!patternOnlyLetters(mealName)) {
            mealName = scanner.nextLine();
        }

        System.out.println("Input the ingredients:");
        String ingredients = scanner.nextLine();

        while (!patternOnlyLettersIngredients(ingredients)) {
            ingredients = scanner.nextLine();
        }

        Meal meal = new Meal(mealType, mealName, ingredients);
        saveMealToDB(mealType, mealName, ingredients);
        this.listMeal.put(mealName, meal);
    }

    public void printMeals() {
        this.listMeal.forEach((key, meal) -> System.out.println(meal.toString()));
    }

    public void showMeals() {
        try {
            Connection conn = getConnection();

            //Selecting from meals database
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM MEALS");
            ResultSet rs = statement.executeQuery();

            //Selecting from ingredients database
            PreparedStatement statementIngredient = conn.prepareStatement("SELECT * FROM INGREDIENTS");
            ResultSet rsIngredient = statementIngredient.executeQuery();

            boolean emptyTable = true;
            while (rs.next() && rsIngredient.next()) {
                StringBuilder sb = new StringBuilder("\n");
                String typeMeal = rs.getString("category");
                String mealName = rs.getString("meal");
                String ingredients = rsIngredient.getString("ingredient");
                String[] ingredientsArray = ingredients.split(", ");

                //Formatting the ingredients
                for (String ingredient : ingredientsArray) {
                    sb.append(ingredient).append("\n");
                }

                System.out.println("\nCategory: " + typeMeal +
                        "\nName: " + mealName +
                        "\nIngredients: " + sb.toString());

                //Resetting the StringBuilder
                sb.setLength(0);
                emptyTable = false;
            }

            if (emptyTable) {
                System.out.println("No meals saved. Add a meal first.");
            }

            statementIngredient.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error" + e.getMessage());
        }
    }

    public int size() {
        return this.listMeal.size();
    }

    public boolean checkTypeMeal(String typeMeal) {
        typeMeal = typeMeal.toLowerCase().trim();

        switch (typeMeal) {
            case "breakfast", "dinner", "lunch" -> {
                return true;
            }
            default -> {
                System.out.println("Wrong meal category! Choose from: breakfast, lunch, dinner.");
                return false;
            }
        }

    }

    public boolean patternOnlyLetters(String string) {
        Pattern pattern = Pattern.compile("^[a-zA-Z, ]+$");
        Matcher matcher = pattern.matcher(string);
        if (matcher.matches()) {
            return true;
        } else {
            System.out.println("Wrong format. Use letters only!");
            return false;
        }
    }

    public boolean patternOnlyLettersIngredients(String string) {
        Pattern pattern = Pattern.compile("^[a-zA-Z]+(?:\\s+[a-zA-Z]+)*(?:,\\s*[a-zA-Z]+(?:\\s+[a-zA-Z]+)*)*$");
        Matcher matcher = pattern.matcher(string);
        if (matcher.matches()) {
            return true;
        } else {
            System.out.println("Wrong format. Use letters only!");
            return false;
        }
    }

    public Connection getConnection() throws SQLException {
        //Connect to the PostgreSQL database
        String url = "jdbc:postgresql:meals_db";
        String username = "postgres";
        String password = "1111";
        return DriverManager.getConnection(url, username, password);
    }

    public void saveMealToDB(String mealType, String mealName, String ingredients) throws SQLException {
        //Get the connection to the PostgreSQL database
        String url = "jdbc:postgresql:meals_db";
        String username = "postgres";
        String password = "1111";
        int meal_id = 1;
        int ingredient_id = 1;
        boolean added = false;

        Connection conn = DriverManager.getConnection(url, username, password);
        conn.setAutoCommit(true);

        while (!added) {
            try {
                ingredient_id = meal_id;
                String sqlMeals = "INSERT INTO MEALS (meal_id, category, meal) VALUES (?, ?, ?)";
                PreparedStatement statement = conn.prepareStatement(sqlMeals);

                //Set the values for the parameters in the SQL statement
                statement.setInt(1, meal_id);
                statement.setString(2, mealType);
                statement.setString(3, mealName);

                String sqlIngredients = "INSERT INTO INGREDIENTS (ingredient, ingredient_id, meal_id) VALUES (?, ?, ?)";
                PreparedStatement statementIngredients = conn.prepareStatement(sqlIngredients);

                //Set the values for the parameters in the SQL statement
                statementIngredients.setString(1, ingredients);
                statementIngredients.setInt(2, ingredient_id);
                statementIngredients.setInt(3, meal_id);

                //Execute the SQL statement to insert the row into the table
                int rowsInserted = statement.executeUpdate();
                statementIngredients.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("The meal has been added!");
                }

                //Close the connection
                conn.close();
                added = true;
            } catch (Exception e) {
                meal_id++;
            }
        }
    }

}
