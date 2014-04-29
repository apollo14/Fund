package pl.js.fund.operation;

import org.joda.time.LocalDate;

import pl.js.fund.model.Register;

public abstract class Operation
{
    public static final String  DATE_FORMAT     = "dd-MM-yyyy";
    public static final Integer ROUNDING_FACTOR = 100000;

    private String              fundName;
    private LocalDate           date;
    private Double              value;
    protected Double            units;

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

    public void perform(Register register)
    {
        Double price = register.getPriceProvider().getPriceAtLastBusinessDay(this.getDate());

        this.units = this.getValue() / price;
        this.units = (double) Math.round(this.units * register.getFund().getUnitsRoundingFactor()) / register.getFund().getUnitsRoundingFactor();

    }

}
