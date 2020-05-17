
import java.util.*;
import java.text.*;

public class Order{
    private int noOfPax;
    private Date orderTakenTime;
    private ArrayList<Meal> meals = new ArrayList<>();
    private Scanner input;
    private static final int GST = 6;

    public Order(){
        this(0);
    }

    public Order(int noOfPax){
        this.noOfPax = noOfPax;
        orderTakenTime = new Date();
    }

    public int getNoOfPax() {
        return noOfPax;
    }

    public void setNoOfPax(int noOfPax) {
        this.noOfPax = noOfPax;
    }

    public ArrayList<Meal> getMeals() {
        return meals;
    }

    public void setMeals(ArrayList<Meal> meals) {
        this.meals = meals;
    }

    public Date getOrderTakenTime() {
        return orderTakenTime;
    }

    public void setOrderTakenTime(Date orderTakenTime) {
        this.orderTakenTime = orderTakenTime;
    }

    public void addMeal(int tableNum){
        input = new Scanner(System.in);
        String mealCode = "";
        char answer;

        while(!mealCode.equals("0")){
            boolean isTakeAway = false;
            boolean codeExist = false;
            while(!codeExist){
                System.out.print("\nEnter meal code(0 to exit): ");
                mealCode = input.nextLine();
                if (mealCode.equals("0"))
                    break;
                codeExist = Meal.getMealCodes().contains(mealCode);
                if (!codeExist)
                    System.out.println("Meal code does not exist!");
            } //Check whether meal code entered exist in the system

            if (mealCode.equals("0"))
                continue; //Return to tables' statuses display

            int indexOfMeal = Meal.getMealCodes().indexOf(mealCode); //Retrieving common index of meal information
            System.out.println(Meal.getMealNames().get(indexOfMeal)); //Displaying meal name after receiving meal code

            answer = ' ';
            while(!(answer == 'N' || answer == 'Y')){
                System.out.print("Is the meal take away?\nEnter 'Y' for yes and 'N' for no: ");
                String answerS = input.nextLine();
                if (answerS.length() >= 1)
                    answer = Character.toUpperCase(answerS.charAt(0));
                if (answer == 'Y')
                    isTakeAway = true;
                if (!(answer == 'N' || answer == 'Y'))
                    System.out.println("Invalid input for whether the meal is take away!");
            } //Validate option whether the meal is dine-in or take away

            Meal meal;
            if (mealCode.contains("DR")){
                //Creating OrderedDrink object if the meal is a drink
                meal = new OrderedDrink(mealCode, Meal.getMealNames().get(indexOfMeal), Meal.getMealPrices().get(indexOfMeal), isTakeAway);
                addDrinkInfo(meal);
            }
            else{
                //Creating OrderedFood object if the meal is a drink
                meal = new OrderedFood(mealCode, Meal.getMealNames().get(indexOfMeal), Meal.getMealPrices().get(indexOfMeal), isTakeAway);
                addFoodInfo(meal, isTakeAway);
            }

            System.out.print("Enter special request for meal: ");
            String remarks = input.nextLine();
            meal.setRemarks(remarks); //Receiving special requests for meal

            meals.add(meal); //Add meal to order's meals list
            Table.getTables().get(tableNum-1).setStatus("OT");
            Table.getTables().get(tableNum-1).setOrder(this);
            System.out.println("Meal is added to order>>");
        }
    }

    public void addDrinkInfo(Meal meal){
        char drinkSize = ' ';
        char answer = ' ';
        boolean isDrinkHot = false;

        while (!(drinkSize == 'R' || drinkSize == 'L') || !(answer == 'N' || answer == 'Y')){
            //Receiving input for drink hotness and size
            System.out.print("Is the drink hot?\nEnter 'Y' for yes and 'N' for no: ");
            String answerS = input.nextLine();
            if (answerS.length() >= 1)
                answer = Character.toUpperCase(answerS.charAt(0));
            if (answer == 'Y')
                isDrinkHot = true;
            System.out.print("Enter drink size('R'-Regular and 'L'-Large): ");
            String drinkSizeS = input.nextLine();
            if (drinkSizeS.length() >= 1)
                drinkSize = Character.toUpperCase(drinkSizeS.charAt(0));
            if (!(answer == 'N' || answer == 'Y'))
                System.out.println("Invalid input for drink hotness!");
            if (!(drinkSize == 'R' || drinkSize == 'L'))
                System.out.println("Invalid input for drink size! Drink size is either 'R'-Regular or 'L'-Large");
        } //Validating input for drink hotness and drink size

        ((OrderedDrink)meal).setIsDrinkHot(isDrinkHot);
        ((OrderedDrink)meal).setDrinkSize(drinkSize); //Casting from Meal type to OrderedDrink type
    }

