package compiler.lexer.token;

import compiler.parser.TokenEvaluator;
import compiler.parser.visitors.TokenVisitor;

public abstract class TypeToken extends KeywordToken {
  private TypeToken(String str, int UUID) {
    super(str, UUID);
  }

  public static class FloatKeywordToken extends TypeToken {
    public FloatKeywordToken() {
      super("float", 13);
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

  public static class IntegerKeywordToken extends TypeToken {
    public IntegerKeywordToken() {
      super("int", 16);
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

  public static class StringKeywordToken extends TypeToken {
    public StringKeywordToken() {
      super("string", 17);
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
}
