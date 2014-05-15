package pl.js.fund.model;

import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Share extends Stock
{
    private static final Logger log        = LoggerFactory.getLogger(Share.class);
    private static final String urlPattern = "http://www.money.pl/gielda/archiwum/spolki/";

    public Share()
    {
        priceFileSeparator = '\'';
        pricePositionIndex = 4;
    }

    @Override
    public URL evaluatePricingURL()
    {
        URL result = null;
        try
        {
            result = new URL(urlPattern);
        }
        catch (Exception e)
        {
            log.error("", e);
        }
        return result;
    }

}
