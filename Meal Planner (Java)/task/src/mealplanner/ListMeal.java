package mealplanner;

import javax.xml.transform.Result;
import java.io.File;
import java.io.IOException;
import java.time.Period;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.sql.*;
import java.io.FileWriter;

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

//    public void showMeals() {
//        try {
//            Connection conn = getConnection();
//
//            //Selecting from meals database
//            PreparedStatement statement = conn.prepareStatement("SELECT * FROM MEALS");
//            ResultSet rs = statement.executeQuery();
//
//            //Selecting from ingredients database
//            PreparedStatement statementIngredient = conn.prepareStatement("SELECT * FROM INGREDIENTS");
//            ResultSet rsIngredient = statementIngredient.executeQuery();
//
//            boolean emptyTable = true;
//            while (rs.next() && rsIngredient.next()) {
//                StringBuilder sb = new StringBuilder("\n");
//                String typeMeal = rs.getString("category");
//                String mealName = rs.getString("meal");
//                String ingredients = rsIngredient.getString("ingredient");
//                String[] ingredientsArray = ingredients.split(", ");
//
//                //Formatting the ingredients
//                for (String ingredient : ingredientsArray) {
//                    sb.append(ingredient).append("\n");
//                }
//
//                System.out.println("\nCategory: " + typeMeal +
//                        "\nName: " + mealName +
//                        "\nIngredients: " + sb.toString());
//
//                //Resetting the StringBuilder
//                sb.setLength(0);
//                emptyTable = false;
//            }
//
//            if (emptyTable) {
//                System.out.println("No meals saved. Add a meal first.");
//            }
//
//            statementIngredient.close();
//            statement.close();
//            conn.close();
//        } catch (SQLException e) {
//            System.out.println("Error" + e.getMessage());
//        }
//    }

    public void showLunchMeals() {
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
                int meal_ID = rs.getInt("meal_id");
                int ingredient_ID = rsIngredient.getInt("meal_id");
                String mealType = rs.getString("category");
                String mealName = rs.getString("meal");
                String ingredients = rsIngredient.getString("ingredient");
                String[] ingredientsArray = ingredients.split(", ");

                if (mealType.equals("lunch") && meal_ID == ingredient_ID) {
                    if (emptyTable) {
                        System.out.println("Category: lunch");
                    }

                    //Formatting the ingredients
                    for (String ingredient : ingredientsArray) {
                        sb.append(ingredient).append("\n");
                    }

                    System.out.println("Name: " + mealName +
                            "\nIngredients: " + sb.toString());

                    emptyTable = false;
                }
                //Resetting the StringBuilder
                sb.setLength(0);

            }

            if (emptyTable) {
                System.out.println("No meals found.");
            }

            statementIngredient.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error" + e.getMessage());
        }
    }

    public void showBreakfastMeals() {
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
                int meal_ID = rs.getInt("meal_id");
                int ingredient_ID = rsIngredient.getInt("meal_id");
                String mealType = rs.getString("category");
                String mealName = rs.getString("meal");
                String ingredients = rsIngredient.getString("ingredient");
                String[] ingredientsArray = ingredients.split(", ");

                if (mealType.equals("breakfast") && meal_ID == ingredient_ID) {
                    if (emptyTable) {
                        System.out.println("Category: breakfast");
                    }

                    //Formatting the ingredients
                    for (String ingredient : ingredientsArray) {
                        sb.append(ingredient).append("\n");
                    }

                    System.out.println("Name: " + mealName +
                            "\nIngredients: " + sb.toString());

                    emptyTable = false;
                }
                //Resetting the StringBuilder
                sb.setLength(0);

            }

            if (emptyTable) {
                System.out.println("No meals found.");
            }

            statementIngredient.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error" + e.getMessage());
        }
    }

    public void showDinnerMeals() {
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
                int meal_ID = rs.getInt("meal_id");
                int ingredient_ID = rsIngredient.getInt("meal_id");
                String mealType = rs.getString("category");
                String mealName = rs.getString("meal");
                String ingredients = rsIngredient.getString("ingredient");
                String[] ingredientsArray = ingredients.split(", ");

                if (mealType.equals("dinner") && meal_ID == ingredient_ID) {
                    if (emptyTable) {
                        System.out.println("Category: dinner");
                    }

                    //Formatting the ingredients
                    for (String ingredient : ingredientsArray) {
                        sb.append(ingredient).append("\n");
                    }

                    System.out.println("Name: " + mealName +
                            "\nIngredients: " + sb.toString());

                    emptyTable = false;
                }
                //Resetting the StringBuilder
                sb.setLength(0);

            }

            if (emptyTable) {
                System.out.println("No meals found.");
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

    public boolean checkTypeMealToShow(String typeMeal) {
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

    public boolean isPlanEmpty() throws SQLException {
        Connection conn = getConnection();
        Statement statement = conn.createStatement();

        ResultSet rs = statement.executeQuery("SELECT * FROM PLAN");

        if (rs.next()) {
            return false;
        } else {
            return true;
        }

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
                //maybe need to change this
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

    public void planWeekMealsFromScratch(Scanner scanner) throws SQLException {
        //Initializing Arraylist with days of the week
        ArrayList<String> daysOfTheWeek = new ArrayList<>(Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"));

        //For loop for adding meals to each day
        for (String day : daysOfTheWeek) {
            System.out.println(day);
            //Adding breakfast
            listBreakfastMeals();
            System.out.println("Choose the breakfast for " + day + " from the list above:");
            String breakfast = scanner.nextLine();
            //This checks whether the item its on the meal list or not
            while (getMealID(breakfast) == -1) {
                System.out.println("This meal doesn’t exist. Choose a meal from the list above.");
                breakfast = scanner.nextLine();
            }
            insertBreakfast(getMealID(breakfast), breakfast);

            //Adding Lunch
            listLunchMeals();
            System.out.println("Choose the lunch for " + day + " from the list above:");
            String lunch = scanner.nextLine();
            while (getMealID(lunch) == -1) {
                System.out.println("This meal doesn’t exist. Choose a meal from the list above.");
                lunch = scanner.nextLine();
            }
            insertLunch(getMealID(lunch), lunch);

            //Adding Dinner
            listDinnerMeals();
            System.out.println("Choose the dinner for " + day + " from the list above:");
            String dinner = scanner.nextLine();
            //This checks whether the item its on the meal list or not
            while (getMealID(dinner) == -1) {
                System.out.println("This meal doesn’t exist. Choose a meal from the list above.");
                dinner = scanner.nextLine();
            }
            insertDinner(getMealID(dinner), dinner);

            System.out.println("Yeah! We planned the meals for " + day + ".\n");
        }
        printMealsForTheWeek();
    }

    public void printMealsForTheWeek() throws SQLException {
        //Initializing Arraylist with days of the week
        ArrayList<String> daysOfTheWeek = new ArrayList<>(Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"));

        //Creating connection with database
        Connection conn = getConnection();

        //Statements for each category meal
        PreparedStatement breakfastStatement = conn.prepareStatement("SELECT * FROM PLAN WHERE CATEGORY = 'breakfast'");
        PreparedStatement lunchStatement = conn.prepareStatement("SELECT * FROM PLAN WHERE CATEGORY = 'lunch'");
        PreparedStatement dinnerStatement = conn.prepareStatement("SELECT * FROM PLAN WHERE CATEGORY = 'dinner'");

        //ResultSets for each category meal
        ResultSet rsBreakfast = breakfastStatement.executeQuery();
        ResultSet rsLunch = lunchStatement.executeQuery();
        ResultSet rsDinner = dinnerStatement.executeQuery();

        //Printing meals by day
        for (String day : daysOfTheWeek) {
            System.out.println(day);
            rsBreakfast.next();
            rsLunch.next();
            rsDinner.next();
            String breakfast = rsBreakfast.getString("meal");
            String lunch = rsLunch.getString("meal");
            String dinner = rsDinner.getString("meal");
            System.out.println("Breakfast: " + breakfast);
            System.out.println("Lunch: " + lunch);
            System.out.println("Dinner: " + dinner + "\n");
        }


    }

    public int getMealID(String userMealInput) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement statement = conn.prepareStatement("SELECT * FROM MEALS");

        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            int meal_id = rs.getInt("meal_id");
            String mealName = rs.getString("meal");

            if (mealName.equals(userMealInput)) {
                return meal_id;
            }
        }
        return -1;
    }

    public void insertBreakfast(int meal_id, String mealName) throws SQLException {
        Connection conn = getConnection();

        String sqlBreakfast = "INSERT INTO PLAN (meal_id, category, meal) VALUES (?, ?, ?)";
        PreparedStatement statement = conn.prepareStatement(sqlBreakfast);

        statement.setInt(1, meal_id);
        statement.setString(2, "breakfast");
        statement.setString(3, mealName);

        statement.executeUpdate();
        conn.close();
    }

    public void insertLunch(int meal_id, String mealName) throws SQLException {
        Connection conn = getConnection();

        String sqlLunch = "INSERT INTO PLAN (meal_id, category, meal) VALUES (?, ?, ?)";
        PreparedStatement statement = conn.prepareStatement(sqlLunch);

        statement.setInt(1, meal_id);
        statement.setString(2, "lunch");
        statement.setString(3, mealName);

        statement.executeUpdate();
        conn.close();
    }

    public void insertDinner(int meal_id, String mealName) throws SQLException {
        Connection conn = getConnection();

        String sqlDinner = "INSERT INTO PLAN (meal_id, category, meal) VALUES (?, ?, ?)";
        PreparedStatement statement = conn.prepareStatement(sqlDinner);

        statement.setInt(1, meal_id);
        statement.setString(2, "dinner");
        statement.setString(3, mealName);

        statement.executeUpdate();
        conn.close();
    }

    public void listBreakfastMeals() throws SQLException {
        Connection conn = getConnection();

        PreparedStatement statement = conn.prepareStatement("SELECT * FROM MEALS ORDER BY MEAL");
        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            String category = rs.getString("category");
            if (category.equals("breakfast")) {
                String meal = rs.getString("meal");
                System.out.println(meal);
            }
        }

    }

    public void listLunchMeals() throws SQLException {
        Connection conn = getConnection();

        PreparedStatement statement = conn.prepareStatement("SELECT * FROM MEALS ORDER BY MEAL");
        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            String category = rs.getString("category");
            if (category.equals("lunch")) {
                String meal = rs.getString("meal");
                System.out.println(meal);
            }
        }
    }

    public void listDinnerMeals() throws SQLException {
        Connection conn = getConnection();

        PreparedStatement statement = conn.prepareStatement("SELECT * FROM MEALS ORDER BY MEAL");
        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            String category = rs.getString("category");
            if (category.equals("dinner")) {
                String meal = rs.getString("meal");
                System.out.println(meal);
            }
        }
    }

    public ArrayList<String> ingredientList() throws SQLException {
        ArrayList<String> ingredientsList = new ArrayList<>();

        Connection conn = getConnection();

        PreparedStatement statementMealIDIngredients = conn.prepareStatement("SELECT * FROM INGREDIENTS");
        PreparedStatement statementMealIDPlan = conn.prepareStatement("SELECT * FROM PLAN");


        ResultSet rsPlan = statementMealIDPlan.executeQuery();
        ResultSet rsIngredients = statementMealIDIngredients.executeQuery();
        rsPlan.next();

        //There's 21 meals on the plan, so that's why 21 a constant variable
        int i = 1;
        for (int j = i; i <= 21;) {
            rsIngredients.next();
            int meal_ID_Ingredient = rsIngredients.getInt("meal_id");
            String ingredients = rsIngredients.getString("ingredient");
            int meal_ID_Plan = rsPlan.getInt("meal_id");

            if (meal_ID_Ingredient == meal_ID_Plan) {
                rsIngredients = statementMealIDIngredients.executeQuery();
                String[] ingredientsSplit = ingredients.split(",");
                //Splitting the ingredients to add it to the List
                for (String ingredient : ingredientsSplit) {
                    ingredientsList.add(ingredient);
                }
                rsPlan.next();
                i++;
            }
        }
        return ingredientsList;
    }

    public String ingredientsToString() throws SQLException {
        StringBuilder sb = new StringBuilder();
        ArrayList<String> ingredientsTotal = new ArrayList<>();
        ArrayList<String> ingredientList = ingredientList();

        for (int i = 0; i < ingredientList.size(); i++) {
            int counter = 1;
            for (int j = i + 1; j <= ingredientList.size(); j++) {
                if (ingredientsTotal.toString().contains(ingredientList.get(i))) {
                    break;
                }
                else if (j == ingredientList.size()) {
                    continue;
                }
                else if (ingredientList.get(i).equals(ingredientList.get(j))) {
                    counter++;
                }
            }
            if (!ingredientsTotal.toString().contains(ingredientList.get(i))) {
                if (counter == 1) {
                    ingredientsTotal.add(ingredientList.get(i));
                } else {
                    String counterToString = String.valueOf(counter);
                    ingredientsTotal.add(ingredientList.get(i) + " x" + counterToString);
                }
            }
        }

        for (String x : ingredientsTotal) {
            sb.append(x).append("\n");
        }

        return sb.toString();

    }

    public void saveFile(String fileName) throws IOException {
        try {
            FileWriter writer = new FileWriter(fileName);
            writer.write(ingredientsToString());
            writer.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

}
