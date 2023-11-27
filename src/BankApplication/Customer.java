package BankApplication;

import java.util.ArrayList;

record Transactions(String type, double amount){

}
public class Customer {
    private final String name;
    private final ArrayList<Transactions> transactions;
    private double initialDeposit;
    private double balance = 0.00;

    public Customer(String name, ArrayList<Transactions> transactions) {
        this.name = name;
        this.transactions = transactions;
    }

    public Customer(String name, double initialDeposit) {
        this(name.toUpperCase(), new ArrayList<>(500));
        this.initialDeposit = initialDeposit;
        transactions.add(new Transactions("Default", initialDeposit));
    }

    public Customer(String name) {
        this(name, 0.00);
    }

    public String getName() {
        return name;
    }

    public void addTransaction(String type, double amount) {
        transactions.add(new Transactions(type, amount));
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void getTransactions() {
        for (Transactions transactions1 : transactions) {
            //remove if condition after implementing initial deposit as default
            //implement above zero withdrawal
            if (!transactions1.type().equals("Default")) {
                System.out.printf("%s: \t |\t \t %.2f %n", transactions1.type(), (float) transactions1.amount());
            }
        }
    }

    public double getTotalDeposit() {
        double balance = 0;
        for (Transactions transactions1 : transactions) {
            if (transactions1.type().equals("Credit")) {
                balance += transactions1.amount();
            }
        }
        return balance;
    }

    @Override
    public String toString() {
        return "BankApplication.Customer Name:" + name + " Balance: " + (float) balance + "\n";
    }
}