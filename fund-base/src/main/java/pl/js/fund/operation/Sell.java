package pl.js.fund.operation;

import java.math.BigDecimal;

import pl.js.fund.constants.AppConstants;
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
        return taxValue.setScale(2, AppConstants.ROUNDING_MODE);
    }

    @Override
    protected void calculateUnitsTotal(Register register)
    {
        this.unitsTotal = register.getUnits().add(units.negate()).setScale(fundName.getUnitsCalculationScale(), AppConstants.ROUNDING_MODE);
    }

    public String toString()
    {
        return "S " + super.toString() + ", taxBase=" + showTaxBase() + ", tax=" + getTaxValue();
    }
}
