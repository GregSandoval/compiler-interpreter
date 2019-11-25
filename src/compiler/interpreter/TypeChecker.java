package compiler.interpreter;

import compiler.lexer.token.*;
import compiler.parser.visitors.TokenTypedAdapterVisitor;

import static compiler.lexer.token.TypeToken.*;

public class TypeChecker implements TokenTypedAdapterVisitor<TypeToken> {
  private SymbolTable symtab;
  private VoidToken voidType = new VoidToken();
  private FloatKeywordToken floatType = new FloatKeywordToken();
  private StringKeywordToken stringType = new StringKeywordToken();
  private IntegerKeywordToken integerType = new IntegerKeywordToken();

  private TypeChecker(SymbolTable symtab) {
    this.symtab = symtab;
  }

  public static void check(Token tree, SymbolTable symtab) {
    tree.accept(new TypeChecker(symtab));
  }

  @Override
  public TypeToken visit(OperatorToken.Asterisk token) {
    return checkBinaryOperator(token);
  }

  @Override
  public TypeToken visit(OperatorToken.Equal token) {
    return checkBinaryOperator(token);
  }

  @Override
  public TypeToken visit(InputKeywordToken token) {
    return copyLineInfo(token, stringType);
  }

  @Override
  public TypeToken visit(IdentifierToken token) {
    return this.symtab.getSymbolType(token);
  }

  @Override
  public TypeToken visit(StringKeywordToken token) {
    return token;
  }

  @Override
  public TypeToken visit(StringToken token) {
    return copyLineInfo(token, stringType);
  }

  @Override
  public TypeToken visit(FloatKeywordToken token) {
    return token;
  }

  @Override
  public TypeToken visit(FloatToken token) {
    return copyLineInfo(token, floatType);
  }

  @Override
  public TypeToken visit(IntegerKeywordToken token) {
    return token;
  }

  @Override
  public TypeToken visit(IntegerToken token) {
    return copyLineInfo(token, integerType);
  }

  private static TypeToken copyLineInfo(Token from, TypeToken to) {
    to.setLineNumber(from.getLineNumber());
    to.setLinePosition(from.getLinePosition());
    return to;
  }

  private TypeToken checkBinaryOperator(OperatorToken operator) {
    final var left = ((Token) operator.children.get(0)).accept(this);
    final var right = ((Token) operator.children.get(1)).accept(this);

    if (left instanceof FloatKeywordToken && right instanceof FloatKeywordToken) {
      return left;
    }

    if (left instanceof IntegerKeywordToken && right instanceof IntegerKeywordToken) {
      return left;
    }

    if (left instanceof FloatKeywordToken && right instanceof IntegerKeywordToken) {
      return left;
    }

    if (left instanceof IntegerKeywordToken && right instanceof FloatKeywordToken) {
      return right;
    }

    if (left.getClass() != right.getClass()) {
      throw new OperatorTypeException(operator, left, right);
    }

    return left;
  }

  @Override
  public TypeToken defaultValue() {
    return voidType;
  }
}
