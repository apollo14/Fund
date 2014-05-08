package pl.js.fund.model;


public abstract class Fund extends Stock
{
    protected String  id;
    protected String  name;

    // number of places aftercoma in units values
    protected Integer unitsRoundingFactor;

    // position of price column in csv file with prices
    protected Integer pricePositionIndex;
    protected char    priceFileSeparator;

    protected Fund(String id, String name)
    {
        this.id = id;
        this.name = name;
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

    public Integer getUnitsRoundingFactor()
    {
        return unitsRoundingFactor;
    }

    public Integer getPricePositionIndex()
    {
        return pricePositionIndex;
    }

    public char getPriceFileSeparator()
    {
        return priceFileSeparator;
    }

}
