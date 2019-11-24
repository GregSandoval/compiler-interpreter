package interpreter;

import compiler.lexer.token.*;
import compiler.lexer.token.KeywordToken.VarKeywordToken;
import compiler.parser.TokenEvaluator;

import java.util.List;

import static compiler.lexer.token.OperatorToken.*;
import static compiler.lexer.token.SymbolToken.*;

public class SymbolTableVisitor implements TokenEvaluator {
  private final SymbolTable symtab;
  private final Object undefined = new Object() {
    @Override
    public String toString() {
      return "undefined";
    }
  };

  private SymbolTableVisitor(SymbolTable symtab) {
    this.symtab = symtab;
  }

  public static void build(Token node, SymbolTable symtab) throws Exception {
    final var visitor = new SymbolTableVisitor(symtab);
    node.accept(visitor);
  }

  @Override
  public Void visit(VarKeywordToken kwdvar) throws Exception {
    final var paren = kwdvar.children.get(0);
    for (final var equal : paren.children) {
      final var identifier = (IdentifierToken) equal
        .children.get(0)
        .children.get(0);

      if (symtab.hasSymbol(identifier)) {
        throw new Exception(identifier.getClass().getSimpleName() + " has already been declared");
      }
      symtab.setSymbolValue(identifier, undefined);
    }
    return null;
  }

  @Override
  public Void visit(Equal equal) throws Exception {
    final var identifier = (IdentifierToken) equal.children.get(0);

    if (!symtab.hasSymbol(identifier)) {
      throw new Exception(identifier.getClass().getSimpleName() + " has not been declared");
    }

    symtab.setSymbolValue(identifier, undefined);
    return null;
  }

  @Override
  public Boolean visit(GreaterThanOrEqual greaterThanOrEqual) throws Exception {
    return null;
  }

  @Override
  public Number visit(Asterisk asterisk) throws Exception {
    return null;
  }

  @Override
  public Number visit(Minus minus) throws Exception {
    return null;
  }

  @Override
  public Number visit(Plus plus) throws Exception {
    return null;
  }

  @Override
  public Number visit(Ampersand ampersand) throws Exception {
    return null;
  }

  @Override
  public Number visit(BitShiftLeft bitShiftLeft) throws Exception {
    return null;
  }

  @Override
  public Number visit(BitShiftRight bitShiftRight) throws Exception {
    return null;
  }

  @Override
  public Number visit(Caret caret) throws Exception {
    return null;
  }

  @Override
  public Number visit(ForwardSlash forwardSlash) throws Exception {
    return null;
  }

  @Override
  public List<Object> visit(LeftParen leftParen) throws Exception {
    return null;
  }

  @Override
  public Float visit(FloatToken floatToken) throws Exception {
    return null;
  }

  @Override
  public Integer visit(IntegerToken integerToken) throws Exception {
    return null;
  }

  @Override
  public String visit(StringToken stringToken) throws Exception {
    return null;
  }

  @Override
  public Boolean visit(LessThan lessThan) throws Exception {
    return null;
  }

  @Override
  public Boolean visit(GreaterThan greaterThan) throws Exception {
    return null;
  }

  @Override
  public Boolean visit(EqualEqual equalEqual) throws Exception {
    return null;
  }

  @Override
  public Boolean visit(NotEqual notEqual) throws Exception {
    return null;
  }

  @Override
  public Boolean visit(LessThanOrEqual lessThanOrEqual) throws Exception {
    return null;
  }

}
