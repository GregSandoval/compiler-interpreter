package compiler.interpreter;

import compiler.lexer.token.IdentifierToken;
import compiler.lexer.token.KeywordToken;
import compiler.lexer.token.KeywordToken.VarKeywordToken;
import compiler.lexer.token.Token;
import compiler.lexer.token.TypeToken;
import compiler.parser.visitors.TokenTypedAdapterVisitor;

import static compiler.lexer.token.OperatorToken.Equal;

public class SymbolTableVisitor implements TokenTypedAdapterVisitor<KeywordToken> {
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

  public static void build(Token node, SymbolTable symtab) {
    final var visitor = new SymbolTableVisitor(symtab);
    node.accept(visitor);
  }

  @Override
  public KeywordToken visit(VarKeywordToken kwdvar) {
    final var paren = kwdvar.children.get(0);
    for (final var equalOrDataType : paren.children) {
      IdentifierToken identifier;
      if (equalOrDataType instanceof TypeToken)
        identifier = (IdentifierToken) equalOrDataType.children.get(0);
      else if (equalOrDataType instanceof Equal)
        identifier = (IdentifierToken) equalOrDataType.children.get(0).children.get(0);
      else
        throw new RuntimeException("Please check symbol table, unchecked type:" + equalOrDataType);

      if (symtab.hasSymbol(identifier)) {
        throw new RuntimeException(identifier.getClass().getSimpleName() + " has already been declared");
      }

      symtab.setSymbolType(identifier, identifier.accept(this));
      symtab.setSymbolValue(identifier, undefined);
    }
    return null;
  }

}
