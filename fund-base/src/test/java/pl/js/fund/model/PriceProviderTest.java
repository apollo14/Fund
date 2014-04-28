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
    public void downloadTest()
    {
        Fund fund = new UnionInvestmentFund(FundName.UI_ANE);
        provider.setFund(fund);

        Double price = provider.getPrice(new LocalDate(2014, 04, 14));

        Assert.assertNotNull(price);
    }
}
