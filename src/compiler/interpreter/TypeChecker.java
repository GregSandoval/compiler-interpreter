package compiler.interpreter;

import compiler.lexer.token.Token;
import compiler.lexer.token.TypeToken;
import compiler.parser.visitors.TokenTypedAdapterVisitor;

public class TypeChecker implements TokenTypedAdapterVisitor<TypeToken> {
  private SymbolTable symtab;

  private TypeChecker(SymbolTable symtab) {
    this.symtab = symtab;
  }

  public static void check(Token tree, SymbolTable symtab) {
    tree.accept(new TypeChecker(symtab));
  }
}
