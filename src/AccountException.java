public class AccountException extends RuntimeException
{
    private final ATMError error;

    public AccountException (ATMError error)
    {
        super(error.getMessage());
        this.error = error;
    }

    public ATMError getError()
    {
        return error;
    }

}
