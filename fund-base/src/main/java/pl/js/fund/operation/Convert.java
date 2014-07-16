package pl.js.fund.operation;

import pl.js.fund.model.Register;

public class Convert extends Operation
{

    @Override
    public void perform(Register register)
    {
        super.perform(register);
        register.setUnits(register.getUnits() + units);

    }
}
