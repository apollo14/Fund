package pl.js.fund.model;

import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Map;
import java.util.TreeMap;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.js.fund.enums.FundName;
import pl.js.fund.enums.FundOperationsFileColumns;
import pl.js.fund.enums.OperationType;
import pl.js.fund.enums.UmbrellaId;
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

    Map<FundName, Register>     registers;
    Map<UmbrellaId, Umbrella>   umbrellas;

    public Wallet()
    {
        registers = new TreeMap<FundName, Register>();
        umbrellas = new TreeMap<UmbrellaId, Umbrella>();
    }

    public Register getRegister(FundName fundName)
    {
        return registers.get(fundName);
    }

    public void addRegister(FundName fundName)
    {
        if (!this.registers.keySet().contains(fundName))
        {
            try
            {

                Fund fund = fundName.instantiate();
                Register register = new Register();
                register.setFund(fund);
                registers.put(fundName, register);
                Umbrella umbrella = null;
                if (!umbrellas.keySet().contains(fund.fundName.getUmbrellaId()))
                {
                    umbrella = new Umbrella();
                    umbrellas.put(fund.fundName.getUmbrellaId(), umbrella);
                }
                else
                {
                    umbrella = umbrellas.get(fund.fundName.getUmbrellaId());
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
            	if ("".equals(nextLine)){
            		continue;
            	}
                FundName fundName = FundName.parse(nextLine[FundOperationsFileColumns.FUND_NAME.getId()]);
                if (fundName == null)
                {
                    continue;
                }
                BigDecimal value = new BigDecimal(nextLine[FundOperationsFileColumns.VALUE.getId()].replace(" ", ""));
                LocalDate date = DateUtils.parseFromString(nextLine[FundOperationsFileColumns.DATE.getId()], Operation.DATE_FORMAT);
                OperationType operationType = OperationType.parse(nextLine[FundOperationsFileColumns.OPERATION_TYPE.getId()]);
                FundName targetFundName = null;
                if (OperationType.CONVERSION.equals(operationType))
                {
                    if (nextLine.length == FundOperationsFileColumns.TARGET_FUND_NAME.getId() + 1)
                    {
                        targetFundName = FundName.parse(nextLine[FundOperationsFileColumns.TARGET_FUND_NAME.getId()]);
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
                operation.setValue(value);
                if (operation instanceof Convert)
                {
                    operation.setValue(operation.getValue().negate());
                }
                addOperation(operation);
                if (targetFundName != null)
                {
                    Convert targetOperation = new Convert();
                    targetOperation.setDate(date);
                    targetOperation.setFundName(targetFundName);
                    targetOperation.setValue(value);
                    ((Convert) operation).setParrentOperation(targetOperation);
                    targetOperation.setChildOperation((Convert) operation);
                    addOperation(targetOperation);
                }

            }
        }
        catch (Exception e)
        {

            log.error(operation.getDate() + " " + operation.getFundName(), e);
        }
        finally
        {
            try
            {
                reader.close();
            }
            catch (IOException ioe)
            {
                throw new RuntimeException("Wallet.loadOperations()", ioe);
            }
        }

    }

    public void addOperation(Operation operation)
    {
        if (!registers.containsKey(operation.getFundName()))
        {
            addRegister(operation.getFundName());
        }
        registers.get(operation.getFundName()).addOperation(operation);
        UmbrellaId umbrellaId = registers.get(operation.getFundName()).getFund().fundName.getUmbrellaId();
        umbrellas.get(umbrellaId).addOperations(operation);
    }

    public void performOperations()
    {
        for (FundName fundName : this.registers.keySet())
        {
            this.getRegister(fundName).performOperations();
        }
    }

    public void performOperations(ISimulation simulation)
    {
        for (FundName fundName : this.registers.keySet())
        {
            this.getRegister(fundName).performOperations(simulation.getOperations());
        }
    }

}
