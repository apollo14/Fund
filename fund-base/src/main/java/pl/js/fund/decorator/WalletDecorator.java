package pl.js.fund.decorator;

import pl.js.fund.enums.FundName;
import pl.js.fund.model.IWallet;
import pl.js.fund.model.Register;
import pl.js.fund.simulation.ISimulation;

public abstract class WalletDecorator implements IWallet
{

    IWallet wallet;

    public WalletDecorator(IWallet wallet)
    {
        this.wallet = wallet;
    }

    public Register getRegister(String fundName)
    {
        return null;// wallet.getRegister(fundName);
    }

    public void loadOperations(String operationsUrl)
    {
        wallet.loadOperations(operationsUrl);

    }

    public void performOperations()
    {
        wallet.performOperations();
    }

    public void performOperations(ISimulation simulation)
    {
        wallet.performOperations(simulation);
    }

    public void addRegister(FundName fundName)
    {
        wallet.addRegister(fundName);
    }

}
