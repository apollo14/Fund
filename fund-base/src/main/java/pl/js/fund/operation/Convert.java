package pl.js.fund.operation;

import java.math.BigDecimal;

import pl.js.fund.constants.AppConstants;
import pl.js.fund.model.Register;

public class Convert extends Operation
{

    private BigDecimal taxBaseInherited = BigDecimal.ZERO;

    public Convert()
    {
        this.sortingFactor = 3;
    }

    @Override
    public void perform(Register register)
    {
        super.perform(register);
        if (this.value.compareTo(BigDecimal.ZERO) == -1)
        {
            // KONWERSJA Z
            register.setUnits(register.getUnits().add(units.negate()));
        }
        else
        {
            // KONWERSJA NA
            register.setUnits(register.getUnits().add(units));
        }

        if (parrentOperation != null)
        {
            calculateTaxBase(register);
            ((Convert) parrentOperation).setTaxBaseInherited(this.taxBase);
        }
    }

    @Override
    protected void calculateUnitsTotal(Register register)
    {
        if (this.value.compareTo(BigDecimal.ZERO) == -1)
        {
            // KONWERSJA Z
            this.unitsTotal = register.getUnits().add(units.negate()).setScale(fundName.getUnitsCalculationScale(), AppConstants.ROUNDING_MODE);
        }
        else
        {
            // KONWERSJA NA
            this.unitsTotal = register.getUnits().add(units).setScale(fundName.getUnitsCalculationScale(), AppConstants.ROUNDING_MODE);
        }
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
