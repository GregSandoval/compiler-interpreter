package compiler.parser;

import compiler.lexer.token.Token;

public class UnexpectedToken extends Exception {
  public UnexpectedToken(AbstractGrammarNode top, Token token, String inputName) {
    super(formatError(top, token, inputName));
  }

  private static String formatError(AbstractGrammarNode top, Token token, String inputName) {
    final var grammarClassName = top.getClass().getSimpleName();
    final var tokenClassName = token.getClass().getSimpleName();
    final int tokenLineNumber = token.getLineNumber();
    return "\n" +
      "Unexpected token; Expected a " + grammarClassName + " but found a " + tokenClassName +
      "\n" +
      "\tat " + inputName + "(" + inputName + ":" + tokenLineNumber + ")";
  }
}
