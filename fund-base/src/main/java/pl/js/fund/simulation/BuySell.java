package pl.js.fund.simulation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.joda.time.LocalDate;

import pl.js.fund.enums.FundName;
import pl.js.fund.operation.Buy;
import pl.js.fund.operation.Operation;
import pl.js.fund.operation.Sell;

public class BuySell extends ASimulation{

	public BuySell() {
		funds = new ArrayList<FundName>();
		operations = new TreeMap<FundName, List<Operation>>();
		end = new LocalDate(new Date().getTime());
		if (start == null) {
			start = new LocalDate(end).minusYears(1);
		}
	}

	public Map<FundName, List<Operation>> getOperations() {
		if (operations.size() == 0) {
			LocalDate currentDate = new LocalDate(start);

			while (currentDate.isBefore(end.minusDays(-1))) {
				if (currentDate.isEqual(start)) {
					for (FundName fundName : funds) {
						Buy buy = new Buy();
						buy.setDate(currentDate);
						buy.setFundName(fundName);
						buy.setValue(new BigDecimal(1000.00));
						if (!operations.containsKey(fundName)) {
							operations
									.put(fundName, new ArrayList<Operation>());
						}
						operations.get(fundName).add(buy);
					}
				}
				if (currentDate.isEqual(end) && operations.size() > 0) {
					for (FundName fundName : funds) {
						Sell sell = new Sell();
						sell.setDate(currentDate);
						sell.setFundName(fundName);
						sell.setAllUnits();
						if (operations.containsKey(fundName)) {
							operations.get(fundName).add(sell);
						}
					}

				}

				currentDate = currentDate.minusDays(-1);
			}
		}
		return operations;
	}

}