package visualization;

import compiler.lexer.token.Token;
import compiler.parser.AbstractGrammarNode;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class StateTransitionLogger {
  public static void logBeforeState(LinkedList<AbstractGrammarNode> stack, Token token) {
    System.out.println("Stack: " + format(stack));
  }

  public static void logGrammarRuleApplication(AbstractGrammarNode top, Token token, List<AbstractGrammarNode> rhs) {
    System.out.println("Move : " + format(top) + "(" + format(token) + ") => " + format(rhs) + "\n");
  }


  public static void logUnexpectedToken(AbstractGrammarNode top, Token token) {
    System.out.println("Expected: " + format(top) + " but found: " + format(token));
  }

  public static void logUnknownGrammarRule(AbstractGrammarNode top, Token token) {
    System.out.println("\n");
    System.out.println("Grammar contains rule not in Grammar hierarchy: " + format(top));
  }

  public static void logPredictionNotFound(AbstractGrammarNode top, Token token) {
    System.out.println("\n");
    System.out.println("User error; Rule: " + format(top) + " has no entry for " + format(token));
  }

  public static String format(List<AbstractGrammarNode> rules) {
    var strings = rules.stream()
      .map(StateTransitionLogger::format)
      .collect(Collectors.toList());
    return "[" + String.join(", ", strings) + "]";
  }

  public static String format(AbstractGrammarNode rule) {
    return rule instanceof Token ? rule.getClass().getSimpleName() : rule.toString();
  }
}
