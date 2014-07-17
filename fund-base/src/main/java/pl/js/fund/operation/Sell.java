package pl.js.fund.operation;

import java.math.BigDecimal;

import pl.js.fund.model.Register;

public class Sell extends Operation
{

    private BigDecimal taxValue = BigDecimal.ZERO;

    @Override
    public void perform(Register register)
    {
        super.perform(register);
        register.setUnits(register.getUnits().add(units.negate()));

        taxValue = calculateTaxBase(register);

    }

    public BigDecimal getTaxValue()
    {
        return taxValue;
    }

    public String toString()
    {
        return "S " + super.toString() + ", tax=" + taxValue;
    }
}
