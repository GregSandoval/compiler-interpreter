package compiler.parser.visitors;

import compiler.lexer.token.*;


public interface TokenTypedAdapterVisitor<T> extends TokenVisitor<T> {
  T defaultValue();

  @Override
  default T visit(CommentToken token) {
    return visitChildren(token);
  }

  @Override
  default T visit(EOFToken token) {
    return visitChildren(token);
  }

  @Override
  default T visit(WhitespaceToken token) {
    return visitChildren(token);
  }

  @Override
  default T visit(IdentifierToken token) {
    return visitChildren(token);
  }

  @Override
  default T visit(KeywordToken.ProgramKeywordToken token) {
    return visitChildren(token);
  }

  @Override
  default T visit(KeywordToken.MainKeywordToken token) {
    return visitChildren(token);
  }

  @Override
  default T visit(KeywordToken.FunctionKeywordToken token) {
    return visitChildren(token);
  }

  @Override
  default T visit(KeywordToken.ClassKeywordToken token) {
    return visitChildren(token);
  }

  @Override
  default T visit(TypeToken.FloatKeywordToken token) {
    return visitChildren(token);
  }

  @Override
  default T visit(TypeToken.IntegerKeywordToken token) {
    return visitChildren(token);
  }

  @Override
  default T visit(TypeToken.StringKeywordToken token) {
    return visitChildren(token);
  }

  @Override
  default T visit(TypeToken.VoidToken ignored) {
    return visitChildren(ignored);
  }

  @Override
  default T visit(KeywordToken.IfKeywordToken token) {
    return visitChildren(token);
  }

  @Override
  default T visit(KeywordToken.ElseIfKeywordToken token) {
    return visitChildren(token);
  }

  @Override
  default T visit(KeywordToken.ElseKeywordToken token) {
    return visitChildren(token);
  }

  @Override
  default T visit(KeywordToken.WhileKeywordToken token) {
    return visitChildren(token);
  }

  @Override
  default T visit(KeywordToken.InputKeywordToken token) {
    return visitChildren(token);
  }

  @Override
  default T visit(KeywordToken.PrintKeywordToken token) {
    return visitChildren(token);
  }

  @Override
  default T visit(KeywordToken.NewKeywordToken token) {
    return visitChildren(token);
  }

  @Override
  default T visit(KeywordToken.ReturnKeywordToken token) {
    return visitChildren(token);
  }

  @Override
  default T visit(KeywordToken.VarKeywordToken token) {
    return visitChildren(token);
  }

  @Override
  default T visit(OperatorToken.LessThan token) {
    return visitChildren(token);
  }

  @Override
  default T visit(OperatorToken.GreaterThan token) {
    return visitChildren(token);
  }

  @Override
  default T visit(OperatorToken.Asterisk token) {
    return visitChildren(token);
  }

  @Override
  default T visit(OperatorToken.Equal token) {
    return visitChildren(token);
  }

  @Override
  default T visit(OperatorToken.Minus token) {
    return visitChildren(token);
  }

  @Override
  default T visit(OperatorToken.Plus token) {
    return visitChildren(token);
  }

  @Override
  default T visit(OperatorToken.Ampersand token) {
    return visitChildren(token);
  }

  @Override
  default T visit(OperatorToken.Arrow token) {
    return visitChildren(token);
  }

  @Override
  default T visit(OperatorToken.EqualEqual token) {
    return visitChildren(token);
  }

  @Override
  default T visit(OperatorToken.NotEqual token) {
    return visitChildren(token);
  }

  @Override
  default T visit(OperatorToken.LessThanOrEqual token) {
    return visitChildren(token);
  }

  @Override
  default T visit(OperatorToken.GreaterThanOrEqual token) {
    return visitChildren(token);
  }

  @Override
  default T visit(OperatorToken.BitShiftLeft token) {
    return visitChildren(token);
  }

  @Override
  default T visit(OperatorToken.BitShiftRight token) {
    return visitChildren(token);
  }

  @Override
  default T visit(FloatToken token) {
    return visitChildren(token);
  }

  @Override
  default T visit(IntegerToken token) {
    return visitChildren(token);
  }

  @Override
  default T visit(StringToken token) {
    return visitChildren(token);
  }

  @Override
  default T visit(SymbolToken.Comma token) {
    return visitChildren(token);
  }

  @Override
  default T visit(SymbolToken.SemiColon token) {
    return visitChildren(token);
  }

  @Override
  default T visit(SymbolToken.LeftBrace token) {
    return visitChildren(token);
  }

  @Override
  default T visit(SymbolToken.RightBrace token) {
    return visitChildren(token);
  }

  @Override
  default T visit(SymbolToken.LeftBracket token) {
    return visitChildren(token);
  }

  @Override
  default T visit(SymbolToken.RightBracket token) {
    return visitChildren(token);
  }

  @Override
  default T visit(SymbolToken.LeftParen token) {
    return visitChildren(token);
  }

  @Override
  default T visit(SymbolToken.RightParen token) {
    return visitChildren(token);
  }

  @Override
  default T visit(SymbolToken.Caret token) {
    return visitChildren(token);
  }

  @Override
  default T visit(SymbolToken.Colon token) {
    return visitChildren(token);
  }

  @Override
  default T visit(SymbolToken.Period token) {
    return visitChildren(token);
  }

  @Override
  default T visit(SymbolToken.ForwardSlash token) {
    return visitChildren(token);
  }


  default T visitChildren(Token token) {
    for (final var child : token.children) {
      ((Token) child).accept(this);
    }
    return defaultValue();
  }
}
