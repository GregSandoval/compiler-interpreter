package compiler.parser;

import compiler.a5.grammar.GrammarNodeElement;
import compiler.lexer.token.Token;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public abstract class GrammarNode extends AbstractGrammarNode implements GrammarNodeElement {
  private static final Map<Class<? extends GrammarNode>, Map<Class<? extends Token>, List<Supplier<AbstractGrammarNode>>>> LLTable = new HashMap<>();

  public GrammarNode() {
    if (!LLTable.containsKey(this.getClass())) {
      LLTable.put(this.getClass(), new HashMap<>());
    }
  }

  public List<AbstractGrammarNode> getRHS(Class<? extends Token> token) {
    final var table = LLTable.get(this.getClass());

    if (!table.containsKey(token)) {
      return null;
    }

    return table
      .get(token)
      .stream()
      .map(Supplier::get)
      .collect(Collectors.toUnmodifiableList());
  }

  public Set<Class<? extends Token>> getRHS() {
    return LLTable.get(this.getClass()).keySet();
  }

  @Override
  public String toString() {
    return this.getClass().getSimpleName();
  }

  @SafeVarargs
  public final RuleBuilderSecondStep on(Class<? extends Token>... first) {
    return new RuleBuilderSecondStep(first);
  }

  public class RuleBuilderFirstStep {
    @SafeVarargs
    public final RuleBuilderSecondStep on(Class<? extends Token>... first) {
      return new RuleBuilderSecondStep(first);
    }
  }

  public class RuleBuilderSecondStep {
    private Class<? extends Token>[] firstItems;

    @SafeVarargs
    private RuleBuilderSecondStep(Class<? extends Token>... first) {
      this.firstItems = first;
    }

    @SafeVarargs
    public final RuleBuilderFirstStep useRHS(Supplier<AbstractGrammarNode>... rest) {
      final var rhs = new ArrayList<>(Arrays.asList(rest));

      for (var token : firstItems) {
        if (LLTable.get(GrammarNode.this.getClass()).containsKey(token)) {
          throw new RuntimeException("Double stuffed LL Table: Rule: " + GrammarNode.this.getClass().getSimpleName() + " Token: " + token.getSimpleName());
        }
        LLTable.get(GrammarNode.this.getClass()).put(token, rhs);
      }

      return new RuleBuilderFirstStep();
    }
  }

}
