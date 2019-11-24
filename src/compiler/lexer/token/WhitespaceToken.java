package compiler.lexer.token;

import compiler.parser.TokenEvaluator;
import compiler.parser.TokenVisitor;

public final class WhitespaceToken extends Token {
  public WhitespaceToken(String str) {
    super(str, 100);
  }

  @Override
  public void accept(TokenVisitor visitor) {
    visitor.visit(this);
  }

  @Override
  public Void accept(TokenEvaluator visitor) throws Exception {
    return visitor.visit(this);
  }
}
