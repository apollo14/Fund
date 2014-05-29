package pl.js.fund.model;

import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.js.fund.enums.FundName;

public class InvestorFund extends Fund
{
    private static final Logger log        = LoggerFactory.getLogger(InvestorFund.class);
    private static final String urlPattern = "https://investors.pl/fundusze-inwestycyjne/export/csv/%s";

    public InvestorFund(FundName fn)
    {
        super(fn.getId(), fn.getName());
        this.unitsRoundingFactor = 1000000;
        this.pricePositionIndex = 1;
        this.priceFileSeparator = ',';
    }

    @Override
    public URL evaluatePricingURL()
    {
        URL result = null;
        try
        {
            result = new URL(String.format(urlPattern, new Object[] { id }));
        }
        catch (Exception e)
        {
            log.error("", e);
        }
        return result;
    }

}
