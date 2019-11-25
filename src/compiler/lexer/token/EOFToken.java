package compiler.lexer.token;

import compiler.parser.TokenEvaluator;
import compiler.parser.visitors.TokenVisitor;

public class EOFToken extends Token {

  public EOFToken() {
    super("", 0);
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
