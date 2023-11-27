package BankApplication;

import java.util.ArrayList;
import java.util.Scanner;

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
        System.out.print("Enter Name of BankApplication.Customer To add: ");
        String name= scanner.nextLine();
        customer= new Customer(name);
        if (currentCustomer(name)==null){
            customers.add(customer);
            System.out.printf("%s has been added Successfully %n", name);
        }
        else{
            System.out.println("BankApplication.Customer Already Exists");
        }
        loadingAnimation(3);
        BankApp.menu();
    }
    public void deleteCustomer(){
        Bank.printAllCustomers();
        System.out.println("Enter Name of BankApplication.Customer To delete");
        String name= scanner.nextLine();
        if (currentCustomer(name)!=null){
            customers.remove(currentCustomer(name));
            System.out.printf("%s has been removed Successfully %n", name);
        }
        else{
            System.out.println("BankApplication.Customer doesn't Exist");
            System.out.println("Enter a valid BankApplication.Customer name");
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
            System.out.printf("BankApplication.Customer Name: %s Account Balance: %.2f %n",customer.getName(),(float)customer.getBalance());
        }
        System.out.println("*".repeat(50));
    }
}