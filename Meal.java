

import java.util.*;

public abstract class Meal {
    private String mealName;
    private String mealCode;
    private double mealPrice;
    private boolean isTakeAway;
    private String remarks;
    private String mealStatus;
    private static double setDrinkPrice = 3;
    private static final String[] mealCodeArray = {"DR1", "DR2", "DR3", "DR4", "DR5", "D01", "D02", "D03", "D04", "D05",
            "IC1", "IC2", "IC3", "IC4", "N01", "N02", "N03", "N04", "N05", "NL1",
            "NL2", "NL3", "R01", "R02", "R03", "R04", "R05", "SD1", "SD2", "SD3",
            "SD4", "SD5", "WC1", "WC2", "WC3"};
    //Default meal codes at program start
    private static final String[] mealNameArray = {"Soya Bean Milk", "Chocolate Drink", "Tea", "Lemon Tea", "Coffee", "Vanilla Ice Cream", "Chocolate Pico", "Cendol", "Ice Kacang", "Mixed Ice Cream",
            "Paratha", "Thosai", "Briyani", "Chapati", "Curry Noodles", "Green Curry Noodles", "Fried Noodles", "Char Kuey Teow", "Fried Mee Hoon", "Nasi Lemak Fried Chicken",
            "Nasi Lemak Rendang", "Nasi Lemak Curry Chicken", "Spicy Fried Rice", "Ginger Fried Rice", "Steamed Chicken Rice", "Minced Chicken Rice", "Tom Yam Rice", "Curry Puff", "Pizza Slice", "Garlic Bread",
            "Mini Burger", "Waffle Toast", "Chicken Chop Rice", "Grilled Chicken", "Meatball Spaghetti"};
    //Default meal names at program start
    private static final Double[] mealPriceArray = {4.90, 5.50, 3.60, 4.25, 4.50, 3.45, 4.55, 4.75, 3.90, 4.45,
            8.90, 6.45, 14.95, 7.90, 9.90, 9.90, 9.50, 10.50, 9.50, 15.90,
            14.90, 14.90, 11.90, 10.90, 12.80, 11.50, 10.90, 6.90, 7.90, 5.50,
            9.90, 6.50, 14.50, 14.90, 13.90};
    //Default meal prices at program start
    private static ArrayList<String> mealCodes = new ArrayList<>(Arrays.asList(mealCodeArray));
    private static ArrayList<String> mealNames = new ArrayList<>(Arrays.asList(mealNameArray));
    private static ArrayList<Double> mealPrices = new ArrayList<>(Arrays.asList(mealPriceArray));
    //Conversion from array to array list to allow dynamic changes for code, name and price by manager

    public Meal(){
        this("", "", 0.0, false);
    }

    public Meal(String mealCode, String mealName, double mealPrice, boolean isTakeAway){
        this.mealName = mealName;
        this.mealCode = mealCode;
        this.mealPrice = mealPrice;
        this.isTakeAway = isTakeAway;
        remarks = "";
        mealStatus = "Cooking";
    }

    public String getMealName(){
        return mealName;
    }

    public void setMealName(String mealName){
        this.mealName = mealName;
    }


    public String getMealCode(){
        return mealCode;
    }

    public void setMealCode(String mealCode){
        this.mealCode = mealCode;
    }

    public double getMealPrice(){
        return mealPrice;
    }

    public void setMealPrice(double mealPrice){
        this.mealPrice = mealPrice;
    }

    public boolean getIsTakeAway() {
        return isTakeAway;
    }

    public void setIsTakeAway(boolean isTakeAway) {
        this.isTakeAway = isTakeAway;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getMealStatus() {
        return mealStatus;
    }

    public void setMealStatus(String mealStatus) {
        this.mealStatus = mealStatus;
    }

    public static double getSetDrinkPrice() {
        return setDrinkPrice;
    }

    public static void setSetDrinkPrice(double setDrinkPrice) {
        Meal.setDrinkPrice = setDrinkPrice;
    }

    public static ArrayList<String> getMealCodes() {
        return mealCodes;
    }

    public static void setMealCodes(ArrayList<String> mealCodes) {
        Meal.mealCodes = mealCodes;
    }

    public static ArrayList<String> getMealNames() {
        return mealNames;
    }

    public static void setMealNames(ArrayList<String> mealNames) {
        Meal.mealNames = mealNames;
    }

    public static ArrayList<Double> getMealPrices() {
        return mealPrices;
    }

    public static void setMealPrices(ArrayList<Double> mealPrices) {
        Meal.mealPrices = mealPrices;
    }

    public String mealOrderType(){
        if (getIsTakeAway())
            return "Take Away";
        else
            return "Dine-in";
    } //return string representation of meal order type

    public String remarks(){
        if (remarks.equals(""))
            return "";
        else
            return "\nRemarks: " + remarks;
    } //return remarks if there is one

    @Override
    public abstract String toString(); //Abstract method that must be implemented in subclasses

    public static void displayMeal(String mealCode){
        int mealIndex = mealCodes.indexOf(mealCode);
        System.out.println("\nMeal Code : " + mealCode);
        System.out.println("Meal Name : " + mealNames.get(mealIndex));
        System.out.printf("Meal Price: PKR%.2f\n", mealPrices.get(mealIndex));
    }

    public abstract double mealPrice(); //Abstract method that must be implemented in subclasses
}
