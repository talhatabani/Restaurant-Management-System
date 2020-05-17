import java.util.*;
public class RestaurantApp {
    private static Scanner input;

    public static void main(String[] args){
        input = new Scanner(System.in);

        // System.out.print("Enter Restaurant: ");
        // String shopName = input.nextLine();
        Manager.setShopName("DESI DHABA");
        System.out.println("\n*-*-*-*-*-* Welcome to " + Manager.getShopName() + " *-*-*-*-*-*");

        Manager manager = new Manager();
        manager.createNewManager("Azhar", "azhar", "123");
        // Table.configTable();
        // Table.displayTable(Table.getTables());

        String username = "", password = "";
        while (true){
            boolean isValid = false;
            while (!isValid){
                System.out.println("\n" + Manager.getShopName() + " Login");
                for(int i=0; i<Manager.getShopName().length()+6; i++)
                    System.out.print("-");
                System.out.print("\nEnter username('0' to terminate system): ");
                username = input.nextLine();
                if (username.equals("0"))
                    break;
                System.out.print("Enter password: ");
                password = input.nextLine();

                manager = new Manager(username, password);
                isValid = manager.isValidManager();

                if (!isValid)
                    System.out.println("Username or Password is incorrect! Try again.\n");
            } //Validate and verify manager
            if (username.equals("0")){
                exitRMS();
                continue;
            }
            Table.configTable(); //Configure tables after login
            enterMenu(); //Enter menu after validation of manager
        }
    }

    public static void enterMenu(){
        char access = ' ';
        while(access!='4'){
            printSystemAccess(); //Print users of the system and menu exit option

            while (!(access>='1' && access<='4')){
                System.out.print("Choose System Access: ");
                String systemAccess = input.nextLine();
                if (systemAccess.length() >= 1 && !(access>='1' && access<='4'))
                    access = systemAccess.charAt(0);
                if (!(access>='1' && access<='4'))
                    System.out.println("Invalid System Access! Try again.");
            } //Validate user option

            enterFunctions(access); //Enter chosen system access

            if (access!='4')
                access = ' '; //Resetting access value to repeat menu option
        }
        System.out.println(Manager.getShopName() + " Successfully Logged Out.");
    }

    public static void printSystemAccess(){
        System.out.println();
        System.out.println("1.Waiter");
        System.out.println("2.Cook");
        System.out.println("3.Manager");
        System.out.println("4.Log Out");
        System.out.println("---------------------"); //Printing system access options
    }

