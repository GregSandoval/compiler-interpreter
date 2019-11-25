package compiler.parser;

import compiler.lexer.token.Token;

import java.util.stream.Collectors;

public class PredictiveParserException extends UserException {

  public PredictiveParserException(GrammarNode grammarNode, Token token) {
    super(formatMessage(grammarNode, token), token);
  }

  private static String formatMessage(GrammarNode grammarRule, Token token) {
    String tokenClassName = token.getClass().getSimpleName();
    String grammarClassName = grammarRule.getClass().getSimpleName();
    String expectedTokens = grammarRule.getRHS().stream().map(Class::getSimpleName).collect(Collectors.joining(" or "));
    return "\n" +
      "LL Table missing entry exception; " + grammarRule + "(" + tokenClassName + ") = undefined" +
      "\n" +
      grammarClassName + " expected " + expectedTokens + " but found " + tokenClassName;
  }
}
