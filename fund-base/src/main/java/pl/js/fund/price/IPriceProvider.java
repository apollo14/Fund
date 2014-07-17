package pl.js.fund.price;

import java.math.BigDecimal;

import org.joda.time.LocalDate;

public interface IPriceProvider
{
    public BigDecimal getPriceAtLastBusinessDay(LocalDate date);

    public BigDecimal getPrice(LocalDate date);
}
