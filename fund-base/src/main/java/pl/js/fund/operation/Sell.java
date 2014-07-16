package pl.js.fund.operation;

import pl.js.fund.model.Register;

public class Sell extends Operation
{

    private Double taxValue = 0.0;

    @Override
    public void perform(Register register)
    {
        super.perform(register);
        register.setUnits(register.getUnits() - units);

        taxValue = calculateTaxBase(register);

    }

    public String toString()
    {
        return "S " + super.toString() + ", tax=" + taxValue;
    }
}
