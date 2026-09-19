import java.util.*;

public final class InputValidator
{
    private InputValidator() {}

    public static double getValidAmount(Scanner sc)
    {
        while(true)
        {
            try
            {
                double amount = sc.nextDouble();

                sc.nextLine();

                if (amount <= 0)
                {
                    System.out.println(ATMError.INVALID_AMOUNT.getMessage());
                }
                else
                {
                    return amount;
                }
            }catch (InputMismatchException e)
            {
                System.out.println(ATMError.INVALID_INPUT.getMessage());

                sc.nextLine();
            }
        }
    }


    public static int getValidMenuChoice(Scanner sc, int min, int max)
    {
        while (true)
        {
            try
            {
                int choice = sc.nextInt();

                sc.nextLine();

                if (choice >= min && choice <= max)
                {
                    return choice;
                }
                System.out.printf("  Please choose between %d and %d: ", min, max);

            }catch (InputMismatchException e)
            {
                sc.nextLine();

                System.out.println(ATMError.INVALID_INPUT.getMessage());

                System.out.printf("  Try again (%d-%d): ", min, max);

            }
        }
    }
}
