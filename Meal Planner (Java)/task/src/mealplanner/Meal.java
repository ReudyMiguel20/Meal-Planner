package mealplanner;

import java.sql.*;

public class Meal {
    private String mealType;
    private String mealName;
    private String[] splitter;

    public Meal(String mealType, String mealName, String ingredients) {
        this.mealType = mealType;
        this.mealName = mealName;
        this.splitter = ingredients.split(", ");
    }

    public String getMealType() {
        return mealType;
    }

    public String getMealName() {
        return mealName;
    }

    public String getIngredients() {
        StringBuilder sb = new StringBuilder("\n");

        for (String ingredient : splitter) {
            sb.append(ingredient).append("\n");
        }
        return sb.toString();
    }

    public String toString() {
        return "\nCategory: " + this.getMealType() +
                "\nName: " + this.getMealName() +
                "\nIngredients: " + getIngredients();
    }

}