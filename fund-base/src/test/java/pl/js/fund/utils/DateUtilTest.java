package pl.js.fund.utils;

import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;

public class DateUtilTest
{

    @Test
    public void getLastBusinessDay()
    {
        LocalDate date = DateUtils.getLastBusinessDay(new LocalDate(2014, 04, 22));
        Assert.assertEquals(new LocalDate(2014, 04, 22), date);

        LocalDate dateBad = DateUtils.getLastBusinessDay(new LocalDate(2014, 04, 20));
        Assert.assertEquals(new LocalDate(2014, 04, 18), dateBad);
    }
}
