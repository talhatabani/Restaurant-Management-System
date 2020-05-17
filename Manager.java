import java.util.*;

//create a manager class to hold manager attributes

public class Manager {
    private static String shopName;
    private String name, username, password;
    private static int discountRate = 0;
    private static double minimumAmount = 0;
    private static Scanner input;
    private static ArrayList<Manager> managers = new ArrayList<>();

    public Manager(){
        this("", "", "");
    }

    public Manager(String username, String password){
        this("", username, password);
    }

    public Manager(String name, String username, String password){
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public static String getShopName() {
        return shopName;
    }

    public static void setShopName(String shopName) {
        Manager.shopName = shopName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static int getDiscountRate() {
        return discountRate;
    }

    public static void setDiscountRate(int discountRate) {
        Manager.discountRate = discountRate;
    }

    public static double getMinimumAmount() {
        return minimumAmount;
    }

    public static void setMinimumAmount(double minimumAmount) {
        Manager.minimumAmount = minimumAmount;
    }

    public void createNewManager(){

        receiveCredentials(); //Receive input for new manager credentials

        boolean isManagerExist = false;
        for(Manager manager: managers){
            if (manager.name.equals(name)){
                isManagerExist = true;
                System.out.println("\nManager Account Already Exists!\n");
                break;
            }
        } //Checking whether new manager credentials match with existing manager
        if (!isManagerExist){
            managers.add(this);
            System.out.println("New Manager Account Successfully Created.");
            displayManager();
        } //Create new manager and add to managers list if the manager does not exist
    }

    public void createNewManager(String name, String username, String password){
        this.name = name;
        this.username = username;
        this.password = password;
        managers.add(this);
    }

    public void receiveCredentials(){
        //Receiving all manager credentials and double checking password entry
        input = new Scanner(System.in);
        System.out.println("\nCreate New Manager Account\n--------------------------");
        System.out.print("Enter manager name: ");
        name = input.nextLine();
        System.out.print("Enter new username: ");
        username = input.nextLine();
        System.out.print("Enter new password: ");
        String passwordFirstTyped = input.nextLine();
        System.out.print("Retype new password: ");
        String passwordRetyped = input.nextLine();
        while(!passwordFirstTyped.equals(passwordRetyped)){
            System.out.println("Retyped password does not match! Try again.");
            System.out.print("Enter new password: ");
            passwordFirstTyped = input.nextLine();
            System.out.print("Retype new password: ");
            passwordRetyped = input.nextLine();
        }
        password = passwordFirstTyped;
    }

    public boolean isValidManager(){
        boolean isValid = false;
        for (Manager manager: managers){
            if (manager.username.equals(username) && manager.password.equals(password))
                isValid = true;
        }
        return isValid; //Validating manager credentials through managers list
    }

    public void displayManager(){
        System.out.println("\nManagers List\n-------------");
        int count = 1;
        for (Manager manager: managers){
            System.out.print(count + ". Manager Name: ");
            System.out.println(manager.name);
            System.out.print("   Manager Username: ");
            System.out.println(manager.username);
            count++;
        } //Displaying all managers in the list
    }

    public void deleteManager(){
        //Checking whether specified manager exist and deleting the manager from managers list if exist
        boolean isExist = false;
        Manager manager = null;
        if (managers.size() > 1){
            System.out.print("\nEnter manager name: ");
            String name = input.nextLine();
            for (Manager managerL: managers){
                if (managerL.name.equals(name)){
                    isExist = true;
                    manager = managerL;
                    break;
                }
            }
        }
        else{
            System.out.println("Manager Account Deletion aborted! Only one manager account exist.");
            return;
        } //If only one manager exist, deletion aborted
        if (isExist){
            managers.remove(manager);
            System.out.println("Specified Manager Account Successfully Deleted.");
        }
        else
            System.out.println("Specified Manager Account Does Not Exist!");
    }

    public void editMenu(){
        String mealCode = "", mealName;
        double mealPrice;
        int choice = -1;

        mealCode = mealCodeInput(); //Receive new meal code

        if (mealCode.equals("0"))
            return; //Return to manager menu

        //Retrieve common mealIndex for the specified mealCode
        int mealIndex = Meal.getMealCodes().indexOf(mealCode);

        while(choice != 0){
            Meal.displayMeal(mealCode);
            choice = -1;
            System.out.println("\nEDIT MENU\n---------");
            System.out.println("1.Edit Meal Code\n2.Edit Meal Name\n3.Edit Meal Price\n-----------------");
            while(!(choice >= 0 && choice <= 3)){
                System.out.print("Enter menu option(0 to exit): ");
                String choiceS = input.nextLine();
                try{
                    choice = Integer.valueOf(choiceS);
                }
                catch(NumberFormatException ex){
                    System.out.println("Invalid input detected!");
                }
                if (!(choice >= 0 && choice <= 3))
                    System.out.println("Invalid choice entered! Try again.");
            } //Validate menu option entered

            if (choice == 0)
                return; //Return to manager menu

            if (choice == 1){
                //If choice is 1, get and set new meal code
                System.out.print("Enter new meal code: ");
                mealCode = input.nextLine();
                while(containsString(mealCode, Meal.getMealCodes()));{
                    System.out.println("Meal code entered already exist! Try again.");
                    System.out.print("Enter new meal code: ");
                    mealCode = input.nextLine();
                }
                //Set new meal code, retrieve name and price within the same meal index and remove them from the meal index
                Meal.getMealCodes().set(mealIndex, mealCode);
                mealName = Meal.getMealNames().get(mealIndex);
                mealPrice = Meal.getMealPrices().get(mealIndex);
                Meal.getMealNames().remove(mealIndex);
                Meal.getMealPrices().remove(mealIndex);
                //Sort the meal codes to reposition the new meal code
                Collections.sort(Meal.getMealCodes());
                mealIndex = Meal.getMealCodes().indexOf(mealCode);
                //Add name and price at corresponding meal index with meal code
                Meal.getMealNames().add(mealIndex, mealName);
                Meal.getMealPrices().add(mealIndex, mealPrice);
                System.out.println("Meal code successfully changed.");
            }
            else if (choice == 2){
                //If choice is 2, get and set new meal name
                System.out.print("Enter new meal name: ");
                mealName = input.nextLine();
                Meal.getMealNames().set(mealIndex, mealName);
                System.out.println("Meal name successfully changed.");
            }
            else if (choice == 3){
                //If choice is 3, get and set new meal price
                mealPrice = mealPriceInput();
                Meal.getMealPrices().set(mealIndex, mealPrice);
                System.out.println("Meal price successfully changed.");
            }
        }
    }

    public void addMenu(){
        String mealName, mealCode = "";
        double mealPrice = -1;
        int mealIndex;

        boolean isExist = true;
        while(isExist){
            System.out.print("\nEnter new meal code(0 to exit): ");
            mealCode = input.nextLine();
            if (mealCode.equals("0"))
                break;
            isExist = containsString(mealCode, Meal.getMealCodes());
            if(isExist)
                System.out.println("Meal code already exist");
        } //While meal code entered exist, enter a new meal code to create a new menu

        if (mealCode.equals("0"))
            return; //Return manager menu

        System.out.print("Enter new meal name: ");
        mealName = input.nextLine();

        mealPrice = mealPriceInput(); //Receive new meal price

        //Add and sort the meal codes to reposition the new meal code
        Meal.getMealCodes().add(mealCode);
        Collections.sort(Meal.getMealCodes());
        //Retrieve common mealIndex for the specified mealCode
        mealIndex = Meal.getMealCodes().indexOf(mealCode);
        //Add name and price at corresponding meal index with meal code
        Meal.getMealPrices().add(mealIndex, mealPrice);
        Meal.getMealNames().add(mealIndex, mealName);
        System.out.println("New menu successfully added.");
    }

    public void deleteMenu(){
        String mealCode;
        int mealIndex;

        mealCode = mealCodeInput(); //Receive new meal code
        if (mealCode.equals("0"))
            return;

        Meal.displayMeal(mealCode);
        mealIndex = Meal.getMealCodes().indexOf(mealCode);
        Meal.getMealCodes().remove(mealIndex);
        Meal.getMealNames().remove(mealIndex);
        Meal.getMealPrices().remove(mealIndex);
        System.out.println("The given meal sucessfully removed!");
    }

    public void setPromotion(){
        int discountRate = -1;
        double minimumAmount = -1;
        double setDrinkPrice = -1;

        while(!(discountRate >= 0 && discountRate <= 100 && minimumAmount >= 0 && setDrinkPrice >= 0)){
            System.out.print("\nEnter new discount rate(%): ");
            String discountRateS = input.nextLine();
            if (discountRateS.equals("0"))
                break;
            System.out.print("Enter new minimum amount: PKR");
            String minimumAmountS = input.nextLine();
            System.out.print("Enter new set drink price: PKR");
            String setDrinkPriceS = input.nextLine();
            try{
                discountRate = Integer.valueOf(discountRateS);
                minimumAmount = Double.valueOf(minimumAmountS);
                setDrinkPrice = Double.valueOf(setDrinkPriceS);

            }
            catch(NumberFormatException ex){
                System.out.println("Invalid input detected!");
            }
            if (!(discountRate >= 0 && discountRate <= 100 && minimumAmount >= 0 && setDrinkPrice >= 0))
                System.out.println("Invalid input for discount rate/minimum amount/set drink price!");
        } //Receive and validate discount rate, minimum amount and set drink price

        if (discountRate == 0)
            return; //If discount rate is 0, then exit to manager menu

        Manager.setDiscountRate(discountRate);
        Manager.setMinimumAmount(minimumAmount);
        Meal.setSetDrinkPrice(setDrinkPrice);
        System.out.println("Discount rate, minimum amount and set drink price successfully changed.");
    }

    public String mealCodeInput(){
        boolean isExist = false;
        String mealCode = "";
        while(!isExist){
            System.out.print("\nEnter meal code(0 to exit): ");
            mealCode = input.nextLine();
            if (mealCode.equals("0"))
                break;
            isExist = containsString(mealCode, Meal.getMealCodes());
            if(!isExist)
                System.out.println("The meal does not exist! Try again.");
        } //Check whether specified mealCode exist
        return mealCode;
    }

    private boolean containsString(String testString, ArrayList<String> list){
        return list.contains(testString);
    } //Check whether the given string is present in the array list of string

    private double mealPriceInput(){
        double mealPrice = -1;
        while(!(mealPrice >= 0)){
            System.out.print("Enter new meal price: PKR");
            String mealPriceS = input.nextLine();
            try{
                mealPrice = Double.valueOf(mealPriceS);
            }
            catch(NumberFormatException ex){
                System.out.println("Invalid input detected!");
            }
            if (!(mealPrice >= 0))
                System.out.println("Invalid meal price entered! Try again.");
        }
        return mealPrice;
    }
}
