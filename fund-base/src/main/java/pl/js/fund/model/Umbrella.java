package pl.js.fund.model;

import java.util.ArrayList;
import java.util.List;

import pl.js.fund.enums.UmbrellaId;
import pl.js.fund.operation.Operation;

public class Umbrella
{
    private UmbrellaId      umbrellaId = null;
    private List<Operation> operations = new ArrayList<Operation>();

    public Umbrella(UmbrellaId umbrellaId)
    {
        this.umbrellaId = umbrellaId;
    }

    public void addOperations(Operation operation)
    {
        this.operations.add(operation);
    }

    public List<Operation> getOperations()
    {
        return operations;
    }

}
