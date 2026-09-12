public enum ATMError
{

    //Enum value
    INVALID_CREDENTIALS         ("Invalid account number or PIN"),
    ACCOUNT_NOT_FOUND           ("Account not found."),
    ACCOUNT_BLOCKED             ("Card blocked. Please contact your bank."),
    INSUFFICIENT_FUNDS          ("Insufficient funds. Amount exceeds your balance."),
    INVALID_DENOMINATION        ("Withdrawals must be multiples of $50."),
    BELOW_MINIMUM_BALANCE       ("Minimum balance of $ 1 000 must be maintained."),
    DAILY_LIMIT_EXCEEDED        ("Daily withdrawal limit of $10 000 reached."),
    INVALID_AMOUNT              ("Amount must be greater than zero."),
    INVALID_INPUT               ("Invalid input. Please enter a number."),
    INVALID_MENU_CHOICE         ("Please choose between 1 and 5."),
    INPUT_STREAM_ERROR          ("Input error. Please restart ATM."),
    FILE_NOT_FOUND              ("Account data not found. Starting fresh."),
    FILE_SAVE_ERROR             ("Failed to save data. Please contact support.");


    private final String message;

    // Constructor
    ATMError(String message)

    {
        this.message = message;
    }

    public String getMessage()
    {

        return message;
    }
}
