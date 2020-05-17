public class OrderedDrink extends Meal{
    private boolean isDrinkHot;
    private char drinkSize;

    public OrderedDrink(){
        this("", "", 0.0, false);
    }

    public OrderedDrink(String mealCode, String mealName, double mealPrice, boolean isTakeAway){
        super(mealCode, mealName, mealPrice, isTakeAway);
    }

    public boolean isDrinkHot() {
        return isDrinkHot;
    }

    public void setIsDrinkHot(boolean isDrinkHot) {
        this.isDrinkHot = isDrinkHot;
    }

    public char getDrinkSize() {
        return drinkSize;
    }

    public void setDrinkSize(char drinkSize) {
        this.drinkSize = drinkSize;
    }

    public String drinkHotness(){
        if (isDrinkHot)
            return "Hot";
        else
            return "Cold";
    } //return string representation of drink hotness

    public String drinkSize(){
        if (drinkSize == 'R')
            return "Regular";
        else
            return "Large";
    } //return string representation of drink size

    public String toString(){
        //Returning drink order information
        String mealDetails = "-" + mealOrderType() + "---" + drinkHotness() + "---" + drinkSize();
        return String.format("%-10s%-25s%-65s", getMealCode(), getMealName(), mealDetails) + getMealStatus() + remarks() + "\n";
    }

    public double mealPrice(){
        //Abstract method in superclass implemented to return meal price
        return getMealPrice();
    }
}
