package pl.js.fund.operation;

import java.math.BigDecimal;

import pl.js.fund.constants.AppConstants;
import pl.js.fund.model.Register;

public class Convert extends Operation
{

    private Convert    parrentOperation;
    private Convert    childOperation;
    private BigDecimal taxBaseInherited = BigDecimal.ZERO;

    @Override
    public void perform(Register register)
    {
        super.perform(register);
        register.setUnits(register.getUnits().add(units));

        if (parrentOperation != null)
        {
            calculateTaxBase(register);
            parrentOperation.setTaxBaseInherited(this.taxBase);
        }
    }

    @Override
    protected void calculateUnitsTotal(Register register)
    {
        this.unitsTotal = register.getUnits().add(units.negate()).setScale(fundName.getUnitsCalculationScale(), AppConstants.ROUNDING_MODE);
    }

    public Convert getParrentOperation()
    {
        return parrentOperation;
    }

    public void setParrentOperation(Convert parrentOperation)
    {
        this.parrentOperation = parrentOperation;
    }

    public Convert getChildOperation()
    {
        return childOperation;
    }

    public void setChildOperation(Convert childOperation)
    {
        this.childOperation = childOperation;
    }

    public BigDecimal getTaxBaseInherited()
    {
        return taxBaseInherited;
    }

    public void setTaxBaseInherited(BigDecimal taxBaseInherited)
    {
        this.taxBaseInherited = taxBaseInherited;
    }

    public String toString()
    {
        return "C " + super.toString();
    }

}
