package pl.js.fund.model;

import java.io.InputStreamReader;
import java.net.URL;
import java.util.Map;
import java.util.TreeMap;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.js.fund.enums.FundName;
import pl.js.fund.enums.FundOperationsFileColumns;
import pl.js.fund.enums.OperationType;
import pl.js.fund.operation.Buy;
import pl.js.fund.operation.Convert;
import pl.js.fund.operation.Operation;
import pl.js.fund.operation.Sell;
import pl.js.fund.simulation.ISimulation;
import pl.js.fund.utils.DateUtils;
import au.com.bytecode.opencsv.CSVReader;

public class Wallet implements IWallet
{
    private final static Logger log = LoggerFactory.getLogger(Wallet.class);

    Map<String, Umbrella>       umbrellas;
    Map<String, Register>       registers;

    public Wallet()
    {
        registers = new TreeMap<String, Register>();
        umbrellas = new TreeMap<String, Umbrella>();
    }

    public Register getRegister(String fundName)
    {
        return registers.get(fundName);
    }

    public void addRegister(FundName fundName)
    {
        Register register = new Register();
        register.setFund(fundName.instantiate());
        this.registers.put(fundName.getName(), register);
    }

    private void addRegister(String fundName)
    {
        if (!this.registers.keySet().contains(fundName))
        {
            try
            {
                Fund fund = FundName.parse(fundName).instantiate();
                Register register = new Register();
                register.setFund(fund);
                registers.put(fundName, register);
                Umbrella umbrella = null;
                if (!umbrellas.keySet().contains(fund.fundName.getUmbrellaId().getId()))
                {
                    umbrella = new Umbrella(fund.fundName.getUmbrellaId());
                    umbrellas.put(fund.fundName.getUmbrellaId().getId(), umbrella);
                }
                else
                {
                    umbrella = umbrellas.get(fund.fundName.getUmbrellaId().getId());
                }
                register.setUmbrella(umbrella);
            }
            catch (Exception e)
            {

            }

        }
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
                String fundName = nextLine[FundOperationsFileColumns.FUND_NAME.getId()];
                if (FundName.contains(fundName))
                {
                    Double value = Double.parseDouble(nextLine[FundOperationsFileColumns.VALUE.getId()].replace(" ", ""));
                    LocalDate date = DateUtils.parseFromString(nextLine[FundOperationsFileColumns.DATE.getId()], Operation.DATE_FORMAT);
                    OperationType operationType = OperationType.parse(nextLine[FundOperationsFileColumns.OPERATION_TYPE.getId()]);
                    String targetFundName = null;
                    if (OperationType.CONVERSION.equals(operationType))
                    {
                        if (nextLine.length == FundOperationsFileColumns.TARGET_FUND_NAME.getId() + 1)
                        {
                            targetFundName = nextLine[FundOperationsFileColumns.TARGET_FUND_NAME.getId()];
                        }
                        else
                        {
                            throw new RuntimeException("Wallet.loadOperations() - Target fund empty in conversion operation");
                        }
                    }

                    switch (operationType)
                    {
                        case BUY:
                            operation = new Buy();
                            break;
                        case SELL:
                            operation = new Sell();
                            break;
                        case CONVERSION:
                            operation = new Convert();
                            break;
                        default:
                            break;
                    }

                    operation.setDate(date);
                    operation.setFundName(fundName);
                    operation.setValue(value * (-1));
                    addOperation(operation);
                    if (targetFundName != null)
                    {
                        Convert targetOperation = new Convert();
                        targetOperation.setDate(date);
                        targetOperation.setFundName(targetFundName);
                        targetOperation.setValue(value);
                        ((Convert) operation).setConnectedOperation(targetOperation);
                        targetOperation.setConnectedOperation((Convert) operation);
                    }
                }
            }
        }
        catch (Exception e)
        {
            log.error(operation.getDate() + " " + operation.getFundName(), e);
        }

    }

    private void addOperation(Operation operation)
    {
        if (!registers.containsKey(operation.getFundName()))
        {
            addRegister(operation.getFundName());
        }
        registers.get(operation.getFundName()).addOperation(operation);
        String umbrellaId = registers.get(operation.getFundName()).getFund().fundName.getUmbrellaId().getId();
        umbrellas.get(umbrellaId).addOperations(operation);
    }

    public void performOperations()
    {
        for (String fundName : this.registers.keySet())
        {
            this.getRegister(fundName).performOperations();
        }
    }

    public void performOperations(ISimulation simulation)
    {
        for (String fundName : this.registers.keySet())
        {
            this.getRegister(fundName).performOperations(simulation.getOperations());
        }
    }

}
