package pl.js.fund.model;

import org.joda.time.LocalDate;

public class Register
{
    private Fund          fund;
    private Double        units = 0.0;
    private PriceProvider priceProvider;

    public Fund getFund()
    {
        return fund;
    }

    public void setFund(Fund fund)
    {
        this.fund = fund;
        this.priceProvider = new PriceProvider();
        this.priceProvider.setFund(this.fund);
    }

    public Double getUnits()
    {
        return units;
    }

    public void setUnits(Double units)
    {
        this.units = (double) Math.round(units * getFund().getUnitsRoundingFactor()) / getFund().getUnitsRoundingFactor();
    }

    public PriceProvider getPriceProvider()
    {
        return priceProvider;
    }

    public Double getValue(LocalDate date)
    {
        return (double) Math.round(priceProvider.getPrice(date) * units * 100) / 100;
    }

}