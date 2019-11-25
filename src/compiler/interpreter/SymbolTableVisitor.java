package compiler.interpreter;

import compiler.lexer.token.IdentifierToken;
import compiler.lexer.token.KeywordToken.VarKeywordToken;
import compiler.lexer.token.Token;
import compiler.lexer.token.TypeToken;
import compiler.parser.visitors.TokenTypedAdapterVisitor;

import static compiler.lexer.token.OperatorToken.Equal;
import static compiler.lexer.token.TypeToken.*;

public class SymbolTableVisitor implements TokenTypedAdapterVisitor<TypeToken> {
  public static final VoidToken voidToken = new VoidToken();
  public static final Undefined undefined = new Undefined();
  private final SymbolTable symtab;

  private SymbolTableVisitor(SymbolTable symtab) {
    this.symtab = symtab;
  }

  public static void build(Token node, SymbolTable symtab) {
    final var visitor = new SymbolTableVisitor(symtab);
    node.accept(visitor);
  }

  @Override
  public TypeToken visit(VarKeywordToken kwdvar) {
    final var paren = kwdvar.children.get(0);
    for (final var equalOrDataType : paren.children) {
      IdentifierToken identifier;
      TypeToken type;

      if (equalOrDataType instanceof TypeToken) {
        type = (TypeToken) equalOrDataType;
        identifier = (IdentifierToken) equalOrDataType.children.get(0);
      } else if (equalOrDataType instanceof Equal) {
        type = (TypeToken) equalOrDataType.children.get(0);
        identifier = (IdentifierToken) type.children.get(0);
      } else {
        throw new UnknownDataTypeException((Token) equalOrDataType);
      }

      if (symtab.hasSymbol(identifier)) {
        throw new RuntimeException(identifier.getClass().getSimpleName() + " has already been declared");
      }

      symtab.setSymbolType(identifier, type);
      symtab.setSymbolValue(identifier, undefined);
    }
    return null;
  }

  @Override
  public TypeToken visit(StringKeywordToken token) {
    return token;
  }

  @Override
  public TypeToken visit(FloatKeywordToken token) {
    return token;
  }

  @Override
  public TypeToken visit(IntegerKeywordToken token) {
    return token;
  }

  @Override
  public TypeToken defaultValue() {
    return voidToken;
  }
}
