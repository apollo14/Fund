package pl.js.fund.operation;

import java.util.Comparator;

public class OperationProcessingComparator implements Comparator<Operation>
{

    public int compare(Operation o1, Operation o2)
    {
        int result = 0;
        if (o1 instanceof Convert && o2 instanceof Convert)
        {
            result = o1.getDate().compareTo(o2.getDate());
            if (result != 0)
            {
                return result;
            }
            result = o1.getValue().compareTo(o2.getValue());
            if (result != 0)
            {
                return result;
            }
        }

        return o1.compareTo(o2);
    }

}
