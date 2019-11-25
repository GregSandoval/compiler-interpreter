package compiler.interpreter;

import compiler.lexer.token.Token;
import compiler.parser.UserException;

public class UndeclaredIdentifierException extends UserException {
  public UndeclaredIdentifierException(Token token) {
    super("Identifier '" + token.getValue() + "' is undeclared. Please declare identifier in variable block", token);
  }
}
