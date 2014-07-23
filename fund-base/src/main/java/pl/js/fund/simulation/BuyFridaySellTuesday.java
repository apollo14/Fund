package pl.js.fund.simulation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.LocalDate;

import pl.js.fund.enums.FundName;
import pl.js.fund.operation.Buy;
import pl.js.fund.operation.Operation;
import pl.js.fund.operation.Sell;

public class BuyFridaySellTuesday implements ISimulation
{
    public static String   DATE_FORMAT = "yyyy-MM-dd";
    private LocalDate      start;
    private LocalDate      end;
    private List<FundName> funds;
    List<Operation>        operations;
    Integer                buyDay;
    Integer                sellDay;

    public BuyFridaySellTuesday()
    {
        funds = new ArrayList<FundName>();
        operations = new ArrayList<Operation>();
        end = new LocalDate(new Date().getTime());
        if (start == null)
        {
            start = new LocalDate(end).minusYears(1);
        }
    }

    public void addFund(FundName fundName)
    {
        this.funds.add(fundName);
    }

    public void setStart(LocalDate start)
    {
        this.start = start;
    }

    public void setEnd(LocalDate end)
    {
        this.end = end;
    }

    public void setBuyDay(Integer buyDay)
    {
        this.buyDay = buyDay;
    }

    public void setSellDay(Integer sellDay)
    {
        this.sellDay = sellDay;
    }

    public List<Operation> getOperations()
    {
        if (operations.size() == 0)
        {
            LocalDate currentDate = new LocalDate(start);

            while (currentDate.isBefore(end))
            {
                LocalDate.Property pDoW = currentDate.dayOfWeek();
                if (pDoW.get() == buyDay)
                {
                    for (FundName fundName : funds)
                    {
                        Buy buy = new Buy();
                        buy.setDate(currentDate);
                        buy.setFundName(fundName);
                        buy.setValue(new BigDecimal(1000.00));
                        operations.add(buy);
                    }
                }
                else if (pDoW.get() == sellDay && operations.size() > 0)
                {
                    for (FundName fundName : funds)
                    {
                        Sell sell = new Sell();
                        sell.setDate(currentDate);
                        sell.setFundName(fundName);
                        sell.setAllUnits();
                        operations.add(sell);
                    }

                }

                currentDate = currentDate.minusDays(-1);
            }
        }
        return operations;
    }

    public BigDecimal getBalance()
    {
        BigDecimal result = BigDecimal.ZERO;

        for (Operation o : operations)
        {
            if (o instanceof Sell)
            {
                result = result.add(o.getValue().add(new BigDecimal(1000).negate()));
            }
        }

        return result;
    }
}
