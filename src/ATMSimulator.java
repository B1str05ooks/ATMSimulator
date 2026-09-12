import java.util.*;
import java.text.*;


public class ATMSimulator
{
     static void main(String args[])
    {
        // Variables
        int selection;
        int attempts = 0;
        String accountNumber = "8245";
        String pin = "0000";
        double balance = 500000;
        boolean running = true;
        boolean loggedIn = false;


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

                    if(accountEntered.equals(accountNumber) && enteredPin.equals(pin))
                    {
                        loggedIn = true;

                        System.out.println("Logged in successfully, Welcome! \n");

                    } else {
                        attempts++;

                        int remaining = 3 - attempts;

                        System.out.println("Invalid credentials." + (remaining > 0 ? " Attempts remaining: " + remaining + "\n" : ""));
                    }

                    if(attempts == 3)
                    {
                        showError(ATMError.ACCOUNT_BLOCKED);

                        running = exit();

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
                        case 1 -> showBalance(balance);

                        case 2 -> balance = deposit(sc, balance);

                        case 3 -> balance = withdraw(sc, balance);

                        case 4 -> running = exit();

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

                    running = exit();
                }
            }

        }while(running);

        sc.close();
    }


    //FUNCTIONS

    static void showBalance(double balance)
    {
        FormattedBalance(balance);

    }

    static double deposit(Scanner sc, double balance)
    {
        System.out.print("How much would you like to deposit?");

        double deposit = getValidAmount(sc);

        balance += deposit;

        FormattedBalance(balance);

        return balance;
    }

    static double withdraw(Scanner sc, double balance)
    {
        System.out.print("How much would you like to withdraw?");

        double withdraw = getValidAmount(sc);

        System.out.println("Withdrawals occurs in multiples of $50: ");

        if(withdraw % 50 != 0)
        {
            System.out.println("Withdrawals must be in multiples of $50. ");

            FormattedBalance(balance);

            return balance;
        }




        if(withdraw > balance )
        {
            System.out.println("You are trying to withdraw an amount exceeding your balance");

            FormattedBalance(balance);

            return balance;

        }else
        {
            balance -= withdraw;

        }

        FormattedBalance(balance);

        return balance;
    }

    static boolean exit()
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
