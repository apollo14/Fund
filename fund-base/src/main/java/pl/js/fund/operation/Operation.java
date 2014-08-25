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

    // fundusz
    protected FundName          fundName;
    // data operacji
    protected LocalDate         date;
    // wartoœæ operacji
    protected BigDecimal        value           = BigDecimal.ZERO;
    // sprzeda¿/konwersja wszystkich jednostek
    private boolean             allUnits        = false;
    //iloœæ jednostek udzia³owych
    protected BigDecimal        units           = BigDecimal.ZERO;
    // cena
    protected BigDecimal        price           = BigDecimal.ZERO;
    // podstawa podatku dla danej operacji zysk/strata
    protected BigDecimal        taxBase         = BigDecimal.ZERO;
    // jednostki pozosta³e do opodatkowania w danej operacji
    protected BigDecimal        taxUnits        = BigDecimal.ZERO;
    // suma jednostkek na rejestrze 
    protected BigDecimal        unitsTotal      = BigDecimal.ZERO;

    // operacja docelowa
    protected Operation         parrentOperation;
    // operacja Ÿrod³owa
    protected Operation         childOperation;

    protected Integer           sortingFactor;

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
        return value;
    }

    public void setValue(BigDecimal value)
    {
        this.value = value;
    }

    public BigDecimal getPrice()
    {
        return price;
    }

    public Integer getSortingFactor()
    {
        return sortingFactor;
    }

    public void setAllUnits()
    {
        this.allUnits = true;
    }

    protected BigDecimal calculateTaxBase(Register register)
    {
        // w ramach funduszu - kompensacja pionowa - bilans zarobku/straty tylko w ramach danego funduszu
        List<Operation> operations = register.getOperations().subList(0, register.getOperations().size());
        // w ramach parasola - kompensacja pozioma - bilans zarobku/straty w ramach parasola
        // List<Operation> operations = register.getUmbrella().getOperations().subList(0,
        // register.getUmbrella().getOperations().size());
        for (Operation o : operations)
        {
            if ((o instanceof Buy) && o.taxUnits.compareTo(BigDecimal.ZERO) == 1)
            {
            	// o.taxUnits > 0
                calculateTaxBase(o);
            }
            if ((o instanceof Convert) && o.taxUnits.compareTo(BigDecimal.ZERO) == 1)
            {
            	// o.taxUnits > 0
                calculateTaxBase(o);
            }
            if (this.taxUnits == BigDecimal.ZERO)
            {
                break;
            }
        }
        if (this.taxBase.compareTo(BigDecimal.ZERO) == 1)
        {
        	// this.taxBase > 0
            return this.taxBase.multiply(new BigDecimal(0.19));
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
            	//o.taxUnits < this.taxUnits
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
        BigDecimal fundValue = register.getUnits().multiply(this.price).setScale(2, AppConstants.ROUNDING_MODE);
        if (fundValue.compareTo(this.value.abs()) == 0)
        {
            this.units = register.getUnits().multiply(BigDecimal.ONE);// copy value
        }
        else
        {
            this.units = this.getValue().abs().divide(price, fundName.getUnitsCalculationScale(), AppConstants.ROUNDING_MODE);
        }
        if (this.units.compareTo(register.getUnits()) == 1 && (this instanceof Sell || (this instanceof Convert && this.value.compareTo(BigDecimal.ZERO) == -1)))
        {
            // ODKUPIENIE - wszystkiego wg zadanej wartoœci
            // KONWERSJA Z - wszystkiego wg zadanej wartoœci
            this.units = register.getUnits().multiply(new BigDecimal(1)); // copy value
        }
        if (allUnits)
        {
            // ODKUPIENIE - wszystkiego
            // KONWERSJA Z - wszystkiego
            this.units = register.getUnits().multiply(new BigDecimal(1)); // copy value
            this.value = this.units.multiply(this.price);
        }
        if (this instanceof Convert && this.getParrentOperation() != null)
        {
            ((Convert) this).getParrentOperation().setValue(this.units.multiply(this.price));
        }

        this.taxUnits = this.units.multiply(new BigDecimal(1)); // copy value

        calculateUnitsTotal(register);
    }

    public Operation getParrentOperation()
    {
        return parrentOperation;
    }

    public void setParrentOperation(Operation parrentOperation)
    {
        this.parrentOperation = parrentOperation;
    }

    public Operation getChildOperation()
    {
        return childOperation;
    }

    public void setChildOperation(Operation childOperation)
    {
        this.childOperation = childOperation;
    }

    protected abstract void calculateUnitsTotal(Register register);

   public int compareTo(Operation o)
    {
        int result = 0;
        // Data
        result = getDate().compareTo(o.getDate());
        if (result != 0)
        {
            return result;
        }
        // Value for Convert operations
        if (this instanceof Convert && o instanceof Convert){
	        result = getValue().compareTo(o.getValue());
	        if (result != 0)
	        {
	            return result;
	        }
        }

        // Typ operacji (BUY, SELL, CONVERT)
        result = getSortingFactor().compareTo(o.getSortingFactor());
        if (result != 0)
        {
            return result;
        }

        // Nazwa funduszu
        return getFundName().getName().compareTo(o.getFundName().getName());
    }

    public BigDecimal showValue()
    {
        return value.setScale(2, AppConstants.ROUNDING_MODE);
    }

    public BigDecimal getUnits()
    {
        return units;
    }

    public BigDecimal getUnitsRounded()
    {
        return units.setScale(fundName.getUnitsPresentationScale(), AppConstants.ROUNDING_MODE);
    }

    public BigDecimal showTaxBase()
    {
        return taxBase.setScale(2, AppConstants.ROUNDING_MODE);
    }

    public BigDecimal getUnitsTotal()
    {
        return unitsTotal;
    }

    public BigDecimal getUnitsTotalRounded()
    {
        return unitsTotal.setScale(fundName.getUnitsPresentationScale(), AppConstants.ROUNDING_MODE);
    }

    public String toString()
    {
        return date.toString(DATE_FORMAT) + ", " + fundName.getName() + ", " + showValue() + ", price=" + getPrice() + ", units=" + getUnitsRounded() + ", unitsTotal=" + getUnitsTotalRounded();
    }
}
