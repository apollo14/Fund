package pl.js.fund.model;

import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.js.fund.enums.FundName;
import pl.js.fund.enums.OperationType;
import pl.js.fund.operation.Buy;
import pl.js.fund.operation.Operation;
import pl.js.fund.operation.Sell;
import pl.js.fund.simulation.ISimulation;
import pl.js.fund.utils.DateUtils;
import au.com.bytecode.opencsv.CSVReader;

public class Wallet implements IWallet
{
    private final static Logger log = LoggerFactory.getLogger(Wallet.class);

    Map<String, Register>       registers;
    List<Operation>             operations;

    public Wallet()
    {
        registers = new TreeMap<String, Register>();
        operations = new ArrayList<Operation>();
    }

    public Map<String, Register> getRegisters()
    {
        return registers;
    }

    public List<Operation> getOperations()
    {
        return operations;
    }

    public void addRegister(FundName fundName)
    {
        Register register = new Register();
        register.setFund(fundName.instantiate());
        this.registers.put(fundName.getName(), register);
    }

    private void addRegister(String fundName)
    {
        if (!this.getRegisters().keySet().contains(fundName))
        {
            Fund fund = FundName.parse(fundName).instantiate();
            Register register = new Register();
            register.setFund(fund);
            registers.put(fundName, register);

        }
    }

    public void addOperation(Operation operation)
    {
        operations.add(operation);
    }

    public void loadOperations(String operationsUrl)
    {
        CSVReader reader = null;
        String[] nextLine;
        Operation operation = null;
        try
        {
            reader = new CSVReader(new InputStreamReader(new URL(operationsUrl).openStream()), ',', '\'', 0);
            while ((nextLine = reader.readNext()) != null)
            {
                switch (OperationType.parse(nextLine[2]))
                {
                    case BUY:
                        operation = new Buy();
                        break;
                    case SELL:
                        operation = new Sell();
                        break;
                    case CONVERSION_UP:
                        operation = new Sell();
                        break;
                    case CONVERSION_DOWN:
                        operation = new Sell();
                        break;
                    default:
                        break;
                }

                operation.setDate(DateUtils.parseFromString(nextLine[0], Operation.DATE_FORMAT));
                operation.setFundName(nextLine[1]);
                operation.setValue(Double.parseDouble(nextLine[3].replace(" ", "")));
                operations.add(operation);
                if (!registers.containsKey(operation.getFundName()))
                {
                    addRegister(operation.getFundName());
                }
            }
        }
        catch (Exception e)
        {
            log.error("", e);
        }
    }

    public void performOperation(Operation operation)
    {
        Register register = registers.get(operation.getFundName());
        operation.perform(register);
    }

    public void performOperations()
    {
        for (Operation operation : operations)
        {
            performOperation(operation);
        }
    }

    public void performOperations(ISimulation simulation)
    {
        for (Operation operation : simulation.getOperations())
        {
            performOperation(operation);
        }
    }

}
