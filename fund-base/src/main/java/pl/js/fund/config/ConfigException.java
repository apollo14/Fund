package pl.js.fund.config;

public class ConfigException extends SystemException
{
    private static final String KEY              = "Configuration error.";

    private static final long   serialVersionUID = 1L;

    public ConfigException()
    {
        key = ConfigException.KEY;
    }

    public ConfigException(Throwable t)
    {
        super(t);
        key = ConfigException.KEY;
    }

}