    public void addFoodInfo(Meal meal, boolean isTakeAway){
        String drinkCode = "";
        char answer = ' ';

        while(!(answer == 'N' || answer == 'Y')){
            //Receiving input whether the food is with drink or not
            System.out.print("Is the food with set drink?\nEnter 'Y' for yes and 'N' for no: ");
            String answerS = input.nextLine();
            if (answerS.length() >= 1)
                answer = Character.toUpperCase(answerS.charAt(0));
            if (!(answer == 'N' || answer == 'Y'))
                System.out.println("Invalid input for whether the food is with set drink!");
        }
        if (answer == 'Y'){ //If food is with drink, get drink information
            boolean codeExist = false;

            while(!codeExist){
                System.out.print("Enter drink code: ");
                drinkCode = input.nextLine();
                if (drinkCode.contains("DR"))
                    codeExist = Meal.getMealCodes().contains(drinkCode);
                if (!codeExist)
                    System.out.println("Drink code does not exist!");
            } //Validate drink code

            int indexOfDrink = Meal.getMealCodes().indexOf(drinkCode); //Retrieving common index of drink information
            System.out.println(Meal.getMealNames().get(indexOfDrink));
            Meal drink = new OrderedDrink(drinkCode, Meal.getMealNames().get(indexOfDrink), Meal.getMealPrices().get(indexOfDrink), isTakeAway);
            addDrinkInfo(drink); //Creating set drink for food and adding information for the drink
            ((OrderedFood)meal).setSetDrink((OrderedDrink)drink); //Casting from Meal type to OrderedFood type and sending drink as OrderedDrink type to set set drink in OrderedFood
        }
    }

    public void removeMeal(int tableNum){
        int mealNo = -1;
        ArrayList<Meal> meals = Table.getTables().get(tableNum-1).getOrder().getMeals();
        while(!(mealNo >= 0 && mealNo <= meals.size())){
            displayOrder(tableNum);
            System.out.print("Enter meal position in order list(0 to exit): ");
            String mealNoS = input.nextLine();
            try{
                mealNo = Integer.valueOf(mealNoS);
            }
            catch(NumberFormatException ex){
                System.out.println("Invalid input detected!");
            }
            if (!(mealNo >= 0 && mealNo <= meals.size()))
                System.out.println("Invalid meal position entered! Try again.");
        } //Receive input for meal position in order list and validate meal position input

        if (mealNo == 0){
            System.out.println("Meal order removing aborted!");
            return;
        } //If mealNo is 0, then exit the function

        meals.remove(mealNo-1);
        System.out.println("Meal successfully deleted from order list."); //Delete specified meal from order list
    }

    public void displayOrder(int tableNum){
        Order order = Table.getTables().get(tableNum-1).getOrder();

        if(order!=null){
            //Table header for order list printing
            System.out.print("\nNo. Meal Code Meal Name                Details                                                          Meal Status\n");
            System.out.println("--- --------- ------------------------ ---------------------------------------------------------------- -----------");
            int count = 1;
            for (Meal meal: order.getMeals()){
                System.out.printf("%-4d%s", count, meal.toString());
                count++;
            }
        } //If meals exist in order list, then print meals information
        else
            System.out.println("No meals ordered!");
    }

    public void deleteOrder(int tableNum){
        char answer = ' ';
        while(!(answer == 'Y' || answer == 'N')){
            System.out.print("Are you sure you want to delete the whole order?\nEnter 'Y' for yes and 'N' for no: ");
            String answerS = input.nextLine();
            if (answerS.length() >= 1)
                answer = Character.toUpperCase(answerS.charAt(0));
            if (!(answer == 'Y' || answer == 'N'))
                System.out.println("Invalid input detected! Try again.");
        } //Validate user option whether to delete order or not

        if (answer == 'Y'){
            Table.getTables().get(tableNum-1).setOrder(null);
            Table.getTables().get(tableNum-1).setStatus("NO");
            System.out.println("Order successfully deleted.");
        } //If answer is yes, then delete the order
    }

