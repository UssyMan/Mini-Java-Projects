import java.util.ArrayList;
import java.util.Scanner;


record Transactions(String type, double amount){

}
class Customer{
    private final String name;
    private final ArrayList<Transactions> transactions;
    private double initialDeposit;
    private double balance= 0.00;

    public Customer(String name, ArrayList<Transactions> transactions) {
        this.name = name;
        this.transactions = transactions;
    }

    public Customer(String name, double initialDeposit) {
        this(name.toUpperCase(), new ArrayList<>(500));
        this.initialDeposit= initialDeposit;
        transactions.add(new Transactions("Default",initialDeposit));
    }
    public Customer(String name){
        this(name,0.00);
    }
    public String getName() {
        return name;
    }

    public void addTransaction(String type,double amount){
        transactions.add(new Transactions(type,amount));
    }
    public double getBalance(){
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void getTransactions(){
        for(Transactions transactions1: transactions){
            //remove if condition after implementing initial deposit as default
            //implement above zero withdrawal
            if (!transactions1.type().equals("Default")){
                System.out.printf("%s: \t |\t \t %.2f %n",transactions1.type(), (float)transactions1.amount());
            }
        }
    }
    public double getTotalDeposit(){
        double balance= 0;
        for(Transactions transactions1: transactions){
            if (transactions1.type().equals("Credit")){
                balance+= transactions1.amount();
            }
        }
        return balance;
    }

    @Override
    public String toString() {
        return "Customer Name:" + name + " Balance: " + (float)balance + "\n";
    }
}
class Bank{
    private static final Scanner scanner= new Scanner(System.in);
    private final String name;
    public static ArrayList<Customer> customers;
    private static int counter=0;

    public Bank(String name) {
        this.name = name;
        customers = new ArrayList<>();
    }

