package pl.js.fund.decorator;


public class TextOperationDecorator // extends WalletDecorator
{
    /*
     * public TextOperationDecorator(IWallet wallet)
     * {
     * super(wallet);
     * }
     * @Override
     * public void performOperation(Operation operation)
     * {
     * if (wallet.getRegisters().get(operation.getFundName()) != null)
     * {
     * wallet.performOperation(operation);
     * System.out.println(logOperation(operation) + " " + logRegister(operation));
     * }
     * }
     * @Override
     * public void performOperations()
     * {
     * for (Operation operation : getOperations())
     * {
     * performOperation(operation);
     * }
     * }
     * private String logOperation(Operation operation)
     * {
     * StringBuilder sb = new StringBuilder();
     * sb.append(DateUtils.formatAsString(operation.getDate(), Operation.DATE_FORMAT)).append(" ")
     * .append(operation.getFundName())
     * .append(" | Oper: ")
     * .append(operation.getUnits()).append("u ")
     * .append(operation.getValue()).append("PLN");
     * return sb.toString();
     * }
     * private String logRegister(Operation operation)
     * {
     * Register register = getRegisters().get(operation.getFundName());
     * StringBuilder sb = new StringBuilder();
     * sb.append("| Reg: ")
     * .append(register.getUnits()).append("u ")
     * .append(register.getValue(operation.getDate()))
     * .append("PLN")
     * .append(" | Price: ")
     * .append(register.getPriceProvider().getPriceAtLastBusinessDay(operation.getDate()))
     * .append("PLN");
     * return sb.toString();
     * }
     */
}
