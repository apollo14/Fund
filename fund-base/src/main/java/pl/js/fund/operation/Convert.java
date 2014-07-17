package pl.js.fund.operation;

import java.math.BigDecimal;

import pl.js.fund.model.Register;

public class Convert extends Operation
{

    private Convert    connectedOperation;
    private BigDecimal taxBaseInherited;

    @Override
    public void perform(Register register)
    {
        super.perform(register);
        register.setUnits(register.getUnits().add(units));

        if (connectedOperation != null)
        {
            calculateTaxBase(register);
            connectedOperation.setTaxBaseInherited(this.taxBase);
        }
    }

    public void setConnectedOperation(Convert operation)
    {
        this.connectedOperation = operation;
    }

    public Convert getConnectedOperation()
    {
        return connectedOperation;
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
