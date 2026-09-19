import java.util.*;

/*
    This is where the ATM logic stays

    Will implement a loop in option 4, instead of exiting, loops back to log in


 */


public class ATM
{
    private final Bank bank;
    private final Scanner sc;

    public ATM(Bank bank, Scanner sc)
    {
        this.bank = bank;
        this.sc = sc;
    }

    // FUNCTIONS

    public void run()
    {
        printBanner();

        boolean running = true;

        while(running)
        {
            Account account = login();

            if (account == null)
            {
                running = false;
            }
            else
            {
                running = menu(account);
            }
        }
    }

    private Account login()
    {
        int attempts = 0;

        System.out.println("Welcome to the ATM!\n");

        while (attempts < 3)
        {
            try
            {
                System.out.println("Enter the last 4 digits of your account number: ");
                String accountEntered = sc.nextLine();

                System.out.println("Enter your pin: ");
                String enteredPin = sc.nextLine().trim();

                Optional<Account> found = bank.findAccount(accountEntered);

                if (found.isEmpty())
                {
                    attempts++;

                    System.out.println(ATMError.ACCOUNT_NOT_FOUND.getMessage());

                    printRemaining(attempts);
                }
                else if (found.get().checkPin(enteredPin))
                {
                    System.out.println("\nLogin successful. Welcome " +
                            found.get().getOwnerName() + "\n!");

                    return found.get();
                }
                else
                {
                    attempts++;
                    System.out.println(ATMError.INVALID_CREDENTIALS.getMessage());

                    printRemaining(attempts);
                }

                if (attempts == 3)
                {
                    System.out.println(ATMError.ACCOUNT_BLOCKED.getMessage());

                    printGoodbye();
                }

            }catch(NoSuchElementException e)
            {
                System.out.println(ATMError.INPUT_STREAM_ERROR.getMessage());

                printGoodbye();

                return null;
            }
        }

        return null;
    }

    private void printRemaining(int attempts)
    {
        int remaining = 3 - attempts;

        if (remaining > 0)
        {
            System.out.println("Attempts remaining: " + remaining + "\n");
        }
    }

    private boolean menu(Account account) {
        boolean loggedIn = true;

        while (loggedIn)
        {
            printMenuHeader(account);

            System.out.println("Please choose the following options:");
            System.out.println("1: Check Balance");
            System.out.println("2: Deposit");
            System.out.println("3: Withdraw");
            System.out.println("4: Transfer");
            System.out.println("5: Transaction History");
            System.out.println("6: Logout");
            System.out.print("Select an option:  ");

            try {
                int selection = InputValidator.getValidMenuChoice(sc, 1, 7);


                switch (selection) {
                    case 1 -> showBalance(account);
                    case 2 -> deposit(account);
                    case 3 -> withdraw(account);
                    case 4 -> transfer(account);
                    case 5 -> account.printStatement();
                    case 6 -> loggedIn = false;
                    default -> System.out.println(ATMError.INVALID_MENU_CHOICE.getMessage());
                }

            } catch (InputMismatchException e) {
                sc.nextLine();

                System.out.println("Invalid input. Please enter the correct selection.");

            } catch (NoSuchElementException e) {
                System.out.println("input error. Ending session...");

                printGoodbye();

                return false;
            }
        }
        bank.saveBalances("accounts.dat");

        printGoodbye();

        return true;
    }

    private void showBalance(Account account)
    {
        System.out.println("\n--- ACCOUNT BALANCE ---------");
        System.out.printf("\nOwner: %s" , account.getOwnerName());
        System.out.printf("\nAccount: %s\n", account.getMaskedAccountNumber());
        System.out.println("\nBalance: " + account.getFormattedBalance());
        System.out.println("-----------------------------\n");
    }

    private void deposit(Account account)
    {
        System.out.println("How much would you like to deposit?");

        double amount = InputValidator.getValidAmount(sc);

        account.deposit(amount);

        System.out.println("Balance: " + account.getFormattedBalance());
    }

    private void withdraw(Account account)
    {
        System.out.println("Withdrawals must be in multiples of $50.");

        System.out.print("How much would you like to withdraw? ");

        double amount = InputValidator.getValidAmount(sc);

        try
        {
            account.withdraw(amount);

            account.printReceipt("WITHDRAWAL", amount);

            if (account instanceof SavingsAccount savings &&
                    savings.getBalance() <= savings.getMinimumBalance())
            {
                System.out.println(ATMError.BELOW_MINIMUM_BALANCE.getMessage());
            }

        }catch(AccountException e)
        {
            System.out.println(e.getMessage());

            System.out.println("Balance: " + account.getFormattedBalance());
        }
    }

    private void transfer(Account account)
    {
        System.out.println("\n── TRANSFER ──────────────────────────────");
        System.out.printf ("  Current Balance: %s%n%n", account.getFormattedBalance());
        System.out.print  ("  Enter destination account number: ");

        String destNumber = sc.nextLine().trim();

        // Cannot transfer to yourself
        if (destNumber.equals(account.getAccountNumber()))
        {
            System.out.println(ATMError.TRANSFER_SAME_ACCOUNT.getMessage() + "\n");
            return;
        }

        Optional<Account> destFound = bank.findAccount(destNumber);

        if (destFound.isEmpty())
        {
            System.out.println(ATMError.DESTINATION_NOT_FOUND.getMessage() + "\n");
            return;
        }

        Account destination = destFound.get();

        System.out.printf("  Sending to: %s (%s)%n%n",
                destination.getOwnerName(),
                destination.getMaskedAccountNumber());
        System.out.print("  Amount to transfer: $");

        double amount = InputValidator.getValidAmount(sc);

        try
        {
            // Withdraw from source — uses that account's rules
            account.withdraw(amount);

            // Deposit into destination — always valid if amount > 0
            destination.deposit(amount);

            System.out.println("\n✓ Transfer successful!");
            System.out.printf ("  Sent %s to %s%n",
                    (amount),
                    destination.getOwnerName());
            System.out.printf ("  Your new balance: %s%n%n",
                    account.getFormattedBalance());
        }
        catch (AccountException e)
        {
            System.out.println(e.getMessage() + "\n");
            System.out.printf("  Balance unchanged: %s%n%n",
                    account.getFormattedBalance());
        }
    }


    private void printBanner()
    {
        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║                                          ║");
        System.out.println("║          W E L C O M E   T O             ║");
        System.out.println("║              THE     A T M               ║");
        System.out.println("║                                          ║");
        System.out.println("╚══════════════════════════════════════════╝");
        System.out.println("  Secure  ·  Reliable  ·  Always Available\n");
    }

    private void printMenuHeader(Account account)
    {
        System.out.println("\n╔══════════════════════════════════════════╗");
        System.out.println("║             M A I N   M E N U              ║");
        System.out.println("╠════════════════════════════════════════════╣");
        System.out.printf ("║  %-18s %-23s║%n", "Account Holder:", account.getOwnerName());
        System.out.printf ("║  %-18s %-23s║%n", "Account:",        account.getMaskedAccountNumber());
        System.out.println("╚══════════════════════════════════════════╝\n");
    }

    private void printGoodbye()
    {
        System.out.println("Thank you for using the ATM, goodbye!");
    }



}
