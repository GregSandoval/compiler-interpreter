package compiler.parser;

import compiler.a5.grammar.GrammarNodeVisitor;
import compiler.lexer.token.EOFToken;
import compiler.lexer.token.Token;

import java.util.List;

public class ParseTreeBuilder {
  private AbstractGrammarNode root;

  public ParseTreeBuilderFirstStep setStartSymbol(GrammarNode startSymbol) {
    return (inputName) -> (tokens) -> {
      final var EOF = new EOFToken();
      ParseTreeBuilder.this.root = new ParseTreeSentinel();
      ParseTreeBuilder.this.root.children.push(EOF);
      ParseTreeBuilder.this.root.children.push(startSymbol);
      ParseTreeBuilder.this.root.children.forEach(node -> node.parent = ParseTreeBuilder.this.root);

      new ParserBuilder()
        .setStartSymbol(startSymbol)
        .setEOF(EOF)
        .onGrammarRuleApplication(ParseTreeBuilder.this::AttachToTree)
        .createParser()
        .parse(inputName, tokens);

      return root;
    };
  }

  private void AttachToTree(AbstractGrammarNode top, Token token, List<AbstractGrammarNode> rhs) {
    if (top instanceof Token && !(top instanceof EOFToken)) {
      token.parent = top.parent;
      top.parent.children.set(top.parent.children.indexOf(top), token);
    }

    if (!(top instanceof Token)) {
      top.children.addAll(rhs);
      rhs.forEach(node -> node.parent = top);
    }
  }

  public interface ParseTreeBuilderFirstStep {
    ParseTreeBuilderLastStep setInputSourceName(String inputName);
  }

  public interface ParseTreeBuilderLastStep {
    AbstractGrammarNode build(List<Token> tokens) throws Exception;
  }

  public static class ParseTreeSentinel extends GrammarNode {
    @Override
    public void accept(GrammarNodeVisitor visitor) {
    }
  }
}
