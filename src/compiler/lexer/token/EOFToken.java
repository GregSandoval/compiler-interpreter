package compiler.lexer.token;

import compiler.parser.TokenEvaluator;
import compiler.parser.TokenVisitor;

public class EOFToken extends Token {

  public EOFToken() {
    super("", 0);
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
