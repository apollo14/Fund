package pl.js.fund.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.joda.time.LocalDate;

import pl.js.fund.operation.Operation;
import pl.js.fund.price.FundPriceProvider;

public class Register
{
    private Umbrella          umbrella;
    private Fund              fund;
    private Double            units = 0.0;
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

    public Double getUnits()
    {
        return units;
    }

    public void setUnits(Double units)
    {
        this.units = (double) Math.round(units * getFund().getUnitsRoundingFactor()) / getFund().getUnitsRoundingFactor();
    }

    public FundPriceProvider getPriceProvider()
    {
        return priceProvider;
    }

    public Umbrella getUmbrella()
    {
        return umbrella;
    }

    public void setUmbrella(Umbrella umbrella)
    {
        this.umbrella = umbrella;
    }

    public Double getValue(LocalDate date)
    {
        return (double) Math.round(priceProvider.getPriceAtLastBusinessDay(date) * units * 100) / 100;
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
}