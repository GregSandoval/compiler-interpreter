package compiler.interpreter;

import compiler.lexer.token.OperatorToken;
import compiler.lexer.token.TypeToken;
import compiler.parser.UserException;

public class OperatorUsageUndefined extends UserException {
  public OperatorUsageUndefined(OperatorToken operator, TypeToken left, TypeToken right) {
    super("Operator " + operator.getValue() + " isn't defined between " + left.getValue() + " and " + right.getValue(), left);
  }
}
