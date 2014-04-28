package pl.js.fund.enums;

public enum OperationType
{
    BUY("KUPNO"),
    SELL("SPRZEDARZ"),
    CONVERSION_UP("KONWERSJA+"),
    CONVERSION_DOWN("KONWERSJA-");

    private final String type;

    OperationType(String type)
    {
        this.type = type;
    }

    public static OperationType parse(String operationTypeName)
    {
        if (operationTypeName != null && !"".equals(operationTypeName))
        {
            for (OperationType ot : OperationType.values())
            {
                if (operationTypeName.equalsIgnoreCase(ot.type))
                {
                    return ot;
                }
            }
        }
        return null;
    }
}
