package pl.js.fund.model;

import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.js.fund.enums.FundName;

public class UnionInvestmentFund extends Fund
{
    private static final Logger log        = LoggerFactory.getLogger(UnionInvestmentFund.class);
    private static final String urlPattern = "http://wyceny.union-investment.pl/historiawycen.php?idfunduszu=%s&action=sendCsv";

    public UnionInvestmentFund(FundName fn)
    {
        super(fn.getId(), fn.getName());
        this.unitsRoundingFactor = 100000;
        this.pricePositionIndex = 2;
        this.priceFileSeparator = ';';
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
