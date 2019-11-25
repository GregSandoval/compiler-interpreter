package compiler.lexer.token;

import compiler.parser.TokenEvaluator;
import compiler.parser.visitors.TokenVisitor;

public class IdentifierToken extends TypedToken<String> {
  private static final IdentifierToken sentinel = new IdentifierToken("Sentinel");

  public IdentifierToken(String s) {
    super(s, 2);
  }

  @Override
  protected String parse(String str) {
    return str;
  }

  public static IdentifierToken getSentinel() {
    return sentinel;
  }

  @Override
  public <T> T accept(TokenVisitor<T> visitor) {
    return visitor.visit(this);
  }

  @Override
  public Object accept(TokenEvaluator visitor) throws Exception {
    return visitor.visit(this);
  }
}
