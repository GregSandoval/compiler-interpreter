package compiler.interpreter;

import compiler.lexer.token.Token;
import compiler.lexer.token.TypeToken;
import compiler.parser.visitors.TokenTypedAdapterVisitor;

import static compiler.lexer.token.TypeToken.VoidToken;

public class TypeChecker implements TokenTypedAdapterVisitor<TypeToken> {
  private VoidToken voidToken = new VoidToken();
  private SymbolTable symtab;

  private TypeChecker(SymbolTable symtab) {
    this.symtab = symtab;
  }

  public static void check(Token tree, SymbolTable symtab) {
    tree.accept(new TypeChecker(symtab));
  }

  @Override
  public TypeToken defaultValue() {
    return voidToken;
  }
}
