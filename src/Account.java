import java.text.*;

//Base class for all accounts, Abstract

public abstract class Account
{

    protected final String accountNumber;
    protected final String pin;
    protected final String ownerName;
    protected double balance;

    protected Account(String accountNumber, String pin, String ownerName, double balance) {
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.ownerName = ownerName;
        this.balance = balance;
    }

    public void deposit(double amount)
    {

        /*if(amount <= 0)
        {
            throw new AccountException()
        }
        */
         balance += amount;
    }

    public abstract void withdraw(double amount);

    public boolean checkPin(String enteredPin)
    {
        return pin.equals(enteredPin);
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

        return "*".repeat(length - 4) + accountNumber.substring(length - 4);
    }

    public String getFormattedBalance()
    {
        return NumberFormat.getCurrencyInstance().format(balance);
    }


}



