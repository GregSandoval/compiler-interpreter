package compiler.a5.grammar;

public interface GrammarNodeElement {
  void accept(GrammarNodeVisitor visitor);
}
