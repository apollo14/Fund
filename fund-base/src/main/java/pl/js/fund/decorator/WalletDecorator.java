package pl.js.fund.decorator;

import java.util.List;
import java.util.Map;

import pl.js.fund.model.IWallet;
import pl.js.fund.model.Register;
import pl.js.fund.operation.Operation;
import pl.js.fund.simulation.ISimulation;

public abstract class WalletDecorator implements IWallet
{

    IWallet wallet;

    public WalletDecorator(IWallet wallet)
    {
        this.wallet = wallet;
    }

    public Map<String, Register> getRegisters()
    {
        return wallet.getRegisters();
    }

    public List<Operation> getOperations()
    {
        return wallet.getOperations();
    }

    public void loadOperations(String operationsUrl)
    {
        wallet.loadOperations(operationsUrl);

    }

    public void performOperation(Operation operation)
    {
        wallet.performOperation(operation);
    }

    public void performOperations()
    {
        wallet.performOperations();
    }

    public void performOperations(ISimulation simulation){
    	wallet.performOperations(simulation);
    }
    
    public void addRegister(Register register)
    {
        wallet.addRegister(register);
    }

}
