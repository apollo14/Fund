package pl.js.fund.operation;

import java.math.BigDecimal;
import java.util.List;

import org.joda.time.LocalDate;

import pl.js.fund.enums.FundName;
import pl.js.fund.model.Register;

public abstract class Operation implements Comparable<Operation>
{
    public static final String  DATE_FORMAT     = "dd-MM-yyyy";
    public static final Integer ROUNDING_FACTOR = 100000;

    private FundName            fundName;
    private LocalDate           date;
    private BigDecimal          value;
    protected BigDecimal        units;
    protected BigDecimal        price;
    protected BigDecimal        taxBase         = BigDecimal.ZERO;
    protected BigDecimal        taxUnits        = BigDecimal.ZERO;

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

    public BigDecimal getUnits()
    {
        return units;
    }

    public BigDecimal getPrice()
    {
        return price;
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
        Double thisTaxUnits = this.taxUnits;
        Double oTaxUnits = o.taxUnits;

        if (o.taxUnits < this.taxUnits)
        {
            this.taxBase = this.taxBase + (Math.round((o.taxUnits * this.price) * 100) / 100 - Math.round((o.taxUnits * o.price) * 100) / 100);
            this.taxUnits = this.taxUnits - o.taxUnits;
            o.taxUnits = 0.0;
        }
        else
        {
            this.taxBase = this.taxBase + (Math.round((this.taxUnits * this.price) * 100) / 100 - Math.round((this.taxUnits * o.price) * 100) / 100);
            o.taxUnits = o.taxUnits - this.taxUnits;
            this.taxUnits = 0.0;
        }

        if (o instanceof Convert)
        {
            Convert oc = (Convert) o;
            if (oc.getConnectedOperation() == null)
            {
                if (oc.taxUnits < thisTaxUnits)
                {
                    this.taxBase = this.taxBase + oc.getTaxBaseInherited();
                    oc.setTaxBaseInherited(0.0);
                }
                else
                {
                    this.taxBase = this.taxBase + (oc.getTaxBaseInherited() * thisTaxUnits / oTaxUnits);
                    oc.setTaxBaseInherited(oc.getTaxBaseInherited() - oc.getTaxBaseInherited() * thisTaxUnits / oTaxUnits);
                }
            }
        }
    }

    public void perform(Register register)
    {
        this.price = register.getPriceProvider().getPriceAtLastBusinessDay(this.getDate());

        this.units = this.getValue().abs().divide(price);
        // this.units = (double) Math.round(this.units * register.getFund().getUnitsRoundingFactor()) /
        // register.getFund().getUnitsRoundingFactor();

        this.taxUnits = this.units.multiply(new BigDecimal(1));
    }

    public BigDecimal getTaxBase()
    {
        return taxBase;
    }

    public BigDecimal getTaxUnits()
    {
        return taxUnits;
    }

    public int compareTo(Operation o)
    {
        return getDate().compareTo(o.getDate());
    }

    public String toString()
    {
        return date.toString(DATE_FORMAT) + ", " + value.toString() + ", price=" + price.toString() + ", units=" + units.toString();
    }
}
