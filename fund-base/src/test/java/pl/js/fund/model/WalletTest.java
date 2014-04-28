package pl.js.fund.model;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import pl.js.fund.enums.FundName;
import pl.js.fund.operation.Operation;
import pl.js.fund.operation.Sell;

@RunWith(MockitoJUnitRunner.class)
public class WalletTest
{
    private Wallet wallet;

    @Before
    public void setUp()
    {
        wallet = new Wallet();
    }

    @Test
    public void addOperationTest() throws Exception
    {
        Operation operation = new Sell();

        wallet.addOperation(operation);

        Assert.assertNotNull(wallet.getOperations());
        Assert.assertEquals(1, wallet.getOperations().size());
    }

    @Test
    public void addRegister()
    {
        Fund fund = new UnionInvestmentFund(FundName.UI_ANE);

        Register register = new Register();
        register.setFund(fund);

        wallet.addRegister(register);

        Assert.assertNotNull(wallet.getRegisters());
        Assert.assertEquals(1, wallet.getRegisters().keySet().size());
    }

    @Test
    public void loadOperations()
    {
        Register registerANE = new Register();
        registerANE.setFund(new UnionInvestmentFund(FundName.UI_ANE));

        Register registerP = new Register();
        registerP.setFund(new UnionInvestmentFund(FundName.UI_P));

        wallet.addRegister(registerANE);
        wallet.addRegister(registerP);

        wallet.loadOperations(this.getClass().getResource("/operacje.txt").toString());

        Assert.assertNotNull(wallet.getOperations());
        Assert.assertEquals(2, wallet.getOperations().size());
    }

    @Test
    public void performOperations()
    {
        /*
         * Register registerANE = new Register();
         * registerANE.setFund(new UnionInvestmentFund(FundName.UI_ANE));
         * Register registerP = new Register();
         * registerP.setFund(new UnionInvestmentFund(FundName.UI_P));
         * wallet.addRegister(registerANE);
         * wallet.addRegister(registerP);
         */
        wallet.loadOperations(this.getClass().getResource("/operacje2.txt").toString());
        wallet.performOperations();

        Assert.assertNotNull(wallet.getOperations());
        Assert.assertEquals(4, wallet.getOperations().size());

    }
}
