public class OrderedFood extends Meal{
    private OrderedDrink setDrink;

    public OrderedFood(){
        this("", "", 0.0, false);
    }

    public OrderedFood(String mealCode, String mealName, double mealPrice, boolean isTakeAway){
        super(mealCode, mealName, mealPrice, isTakeAway);
    }

    public OrderedDrink getSetDrink() {
        return setDrink;
    }

    public void setSetDrink(OrderedDrink setDrink) {
        this.setDrink = setDrink;
    }

    public String setDrink(){
        if (setDrink!=null){
            return "Set Drink: " + setDrink.getMealCode() + "-" + setDrink.getMealName() + "---" + setDrink.drinkHotness() + "---" + setDrink.drinkSize();
        }
        else
            return "No Set Drink";
    }

    public String toString(){
        //Returning food order information
        String mealDetails = "-" + mealOrderType() + "---" + setDrink();
        return String.format("%-10s%-25s%-65s", getMealCode(), getMealName(), mealDetails) + getMealStatus() + remarks() + "\n";
    }

    public double mealPrice(){
        //Abstract method in superclass implemented to return meal price including set drink
        if(setDrink == null)
            return getMealPrice();
        else
            return getMealPrice() + getSetDrinkPrice();
    }
}
