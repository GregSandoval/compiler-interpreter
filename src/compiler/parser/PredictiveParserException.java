package compiler.parser;

import compiler.lexer.token.Token;
import compiler.utils.LineErrorFormatter;

import java.util.stream.Collectors;

public class PredictiveParserException extends Exception {

  public PredictiveParserException(GrammarNode grammarNode, Token token, String inputName) {
    super(formatMessage(grammarNode, token, inputName));
  }

  private static String formatMessage(GrammarNode grammarRule, Token token, String inputName) {
    String tokenClassName = token.getClass().getSimpleName();
    String grammarClassName = grammarRule.getClass().getSimpleName();
    String expectedTokens = grammarRule.getRHS().stream().map(Class::getSimpleName).collect(Collectors.joining(" or "));
    return "\n" +
      "LL Table missing entry exception; " + grammarRule + "(" + tokenClassName + ") = undefined" +
      "\n" +
      grammarClassName + " expected " + expectedTokens + " but found " + tokenClassName +
      "\n" +
      LineErrorFormatter.renderLineNumber(token, inputName);
  }
}
