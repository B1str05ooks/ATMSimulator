import java.util.*;
import java.text.*;


public class ATMSimulator
{
    // ACCOUNT DATA
    static String[] accountNumbers = {"8245", "1234", "5678"};
    static String[] accountPins = {"0000", "1111", "2222"};
    static double[] balances = {500000.00, 25000.00, 7500.00};
    static String[] ownerNames = {"John Doe", "Jane Smith", "Bob Marley"};

    static void main(String args[])
    {
        // Variables
        int selection;
        int attempts = 0;
        int accountIndex = -1;
        boolean running = true;
        boolean loggedIn = false;
        double dailyWithdrawal = 0.00;



        //Constants
        final double MINIMUM_BALANCE = 1000.00;
        final double DAILY_LIMIT = 10000.00;


        //ErrorTypes errorTypes = Error;   /enums


        Scanner sc = new Scanner(System.in);
        // The Menu Loop
        do
        {

            System.out.println("Welcome to the ATM");

            //Thinking of utility class, for validation
            while (!loggedIn && attempts < 3)
            {
                try
                {
                    System.out.println("Enter the last 4 digits of your account number: ");
                    String accountEntered = sc.nextLine();

                    System.out.println("Enter your pin: ");
                    String enteredPin = sc.nextLine().trim();

                    int foundIndex = -1;

                    for(int i = 0; i < accountNumbers.length;i++)
                    {
                        if(accountNumbers[i].equals(accountEntered))
                        {
                            foundIndex = i;
                            break;
                        }
                    }

                    if(foundIndex == -1)
                    {
                        attempts++;

                        showError(ATMError.ACCOUNT_NOT_FOUND);

                        int remaining = 3 - attempts;

                        System.out.println(" Invalid Account Number. " + (remaining > 0 ? " Attempts remaining: " + remaining + "\n" : ""));

                    }
                    else if(enteredPin.equals(accountPins[foundIndex]))
                    {
                        loggedIn = true;

                        accountIndex = foundIndex;

                        System.out.println("\nLogin successful. Welcome, " + ownerNames[accountIndex] + "!\n");

                    }
                    //For Wrong PIN
                    else
                    {
                        attempts++;

                        showError(ATMError.INVALID_CREDENTIALS);

                        int remaining = 3 - attempts;

                        System.out.println("Invalid PIN. " + (remaining > 0 ? " Attempts remaining: " + remaining + "\n" : ""));
                    }

                    if(attempts == 3)
                    {
                        showError(ATMError.ACCOUNT_BLOCKED);

                        running = exit(sc);
                    }

                }
                catch (NoSuchElementException e)
                {
                    showError(ATMError.INPUT_STREAM_ERROR);

                    running = false;

                    break;
                }

            }

            while(running)
            {
                System.out.println("Please choose the following options:");
                System.out.println("1: Check Balance");
                System.out.println("2: Deposit");
                System.out.println("3: Withdraw");
                System.out.println("4: Exit");

                try
                {
                    selection = sc.nextInt();
                    sc.nextLine();

                    switch(selection)
                    {
                        case 1 -> showBalance(accountIndex);

                        case 2 -> balances[accountIndex] = deposit(sc, accountIndex);

                        case 3 -> balances[accountIndex] = withdraw(sc, accountIndex, dailyWithdrawal);

                        case 4 -> running = exit(sc);

                        //case 5 ->

                        default -> showError(ATMError.INVALID_MENU_CHOICE);
                    }
                }
                catch (InputMismatchException e)
                {
                    sc.nextLine();

                    System.out.println("Invalid input. Please enter a number between 1 and 4.\n");
                }
                catch (NoSuchElementException e)
                {
                    System.out.println("Input error. Ending session...");

                    running = exit(sc);
                }
            }

        }while(running);

        sc.close();
    }


    //FUNCTIONS

    static void showBalance(int index)
    {
        System.out.println("\n--- ACCOUNT BALANCE ---------");
        System.out.printf("\nOwner: %s" , ownerNames[index]);
        System.out.printf("\nAccount: %s\n", accountNumbers[index]);

        FormattedBalance(balances[index]);

        System.out.println("-----------------------------\n");

    }

    static double deposit(Scanner sc, int index)
    {
        System.out.print("How much would you like to deposit?");

        double deposit = getValidAmount(sc);

        balances[index] += deposit;

        FormattedBalance(balances[index]);

        return balances[index];
    }


    static double withdraw(Scanner sc, int index, double dailyWithdrawal)
    {
        System.out.println("Withdrawals occurs in multiples of $50: ");

        System.out.print("How much would you like to withdraw?");

        double withdraw = getValidAmount(sc);


        if(withdraw % 50 != 0)
        {
            showError(ATMError.INVALID_DENOMINATION);

           // return new double[] {balances[index], dailyWithdraw};
        }


        if(withdraw > balances[index] )
        {
           showError(ATMError.INSUFFICIENT_FUNDS);

            FormattedBalance(balances[index]);

            return balances[index];

        }else
        {
            balances[index] -= withdraw;

            // dailyWithdrawal += withdraw;

           /* if(dailyWithdrawal == DAILY_LIMIT)
            {
                System.out.println("You have exceeded your daily withdrawal limit. Please choose another option.");

                FormattedBalance(balances[index]);

                return balances[index];
            }

            if(balances[index] == MINIMUM_BALANCE)
            {
                System.out.println("Your balance is low, please deposit.");

                FormattedBalance(balances[index]);

                return balances[index];
            } */

        }

        FormattedBalance(balances[index]);

        return balances[index];
    }

    static boolean exit(Scanner sc)
    {
        System.out.println("Thank you for using the ATM");

        return false;
    }







    //Utility functions

    static double getValidAmount(Scanner sc)
    {
        while(true)
        {
            try
            {
                double amount = sc.nextDouble();

                sc.nextLine();

                if (amount <= 0)
                {
                    showError(ATMError.INVALID_AMOUNT);
                } else
                {
                    return amount;
                }
            } catch (InputMismatchException e)
            {
                showError(ATMError.INVALID_INPUT);
                sc.next();
            }
        }
    }


    static void FormattedBalance(double balance)
    {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();

        String finalBalance = formatter.format(balance);

        System.out.println("Balance: " + finalBalance);

    }

    static void showError(ATMError error)
    {
        System.out.println(error.getMessage());
    }
}
