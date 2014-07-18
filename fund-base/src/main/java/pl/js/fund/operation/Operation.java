package pl.js.fund.operation;

import java.math.BigDecimal;
import java.util.List;

import org.joda.time.LocalDate;

import pl.js.fund.constants.AppConstants;
import pl.js.fund.enums.FundName;
import pl.js.fund.model.Register;

public abstract class Operation implements Comparable<Operation>
{
    public static final String  DATE_FORMAT     = "dd-MM-yyyy";
    public static final Integer ROUNDING_FACTOR = 100000;

    protected FundName          fundName;
    protected LocalDate         date;
    protected BigDecimal        value;
    protected BigDecimal        units;
    protected BigDecimal        price;
    protected BigDecimal        taxBase         = BigDecimal.ZERO;
    protected BigDecimal        taxUnits        = BigDecimal.ZERO;
    protected BigDecimal        unitsTotal      = BigDecimal.ZERO;

    public FundName getFundName()
    {
        return fundName;
    }

    public void setFundName(FundName fundName)
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

    public BigDecimal getValue()
    {
        return value.setScale(2);
    }

    public void setValue(BigDecimal value)
    {
        this.value = value.setScale(2);
    }

    public BigDecimal getPrice()
    {
        return price.setScale(2);
    }

    protected BigDecimal calculateTaxBase(Register register)
    {
        // w ramach funduszu - kompensacja pionowa
        List<Operation> operations = register.getOperations().subList(0, register.getOperations().size());
        // w ramach parasola - kompensacja pozioma
        // List<Operation> operations = register.getUmbrella().getOperations().subList(0,
        // register.getUmbrella().getOperations().size());
        for (Operation o : operations)
        {
            if ((o instanceof Buy) && o.taxUnits.compareTo(BigDecimal.ZERO) == 1)
            {
                calculateTaxBase(o);
            }
            if ((o instanceof Convert) && o.taxUnits.compareTo(BigDecimal.ZERO) == 1)// && ((Convert)
                                                                                     // o).getConnectedOperation() !=
                                                                                     // null)
            {
                calculateTaxBase(o);
            }
            if (this.taxUnits == BigDecimal.ZERO)
            {
                break;
            }
        }
        if (taxBase.compareTo(BigDecimal.ZERO) == 1)
        {
            return taxBase.multiply(new BigDecimal(0.19));
        }
        return BigDecimal.ZERO;
    }

    private void calculateTaxBase(Operation o)
    {
        BigDecimal thisTaxUnits = this.taxUnits;
        BigDecimal oTaxUnits = o.taxUnits;

        BigDecimal income = BigDecimal.ZERO;
        BigDecimal cost = BigDecimal.ZERO;
        try
        {
            if (o.taxUnits.compareTo(this.taxUnits) == -1)
            {
                income = o.taxUnits.multiply(this.price);
                cost = o.taxUnits.multiply(o.price).negate();
                this.taxBase = this.taxBase.add(income.add(cost));
                this.taxUnits = this.taxUnits.add(o.taxUnits.negate());
                o.taxUnits = BigDecimal.ZERO;
            }
            else
            {
                income = this.taxUnits.multiply(this.price);
                cost = this.taxUnits.multiply(o.price).negate();
                this.taxBase = this.taxBase.add(income.add(cost));
                o.taxUnits = o.taxUnits.add(this.taxUnits.negate());
                this.taxUnits = BigDecimal.ZERO;
            }

            if (o instanceof Convert)
            {
                Convert oc = (Convert) o;
                if (oc.getParrentOperation() == null)
                {
                    if (oc.taxUnits.compareTo(thisTaxUnits) == -1)
                    {
                        this.taxBase = this.taxBase.add(oc.getTaxBaseInherited());
                        oc.setTaxBaseInherited(BigDecimal.ZERO);
                    }
                    else
                    {
                        BigDecimal factor = thisTaxUnits.divide(oTaxUnits, fundName.getUnitsCalculationScale(), AppConstants.ROUNDING_MODE);
                        this.taxBase = this.taxBase.add(oc.getTaxBaseInherited().multiply(factor));
                        oc.setTaxBaseInherited(oc.getTaxBaseInherited().add(oc.getTaxBaseInherited().multiply(factor).negate()));
                    }
                }
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException("Operation.calculateTaxBase() date=" + this.getDate() + ", value=" + this.value + " o(date=" + o.date + ", value=" + o.value + ")", e);
        }
    }

    public void perform(Register register)
    {
        this.price = register.getPriceProvider().getPriceAtLastBusinessDay(this.getDate());

        this.units = this.getValue().abs().divide(price, fundName.getUnitsCalculationScale(), AppConstants.ROUNDING_MODE);

        this.taxUnits = this.units.multiply(new BigDecimal(1));

        calculateUnitsTotal(register);
    }

    protected abstract void calculateUnitsTotal(Register register);

    public int compareTo(Operation o)
    {
        return getDate().compareTo(o.getDate());
    }

    public BigDecimal showUnits()
    {
        return units.setScale(fundName.getUnitsPresentationScale(), AppConstants.ROUNDING_MODE);
    }

    public BigDecimal showTaxBase()
    {
        return taxBase.setScale(2, AppConstants.ROUNDING_MODE);
    }

    public BigDecimal showUnitsTotal()
    {
        return unitsTotal.setScale(fundName.getUnitsPresentationScale(), AppConstants.ROUNDING_MODE);
    }

    public String toString()
    {
        return date.toString(DATE_FORMAT) + ", " + fundName.getName() + ", " + getValue() + ", price=" + getPrice() + ", units=" + showUnits() + ", unitsTotal=" + showUnitsTotal();
    }
}