    public void changeTable(int tableNum){
        int newTableNum = 0;
        String status = "NO";
        while(!(newTableNum >= 1 && newTableNum <= Table.getTotalTables()) || !status.equals("NO")){
            System.out.print("Enter new table number: ");
            String tableNumS = input.nextLine();
            try {
                newTableNum = Integer.valueOf(tableNumS);
            }
            catch(NumberFormatException ex){
                System.out.println("Invalid input detected!");
            }
            if (!(newTableNum >= 1 && newTableNum <= Table.getTotalTables()))
                System.out.println("Invalid input for table number!");
            else
                status = Table.getTables().get(newTableNum-1).getStatus();
            if (!status.equals("NO"))
                System.out.println("Table entered is already occupied!");
        } //Validate new table number input and verify that the new table is not occupied

        //Create table objects to refer to old and new table
        Table oldTable = Table.getTables().get(tableNum-1);
        Table newTable = Table.getTables().get(newTableNum-1);

        newTable.setOrder(oldTable.getOrder()); //Retrieve order from old table and set to new table
        newTable.setStatus(oldTable.getStatus()); //Retrieve status from old table and set to new table
        oldTable.setOrder(null); //Delete order for old table
        oldTable.setStatus("NO"); //Change table status to not occupied for old table
        System.out.println("Table number changed successfully!");
    }

    public void printReceipt(int tableNum){
        //Variables to determine whether order contains take away and dine-in meals
        boolean haveDineIn = false;
        boolean haveTakeAway = false;
        //Variables to determine receipt values
        double total = 0, subTotal = 0;
        double discount = 0;
        double gst = 0;

        Order order = Table.getTables().get(tableNum-1).getOrder();
        int totWidth = Manager.getShopName().length() + 33;

        //Printing receipt header
        System.out.println("\n*-*-*-*-*-*Welcome to " + Manager.getShopName() + "*-*-*-*-*-*");

        for (int i=0; i<(totWidth-11)/2; i++)
            System.out.print(" ");
        System.out.println("TAX INVOICE");

        for(int i=0; i<totWidth; i++)
            System.out.print("-");
        //Printing order taken time
        SimpleDateFormat ft = new SimpleDateFormat ("E dd.MM.yyyy  hh:mm:ss a");
        System.out.println("\n" + ft.format(order.getOrderTakenTime()));
        //Printing table number
        System.out.println("\nTABLE NO: " + tableNum);
        for(int i=0; i<totWidth; i++)
            System.out.print("-");
        System.out.println();

        ArrayList<Meal> takeAwayMeals = new ArrayList<>();
        ArrayList<Meal> dineInMeals = new ArrayList<>();

        //Separating take away and dine-in meals into two array lists
        for(Meal meal: meals){
            subTotal += meal.mealPrice();
            if(meal.getIsTakeAway()){
                haveTakeAway = true;
                takeAwayMeals.add(meal);
            }
            else{
                haveDineIn = true;
                dineInMeals.add(meal);
            }
        }
        //Printing the meals according to dine-in and take away
        if(haveDineIn){
            for (int i=0; i<(totWidth-17)/2; i++)
                System.out.print(" ");
            System.out.println("---- Dine In ----");
            receiptMealDisplay(dineInMeals);
        }
        if(haveTakeAway){
            System.out.println();
            for (int i=0; i<(totWidth-19)/2; i++)
                System.out.print(" ");
            System.out.println("---- Take Away ----");
            receiptMealDisplay(takeAwayMeals);
        }

        //If minimum amount is spent, then allow and calculate discount
        if (subTotal >= Manager.getMinimumAmount())
            discount = Manager.getDiscountRate()/100.0*subTotal;

        total = subTotal - discount; //Calculating total after discount
        gst = subTotal*GST/100;
        total += gst; //Calculating total after GST at 6%
        //Printing subTotal and total after discount and GST
        System.out.printf("\nSub Total(PKR):\t\t\t %5.2f\n", subTotal);
        System.out.printf("Discount(" + Manager.getDiscountRate() + "%%)(PKR):\t\t %5.2f\n", discount);
        System.out.printf("GST(6%%)(PKR):\t\t\t %5.2f\n", gst);
        System.out.printf("Total(PKR):\t\t\t %5.2f\n", total);

        //Finding the rounding amount and rounding the total appropriately
        String totalS = String.format("%.2f", total);
        char lastSen = totalS.charAt(totalS.length()-1);
        double rounding = 0;
        if (lastSen == '1' || lastSen == '2' || lastSen == '6' || lastSen == '7'){
            if (lastSen == '2' || lastSen == '7')
                rounding = -0.02;
            else
                rounding = -0.01;
        }
        else if(lastSen == '3' || lastSen == '4' || lastSen == '8' || lastSen == '9'){
            if (lastSen == '3' || lastSen == '8')
                rounding = 0.02;
            else
                rounding = 0.01;
        }
        total += rounding;

        System.out.printf("Rounding(PKR):\t\t\t %5.2f\n", rounding);
        System.out.printf("Total(PKR):\t\t\t %5.2f\n", total);

        //Printing order closing time
        System.out.println("\nCLOSED: " + ft.format(new Date()));
        for(int i=0; i<totWidth; i++)
            System.out.print("-");
        System.out.println();

        for (int i=0; i<(totWidth-9)/2; i++)
            System.out.print(" ");
        System.out.println("Thank You For Visit " + Manager.getShopName());
        for (int i=0; i<(totWidth-17)/2; i++)
            System.out.print(" ");
        System.out.println("Please Come Again");

        Table.getTables().get(tableNum-1).setStatus("NO");
    }

