package pl.js.fund.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.joda.time.LocalDate;

import pl.js.fund.constants.AppConstants;
import pl.js.fund.operation.Operation;
import pl.js.fund.price.FundPriceProvider;

public class Register
{
    private Umbrella          umbrella;
    private Fund              fund;
    private BigDecimal        units = BigDecimal.ZERO;
    private FundPriceProvider priceProvider;
    private List<Operation>   operations;

    public Register()
    {
        operations = new ArrayList<Operation>();
    }

    public Fund getFund()
    {
        return fund;
    }

    public void setFund(Fund fund)
    {
        this.fund = fund;
        this.priceProvider = new FundPriceProvider();
        this.priceProvider.setFund(this.fund);
    }

    public BigDecimal getUnits()
    {
        return units;
    }

    public void setUnits(BigDecimal units)
    {
        this.units = units;
    }

    public FundPriceProvider getPriceProvider()
    {
        return priceProvider;
    }

    public void setPriceProvider(FundPriceProvider priceProvider)
    {
        this.priceProvider = priceProvider;
        this.priceProvider.setFund(this.fund);
    }

    public Umbrella getUmbrella()
    {
        return umbrella;
    }

    public void setUmbrella(Umbrella umbrella)
    {
        this.umbrella = umbrella;
    }

    public BigDecimal getValue(LocalDate date)
    {
        return priceProvider.getPriceAtLastBusinessDay(date).multiply(units);
    }

    public void addOperation(Operation operation)
    {
        this.operations.add(operation);
        Collections.sort(this.operations);
    }

    public List<Operation> getOperations()
    {
        return operations;
    }

    public void performOperations()
    {
        for (Operation o : this.operations)
        {
            o.perform(this);
        }
    }

    public void performOperations(List<Operation> operationsSim)
    {
        for (Operation o : operationsSim)
        {
            o.perform(this);
        }

    }

    private BigDecimal showUnits()
    {
        return this.units.setScale(fund.fundName.getUnitsPresentationScale(), AppConstants.ROUNDING_MODE);
    }

    @Override
    public String toString()
    {
        return fund.toString() + ", units=" + showUnits();// + ", value=" + getValue(getLastOperationDate());
    }

}