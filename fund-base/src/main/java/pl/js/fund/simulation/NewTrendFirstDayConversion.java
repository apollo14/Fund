package pl.js.fund.simulation;

import java.util.List;

import org.joda.time.LocalDate;

import pl.js.fund.operation.Operation;

public class NewTrendFirstDayConversion implements ISimulation
{

    private LocalDate start;

    public LocalDate getStart()
    {
        return start;
    }

    public void setStart(LocalDate start)
    {
        this.start = start;
    }

    public List<Operation> getOperations()
    {
        return null;
    }

}
