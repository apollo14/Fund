package pl.js.fund.enums;


public enum FundOperationsFileColumns
{

    DATE(0),
    FUND_NAME(1),
    OPERATION_TYPE(2),
    VALUE(3);

    private int id;

    FundOperationsFileColumns(int id)
    {
        this.id = id;
    }

    public int getId()
    {
        return this.id;
    }

}
