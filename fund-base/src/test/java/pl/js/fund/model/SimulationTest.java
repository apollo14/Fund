package pl.js.fund.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import pl.js.fund.enums.FundName;
import pl.js.fund.operation.Operation;
import pl.js.fund.simulation.ASimulation;
import pl.js.fund.simulation.BuyFridaySellTuesday;
import pl.js.fund.simulation.BuySell;

@RunWith(MockitoJUnitRunner.class)
public class SimulationTest
{

    private ASimulation simulation;
    private Wallet      wallet;
    private static LocalDate start;
    private static LocalDate end;
    private static FundName[] funds;
    
    @BeforeClass
    public static void setUpOnce(){
    	//start = new LocalDate(2014, 01, 01);
    	//end = new LocalDate(2014, 8, 25);
    	//start = new LocalDate(2013, 01, 01);
    	//end = new LocalDate(2013, 12, 31);
    	start = new LocalDate(2012, 01, 01);
    	end = new LocalDate(2012, 12, 31);


    	funds = new FundName[]{FundName.UI_A, FundName.UI_ANE};
        System.out.println("Start date:" + start);
        System.out.println("End date:" + end);    	
    }
    
    @Before
    public void setUp()
    {        
        wallet = new Wallet();
        
    }

    @Test
    public void buyFridaySellTuesdayTest()
    {
    	System.out.println("BuyFridaySellTuesday");
    	simulation = new BuyFridaySellTuesday(); 
        simulation.setStart(start);
        simulation.setEnd(end);
        for(FundName fn : funds){
        	simulation.addFund(fn);
        }
        
        simulation.getOperations();
        
        wallet.performOperations(simulation);
        Map<FundName, BigDecimal> balance = simulation.getBalance();
        balance.keySet().parallelStream().forEach(fn->{
        	System.out.println("Fund balance: " + fn.getName() + "=" + balance.get(fn));	
        });
    }
    
    @Test
    public void buySellTest(){
    	System.out.println("BuySell");
    	simulation = new BuySell(); 
        simulation.setStart(start);
        simulation.setEnd(end);
        for(FundName fn : funds){
        	simulation.addFund(fn);
        }
        simulation.getOperations();
        
        wallet.performOperations(simulation);
        Map<FundName, BigDecimal> balance = simulation.getBalance();
        balance.keySet().parallelStream().forEach(fn->{
        	System.out.println("Fund balance: " + fn.getName() + "=" + balance.get(fn));	
        });

    }
}
