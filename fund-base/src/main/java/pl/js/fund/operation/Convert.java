package pl.js.fund.operation;

import pl.js.fund.model.Register;

public class Convert extends Operation
{

    private Convert connectedOperation;

    @Override
    public void perform(Register register)
    {
        super.perform(register);
        register.setUnits(register.getUnits() + units);

    }

    public void setConnectedOperation(Convert operation)
    {
        this.connectedOperation = operation;
    }

    public String toString()
    {
        return "C " + super.toString();
    }

}
