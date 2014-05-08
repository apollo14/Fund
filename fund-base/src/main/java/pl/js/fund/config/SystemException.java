package pl.js.fund.config;

public class SystemException extends RuntimeException
{
    private static final long serialVersionUID = 1L;
    protected String          key;

    public SystemException()
    {

    }

    public SystemException(Throwable t)
    {
        super(t);
    }

    public String getKey()
    {
        return key;
    }

}
