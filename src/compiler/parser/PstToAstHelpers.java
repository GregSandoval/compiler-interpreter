package compiler.parser;

import compiler.lexer.token.Token;

import java.util.LinkedList;

public class PstToAstHelpers {

  public static void hoist(AbstractGrammarNode tree) {
    for (var token : tree.children) {
      if (!(token instanceof Token)) {
        continue;
      }

      final var parent = tree.parent;
      var tokenIndex = parent.children.indexOf(tree);

      // Replace token's parent
      token.parent = parent;

      // Replace rule with token
      parent.children.set(tokenIndex, token);

      // Remove token from rule's children
      tree.children.remove(token);

      // Set tree's children to point to token as new parent
      tree.children.forEach(child -> child.parent = token);

      // Add rule's children to the token;
      while (--tokenIndex != 0 && !tree.children.isEmpty())
        token.children.addLast(tree.children.pop());

      while (!tree.children.isEmpty())
        token.children.addLast(tree.children.pop());

      return;
    }
  }

  public static void reverseHoist(AbstractGrammarNode tree) {
    LinkedList<AbstractGrammarNode> children = tree.children;
    for (int i = children.size() - 1; i >= 0; i--) {
      AbstractGrammarNode token = children.get(i);
      if (!(token instanceof Token)) {
        continue;
      }

      final var parent = tree.parent;
      var tokenIndex = parent.children.indexOf(tree);

      // Replace token's parent
      token.parent = parent;

      // Replace rule with token
      parent.children.set(tokenIndex, token);

      // Remove token from rule's children
      tree.children.remove(token);

      // Set tree's children to point to token as new parent
      tree.children.forEach(child -> child.parent = token);

      // Add rule's children to the token;
      while (!tree.children.isEmpty())
        token.children.addFirst(tree.children.pop());

      return;
    }
  }

  public static void rightContraction(AbstractGrammarNode tree) {
    final var rightMostNode = tree.children.getLast();
    if (rightMostNode instanceof GrammarNode) {
      tree.children.remove(rightMostNode);
      rightMostNode.parent = null;

      rightMostNode.children.forEach(tree.children::addLast);
      rightMostNode.children.forEach(child -> child.parent = tree);
    }
  }
}
