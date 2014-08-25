package pl.js.fund.simulation;

import java.util.List;
import java.util.Map;

import pl.js.fund.enums.FundName;
import pl.js.fund.operation.Operation;

public interface ISimulation
{
    public Map<FundName, List<Operation>> getOperations();

}
