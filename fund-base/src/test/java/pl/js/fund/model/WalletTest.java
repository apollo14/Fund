package pl.js.fund.model;

import java.math.BigDecimal;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import pl.js.fund.constants.AppConstants;
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

        Assert.assertNotNull(wallet.getRegister(FundName.UI_ANE));
        Assert.assertNotNull(wallet.getRegister(FundName.UI_P));
        Assert.assertEquals(1, wallet.getRegister(FundName.UI_ANE).getOperations().size());
        Assert.assertEquals(1, wallet.getRegister(FundName.UI_P).getOperations().size());
    }

    // @Test
    public void performOperations2()
    {
        wallet.loadOperations(this.getClass().getResource("/operacje2.txt").toString());
        wallet.performOperations();

        Assert.assertNotNull(wallet.getRegister(FundName.UI_ANE));
        Assert.assertNotNull(wallet.getRegister(FundName.UI_P));
        Assert.assertEquals(3, wallet.getRegister(FundName.UI_ANE).getOperations().size());
        Assert.assertEquals(3, wallet.getRegister(FundName.UI_P).getOperations().size());

    }

    // @Test
    public void performOperations3()
    {
        wallet.loadOperations(this.getClass().getResource("/operacje3.txt").toString());
        wallet.performOperations();

        Assert.assertNotNull(wallet.getRegister(FundName.UI_ANE));
        Assert.assertNotNull(wallet.getRegister(FundName.INV_ROSJA));
        Assert.assertEquals(3, wallet.getRegister(FundName.UI_ANE).getOperations().size());
        Assert.assertEquals(2, wallet.getRegister(FundName.INV_ROSJA).getOperations().size());
    }

    // @Test
    public void performOperations4()
    {
        wallet.loadOperations(this.getClass().getResource("/operacje4.txt").toString());
        wallet.performOperations();

        Assert.assertNotNull(wallet.getRegister(FundName.UI_ANE));
        Assert.assertNotNull(wallet.getRegister(FundName.INV_ROSJA));
    }

    @Test
    public void performOperationsInvestorPart()
    {
        wallet.loadOperations(this.getClass().getResource("/INVESTOR.txt").toString());
        wallet.performOperations();

        assertEqualsBD(FundName.INV_ROSJA, 0, new BigDecimal(1.120134), new BigDecimal(1.120134));
        assertEqualsBD(FundName.INV_ROSJA, 1, new BigDecimal(5.742836), new BigDecimal(6.862970));
        assertEqualsBD(FundName.INV_ROSJA, 2, new BigDecimal(2.725538), new BigDecimal(9.588509));
        assertEqualsBD(FundName.INV_ROSJA, 3, new BigDecimal(5.110515), new BigDecimal(4.477994));

        assertEqualsBD(FundName.INV_TURCJA, 0, new BigDecimal(4.195863), new BigDecimal(4.195863));
        assertEqualsBD(FundName.INV_TURCJA, 1, new BigDecimal(21.792190), new BigDecimal(25.988053));

        assertEqualsBD(FundName.INV_ROSJA, 4, new BigDecimal(2.586653), new BigDecimal(1.891341));

        assertEqualsBD(FundName.INV_TURCJA, 2, new BigDecimal(13.993190), new BigDecimal(11.994863));
        assertEqualsBD(FundName.INV_TURCJA, 3, new BigDecimal(2.297055), new BigDecimal(14.291918));
        assertEqualsBD(FundName.INV_TURCJA, 4, new BigDecimal(14.291918), new BigDecimal(0.000000));
        assertEqualsBD(FundName.INV_TURCJA, 5, new BigDecimal(11.582788), new BigDecimal(11.582788));

        assertEqualsBD(FundName.INV_ROSJA, 5, new BigDecimal(1.891341), new BigDecimal(0.000000));

        assertEqualsBD(FundName.INV_TURCJA, 6, new BigDecimal(1.344104), new BigDecimal(12.926892));
        assertEqualsBD(FundName.INV_TURCJA, 7, new BigDecimal(5.782352), new BigDecimal(18.709244));
        assertEqualsBD(FundName.INV_TURCJA, 8, new BigDecimal(14.807795), new BigDecimal(3.901449));

        assertEqualsBD(FundName.INV_GOTOWKOWY, 0, new BigDecimal(19.841270), new BigDecimal(19.841270));
        assertEqualsBD(FundName.INV_GOTOWKOWY, 1, new BigDecimal(19.841270), new BigDecimal(0.000000));

        assertEqualsBD(FundName.INV_TURCJA, 9, new BigDecimal(13.933476), new BigDecimal(17.834925));
        assertEqualsBD(FundName.INV_TURCJA, 10, new BigDecimal(17.834925), new BigDecimal(0.000000));

        assertEqualsBD(FundName.INV_ROSJA, 6, new BigDecimal(25.416739), new BigDecimal(25.416739));
        assertEqualsBD(FundName.INV_ROSJA, 7, new BigDecimal(25.416739), new BigDecimal(0.000000));

        System.out.print("");
    }

    private BigDecimal roundValue(FundName fundName, BigDecimal value)
    {
        return value.setScale(fundName.getUnitsPresentationScale(), AppConstants.ROUNDING_MODE);
    }

    private void assertEqualsBD(FundName fundName, int indexOperation, BigDecimal unitsExpected, BigDecimal unitsTotalExpected)
    {
        Assert.assertEquals(roundValue(fundName, wallet.getRegister(fundName).getOperations().get(indexOperation).getUnits()), roundValue(FundName.INV_ROSJA, unitsExpected));
        Assert.assertEquals(roundValue(fundName, wallet.getRegister(fundName).getOperations().get(indexOperation).getUnitsTotal()), roundValue(FundName.INV_ROSJA, unitsTotalExpected));
    }

}
