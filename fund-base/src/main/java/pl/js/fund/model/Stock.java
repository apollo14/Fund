package pl.js.fund.model;

import java.net.URL;

public abstract class Stock
{
    protected String  id;
    protected String  name;

    // position of price column in csv file with prices
    protected Integer pricePositionIndex;
    protected char    priceFileSeparator;

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

    public Integer getPricePositionIndex()
    {
        return pricePositionIndex;
    }

    public char getPriceFileSeparator()
    {
        return priceFileSeparator;
    }

    public abstract URL evaluatePricingURL();
}
