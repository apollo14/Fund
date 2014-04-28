package pl.js.fund.model;

import java.net.URL;

public abstract class Fund
{
    protected String  id;
    protected String  name;
    protected URL     url;
    protected Integer unitsRoundingFactor;

    protected Fund(String id, String name)
    {
        this.id = id;
        this.name = name;
        this.url = evaluateURL();
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public URL getUrl()
    {
        return url;
    }

    public void setUrl(URL url)
    {
        this.url = url;
    }

    public Integer getUnitsRoundingFactor()
    {
        return unitsRoundingFactor;
    }

    protected abstract URL evaluateURL();
}
