package pl.js.fund.enums;

import java.lang.reflect.Constructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.js.fund.model.Fund;
import pl.js.fund.model.UnionInvestmentFund;
import pl.js.fund.model.Wallet;

public enum FundName
{

    UI_ANE("36", "UniAkcje: Nowa Europa", UnionInvestmentFund.class),
    UI_P("24", "UniKorona Pieniężny", UnionInvestmentFund.class),
    UI_A("34", "UniKorona Akcje", UnionInvestmentFund.class),
    UI_ONE("26", "UniObligacje: Nowa Europa", UnionInvestmentFund.class);

    private final static Logger log = LoggerFactory.getLogger(Wallet.class);
    private String              id;
    private String              name;
    private Class<? extends Fund>               classValue;

    FundName(String id, String name, Class<? extends Fund> classValue)
    {
        this.id = id;
        this.name = name;
        this.classValue = classValue;
    }

    public String getId()
    {
        return this.id;
    }

    public String getName()
    {
        return this.name;
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
            Constructor<? extends Fund> constructor = this.classValue.getDeclaredConstructor(this.getClass());
            result = (Fund) constructor.newInstance(this);
        }
        catch (Exception e)
        {
            log.error("", e);
        }
        return result;

    }
}
