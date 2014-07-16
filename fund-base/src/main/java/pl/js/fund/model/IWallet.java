package pl.js.fund.model;

import pl.js.fund.enums.FundName;
import pl.js.fund.simulation.ISimulation;

public interface IWallet
{

    public Register getRegister(String fundName);

    public void addRegister(FundName fundName);

    public void loadOperations(String operationsUrl);

    public void performOperations();

    public void performOperations(ISimulation simulation);
}
