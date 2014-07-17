package pl.js.fund.model;

import java.util.ArrayList;
import java.util.List;

import pl.js.fund.operation.Operation;

public class Umbrella
{

    private List<Operation> operations = new ArrayList<Operation>();

    public void addOperations(Operation operation)
    {
        this.operations.add(operation);
    }

    public List<Operation> getOperations()
    {
        return operations;
    }

}
