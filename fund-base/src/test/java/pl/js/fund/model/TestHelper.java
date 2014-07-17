package pl.js.fund.model;

import java.math.BigDecimal;

import org.joda.time.LocalDate;

import pl.js.fund.enums.FundName;
import pl.js.fund.operation.Buy;
import pl.js.fund.operation.Operation;
import pl.js.fund.operation.Sell;
import pl.js.fund.utils.DateUtils;

public class TestHelper
{
    public static Operation        BUY_UI_ANE_1            = new Buy();
    public static Operation        SELL_UI_ANE_3           = new Sell();
    public static BigDecimal       SELL_UI_ANE_3_TAX_BASE  = new BigDecimal(50).setScale(2);
    public static BigDecimal       SELL_UI_ANE_3_TAX_VALUE = new BigDecimal(9.5).setScale(2);

    public static Operation        BUY_UI_P_1              = new Buy();
    public static Operation        SELL_UI_P_3             = new Sell();

    public static final LocalDate  DAY_1                   = DateUtils.parseFromString("01-01-2014", Operation.DATE_FORMAT);
    public static final LocalDate  DAY_2                   = DateUtils.parseFromString("01-02-2014", Operation.DATE_FORMAT);
    public static final LocalDate  DAY_3                   = DateUtils.parseFromString("01-03-2014", Operation.DATE_FORMAT);
    public static final LocalDate  DAY_4                   = DateUtils.parseFromString("01-03-2014", Operation.DATE_FORMAT);
    public static final LocalDate  DAY_5                   = DateUtils.parseFromString("01-03-2014", Operation.DATE_FORMAT);

    public static final BigDecimal VALUE_50                = new BigDecimal(50.0).setScale(2);
    public static final BigDecimal VALUE_100               = new BigDecimal(100.0).setScale(2);
    public static final BigDecimal VALUE_150               = new BigDecimal(150.0).setScale(2);
    public static final BigDecimal VALUE_200               = new BigDecimal(200.0).setScale(2);

    public static final BigDecimal PRICE_UI_ANE_1          = new BigDecimal(100.0).setScale(2);
    public static final BigDecimal PRICE_UI_ANE_2          = new BigDecimal(150.0).setScale(2);
    public static final BigDecimal PRICE_UI_ANE_3          = new BigDecimal(200.0).setScale(2);
    public static final BigDecimal PRICE_UI_ANE_4          = new BigDecimal(170.0).setScale(2);
    public static final BigDecimal PRICE_UI_ANE_5          = new BigDecimal(200.0).setScale(2);

    public static final BigDecimal PRICE_UI_P_1            = new BigDecimal(200.0).setScale(2);
    public static final BigDecimal PRICE_UI_P_2            = new BigDecimal(250.0).setScale(2);
    public static final BigDecimal PRICE_UI_P_3            = new BigDecimal(300.0).setScale(2);

    static
    {
        BUY_UI_ANE_1.setDate(DAY_1);
        BUY_UI_ANE_1.setFundName(FundName.UI_ANE);
        BUY_UI_ANE_1.setValue(VALUE_200);

        BUY_UI_P_1.setDate(DAY_1);
        BUY_UI_P_1.setFundName(FundName.UI_P);
        BUY_UI_P_1.setValue(VALUE_200);

        SELL_UI_ANE_3.setDate(DAY_3);
        SELL_UI_ANE_3.setFundName(FundName.UI_ANE);
        SELL_UI_ANE_3.setValue(VALUE_100);

        SELL_UI_P_3.setDate(DAY_3);
        SELL_UI_P_3.setFundName(FundName.UI_P);
        SELL_UI_P_3.setValue(VALUE_150);

    }

}
