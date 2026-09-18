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

                sc.next();
            }
        }
    }
}
