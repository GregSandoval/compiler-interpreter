package compiler.lexer.token;

import compiler.parser.TokenEvaluator;
import compiler.parser.visitors.TokenVisitor;

public final class WhitespaceToken extends Token {
  public WhitespaceToken(String str) {
    super(str, 100);
  }

  @Override
  public <T> T accept(TokenVisitor<T> visitor) {
    return visitor.visit(this);
  }

  @Override
  public Void accept(TokenEvaluator visitor) throws Exception {
    return visitor.visit(this);
  }
}
