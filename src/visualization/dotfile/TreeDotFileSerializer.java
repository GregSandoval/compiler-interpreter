package visualization.dotfile;

import compiler.a5.grammar.GrammarNodeVisitor;
import compiler.lexer.token.EOFToken;
import compiler.lexer.token.StringToken;
import compiler.lexer.token.Token;
import compiler.parser.AbstractGrammarNode;
import compiler.parser.GrammarNode;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;


public class TreeDotFileSerializer {
  private static class NULL_NODE extends GrammarNode {

    @Override
    public void accept(GrammarNodeVisitor visitor) {

    }
  }

  public static void serialize(AbstractGrammarNode tree, String filename) throws IOException {
    final var NullSentienal = new NULL_NODE();
    NullSentienal.children.add(tree);
    tree.parent = NullSentienal;

    final var builder = new StringBuilder();
    final var visitedNodes = new HashSet<String>();

    builder.append("digraph ")
      .append(filename.split("\\.")[0])
      .append(" {\n");

    addNodes(builder, visitedNodes, NullSentienal);
    visitedNodes.clear();
    addEdges(builder, visitedNodes, NullSentienal, NullSentienal);

    builder.append("}\n");

    try (final var writer = new PrintWriter(filename)) {
      writer.write(builder.toString());
    }
    tree.parent = null;
  }

  private static void addEdges(StringBuilder builder, Set<String> visitedNodes, NULL_NODE nullNode, AbstractGrammarNode tree) {
    if (tree == null || visitedNodes.contains(tree.UUID)) {
      return;
    }

    if (tree.parent == null) {
      builder.append("ID")
        .append(tree.UUID)
        .append(" -> ID")
        .append(nullNode.UUID)
        .append(";\n");
    } else {
      builder.append("ID")
        .append(tree.UUID)
        .append(" -> ID")
        .append(tree.parent.UUID)
        .append(";\n");
    }

    visitedNodes.add(tree.UUID);

    for (final var child : tree.children) {
      builder.append("ID")
        .append(tree.UUID)
        .append(" -> ID")
        .append(child.UUID)
        .append(";\n");
    }

    for (final var child : tree.children) {
      addEdges(builder, visitedNodes, nullNode, child);
    }
  }

  private static void addNodes(StringBuilder builder, Set<String> visitedNodes, AbstractGrammarNode tree) {
    if (tree == null) {
      return;
    }

    if (visitedNodes.contains(tree.UUID)) {
      System.err.println("Cycle in tree; Duplicated Node: " + formatWithValue(tree));
      return;
    }

    if (tree.parent != null && !visitedNodes.contains(tree.parent.UUID)) {
      builder.append("ID")
        .append(tree.parent.UUID)
        .append(" [label=\"")
        .append(formatWithValue(tree.parent))
        .append("[Parent] (Error: Missing)")
        .append("\"];\n");
      System.err.println(formatWithValue(tree) + " of type " + tree.getClass().getSimpleName() + " with parent " + formatWithValue(tree.parent) + " is not in graph");
      visitedNodes.add(tree.parent.UUID);
    }

    builder.append("ID")
      .append(tree.UUID)
      .append(" [label=\"")
      .append(formatWithValue(tree))
      .append("\"];\n");

    visitedNodes.add(tree.UUID);

    for (final var child : tree.children) {
      addNodes(builder, visitedNodes, child);
    }
  }

  public static String formatWithValue(AbstractGrammarNode rule) {
    if (rule instanceof EOFToken)
      return "EOF";

    if (rule instanceof StringToken)
      return "\'\'" + ((StringToken) rule).getValue() + "\'\'";

    if (rule instanceof Token)
      return ((Token) rule).getValue();

    return rule.toString();
  }
}
