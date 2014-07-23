package pl.js.fund.operation;

import pl.js.fund.model.Register;

public class Buy extends Operation
{

    public Buy()
    {
        this.sortingFactor = 1;
    }

    @Override
    public void perform(Register register)
    {
        super.perform(register);
        register.setUnits(register.getUnits().add(units));

    }

    @Override
    protected void calculateUnitsTotal(Register register)
    {
        this.unitsTotal = register.getUnits().add(units);
    }

    public String toString()
    {
        return "B " + super.toString();
    }

}