    public static void enterFunctions(char access){

        if (access=='1'){
            int tableNum = -1;

            while (tableNum != 0){
                System.out.println();
                Table.displayTable(Table.getTables()); //Displaying current tables' statuses
                ArrayList<Integer> options = new ArrayList<>();
                while(!(tableNum >= 0 && tableNum <= Table.getTotalTables())){
                    System.out.print("Enter table number(0 to exit): ");
                    String tableNumS = input.nextLine();
                    try {
                        tableNum = Integer.valueOf(tableNumS);
                    }
                    catch(NumberFormatException ex){
                        System.out.println("Invalid input detected!");
                    }
                    if (!(tableNum >= 0 && tableNum <= Table.getTotalTables()))
                        System.out.println("Invalid input for table number!");
                } //Validate table number input

                if (tableNum == 0) //If tableNum is 0, exit to system access menu
                    break;

                String status = Table.getTables().get(tableNum-1).getStatus(); //Getting table status

                if (status == "NO"){
                    int noOfPax = 0;
                    System.out.println("\nCreate New Order\n----------------");

                    while(!(noOfPax >= 1)){
                        System.out.print("Enter number of customer(s): ");
                        String noOfPaxS = input.nextLine();
                        try {
                            noOfPax = Integer.valueOf(noOfPaxS);
                        }
                        catch(NumberFormatException ex){
                            System.out.println("Invalid input detected!");
                        }
                        if (noOfPax < 1)
                            System.out.println("Invalid input for number of customers!");
                    } //Validate number of customers input

                    Order order = new Order(noOfPax); //Create new order
                    order.addMeal(tableNum); //Add meals to order
                }
                else if (status == "OT"){
                    //If table status is "OT", then five options are given
                    options.clear();
                    for (int i=0; i<5; i++)
                        options.add(i);
                    System.out.println();
                    int option = menuEntry(options); //Display menu options and receive menu option
                    enterFunctions(option, tableNum); //Perform the specified menu option
                }
                else if (status == "FR"){
                    //If table status is "FR", then three options are given
                    options.clear();
                    options.add(0);
                    options.add(2);
                    options.add(4);
                    options.add(5);
                    System.out.println();
                    int option = menuEntry(options); //Display menu options and receive menu option
                    enterFunctions(option, tableNum); //Perform the specified menu option
                }
                else if (status == "FS"){
                    //If table status is "FS", then four options are given
                    options.clear();
                    options.add(0);
                    options.add(2);
                    options.add(4);
                    options.add(6);
                    System.out.println();
                    int option = menuEntry(options); //Display menu options and receive menu option
                    enterFunctions(option, tableNum); //Perform the specified menu option
                }

                tableNum = -1; //To make loop continuity correctly
            } //To repeat display tables' statuses, table number receiving and operations
        }

        else if(access=='2'){
            int tableNum = -1;
            boolean isExist;
            Table chosenTable = null;
            //Creating array list of tables for containing tables with order
            ArrayList<Table> tables = new ArrayList<>();

            while(tableNum != 0){
                tableNum = -1;
                Order.displayTableOrders(tables); //Display all table orders

                if (tables.isEmpty()){
                    System.out.println("No orders at the moment.");
                    break;
                }
                isExist = false;
                while(!(tableNum >= 0 && isExist)){
                    System.out.print("\nEnter table number(0 to exit): ");
                    String tableNumS = input.nextLine();
                    try {
                        tableNum = Integer.valueOf(tableNumS);
                    }
                    catch(NumberFormatException ex){
                        System.out.println("Invalid input detected!");
                    }
                    if (tableNum == 0)
                        break;
                    for (Table table: tables){
                        if (table.getTableNum() == tableNum){
                            isExist = true;
                            chosenTable = table; //Assigning chosenTable with the table that matches tableNum
                            break;
                        }
                    }
                    if (!(tableNum >= 0 && isExist))
                        System.out.println("Table number entered does not exist in order list!");
                } //Validate table number input

                if (tableNum == 0)
                    continue; //Return to system access menu

                //Getting total number of meals present in the table order
                int numOfOrders = chosenTable.getOrder().getMeals().size();
                int orderNo = 0;
                while(!(orderNo >= 1 && orderNo <= numOfOrders)){
                    System.out.print("Enter which meal have been cooked: ");
                    String orderNoS = input.nextLine();
                    try{
                        orderNo = Integer.valueOf(orderNoS);
                    }
                    catch(NumberFormatException ex){
                        System.out.println("Invalid input detected!");
                    }
                    if (!(orderNo >= 1 && orderNo <= numOfOrders))
                        System.out.println("Order number entered is invalid! Try again.");
                } //Receiving order number of the orders in list of the chosen table number

                //Creating Meal object to hold the chosen meal
                Meal chosenMeal = chosenTable.getOrder().getMeals().get(orderNo-1);
                //Getting whether the meal chosen in order list is already cooked
                boolean isMealCooking = chosenMeal.getMealStatus().equals("Cooking");
                //Set the chosen meal as ready
                if (isMealCooking)
                    chosenMeal.setMealStatus("Ready");
                else
                    System.out.println("The meal chosen is already cooked!");
                Order.setOrderReady(chosenTable); //If all meal is ready, set the order as ready
            }
        }

        else if (access=='3'){
            Manager manager = null;
            String username = "", password = "";

            while (!username.equals("0")){
                boolean isValid = false;
                while (!isValid){
                    System.out.println("\n" + Manager.getShopName() + " Manager Login");
                    for(int i=0; i<Manager.getShopName().length()+14; i++)
                        System.out.print("-");
                    System.out.print("\nEnter username('0' to exit): ");
                    username = input.nextLine();
                    if (username.equals("0"))
                        break;
                    System.out.print("Enter password: ");
                    password = input.nextLine();

                    manager = new Manager(username, password);
                    isValid = manager.isValidManager();

                    if (!isValid)
                        System.out.println("Username or Password is incorrect! Try again.\n");
                } //Validate and verify manager

                if (username.equals("0"))
                    continue; //Exit manager system access immediately and return to choose system access

                char option = ' ';
                while (option != '0'){
                    displayManagerMenu(); //Display manager functions menu
                    option = ' ';
                    while (!(option >= '0' && option <= '8')){
                        System.out.print("Enter menu option(0 to exit): ");
                        String optionS = input.nextLine();
                        if (optionS.length() >= 1)
                            option = optionS.charAt(0);
                        if (!(option >= '0' && option <= '8'))
                            System.out.println("Invalid option! Try again.");
                    } //Validate manager option

                    if (option == '0')
                        continue; //If option is 0, then return to manager login
                    else if (option == '1')
                        manager.editMenu();
                    else if (option == '2')
                        manager.addMenu();
                    else if (option == '3')
                        manager.deleteMenu();
                    else if (option == '4'){
                        manager.setPromotion();
                    }
                    else if (option == '5'){
                        boolean haveOrder = false;
                        for (Table table: Table.getTables()){
                            if (!table.getStatus().equals("NO")){
                                haveOrder = true;
                                break;
                            } //If any table has order, then table configuration is not allowed
                        }
                        if (!haveOrder)
                            Table.configTable();
                        else
                            System.out.println("Table Configurations not allowed while table contains order!");
                    }
                    else if (option == '6')
                        manager.createNewManager();
                    else if (option == '7'){
                        manager.deleteManager();
                    }
                    else if (option == '8')
                        manager.displayManager();
                    //Enter respective functions corresponding to manager option
                }
            }
        }
    }

