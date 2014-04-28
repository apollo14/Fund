package pl.js.fund.decorator;

import org.joda.time.LocalDate;

import pl.js.fund.model.IWallet;
import pl.js.fund.model.Register;
import pl.js.fund.operation.Operation;
import pl.js.fund.utils.DateUtils;

public class DefaultWalletDecorator extends WalletDecorator
{

    public DefaultWalletDecorator(IWallet wallet)
    {
        super(wallet);
    }

    @Override
    public void performOperation(Operation operation)
    {
        wallet.performOperation(operation);
        System.out.println(logOperation(operation) + " " + logRegister(operation));
    }

    @Override
    public void performOperations()
    {
        for (Operation operation : getOperations())
        {
            performOperation(operation);
        }
        System.out.println("\nCurrent result:\n");
        for (String fundName : getRegisters().keySet())
        {
            Register register = getRegisters().get(fundName);
            System.out.println(logRegister(register));
        }
    }

    private String logOperation(Operation operation)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(DateUtils.formatAsString(operation.getDate(), Operation.DATE_FORMAT)).append(" ")
                .append(operation.getFundName()).append(" ")
                .append(operation.getUnits()).append("u ")
                .append(operation.getValue()).append("PLN");
        return sb.toString();
    }

    private String logRegister(Operation operation)
    {
        Register register = getRegisters().get(operation.getFundName());
        StringBuilder sb = new StringBuilder();
        sb.append(register.getPriceProvider().getPrice(operation.getDate())).append("PLN | ")
                .append(register.getUnits()).append("u ")
                .append(register.getValue(operation.getDate()));
        return sb.toString();
    }

    private String logRegister(Register register)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(register.getFund().getName()).append(" ")
                .append(register.getPriceProvider().getPrice(new LocalDate())).append("PLN | ")
                .append(register.getUnits()).append("u ")
                .append(register.getValue(new LocalDate()));

        return sb.toString();
    }
}
