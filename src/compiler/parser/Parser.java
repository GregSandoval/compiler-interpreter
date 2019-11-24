package compiler.parser;

import compiler.lexer.token.Token;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static compiler.parser.ParserListeners.*;

public class Parser {
  private final GrammarNode startSymbol;
  private final AbstractGrammarNode eof;
  private final BeforeRuleApplicationListener beforeRuleApplication;
  private final GeneralListener onUnexpectedToken;
  private final GeneralListener onUnknownGrammarRule;
  private final GeneralListener onPredictionNotFoundError;
  private final GrammarRuleApplicationListener onGrammarRuleApplication;

  public Parser(
    Token eof,
    GrammarNode startSymbol,
    BeforeRuleApplicationListener beforeRuleApplication,
    GeneralListener onUnexpectedToken,
    GeneralListener onUnknownGrammarRule,
    GeneralListener onPredictionNotFoundError,
    GrammarRuleApplicationListener onGrammarRuleApplication
  ) {
    this.eof = eof;
    this.startSymbol = startSymbol;
    this.beforeRuleApplication = beforeRuleApplication;
    this.onUnexpectedToken = onUnexpectedToken;
    this.onUnknownGrammarRule = onUnknownGrammarRule;
    this.onPredictionNotFoundError = onPredictionNotFoundError;
    this.onGrammarRuleApplication = onGrammarRuleApplication;
  }

  public void parse(String inputName, List<Token> tokensIn) throws Exception {
    final var stack = new LinkedList<AbstractGrammarNode>();
    final var tokens = new LinkedList<>(tokensIn);
    stack.push(this.eof);
    stack.push(this.startSymbol);

    while (!tokens.isEmpty() && !stack.isEmpty()) {
      beforeRuleApplication.accept(stack, tokens.peek());
      final Token token = tokens.peek();
      final AbstractGrammarNode top = stack.pop();

      if (top instanceof Token && isEqual(token, top)) {
        tokens.pop();
        onGrammarRuleApplication.accept(top, token, Collections.emptyList());
        continue;
      }

      if (top instanceof Token) {
        onUnexpectedToken.accept(top, token);
        throw new Exception("\nUnexpected token; Expected a " + top.getClass().getSimpleName() + " but found a " + token.getClass().getSimpleName() +
          "\n\tat " + inputName + "(" + inputName + ":" + token.getLineNumber() + ")");
      }

      if (!(top instanceof GrammarNode)) {
        onUnknownGrammarRule.accept(top, token);
        throw new Exception("Node is neither Token nor Grammar rule: " + top);
      }

      final var rhs = ((GrammarNode) top).getRHS(token.getClass());

      if (rhs == null) {
        onPredictionNotFoundError.accept(top, token);
        throw new Exception(
          "\nLL Table missing entry exception; " + top + "(" + token.getClass().getSimpleName() + ") = undefined\n" +
            top.getClass().getSimpleName() + " expected " + ((GrammarNode) top).getRHS().stream().map(Class::getSimpleName).collect(Collectors.joining(" or ")) +
            " but found " + token.getClass().getSimpleName() +
            "\n\tat " + inputName + "(" + inputName + ":" + token.getLineNumber() + ")"
        );
      }

      onGrammarRuleApplication.accept(top, token, rhs);
      for (int i = rhs.size() - 1; i >= 0; i--) {
        stack.push(rhs.get(i));
      }
    }

    if (tokens.isEmpty() && stack.isEmpty()) {
      System.out.println("Parsing successful");
    }

    if (!stack.isEmpty()) {
      throw new Exception("Failed to parse input; Expected tokens but received EOF;");
    }

    if (!tokens.isEmpty()) {
      throw new Exception(
        "Failed to parse input; Expected grammar rule but found none.\n" +
          "Remaining tokens: " + tokens.stream()
          .map(token -> token.getClass().getSimpleName())
          .collect(Collectors.toList()));
    }
  }

  public boolean isEqual(Token token, AbstractGrammarNode top) {
    return token.getClass() == top.getClass();
  }

}
