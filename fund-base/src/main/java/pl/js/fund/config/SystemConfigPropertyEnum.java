package pl.js.fund.config;

/**
 * Core property keys are stored here.
 * 
 * @author walczykm
 */
public enum SystemConfigPropertyEnum implements ConfigProperty
{
    PricingDir("pricing.dir");

    private String keyName;

    SystemConfigPropertyEnum(String keyName)
    {
        this.keyName = keyName;
    }

    public String getName()
    {
        return keyName;
    }
}
