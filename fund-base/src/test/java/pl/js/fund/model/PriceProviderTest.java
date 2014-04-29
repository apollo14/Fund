package pl.js.fund.model;

import junit.framework.Assert;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import pl.js.fund.enums.FundName;

@RunWith(MockitoJUnitRunner.class)
public class PriceProviderTest
{

    PriceProvider provider;

    @Before
    public void setUp()
    {
        provider = new PriceProvider();
    }

    @Test
    public void investorRosjaTest()
    {
        priceproviderForFund(new InvestorFund(FundName.INV_ROSJA));
    }

    @Test
    public void investorTurcjaTest()
    {
        priceproviderForFund(new InvestorFund(FundName.INV_TURCJA));
    }

    @Test
    public void investorGotówkowyTest()
    {
        priceproviderForFund(new InvestorFund(FundName.INV_GOTOWKOWY));
    }

    @Test
    public void unionInvestmentANETest()
    {
        priceproviderForFund(new UnionInvestmentFund(FundName.UI_ANE));
    }

    @Test
    public void unionInvestmentATest()
    {
        priceproviderForFund(new UnionInvestmentFund(FundName.UI_A));
    }

    @Test
    public void unionInvestmentPTest()
    {
        priceproviderForFund(new UnionInvestmentFund(FundName.UI_P));
    }

    @Test
    public void unionInvestmentONETest()
    {
        priceproviderForFund(new UnionInvestmentFund(FundName.UI_ONE));
    }

    private void priceproviderForFund(Fund fund)
    {
        provider.setFund(fund);

        Double price = provider.getPriceAtLastBusinessDay(new LocalDate(2014, 04, 14));

        Assert.assertNotNull(price);
    }
}
