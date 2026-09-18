import java.util.*;
/*

 */

public class Bank
{
    private final List<Account> accounts = new ArrayList<>();

    public Bank()
    {
            // Seed data
            accounts.add(new SavingsAccount("8245", "0000", "John Doe", 500000.00, 1000.00, 10000.00, 0.02));
            accounts.add(new CheckingAccount("1234", "1111", "Jane Smith", 25000.00, 500.00));
            accounts.add(new SavingsAccount("5678", "2222", "Bob Marley", 7500.00, 1000.00, 10000.00, 0.02));
    }

    //The following function tries to find the account number and pin are within the system
    public Optional<Account> findAccount(String accountNumber)
    {
        return accounts.stream().filter(a->a.getAccountNumber().equals(accountNumber))
                .findFirst();
    }
}
