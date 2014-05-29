package pl.js.fund.decorator;

import org.joda.time.LocalDate;

import pl.js.fund.model.IWallet;
import pl.js.fund.model.Register;

public class TextRegisterDecorator extends WalletDecorator
{

    public TextRegisterDecorator(IWallet wallet)
    {
        super(wallet);
    }

    @Override
    public void performOperations()
    {
        wallet.performOperations();
        System.out.println("\nCurrent registers values:\n");
        for (String fundName : getRegisters().keySet())
        {
            Register register = getRegisters().get(fundName);
            System.out.println(logRegister(register));
        }
    }

    private String logRegister(Register register)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(register.getFund().getName()).append(" ")
                .append(" | Price: ")
                .append(register.getPriceProvider().getPriceAtLastBusinessDay(new LocalDate()))
                .append("PLN ")
                .append(" | Reg: ")
                .append(register.getUnits()).append("u ")
                .append(register.getValue(new LocalDate()));

        return sb.toString();
    }
}
