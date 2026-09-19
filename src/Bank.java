import java.util.*;
import java.io.*;


public class Bank
{
    private final List<Account> accounts = new ArrayList<>();

    public Bank()
    {
            // Seed data
            accounts.add(new SavingsAccount("8245", "0000", "John Doe", 500000.00, 1000.00, 10000.00, 0.02));

            accounts.add(new CheckingAccount("1234", "1111", "Jane Smith", 25000.00, 500.00));

            accounts.add(new SavingsAccount("5678", "2222", "Bob Marley", 7500.00, 1000.00, 10000.00, 0.02));

            loadBalance("accounts.dat");
    }

    //The following function tries to find the account number and pin are within the system
    public Optional<Account> findAccount(String accountNumber)
    {
        return accounts.stream().filter(a->a.getAccountNumber().equals(accountNumber))
                .findFirst();
    }


    public void saveBalances(String filename)
    {

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filename)))
        {
           for (Account account: accounts)
           {
               writer.write(account.getAccountNumber() + "," + account.getBalance());

               writer.newLine();
           }
        }catch(IOException e)
        {
            System.out.println(ATMError.FILE_SAVE_ERROR.getMessage());
        }
    }

    public void loadBalance(String filename)
    {
        try(BufferedReader reader = new BufferedReader(new FileReader(filename)))
        {
            String line;
            while ((line = reader.readLine()) != null)
            {
                String[] parts = line.split(",");

                if(parts.length == 2)
                {
                    String accountNum = parts[0];
                    double savedBalance = Double.parseDouble(parts[1]);

                    findAccount(accountNum).ifPresent(a ->{a.balance = savedBalance;});
                }
            }
        }catch(FileNotFoundException e)
        {
            System.out.println(ATMError.FILE_NOT_FOUND.getMessage());
        }
        catch(IOException e)
        {
            System.out.println(ATMError.FILE_SAVE_ERROR.getMessage());
        }
    }



}
