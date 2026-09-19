/*
Savings account that forces a $50 denomination
Withdrawal cap per session
 */

public class SavingsAccount extends Account implements Interest
{
    //Variables
    private static final int DENOMINATION = 50;

    private final double minimumBalance;
    private final double dailyWithdrawalLimit;
    private final double interestRate;
    private double dailyWithdrawn;

    public SavingsAccount(String accountNumber, String pin, String ownerName, double balance,
                          double minimumBalance, double dailyWithdrawalLimit, double interestRate)
    {
        super(accountNumber, pin, ownerName, balance);
        this.minimumBalance = minimumBalance;
        this.dailyWithdrawalLimit = dailyWithdrawalLimit;
        this.interestRate = interestRate;
        this.dailyWithdrawn = 0.0;
    }

    @Override
    public void withdraw(double amount)
    {
        if(amount <= 0)
        {
            //throw account exception
        }

        // For more accuracy, should compare in cents rather than full amounts

        long cents = Math.round(amount * 100);

        if(cents % (DENOMINATION * 100) != 0 )
        {
            throw new AccountException(ATMError.INVALID_AMOUNT);
        }

        if(dailyWithdrawn + amount > dailyWithdrawalLimit)
        {
            throw new AccountException(ATMError.INVALID_DENOMINATION);
        }

        if(balance - amount < minimumBalance)
        {
            throw new AccountException(ATMError.BELOW_MINIMUM_BALANCE);
        }

        balance -= amount;

        dailyWithdrawn += amount;

        addToHistory("WITHDRAWAL", amount);
    }

    //GETTERS AND SETTERS

    public double getDailyWithdrawn()
    {
        return dailyWithdrawn;
    }

    public double getDailyWithdrawalLimit()
    {
        return dailyWithdrawalLimit;
    }

    public double getMinimumBalance()
    {
        return minimumBalance;
    }

    //FUNCTIONS

    @Override
    public double getInterestRate()
    {
        return interestRate;
    }

    @Override
    public void applyInterest()
    {
        double interest = balance * interestRate;

        balance += interest;

        addToHistory("INTEREST", interest);

    }

    // Always call at the start of every day to reset withdrawn balance
    public void resetDailyWithdrawn()
    {
        dailyWithdrawn = 0.0;
    }



}
