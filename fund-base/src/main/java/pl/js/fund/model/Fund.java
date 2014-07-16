package pl.js.fund.model;

import pl.js.fund.enums.FundName;

public abstract class Fund extends Stock
{
    // number of places aftercoma in units values
    protected Integer  unitsRoundingFactor;
    protected FundName fundName;

    protected Fund(String id, String name)
    {
        this.id = id;
        this.name = name;
        this.fundName = FundName.parse(name);
    }

    public Integer getUnitsRoundingFactor()
    {
        return unitsRoundingFactor;
    }

}
