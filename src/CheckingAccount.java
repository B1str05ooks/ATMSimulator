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
            throw new AccountException(ATMError.INVALID_AMOUNT);
        }

        if(balance - amount < -overdraftLimit)
        {
            throw new AccountException(ATMError.INSUFFICIENT_FUNDS);
        }

        balance -= amount;

        addToHistory("WITHDRAWAL", amount);

        if (balance < 0)
        {
            System.out.printf(" Overdraft used. Current balance: %s", getFormattedBalance());
        }
    }

    //Getters & Setters

    public double getOverdraftLimit()
    {
        return overdraftLimit;
    }
}
