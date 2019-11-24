package compiler.lexer.token;

import compiler.parser.TokenEvaluator;
import compiler.parser.TokenVisitor;

import java.util.List;

public abstract class SymbolToken extends Token {

  private SymbolToken(String str, int UUID) {
    super(str, UUID);
  }

  public static class Comma extends SymbolToken {
    public Comma() {
      super(",", 6);
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

  public static class SemiColon extends SymbolToken {
    public SemiColon() {
      super(";", 7);
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

  public static class LeftBrace extends SymbolToken {
    public LeftBrace() {
      super("{", 33);
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

  public static class RightBrace extends SymbolToken {
    public RightBrace() {
      super("}", 34);
    }

    @Override
    public Void accept(TokenEvaluator visitor) throws Exception {
      return visitor.visit(this);
    }

    @Override
    public void accept(TokenVisitor visitor) {
      visitor.visit(this);
    }
  }

  public static class LeftBracket extends SymbolToken {
    public LeftBracket() {
      super("[", 35);
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

  public static class RightBracket extends SymbolToken {
    public RightBracket() {
      super("]", 36);
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

  public static class LeftParen extends SymbolToken {
    public LeftParen() {
      super("(", 37);
    }

    @Override
    public void accept(TokenVisitor visitor) {
      visitor.visit(this);
    }

    @Override
    public List<Object> accept(TokenEvaluator visitor) throws Exception {
      return visitor.visit(this);
    }
  }

  public static class RightParen extends SymbolToken {
    public RightParen() {
      super(")", 38);
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

  public static class Caret extends SymbolToken {
    public Caret() {
      super("^", 42);
    }

    @Override
    public void accept(TokenVisitor visitor) {
      visitor.visit(this);
    }

    @Override
    public Number accept(TokenEvaluator visitor) throws Exception {
      return visitor.visit(this);
    }
  }

  public static class Colon extends SymbolToken {
    public Colon() {
      super(":", 43);
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

  public static class Period extends SymbolToken {
    public Period() {
      super(".", 44);
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

  public static class ForwardSlash extends SymbolToken {
    public ForwardSlash() {
      super("/", 48);
    }

    @Override
    public void accept(TokenVisitor visitor) {
      visitor.visit(this);
    }

    @Override
    public Number accept(TokenEvaluator visitor) throws Exception {
      return visitor.visit(this);
    }
  }

}
