package pl.js.fund.model;

import org.junit.Before;
import org.junit.Test;

import pl.js.fund.decorator.TextOperationDecorator;
import pl.js.fund.decorator.TextRegisterDecorator;

public class WalletDecoratorTest
{

    private IWallet wallet;

    @Before
    public void setUp()
    {
        wallet = new TextRegisterDecorator(new TextOperationDecorator(new Wallet()));
    }

    @Test
    public void defaultWalletDecoratorTest()
    {
        wallet.loadOperations(this.getClass().getResource("/operacje2.txt").toString());
        wallet.performOperations();
    }
}
