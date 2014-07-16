package pl.js.fund.operation;

import java.util.List;

import org.joda.time.LocalDate;

import pl.js.fund.model.Register;

public abstract class Operation implements Comparable<Operation>
{
    public static final String  DATE_FORMAT     = "dd-MM-yyyy";
    public static final Integer ROUNDING_FACTOR = 100000;

    private String              fundName;
    private LocalDate           date;
    private Double              value;
    protected Double            units;
    protected Double            price;
    protected Double            taxBase         = 0.0;
    protected Double            taxUnits        = 0.0;

    public String getFundName()
    {
        return fundName;
    }

    public void setFundName(String fundName)
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

    public Double getValue()
    {
        return value;
    }

    public void setValue(Double value)
    {
        this.value = value;
    }

    public Double getUnits()
    {
        return units;
    }

    public Double getPrice()
    {
        return price;
    }

    protected Double calculateTaxBase(Register register)
    {
        // w ramach funduszu - kompensacja pionowa
        List<Operation> operations = register.getOperations().subList(0, register.getOperations().size());
        // w ramach parasola - kompensacja pozioma
        // List<Operation> operations = register.getUmbrella().getOperations().subList(0,
        // register.getUmbrella().getOperations().size());
        for (Operation o : operations)
        {
            if ((o instanceof Buy) && o.taxUnits > 0)
            {
                calculateTaxBase(o);
            }
            if ((o instanceof Convert) && o.taxUnits > 0)// && ((Convert) o).getConnectedOperation() != null)
            {
                calculateTaxBase(o);
            }
            if (this.taxUnits == 0.0)
            {
                break;
            }
        }
        if (taxBase > 0)
        {
            return new Double(Math.round(taxBase * 19)) / 100;
        }
        return 0.0;
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

        this.units = Math.abs(this.getValue()) / price;
        this.units = (double) Math.round(this.units * register.getFund().getUnitsRoundingFactor()) / register.getFund().getUnitsRoundingFactor();

        this.taxUnits = new Double(this.units);
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
