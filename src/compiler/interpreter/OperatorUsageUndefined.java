package compiler.interpreter;

import compiler.lexer.token.OperatorToken;
import compiler.parser.UserException;

public class OperatorUsageUndefined extends UserException {
  public OperatorUsageUndefined(OperatorToken operator, Object lvalue, Object rvalue) {
    super("Operator " + operator.getValue() + " isn't defined between " + lvalue.getClass().getSimpleName() + " and " + rvalue.getClass().getSimpleName(), operator);
  }
}
