public enum Errors {;

    private final int ErrorTypes;

    Errors(int ErrorTypes)
    {
        this.ErrorTypes = ErrorTypes;
    }

    public int getErrorTypes()
    {
        return this.ErrorTypes;
    }
}
