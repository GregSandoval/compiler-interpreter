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
  public TypeToken visit(OperatorToken.Equal token) {
    final var left = ((Token) token.children.get(0)).accept(this);
    final var right = ((Token) token.children.get(1)).accept(this);

    if (left.getClass() != right.getClass()) {
      throw new RuntimeException(right.getValue() + " is not assignable to " + left.getValue());
    }

    return left;
  }

  @Override
  public TypeToken visit(InputKeywordToken token) {
    return stringType;
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
    return stringType;
  }

  @Override
  public TypeToken visit(FloatKeywordToken token) {
    return token;
  }

  @Override
  public TypeToken visit(FloatToken token) {
    return floatType;
  }

  @Override
  public TypeToken visit(IntegerKeywordToken token) {
    return token;
  }

  @Override
  public TypeToken visit(IntegerToken token) {
    return integerType;
  }

  @Override
  public TypeToken defaultValue() {
    return voidType;
  }
}