    public final String getName() {
        return name;
    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public static void loadingAnimation(int seconds){
        System.out.println("");
        System.out.print("Loading");
        try {
            for (int i=0; i<seconds;i++){
                System.out.print(".");
                Thread.sleep( 1000);
            }
            System.out.println("");
        } catch (InterruptedException e) {
            e.printStackTrace(); // Handle the exception if needed
        }
        System.out.println("");
    }
    public void addCustomer(Customer customer){
        System.out.print("Enter Name of Customer To add: ");
        String name= scanner.nextLine();
        customer= new Customer(name);
        if (currentCustomer(name)==null){
            customers.add(customer);
            System.out.printf("%s has been added Successfully %n", name);
        }
        else{
            System.out.println("Customer Already Exists");
        }
        loadingAnimation(3);
        BankApp.menu();
    }
    public void deleteCustomer(){
        Bank.printAllCustomers();
        System.out.println("Enter Name of Customer To delete");
        String name= scanner.nextLine();
        if (currentCustomer(name)!=null){
            customers.remove(currentCustomer(name));
            System.out.printf("%s has been removed Successfully %n", name);
        }
        else{
            System.out.println("Customer doesn't Exist");
            System.out.println("Enter a valid Customer name");
            deleteCustomer();
        }
        loadingAnimation(3);
        BankApp.menu();
    }
    public static void deleteCurrentCustomer(Customer customer){
        customers.remove(customer);
        System.out.printf("%s has been deleted successfully",customer.getName());
        loadingAnimation(3);
        BankApp.menu();
    }
    public static Customer currentCustomer(String name){
        for (var customer: customers){
            if (customer.getName().equalsIgnoreCase(name)) {
                return customer;
            }
        }
        return null;
    }
    //deposit to customer account
    public static void deposit(Customer customer){
        System.out.print("Enter amount to deposit: ");
        double amount= Double.parseDouble(scanner.nextLine());
        if (amount<1){
            System.out.println("Deposit must be greater than 0");
            deposit(customer);
        }
        customer.addTransaction("Credit",amount);
        customer.setBalance(customer.getBalance() + amount);
        System.out.printf("%.2f has been deposited to %s's account %n",amount,customer.getName());
        //implement printing balance
        System.out.printf("New balance is: %.2f %n",customer.getBalance());
        loadingAnimation(3);
        BankApp.customerMenu(customer);

    }
    //withdrawing
    public  static void withdraw(Customer customer){
        System.out.print("Enter amount to withdraw: ");
        double amount= Double.parseDouble(scanner.nextLine());
        if (amount<= customer.getBalance() && amount>0){
            customer.addTransaction("Debit ",amount);
            System.out.printf("%.2f Successfully debited %n",amount);
            customer.setBalance(customer.getBalance() - amount);
        }
        else if (amount<1) {
            System.out.println("Invalid Amount...Amount must be greater than 0");
            withdraw(customer);

        }
        else{
            while (counter<3){
                //needs more implementation
                System.out.println("******Insufficient Funds******");
                System.out.println("Below is your current Balance");
                printBalance(customer);
                System.out.print("Enter a valid Amount: ");
                counter++;
                System.out.println(counter);
                withdraw(customer);
            }
        }
        loadingAnimation(3);
        BankApp.customerMenu(customer);
    }
    private static void printBalance(Customer customer){
        System.out.println("*".repeat(30));
        System.out.println("");
        System.out.printf("Account Balance: %.2f %n ",(float)customer.getBalance());
        System.out.println("");
        System.out.println("*".repeat(30));
    }
    public static void accountBalance(Customer customer){
        printBalance(customer);
        loadingAnimation(3);
        BankApp.customerMenu(customer);
    }
    public static void printStatement(Customer customer){
        //implement No record found for empty deposit;
        if (customer.getTotalDeposit() != 0){
            System.out.println("-".repeat(30));
            customer.getTransactions();
            System.out.println("-".repeat(30));
            loadingAnimation(10);
        }
        else {
            System.out.println("-".repeat(30));
            System.out.println("No records Found");
            System.out.println("-".repeat(30));
            loadingAnimation(3);
        }
        BankApp.customerMenu(customer);
    }
    public static void printAllCustomers(){
        System.out.println("*".repeat(50));
        for (Customer customer: customers){
            System.out.printf("Customer Name: %s Account Balance: %.2f %n",customer.getName(),(float)customer.getBalance());
        }
        System.out.println("*".repeat(50));
    }
}
public class BankApp {
    private static Scanner scanner= new Scanner(System.in);
    private static Bank bank= new Bank("FCMB");
    private static Customer blankCustomer= new Customer("");
    public static void main(String[] args) {
        Customer customer= new Customer("Usman");
        Customer customer1= new Customer("Mahadi");
        Bank.customers.add(customer);
        Bank.customers.add(customer1);

        menu();

    }
    public static void menu(){
        System.out.println("Please select a Task to do.....");
        System.out.println("1 -> Add customer");
        System.out.println("2 -> Delete customer");
        System.out.println("3 -> View all Customers");
        System.out.println("4 -> Quit App");
        int userChoice = Integer.parseInt(scanner.nextLine());
        switch (userChoice){
            case 1 -> bank.addCustomer(blankCustomer);
            case 2 -> bank.deleteCustomer();
            case 3 -> viewCustomers();
            //default -> flag= false;
        }

    }
    public static void customerMenu(Customer customer){
        System.out.printf("Please Enter Transaction to perform in %s's account %n",customer.getName());
        System.out.println("1 -> Deposit");
        System.out.println("2 -> Check Balance");
        System.out.println("3 -> Withdraw");
        System.out.println("4 -> Print Bank Statement");
        System.out.println("5 -> Back to Menu");
        System.out.println("6 -> Delete Customer");
        System.out.println("7 -> Quit app");
        int userChoice= Integer.parseInt(scanner.nextLine());
        switch (userChoice){
            case 1 -> Bank.deposit(customer);
            case 2 -> Bank.accountBalance(customer);
            case 3 -> Bank.withdraw(customer);
            case 4 -> Bank.printStatement(customer);
            case 5 -> menu();
            case 6 -> Bank.deleteCurrentCustomer(customer);
            //default -> flag= false;
        }

    }
    private static void viewCustomers(){
//        System.out.println(Bank.customers.toString());
        Bank.printAllCustomers();
        System.out.print("Type customer name to work on or Enter 1 to go back to menu: ");
        String userInput= scanner.nextLine();
        try {
            if (Integer.parseInt(userInput)==1){
                menu();
            }
        } catch (NumberFormatException e) {
            if (Bank.currentCustomer(userInput) != null){
                customerMenu(Bank.currentCustomer(userInput));
            }
            else{
                System.out.println("Customer not in Bank......Please Enter a valid customer's name");
                System.out.println("Below is the List of Customers");
                viewCustomers();
            }
        }
    }

}
