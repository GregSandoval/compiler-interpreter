package compiler.lexer.token;

import compiler.parser.TokenEvaluator;
import compiler.parser.TokenVisitor;

public final class StringToken extends Token {
  private static final StringToken sentinel = new StringToken("");

  public StringToken(String str) {
    super(str.replace("\"", ""), 5);
  }

  public static StringToken getSentinel() {
    return sentinel;
  }

  @Override
  public void accept(TokenVisitor visitor) {
    visitor.visit(this);
  }

  @Override
  public Object accept(TokenEvaluator visitor) throws Exception {
    return this.getValue();
  }
}
