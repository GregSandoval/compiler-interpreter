package compiler.parser;

import compiler.a5.grammar.GrammarNodeVisitor;
import compiler.a5.grammar.PstToAstGrammarVisitor;
import compiler.a5.grammar.PstToAstTokenVisitor;
import compiler.lexer.token.Token;


public class AbstractSyntaxTreeBuilder {

  public static void fromParseTree(AbstractGrammarNode tree) {
    final var tokenVisitor = new PstToAstTokenVisitor();
    final var grammarVisitor = new PstToAstGrammarVisitor();
    postordervisit(tree, tokenVisitor, grammarVisitor);
  }

  public static void postordervisit(AbstractGrammarNode tree, TokenVisitor tokenVisitor, GrammarNodeVisitor grammarVisitor) {
    if (tree == null) {
      return;
    }

    for (var child : tree.children) {
      postordervisit(child, tokenVisitor, grammarVisitor);
    }

    trim(tree);
    contract(tree);
    if (tree instanceof GrammarNode) {
      ((GrammarNode) tree).accept(grammarVisitor);
    }

    if (tree instanceof Token) {
      ((Token) tree).accept(tokenVisitor);
    }
  }

  private static void trim(AbstractGrammarNode tree) {
    tree
      .children
      .removeIf(child -> child instanceof GrammarNode && child.children.isEmpty());
  }

  private static void contract(AbstractGrammarNode tree) {
    if (tree instanceof GrammarNode && tree.children.size() == 1) {
      final var child = tree.children.get(0);
      child.parent = tree.parent;
      tree.parent.children.set(tree.parent.children.indexOf(tree), child);
      // IMPORTANT DO NOT REMOVE
      tree.children.clear();
    }
  }

}
