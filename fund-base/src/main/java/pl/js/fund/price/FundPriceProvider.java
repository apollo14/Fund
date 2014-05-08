package pl.js.fund.price;

import java.io.InputStreamReader;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.js.fund.model.Fund;
import au.com.bytecode.opencsv.CSVReader;

public class FundPriceProvider extends PriceProvider
{
    private final static Logger log         = LoggerFactory.getLogger(FundPriceProvider.class);
    public static final String  DATE_FORMAT = "yyyy-MM-dd";

    public FundPriceProvider()
    {
        this.dateFormat = FundPriceProvider.DATE_FORMAT;
    }

    private Fund fund;

    public Fund getFund()
    {
        return fund;
    }

    public void setFund(Fund fund)
    {
        this.fund = fund;
    }

    protected void load()
    {
        CSVReader reader = null;
        String[] nextLine;
        prices = new TreeMap<String, Double>();
        try
        {
            reader = new CSVReader(new InputStreamReader(fund.evaluatePricingURL().openStream()), fund.getPriceFileSeparator(), '\'', 1);
            while ((nextLine = reader.readNext()) != null)
            {
                prices.put(nextLine[0].replace("\"", ""), Double.valueOf(nextLine[fund.getPricePositionIndex()].replace(',', '.')));
            }
        }
        catch (Exception e)
        {
            log.error("", e);
        }

    }
}
