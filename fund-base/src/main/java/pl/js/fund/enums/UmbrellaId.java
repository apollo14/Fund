package pl.js.fund.enums;


public enum UmbrellaId
{

    UI("UI"),
    INV("INV");

    private String id;

    UmbrellaId(String id)
    {
        this.id = id;
    }

    public String getId()
    {
        return this.id;
    }

}
