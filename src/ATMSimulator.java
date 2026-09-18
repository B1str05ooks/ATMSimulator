import java.util.*;
import java.text.*;


public class ATMSimulator
{

    static void main(String args[])
    {
        Bank bank = new Bank();

        Scanner sc = new Scanner(System.in);

        ATM atm = new ATM(bank, sc);

        atm.run();
    }
}
