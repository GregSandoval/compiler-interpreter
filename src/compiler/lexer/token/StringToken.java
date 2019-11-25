package compiler.lexer.token;

import compiler.parser.TokenEvaluator;
import compiler.parser.visitors.TokenVisitor;

public final class StringToken extends Token {
  private static final StringToken sentinel = new StringToken("");

  public StringToken(String str) {
    super(str.replace("\"", ""), 5);
  }

  public static StringToken getSentinel() {
    return sentinel;
  }

  @Override
  public <T> T accept(TokenVisitor<T> visitor) {
    return visitor.visit(this);
  }

  @Override
  public Object accept(TokenEvaluator visitor) throws Exception {
    return this.getValue();
  }
}