    public void receiptMealDisplay(ArrayList<Meal> meals){
        String setIndicator = "";
        for (Meal meal: meals){
            //To print set meals(food) with the indication "Set" and otherwise "Ala"
            if(!meal.getMealCode().contains("DR")){
                if (((OrderedFood)meal).getSetDrink() != null)
                    setIndicator = "Set";
                else
                    setIndicator = "Ala";
            }
            else
                setIndicator = "";
            //Print out each meal with ordered with the respective information
            System.out.printf("%s %s %-25s%5.2f\n", meal.getMealCode(), setIndicator, meal.getMealName(), meal.mealPrice());
        }
    }

    public static void displayTableOrders(ArrayList<Table> tables){
        tables.clear();
        //Searching for tables with order and adding them to tables list
        for (Table table: Table.getTables()){
            if (table.getStatus() == "OT")
                tables.add(table);
        }
        if(tables.isEmpty())
            return; //Return to system access menu
        //Sorting the tables according to time in which order is taken
        Collections.sort(tables);
        //Display orders with table number according to which order came first
        System.out.println("\nAll Orders\n----------");
        for (Table table: tables){
            System.out.print("\nTable Number: " + table.getTableNum());
            table.getOrder().displayOrder(table.getTableNum());
        }
    }

    public static void setOrderReady(Table chosenTable){
        boolean isOrderReady = true;
        //If all meal is ready, set order as ready
        for(Meal meal: chosenTable.getOrder().getMeals()){
            if(meal.getMealStatus().equals("Cooking")){
                isOrderReady = false;
                break;
            }
        }
        if (isOrderReady)
            chosenTable.setStatus("FR");
    }

    public void setOrderServed(int tableNum){
        char option = ' ';
        while (!(option == 'N' || option == 'Y')){
            System.out.print("\nMeal Served?\nEnter 'Y' for yes and 'N' for no: ");
            String optionS = input.nextLine();
            if (optionS.length() >= 1)
                option = Character.toUpperCase(optionS.charAt(0));
            if (option == 'Y'){
                Table.getTables().get(tableNum-1).setStatus("FS");
                ArrayList<Meal> meals = Table.getTables().get(tableNum-1).getOrder().getMeals();
                for (Meal meal: meals){
                    meal.setMealStatus("Served");
                }
                System.out.println("Meal served.");
            }
            if (!(option == 'N' || option == 'Y'))
                System.out.println("Invalid input detected! Try again.");
        } //Receive input whether meal is served and set table status to Food Served
    }
}
