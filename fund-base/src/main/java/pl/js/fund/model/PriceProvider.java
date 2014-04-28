package pl.js.fund.model;

import java.io.InputStreamReader;
import java.util.Map;
import java.util.TreeMap;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.js.fund.utils.DateUtils;
import au.com.bytecode.opencsv.CSVReader;

public class PriceProvider
{
    private final static Logger log         = LoggerFactory.getLogger(PriceProvider.class);
    public static final String  DATE_FORMAT = "yyyy-MM-dd";

    private Fund                fund;
    private Map<String, Double> prices;

    public Fund getFund()
    {
        return fund;
    }

    public void setFund(Fund fund)
    {
        this.fund = fund;
    }

    public Double getPrice(LocalDate date)
    {

        Double result = null;

        if (prices == null || prices.size() == 0)
        {
            load();
        }
        do
        {
            result = prices.get(DateUtils.formatAsString(DateUtils.getLastBusinessDay(date), DATE_FORMAT));
            date = date.minusDays(1);
        }
        while (result == null);

        return result;
    }

    private void load()
    {
        CSVReader reader = null;
        String[] nextLine;
        prices = new TreeMap<String, Double>();
        try
        {
            reader = new CSVReader(new InputStreamReader(fund.getUrl().openStream()), ';', '\'', 1);
            while ((nextLine = reader.readNext()) != null)
            {
                prices.put(nextLine[0], Double.valueOf(nextLine[2].replace(',', '.')));
            }
        }
        catch (Exception e)
        {
            log.error("", e);
        }

    }
}
