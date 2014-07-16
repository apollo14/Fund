package pl.js.fund.operation;

import pl.js.fund.model.Register;

public class Convert extends Operation
{

    private Convert connectedOperation;
    private Double  taxBaseInherited;

    @Override
    public void perform(Register register)
    {
        super.perform(register);
        register.setUnits(register.getUnits() + units);

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

    public Double getTaxBaseInherited()
    {
        return taxBaseInherited;
    }

    public void setTaxBaseInherited(Double taxBaseInherited)
    {
        this.taxBaseInherited = taxBaseInherited;
    }

    public String toString()
    {
        return "C " + super.toString();
    }

}
