package pl.js.fund.model;

import java.util.List;
import java.util.Map;

import pl.js.fund.enums.FundName;
import pl.js.fund.operation.Operation;
import pl.js.fund.simulation.ISimulation;

public interface IWallet
{

    public Map<String, Register> getRegisters();

    public List<Operation> getOperations();

    public void addRegister(FundName fundName);

    public void loadOperations(String operationsUrl);

    public void performOperation(Operation operation);

    public void performOperations();

    public void performOperations(ISimulation simulation);
}
