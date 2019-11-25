package compiler.interpreter;

import compiler.lexer.token.OperatorToken;
import compiler.lexer.token.Token;
import compiler.parser.UserException;

public class OperatorTypeException extends UserException {

  public OperatorTypeException(OperatorToken operator, Token left, Token right) {
    super(formatMessage(operator, left, right), right);
  }

  private static String formatMessage(OperatorToken operator, Token left, Token right) {
    return "Operator '" + operator.getValue() + "' is not defined between types " + left.getValue() + " and " + right.getValue();
  }

}
