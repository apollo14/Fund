package pl.js.fund.price;

import java.util.Map;

import org.joda.time.LocalDate;

import pl.js.fund.model.Stock;
import pl.js.fund.utils.DateUtils;

public abstract class PriceProvider implements IPriceProvider
{
    protected String              dateFormat;
    protected Stock               stock;
    protected Map<String, Double> prices;

    public Double getPriceAtLastBusinessDay(LocalDate date)
    {

        Double result = null;

        if (this.prices == null || this.prices.size() == 0)
        {
            load();
        }
        do
        {
            result = this.prices.get(DateUtils.formatAsString(DateUtils.getLastBusinessDay(date), dateFormat));
            date = date.minusDays(1);
        }
        while (result == null);

        return result;
    }

    public Double getPrice(LocalDate date)
    {
        Double result = null;

        if (prices == null || prices.size() == 0)
        {
            load();
        }
        result = prices.get(DateUtils.formatAsString(DateUtils.getLastBusinessDay(date), dateFormat));

        return result;
    }

    protected abstract void load();
}
