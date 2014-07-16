package pl.js.fund.model;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import pl.js.fund.enums.FundName;

@RunWith(MockitoJUnitRunner.class)
public class WalletTest
{
    private Wallet wallet;

    @Before
    public void setUp()
    {
        wallet = new Wallet();
    }

    // @Test
    public void performOperations1()
    {

        wallet.loadOperations(this.getClass().getResource("/operacje.txt").toString());
        wallet.performOperations();

        Assert.assertNotNull(wallet.getRegister(FundName.UI_ANE.getName()));
        Assert.assertNotNull(wallet.getRegister(FundName.UI_P.getName()));
        Assert.assertEquals(1, wallet.getRegister(FundName.UI_ANE.getName()).getOperations().size());
        Assert.assertEquals(1, wallet.getRegister(FundName.UI_P.getName()).getOperations().size());
    }

    @Test
    public void performOperations2()
    {
        wallet.loadOperations(this.getClass().getResource("/operacje2.txt").toString());
        wallet.performOperations();

        Assert.assertNotNull(wallet.getRegister(FundName.UI_ANE.getName()));
        Assert.assertNotNull(wallet.getRegister(FundName.UI_P.getName()));
        Assert.assertEquals(2, wallet.getRegister(FundName.UI_ANE.getName()).getOperations().size());
        Assert.assertEquals(2, wallet.getRegister(FundName.UI_P.getName()).getOperations().size());

    }

    // @Test
    public void performOperations3()
    {
        wallet.loadOperations(this.getClass().getResource("/operacje3.txt").toString());
        wallet.performOperations();

        Assert.assertNotNull(wallet.getRegister(FundName.UI_ANE.getName()));
        Assert.assertNotNull(wallet.getRegister(FundName.INV_ROSJA.getName()));
        Assert.assertEquals(3, wallet.getRegister(FundName.UI_ANE.getName()).getOperations().size());
        Assert.assertEquals(2, wallet.getRegister(FundName.INV_ROSJA.getName()).getOperations().size());
    }

}
