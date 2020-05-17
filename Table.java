import java.util.*;

public class Table implements Comparable<Table>{
    private final int tableNum;
    private String status;
    private Order order;
    private static int tablesPerRow;
    private static int totalTables;
    private static ArrayList<Table> tables = new ArrayList<>();
    private static Scanner input;

    public Table() {
        this(0, "NO");
    }

    public Table(int tableNum, String status) {
        this.tableNum = tableNum;
        this.status = status;
    }

    public int getTableNum() {
        return tableNum;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static int getTablesPerRow() {
        return tablesPerRow;
    }

    public static void setTablesPerRow(int tablesPerRow) {
        Table.tablesPerRow = tablesPerRow;
    }

    public static int getTotalTables() {
        return totalTables;
    }

    public static void setTotalTables(int totalTables) {
        Table.totalTables = totalTables;
    }

    public static ArrayList<Table> getTables() {
        return tables;
    }

    public static void setTables(ArrayList<Table> tables) {
        Table.tables = tables;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public static void displayTable(ArrayList<Table> tables) {
        System.out.println("All Table Status\n----------------");
        int count = 0; //To count number of tables printed

        for (int i=0; i<tables.size(); i+=tablesPerRow){ //Loop for printing each rows of table
            for (int j=1; j<=tablesPerRow && j+i<=tables.size(); j++) //Loop for printing upper table design for each row
                System.out.print("   \u250F\u2501\u2501\u2501\u2501\u2513  ");
            System.out.println();

            int statusCount = count; //Store count value to statusCount before manipulating count

            for (; count<i+tablesPerRow && count<tables.size(); count++) //Print table number of tables in each row
                System.out.printf("   \u2502 %02d \u2502  ", tables.get(count).tableNum);
            System.out.println();

            for (; statusCount<i+tablesPerRow && statusCount<tables.size(); statusCount++) //Print table status of tables in each row
                System.out.print("   \u2502 " + tables.get(statusCount).status + " \u2502  ");
            System.out.println();

            for (int j=1; j<=tablesPerRow && j+i<=tables.size(); j++) //Loop for printing lower table design for each row
                System.out.print("   \u2517\u2501\u2501\u2501\u2501\u251B  ");
            System.out.println("\n");
        }
    }

    public static void configTable(){
        System.out.println("\nConfigure Table Settings\n------------------------");
        int numOfTables = 0;
        int numPerRow = 0;
        input = new Scanner(System.in);

        while(!(numOfTables>=1 && numOfTables<=99 && numPerRow<=numOfTables && numPerRow>=1)){
            System.out.print("Enter number of tables: ");
            String totTables = input.nextLine();
            System.out.print("Enter number of tables per row: ");
            String perRow = input.nextLine();
            try {
                numOfTables = Integer.valueOf(totTables);
                numPerRow = Integer.valueOf(perRow);
            }
            catch(NumberFormatException ex){
                System.out.println("Invalid input detected!");
            }
            if (!(numOfTables>=1 && numOfTables<=99 && numPerRow<=numOfTables && numPerRow>=1))
                System.out.println("Invalid input for total tables or tables per row!");
            else{
                Table.totalTables = numOfTables;
                Table.tablesPerRow = numPerRow; //Assign number of tables and tables per row(for printing)
            }
        } //Validate configuration of tables

        createTables(); //Create specified number of table objects
        System.out.println("Table configuration complete.");
    }

    public static void createTables(){
        tables.clear();
        for (int i=1; i<=totalTables; i++){
            Table table = new Table(i, "NO"); //Create table objects with table number and initial status "Not Occupied"
            tables.add(table);
        }
    }

    @Override
    public int compareTo(Table table){
        return order.getOrderTakenTime().compareTo(table.order.getOrderTakenTime());
    } //Compare time of the order taken
}
