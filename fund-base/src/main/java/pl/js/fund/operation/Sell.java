package pl.js.fund.operation;

import java.util.List;

import pl.js.fund.model.Register;

public class Sell extends Operation
{

    private Double taxValue = 0.0;

    @Override
    public void perform(Register register)
    {
        super.perform(register);
        register.setUnits(register.getUnits() - units);

        calculateTaxBase(register);

    }

    private void calculateTaxBase(Register register)
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
                if (o.taxUnits < this.taxUnits)
                {
                    this.taxBase = this.taxBase + ((o.taxUnits * this.price) - (o.taxUnits * o.price));
                    this.taxUnits = this.taxUnits - o.taxUnits;
                    o.taxUnits = 0.0;
                }
                else
                {
                    this.taxBase = this.taxBase + ((this.taxUnits * this.price) - (this.taxUnits * o.price));
                    o.taxUnits = o.taxUnits - this.taxUnits;
                    this.taxUnits = 0.0;
                }

            }
        }
        if (taxBase > 0)
        {
            taxValue = new Double(Math.round(taxBase * 19) / 100);
        }
    }

    public String toString()
    {
        return super.toString() + ", tax=" + taxValue;
    }
}
