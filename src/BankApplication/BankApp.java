package BankApplication;

import java.util.Scanner;



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
            case 4 -> System.exit(0);
            //default -> flag= false;
        }

    }
    public static void customerMenu(Customer customer){
        System.out.printf("Please Enter Transaction to perform in %s's account %n",customer.getName());
        System.out.println("1 -> Deposit");
        System.out.println("2 -> Check Balance");
        System.out.println("3 -> Withdraw");
        System.out.println("4 -> Print BankApplication.Bank Statement");
        System.out.println("5 -> Back to Menu");
        System.out.println("6 -> Delete BankApplication.Customer");
        System.out.println("7 -> Quit app");
        int userChoice= Integer.parseInt(scanner.nextLine());
        switch (userChoice){
            case 1 -> Bank.deposit(customer);
            case 2 -> Bank.accountBalance(customer);
            case 3 -> Bank.withdraw(customer);
            case 4 -> Bank.printStatement(customer);
            case 5 -> menu();
            case 6 -> Bank.deleteCurrentCustomer(customer);
            case 7 -> System.exit(0);
            //default -> flag= false;
        }

    }
    private static void viewCustomers(){
//        System.out.println(BankApplication.Bank.customers.toString());
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
                System.out.println("BankApplication.Customer not in BankApplication.Bank......Please Enter a valid customer's name");
                System.out.println("Below is the List of Customers");
                viewCustomers();
            }
        }
    }

}
