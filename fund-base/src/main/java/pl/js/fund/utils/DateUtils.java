package pl.js.fund.utils;

import java.util.HashSet;

import net.objectlab.kit.datecalc.common.DateCalculator;
import net.objectlab.kit.datecalc.common.DefaultHolidayCalendar;
import net.objectlab.kit.datecalc.common.HolidayHandlerType;
import net.objectlab.kit.datecalc.joda.LocalDateKitCalculatorsFactory;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import pl.js.fund.operation.Operation;

public class DateUtils
{
    public static LocalDate getLastBusinessDay(LocalDate date)
    {
        HashSet<LocalDate> holidays = new HashSet<LocalDate>();
        holidays.add(new LocalDate(2014, 4, 21));

        DefaultHolidayCalendar<LocalDate> holidayCalendar = new DefaultHolidayCalendar<LocalDate>(holidays);

        LocalDateKitCalculatorsFactory.getDefaultInstance().registerHolidays("holidays", holidayCalendar);

        DateCalculator<LocalDate> dateCalculator = LocalDateKitCalculatorsFactory.getDefaultInstance().getDateCalculator("holidays", HolidayHandlerType.BACKWARD);
        dateCalculator.setStartDate(date);
        return dateCalculator.getCurrentBusinessDate();
    }

    public static String getLastBusinessDayAsString(LocalDate date)
    {
        DateTimeFormatter fmt = DateTimeFormat.forPattern(Operation.DATE_FORMAT);
        return fmt.print(getLastBusinessDay(date));
    }

    public static String formatAsString(LocalDate date, String format)
    {
        DateTimeFormatter fmt = DateTimeFormat.forPattern(format);
        return fmt.print(date);
    }

    public static LocalDate parseFromString(String date, String format)
    {
        DateTimeFormatter fmt = DateTimeFormat.forPattern(format);
        return fmt.parseDateTime(date).toLocalDate();
    }
}