    public static int menuEntry(ArrayList<Integer> options){
        //All possible order functions listed in array of String
        String[] orderFunctions = {"Add Meal", "Remove Meal", "Display Order", "Delete Order", "Change Table", "Serve Meal", "Print Receipt"};
        int functionsNo = 1;
        for (int i=0; i<orderFunctions.length; i++){
            if (options.contains(i)){
                System.out.println(functionsNo + ". " + orderFunctions[i]);
                functionsNo++;
            }
        } //To display allowed functions according to table status
        System.out.println("------------------");

        int option = 0;
        while (!(option >= 1 && option <= options.size())){
            System.out.print("Enter menu function: ");
            String optionS = input.nextLine();
            try{
                option = Integer.valueOf(optionS);
            }
            catch(NumberFormatException ex){
                System.out.println("Invalid input detected!");
            }
            if (!(option >= 1 && option <= options.size()))
                System.out.println("Invalid option! Try again.");
        } //Validate option received and verify that allowed function is chosen

        option = options.get(option-1) + 1; //Make sure option is mapped to right option in options list
        return option;
    }

    public static void enterFunctions(int option, int tableNum){
        Order order = Table.getTables().get(tableNum-1).getOrder();

        if (option == 1)
            order.addMeal(tableNum);
        else if (option == 2)
            order.removeMeal(tableNum);
        else if (option == 3){
            order.displayOrder(tableNum);
            System.out.print("\nPress 'Enter' to continue...");
            input.nextLine();
        }
        else if (option == 4)
            order.deleteOrder(tableNum);
        else if (option == 5)
            order.changeTable(tableNum);
        else if (option == 6)
            order.setOrderServed(tableNum);
        else if (option == 7){
            order.printReceipt(tableNum);
            System.out.print("\nPress 'Enter' to continue...");
            input.nextLine();
        }
        //Enter respective functions according to option
    }

    public static void displayManagerMenu(){
        System.out.println();
        System.out.println("1. Edit Meal Menu");
        System.out.println("2. Add Meal Menu");
        System.out.println("3. Delete Meal Menu");
        System.out.println("4. Set Promotion");
        System.out.println("5. Configure Table Settings");
        System.out.println("6. Create New Manager Account");
        System.out.println("7. Delete Existing Manager Account");
        System.out.println("8. Display Manager Account(s)");
        System.out.println("----------------------------------"); //Printing manager functions
    }

    public static void exitRMS(){
        char option = ' ';
        System.out.println("Are you sure, you want to exit? All saved data will be lost!");
        while (!(option == 'N')){
            System.out.print("Enter 'Y' for \"Yes\" and 'N' for \"No\": ");
            String optionS = input.nextLine();
            if (optionS.length() >= 1)
                option = Character.toUpperCase(optionS.charAt(0));
            if (option == 'Y'){
                System.out.println(Manager.getShopName() + "'s RMS Successfully Terminated.");
                System.exit(0);
            } //Exit TOMS system with confirmation from user, where data will be lost after exiting
            if (!(option == 'N'))
                System.out.println("Invalid input detected! Try again.");
        }
    }
}

