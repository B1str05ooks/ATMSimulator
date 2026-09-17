// Checking account allows withdrawals into a fixed overdraft amount

public class CheckingAccount extends Account
{
    //Variables
   private final double overdraftLimit;


   //Constructor

    public CheckingAccount(String accountNumber, String pin, String ownerName, double balance, double overdraftLimit)
    {
        super(accountNumber, pin, ownerName, balance);
        this.overdraftLimit = overdraftLimit;
    }


   //Functions

    @Override
    public void withdraw(double amount)
    {
        if(amount <= 0)
        {
            //throw Account exception
        }

        if(balance - amount < -overdraftLimit)
        {
            //throw Account exception
        }

        balance -= amount;
    }

    //Getters & Setters

    public double getOverdraftLimit()
    {
        return overdraftLimit;
    }
}
