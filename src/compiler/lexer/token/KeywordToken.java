package compiler.lexer.token;

import compiler.parser.TokenEvaluator;
import compiler.parser.visitors.TokenVisitor;

public abstract class KeywordToken extends Token {
  protected KeywordToken(String str, int UUID) {
    super(str, UUID);
  }

  public static class ProgramKeywordToken extends KeywordToken {
    public ProgramKeywordToken() {
      super("prog", 10);
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

  public static class MainKeywordToken extends KeywordToken {
    public MainKeywordToken() {
      super("main", 11);
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

  public static class FunctionKeywordToken extends KeywordToken {
    public FunctionKeywordToken() {
      super("fcn", 12);
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

  public static class ClassKeywordToken extends KeywordToken {
    public ClassKeywordToken() {
      super("class", 13);
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

  public static class IfKeywordToken extends KeywordToken {
    public IfKeywordToken() {
      super("if", 18);
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

  public static class ElseIfKeywordToken extends KeywordToken {
    public ElseIfKeywordToken() {
      super("elseif", 19);
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

  public static class ElseKeywordToken extends KeywordToken {
    public ElseKeywordToken() {
      super("else", 20);
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

  public static class WhileKeywordToken extends KeywordToken {
    public WhileKeywordToken() {
      super("while", 21);
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

  public static class InputKeywordToken extends KeywordToken {
    public InputKeywordToken() {
      super("input", 22);
    }

    @Override
    public <T> T accept(TokenVisitor<T> visitor) {
      return visitor.visit(this);
    }


    @Override
    public String accept(TokenEvaluator visitor) throws Exception {
      return visitor.visit(this);
    }
  }

  public static class PrintKeywordToken extends KeywordToken {
    public PrintKeywordToken() {
      super("print", 23);
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

  public static class NewKeywordToken extends KeywordToken {
    public NewKeywordToken() {
      super("new", 24);
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

  public static class ReturnKeywordToken extends KeywordToken {
    public ReturnKeywordToken() {
      super("return", 25);
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

  public static class VarKeywordToken extends KeywordToken {
    public VarKeywordToken() {
      super("var", 2);
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
