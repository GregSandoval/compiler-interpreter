package compiler.interpreter;

import compiler.lexer.token.Token;
import compiler.parser.UserException;

public class UnknownDataTypeException extends UserException {
  public UnknownDataTypeException(Token token) {
    super("Unknown data type: " + token.getValue(), token);
  }
}
