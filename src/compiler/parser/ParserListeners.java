package compiler.parser;

import compiler.lexer.token.Token;
import compiler.utils.TriConsumer;

import java.util.LinkedList;
import java.util.List;
import java.util.function.BiConsumer;

public class ParserListeners {
  public interface BeforeRuleApplicationListener extends BiConsumer<LinkedList<AbstractGrammarNode>, Token> {
    default BeforeRuleApplicationListener andThen(BeforeRuleApplicationListener after) {
      return (l, r) -> {
        accept(l, r);
        after.accept(l, r);
      };
    }
  }

  public interface GeneralListener extends BiConsumer<AbstractGrammarNode, Token> {
    default GeneralListener andThen(GeneralListener after) {
      return (l, r) -> {
        accept(l, r);
        after.accept(l, r);
      };
    }
  }

  public interface GrammarRuleApplicationListener extends TriConsumer<AbstractGrammarNode, Token, List<AbstractGrammarNode>> {
    default GrammarRuleApplicationListener andThen(GrammarRuleApplicationListener after) {
      return (l, m, r) -> {
        accept(l, m, r);
        after.accept(l, m, r);
      };
    }

  }

  public static BeforeRuleApplicationListener BeforeRuleApplicationListenerIdentity() {
    return (l, r) -> {
    };
  }

  public static GeneralListener GeneralListenerIdentity() {
    return (l, r) -> {
    };
  }

  public static GrammarRuleApplicationListener GrammarRuleApplicationIdentity() {
    return (l, m, r) -> {
    };
  }

}
