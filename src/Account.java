import java.text.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

//Base class for all accounts, Abstract

public abstract class Account
{

    protected final String accountNumber;
    protected final String pin;
    protected final String ownerName;
    protected double balance;

    private final List<String> transactionHistory = new ArrayList<>();

    protected Account(String accountNumber, String pin, String ownerName, double balance) {
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.ownerName = ownerName;
        this.balance = balance;
    }

    public void deposit(double amount)
    {

        if(amount <= 0)
        {
            throw new AccountException(ATMError.INVALID_AMOUNT);
        }

         balance += amount;

        addToHistory("DEPOSIT", amount);
    }

    public abstract void withdraw(double amount);


    public boolean checkPin(String enteredPin)
    {
        return pin.equals(enteredPin);
    }


    public void addToHistory(String type, double amount)
    {
        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm"));

        transactionHistory.add(String.format("%-22s %-15s $%-13.2f $%,.2f",
            timestamp, type, amount, balance));
    }

    public void printStatement()
    {
        System.out.println("\n-- TRANSACTION HISTORY ------");
        System.out.printf("  %-22s %-15s %-14s %s%n",
                "DATE/TIME", "TYPE", "AMOUNT", "BALANCE");
        System.out.println("  " + "─".repeat(68));

        if(transactionHistory.isEmpty())
        {
            System.out.println("   No transactions this session.");
        }
        else
        {
            for (String record : transactionHistory)
                System.out.println(" " + record);
        }

        System.out.println(" " + "-".repeat(68));

        System.out.println(" Total: " + transactionHistory.size()
                + " transaction(s)\n");

    }


    public void printReceipt(String type, double amount)
    {
        String timestamp = java.time.LocalDateTime.now()
                .format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        System.out.println("\n  ─────────────────────────────────────");
        System.out.println("            TRANSACTION RECEIPT         ");
        System.out.println("   ───────────────────────────────────── ");
        System.out.printf ("     %-12s %-24s│%n", "Type:",      type);
        System.out.printf ("     %-12s %-24s│%n", "Account:",   getMaskedAccountNumber());
        System.out.printf ("     %-12s $%-23.2f│%n", "Amount:",    amount);
        System.out.printf ("     %-12s %-24s│%n", "Balance:",   getFormattedBalance());
        System.out.printf ("     %-12s %-24s│%n", "Date/Time:", timestamp);
        System.out.println("   ───────────────────────────────────── ");
        System.out.println("          Transaction successful!\n");
    }


    public double getBalance()
    {
        return balance;
    }


    public String getAccountNumber()
    {
        return accountNumber;
    }

    public String getOwnerName()
    {
        return ownerName;
    }


    public String getMaskedAccountNumber()
    {
        int length = accountNumber.length();

        if (length <= 4)
        {
            return "****" + accountNumber;
        }

        return "*".repeat(length - 4) + accountNumber.substring(length - 4);

    }

    public String getFormattedBalance()
    {
        return NumberFormat.getCurrencyInstance().format(balance);
    }


}



