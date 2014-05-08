package pl.js.fund.price;

import org.joda.time.LocalDate;

public interface IPriceProvider
{
    public Double getPriceAtLastBusinessDay(LocalDate date);

    public Double getPrice(LocalDate date);
}
