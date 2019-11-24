package compiler.lexer.token;

import compiler.parser.TokenEvaluator;
import compiler.parser.TokenVisitor;

public abstract class KeywordToken extends Token {
  private KeywordToken(String str, int UUID) {
    super(str, UUID);
  }

  public static class ProgramKeywordToken extends KeywordToken {
    public ProgramKeywordToken() {
      super("prog", 10);
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

  public static class MainKeywordToken extends KeywordToken {
    public MainKeywordToken() {
      super("main", 11);
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

  public static class FunctionKeywordToken extends KeywordToken {
    public FunctionKeywordToken() {
      super("fcn", 12);
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

  public static class ClassKeywordToken extends KeywordToken {
    public ClassKeywordToken() {
      super("class", 13);
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

  public static class FloatKeywordToken extends KeywordToken {
    public FloatKeywordToken() {
      super("float", 13);
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

  public static class IntegerKeywordToken extends KeywordToken {
    public IntegerKeywordToken() {
      super("int", 16);
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

  public static class StringKeywordToken extends KeywordToken {
    public StringKeywordToken() {
      super("string", 17);
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

  public static class IfKeywordToken extends KeywordToken {
    public IfKeywordToken() {
      super("if", 18);
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

  public static class ElseIfKeywordToken extends KeywordToken {
    public ElseIfKeywordToken() {
      super("elseif", 19);
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

  public static class ElseKeywordToken extends KeywordToken {
    public ElseKeywordToken() {
      super("else", 20);
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

  public static class WhileKeywordToken extends KeywordToken {
    public WhileKeywordToken() {
      super("while", 21);
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

  public static class InputKeywordToken extends KeywordToken {
    public InputKeywordToken() {
      super("input", 22);
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

  public static class PrintKeywordToken extends KeywordToken {
    public PrintKeywordToken() {
      super("print", 23);
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

  public static class NewKeywordToken extends KeywordToken {
    public NewKeywordToken() {
      super("new", 24);
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

  public static class ReturnKeywordToken extends KeywordToken {
    public ReturnKeywordToken() {
      super("return", 25);
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

  public static class VarKeywordToken extends KeywordToken {
    public VarKeywordToken() {
      super("var", 2);
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
}
