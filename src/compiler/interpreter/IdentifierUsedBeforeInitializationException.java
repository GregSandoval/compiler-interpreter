package compiler.interpreter;

import compiler.lexer.token.IdentifierToken;
import compiler.parser.UserException;

public class IdentifierUsedBeforeInitializationException extends UserException {

  public IdentifierUsedBeforeInitializationException(IdentifierToken token) {
    super("Identifier '" + token.getValue() + "' used before it's been initialized", token);
  }

}
