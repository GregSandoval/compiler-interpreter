package compiler.lexer.token;

import compiler.parser.TokenEvaluator;
import compiler.parser.visitors.TokenVisitor;

public final class IntegerToken extends TypedToken<Integer> {
  private static final IntegerToken sentinel = new IntegerToken("0");

  public IntegerToken(String str) {
    super(str, 3);
  }

  @Override
  protected Integer parse(String str) {
    return Integer.parseInt(str);
  }

  public static IntegerToken getSentinel() {
    return sentinel;
  }

  @Override
  public String toStringExtra() {
    return " int= " + this.value;
  }

  @Override
  public <T> T accept(TokenVisitor<T> visitor) {
    return visitor.visit(this);
  }

  @Override
  public Number accept(TokenEvaluator visitor) throws Exception {
    return visitor.visit(this);
  }
}
