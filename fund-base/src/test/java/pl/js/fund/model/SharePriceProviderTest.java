package pl.js.fund.model;

import junit.framework.Assert;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import pl.js.fund.price.SharePriceProvider;

@RunWith(MockitoJUnitRunner.class)
public class SharePriceProviderTest
{

    SharePriceProvider provider;

    @Before
    public void setUp()
    {
        provider = new SharePriceProvider();
    }

    @Test
    public void investorRosjaTest()
    {
        Share share = new Share();
        share.setId("ABE");

        provider.setShare(share);

        Double price = provider.getPriceAtLastBusinessDay(new LocalDate(2014, 04, 14));

        Assert.assertNotNull(price);

    }

}
