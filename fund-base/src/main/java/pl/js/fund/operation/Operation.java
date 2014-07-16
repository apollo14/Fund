package pl.js.fund.operation;

import org.joda.time.LocalDate;

import pl.js.fund.model.Register;

public abstract class Operation implements Comparable<Operation>
{
    public static final String  DATE_FORMAT     = "dd-MM-yyyy";
    public static final Integer ROUNDING_FACTOR = 100000;

    private String              fundName;
    private LocalDate           date;
    private Double              value;
    protected Double            units;
    protected Double            price;
    protected Double            taxBase         = 0.0;
    protected Double            taxUnits        = 0.0;

    public String getFundName()
    {
        return fundName;
    }

    public void setFundName(String fundName)
    {
        this.fundName = fundName;
    }

    public LocalDate getDate()
    {
        return date;
    }

    public void setDate(LocalDate date)
    {
        this.date = date;
    }

    public Double getValue()
    {
        return value;
    }

    public void setValue(Double value)
    {
        this.value = value;
    }

    public Double getUnits()
    {
        return units;
    }

    public Double getPrice()
    {
        return price;
    }

    public void perform(Register register)
    {
        this.price = register.getPriceProvider().getPriceAtLastBusinessDay(this.getDate());

        this.units = Math.abs(this.getValue()) / price;
        this.units = (double) Math.round(this.units * register.getFund().getUnitsRoundingFactor()) / register.getFund().getUnitsRoundingFactor();

        this.taxUnits = new Double(this.units);
    }

    public int compareTo(Operation o)
    {
        return getDate().compareTo(o.getDate());
    }

    public String toString()
    {
        return date.toString(DATE_FORMAT) + ", " + value.toString() + ", price=" + price.toString() + ", units=" + units.toString();
    }
}
