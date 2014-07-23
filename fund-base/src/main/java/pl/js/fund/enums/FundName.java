package pl.js.fund.enums;

import java.lang.reflect.Constructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.js.fund.model.Fund;
import pl.js.fund.model.InvestorFund;
import pl.js.fund.model.UnionInvestmentFund;
import pl.js.fund.model.Wallet;

public enum FundName
{

    UI_ANE("36", "UniAkcje: Nowa Europa", UnionInvestmentFund.class, UmbrellaId.UI, 6),
    UI_P("24", "UniKorona Pieniê¿ny", UnionInvestmentFund.class, UmbrellaId.UI, 6),
    UI_A("34", "UniKorona Akcje", UnionInvestmentFund.class, UmbrellaId.UI, 6),
    UI_ONE("26", "UniObligacje: Nowa Europa", UnionInvestmentFund.class, UmbrellaId.UI, 6),
    UI_O("25", "UniKorona Obligacje", UnionInvestmentFund.class, UmbrellaId.UI, 6),

    INV_GOTOWKOWY("S7smsq", "Investor Gotówkowy", InvestorFund.class, UmbrellaId.INV, 16),
    INV_TURCJA("qnXKPC", "Investor Turcja", InvestorFund.class, UmbrellaId.INV, 16),
    INV_ROSJA("fIeFUp", "Investor Rosja", InvestorFund.class, UmbrellaId.INV, 16);

    private final static Logger   log                    = LoggerFactory.getLogger(Wallet.class);
    private String                id;
    private String                name;
    private UmbrellaId            umbrellaId;
    private int                   unitsCalculationScale;
    private int                   unitsPresentationScale = 6;
    private Class<? extends Fund> classValue;

    FundName(String id, String name, Class<? extends Fund> classValue, UmbrellaId umbrellaId, int scale)
    {
        this.id = id;
        this.name = name;
        this.classValue = classValue;
        this.umbrellaId = umbrellaId;
        this.unitsCalculationScale = scale;
    }

    public String getId()
    {
        return this.id;
    }

    public String getName()
    {
        return this.name;
    }

    public UmbrellaId getUmbrellaId()
    {
        return umbrellaId;
    }

    public int getUnitsCalculationScale()
    {
        return unitsCalculationScale;
    }

    public int getUnitsPresentationScale()
    {
        return unitsPresentationScale;
    }

    public static FundName parse(String fundName)
    {
        if (fundName != null && !"".equals(fundName))
        {
            for (FundName fn : FundName.values())
            {
                if (fundName.equalsIgnoreCase(fn.name))
                {
                    return fn;
                }
            }
        }
        return null;
    }

    public Fund instantiate()
    {

        Fund result = null;
        try
        {
            // result = (Fund) this.classValue.newInstance();
            Constructor<? extends Fund> constructor = this.classValue
                    .getDeclaredConstructor(this.getClass());
            result = (Fund) constructor.newInstance(this);
        }
        catch (Exception e)
        {
            log.error("", e);
        }
        return result;

    }

    public String toString()
    {
        return this.getName();
    }
}
