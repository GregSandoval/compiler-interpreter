package compiler.parser.visitors;

import compiler.lexer.token.*;

import static compiler.lexer.token.KeywordToken.*;
import static compiler.lexer.token.OperatorToken.*;
import static compiler.lexer.token.SymbolToken.*;

public interface TokenTypedAdapterVisitor<T> extends TokenVisitor<T> {
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
  default T visit(ProgramKeywordToken token) {
    return visitChildren(token);
  }

  @Override
  default T visit(MainKeywordToken token) {
    return visitChildren(token);
  }

  @Override
  default T visit(FunctionKeywordToken token) {
    return visitChildren(token);
  }

  @Override
  default T visit(ClassKeywordToken token) {
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
  default T visit(IfKeywordToken token) {
    return visitChildren(token);
  }

  @Override
  default T visit(ElseIfKeywordToken token) {
    return visitChildren(token);
  }

  @Override
  default T visit(ElseKeywordToken token) {
    return visitChildren(token);
  }

  @Override
  default T visit(WhileKeywordToken token) {
    return visitChildren(token);
  }

  @Override
  default T visit(InputKeywordToken token) {
    return visitChildren(token);
  }

  @Override
  default T visit(PrintKeywordToken token) {
    return visitChildren(token);
  }

  @Override
  default T visit(NewKeywordToken token) {
    return visitChildren(token);
  }

  @Override
  default T visit(ReturnKeywordToken token) {
    return visitChildren(token);
  }

  @Override
  default T visit(VarKeywordToken token) {
    return visitChildren(token);
  }

  @Override
  default T visit(LessThan token) {
    return visitChildren(token);
  }

  @Override
  default T visit(GreaterThan token) {
    return visitChildren(token);
  }

  @Override
  default T visit(Asterisk token) {
    return visitChildren(token);
  }

  @Override
  default T visit(Equal token) {
    return visitChildren(token);
  }

  @Override
  default T visit(Minus token) {
    return visitChildren(token);
  }

  @Override
  default T visit(Plus token) {
    return visitChildren(token);
  }

  @Override
  default T visit(Ampersand token) {
    return visitChildren(token);
  }

  @Override
  default T visit(Arrow token) {
    return visitChildren(token);
  }

  @Override
  default T visit(EqualEqual token) {
    return visitChildren(token);
  }

  @Override
  default T visit(NotEqual token) {
    return visitChildren(token);
  }

  @Override
  default T visit(LessThanOrEqual token) {
    return visitChildren(token);
  }

  @Override
  default T visit(GreaterThanOrEqual token) {
    return visitChildren(token);
  }

  @Override
  default T visit(BitShiftLeft token) {
    return visitChildren(token);
  }

  @Override
  default T visit(BitShiftRight token) {
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
  default T visit(Comma token) {
    return visitChildren(token);
  }

  @Override
  default T visit(SemiColon token) {
    return visitChildren(token);
  }

  @Override
  default T visit(LeftBrace token) {
    return visitChildren(token);
  }

  @Override
  default T visit(RightBrace token) {
    return visitChildren(token);
  }

  @Override
  default T visit(LeftBracket token) {
    return visitChildren(token);
  }

  @Override
  default T visit(RightBracket token) {
    return visitChildren(token);
  }

  @Override
  default T visit(LeftParen token) {
    return visitChildren(token);
  }

  @Override
  default T visit(RightParen token) {
    return visitChildren(token);
  }

  @Override
  default T visit(Caret token) {
    return visitChildren(token);
  }

  @Override
  default T visit(Colon token) {
    return visitChildren(token);
  }

  @Override
  default T visit(Period token) {
    return visitChildren(token);
  }

  @Override
  default T visit(ForwardSlash token) {
    return visitChildren(token);
  }

  default T visitChildren(Token token) {
    for (final var child : token.children) {
      ((Token) child).accept(this);
    }
    return null;
  }

}
