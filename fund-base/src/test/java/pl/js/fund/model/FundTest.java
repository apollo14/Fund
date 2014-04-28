package pl.js.fund.model;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import pl.js.fund.enums.FundName;

@RunWith(MockitoJUnitRunner.class)
public class FundTest
{

    @Before
    public void setUp()
    {

    }

    @Test
    public void createUnionInvestmentAkcjeNowaEuropa()
    {
        Fund fund = new UnionInvestmentFund(FundName.UI_ANE);

        Assert.assertNotNull(fund);
        Assert.assertTrue(fund.getUrl().toString().contains(fund.getId()));
    }
}
