package pl.js.fund.model;

public abstract class Fund extends Stock
{
    // number of places aftercoma in units values
    protected Integer unitsRoundingFactor;

    protected Fund(String id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public Integer getUnitsRoundingFactor()
    {
        return unitsRoundingFactor;
    }

}
