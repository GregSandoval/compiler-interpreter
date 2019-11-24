package compiler.lexer.token;

import compiler.parser.TokenEvaluator;
import compiler.parser.TokenVisitor;

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
  public void accept(TokenVisitor visitor) {
    visitor.visit(this);
  }

  @Override
  public Object accept(TokenEvaluator visitor) throws Exception {
    return visitor.visit(this);
  }
}
