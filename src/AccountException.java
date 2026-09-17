public class AccountException
{
    private final ATMError error;

    public AccountException(ATMError error)
    {
        super();
        this.error = error;
    }

    public ATMError getError()
    {
        return error;
    }

}
