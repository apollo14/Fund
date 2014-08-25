package pl.js.fund.simulation;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.joda.time.LocalDate;

import pl.js.fund.enums.FundName;
import pl.js.fund.operation.Operation;
import pl.js.fund.operation.Sell;

public abstract class ASimulation implements ISimulation {
    public static String   DATE_FORMAT = "yyyy-MM-dd";
    protected LocalDate      start;
    protected LocalDate      end;
    protected List<FundName> funds;
    Map<FundName, List<Operation>>        operations;

    public void setStart(LocalDate start)
    {
        this.start = start;
    }
    public void setEnd(LocalDate end)
    {
        this.end = end;
    }
    public void addFund(FundName fundName)
    {
        this.funds.add(fundName);
    }


	@Override
	public abstract Map<FundName, List<Operation>> getOperations();
	
    public Map<FundName, BigDecimal> getBalance()
    {
        Map<FundName, BigDecimal> result = new TreeMap<FundName, BigDecimal>();

        for(FundName fn:operations.keySet()){
        	BigDecimal balance = BigDecimal.ZERO;
	        for (Operation o : operations.get(fn))
	        {
	            if (o instanceof Sell)
	            {
	            	balance = balance.add(o.getValue().add(new BigDecimal(1000).negate()));
	            }
	        }
	        result.put(fn, balance);
        }

        return result;
    }


}
