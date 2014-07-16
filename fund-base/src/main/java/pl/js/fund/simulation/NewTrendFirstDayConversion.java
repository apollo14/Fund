package pl.js.fund.simulation;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;

import pl.js.fund.enums.FundName;
import pl.js.fund.model.IWallet;
import pl.js.fund.operation.Operation;
import pl.js.fund.utils.DateUtils;

public class NewTrendFirstDayConversion implements ISimulation
{

    private FundName  safe;
    private FundName  riski;
    private LocalDate start;
    private IWallet   wallet;

    public NewTrendFirstDayConversion(FundName safe, FundName riski, IWallet wallet)
    {
        this.safe = safe;
        this.riski = riski;
        this.wallet = wallet;
        this.wallet.addRegister(safe);
        this.wallet.addRegister(riski);
    }

    public LocalDate getStart()
    {
        return start;
    }

    public void setStart(LocalDate start)
    {
        this.start = start;
    }

    public List<Operation> getOperations()
    {
        List<Operation> result = new ArrayList<Operation>();
        LocalDate date = DateUtils.getLastBusinessDay(start);
        LocalDate currentDate = DateUtils.getLastBusinessDay(new LocalDate());

        while (currentDate.isAfter(date))
        {
            if (wallet.getRegister(safe.getName()).getPriceProvider().getPrice(date) != null)
            {

            }

            date = date.plusDays(1);
        }

        return result;
    }
}
