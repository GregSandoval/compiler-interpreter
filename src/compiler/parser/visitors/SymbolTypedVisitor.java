package compiler.parser.visitors;

import static compiler.lexer.token.SymbolToken.*;

public interface SymbolTypedVisitor<T> {
  T visit(Comma token);

  T visit(SemiColon token);

  T visit(LeftBrace token);

  T visit(RightBrace token);

  T visit(LeftBracket token);

  T visit(RightBracket token);

  T visit(LeftParen token);

  T visit(RightParen token);

  T visit(Colon token);

  T visit(Period token);

}
