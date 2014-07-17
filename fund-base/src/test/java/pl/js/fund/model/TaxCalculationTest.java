package pl.js.fund.model;

import org.fest.assertions.Assertions;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import pl.js.fund.enums.FundName;
import pl.js.fund.operation.Sell;
import pl.js.fund.price.FundPriceProvider;

@RunWith(MockitoJUnitRunner.class)
public class TaxCalculationTest
{
    private Wallet            wallet;
    @Mock
    private FundPriceProvider priceProviderUIANE;

    @Mock
    private FundPriceProvider priceProviderUIP;

    @Before
    public void setUp()
    {
        wallet = new Wallet();
        wallet.addRegister(FundName.UI_ANE);
        wallet.getRegister(FundName.UI_ANE).setPriceProvider(this.priceProviderUIANE);

        wallet.addRegister(FundName.UI_P);
        wallet.getRegister(FundName.UI_P).setPriceProvider(this.priceProviderUIP);

    }

    @Test
    public void addBuyOperations()
    {
        Mockito.doReturn(TestHelper.PRICE_UI_ANE_1).when(priceProviderUIANE).getPriceAtLastBusinessDay(TestHelper.DAY_1);

        Mockito.doReturn(TestHelper.PRICE_UI_P_1).when(priceProviderUIP).getPriceAtLastBusinessDay(TestHelper.DAY_1);

        wallet.addOperation(TestHelper.BUY_UI_ANE_1);
        wallet.addOperation(TestHelper.BUY_UI_P_1);

        wallet.performOperations();

        Mockito.verify(priceProviderUIANE, Mockito.times(1)).getPriceAtLastBusinessDay(Mockito.any(LocalDate.class));
        Mockito.verify(priceProviderUIP, Mockito.times(1)).getPriceAtLastBusinessDay(Mockito.any(LocalDate.class));

        Assertions.assertThat(wallet.getRegister(FundName.UI_ANE).getOperations().size()).isEqualTo(1);
        Assertions.assertThat(wallet.getRegister(FundName.UI_P).getOperations().size()).isEqualTo(1);
    }

    @Test
    public void addSellOperations()
    {
        Mockito.doReturn(TestHelper.PRICE_UI_ANE_1).when(priceProviderUIANE).getPriceAtLastBusinessDay(TestHelper.DAY_1);
        Mockito.doReturn(TestHelper.PRICE_UI_ANE_2).when(priceProviderUIANE).getPriceAtLastBusinessDay(TestHelper.DAY_2);
        Mockito.doReturn(TestHelper.PRICE_UI_ANE_3).when(priceProviderUIANE).getPriceAtLastBusinessDay(TestHelper.DAY_3);

        Mockito.doReturn(TestHelper.PRICE_UI_P_1).when(priceProviderUIP).getPriceAtLastBusinessDay(TestHelper.DAY_1);

        wallet.addOperation(TestHelper.BUY_UI_ANE_1);
        wallet.addOperation(TestHelper.BUY_UI_P_1);
        wallet.addOperation(TestHelper.SELL_UI_ANE_3);
        wallet.addOperation(TestHelper.SELL_UI_P_1);

        wallet.performOperations();

        Mockito.verify(priceProviderUIANE, Mockito.times(2)).getPriceAtLastBusinessDay(Mockito.any(LocalDate.class));
        Mockito.verify(priceProviderUIP, Mockito.times(1)).getPriceAtLastBusinessDay(Mockito.any(LocalDate.class));

        Assertions.assertThat(wallet.getRegister(FundName.UI_ANE).getOperations().size()).isEqualTo(2);
        Assertions.assertThat(wallet.getRegister(FundName.UI_P).getOperations().size()).isEqualTo(1);
        Assertions.assertThat(wallet.getRegister(FundName.UI_ANE).getOperations().get(1).getTaxBase()).isEqualTo(TestHelper.SELL_UI_ANE_3_TAX_BASE);
        Assertions.assertThat(((Sell) wallet.getRegister(FundName.UI_ANE).getOperations().get(1)).getTaxValue()).isEqualTo(TestHelper.SELL_UI_ANE_3_TAX_VALUE);

    }

}
