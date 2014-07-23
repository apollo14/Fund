package pl.js.fund.model;

import java.util.List;

import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import pl.js.fund.enums.FundName;
import pl.js.fund.operation.Operation;
import pl.js.fund.simulation.BuyFridaySellTuesday;

@RunWith(MockitoJUnitRunner.class)
public class BuyFridaySellTuesdayTest
{

    private BuyFridaySellTuesday simulation;
    private Wallet               wallet;

    @Before
    public void setUp()
    {
        simulation = new BuyFridaySellTuesday();
        wallet = new Wallet();
    }

    @Test
    public void getOperationsTest()
    {

        simulation.setStart(new LocalDate(2007, 10, 15));
        simulation.setEnd(new LocalDate(2013, 7, 23));
        simulation.addFund(FundName.UI_A);
        // simulation.addFund(FundName.UI_ANE);
        simulation.setBuyDay(DateTimeConstants.MONDAY);
        // simulation.setBuyDay(DateTimeConstants.THURSDAY);
        // simulation.setBuyDay(DateTimeConstants.FRIDAY);
        // simulation.setSellDay(DateTimeConstants.MONDAY);
        // simulation.setSellDay(DateTimeConstants.TUESDAY);
        simulation.setSellDay(DateTimeConstants.WEDNESDAY);
        List<Operation> operatios = simulation.getOperations();
        System.out.print("");
        wallet.performOperations(simulation);
        System.out.print("Balance=" + simulation.getBalance());

    }
}
