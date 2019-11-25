package compiler.a5.grammar;

import compiler.lexer.token.*;
import compiler.parser.visitors.TokenVisitor;

import static compiler.parser.PstToAstHelpers.hoist;

public class PstToAstTokenVisitor implements TokenVisitor<Void> {
  @Override
  public Void visit(SymbolToken.ForwardSlash forwardSlash) {
    hoist(forwardSlash);
    return null;
  }

  @Override
  public Void visit(OperatorToken.Asterisk asterisk) {
    hoist(asterisk);
    return null;
  }

  @Override
  public Void visit(SymbolToken.LeftParen token) {
    return null;
  }

  @Override
  public Void visit(IdentifierToken token) {
    return null;
  }

  @Override
  public Void visit(CommentToken token) {
    return null;
  }

  @Override
  public Void visit(EOFToken token) {
    return null;
  }

  @Override
  public Void visit(FloatToken token) {
    return null;
  }

  @Override
  public Void visit(IntegerToken token) {
    return null;
  }

  @Override
  public Void visit(KeywordToken.MainKeywordToken token) {
    return null;
  }

  @Override
  public Void visit(KeywordToken.FunctionKeywordToken token) {
    return null;
  }

  @Override
  public Void visit(TypeToken.FloatKeywordToken token) {
    return null;
  }

  @Override
  public Void visit(TypeToken.IntegerKeywordToken token) {
    return null;
  }

  @Override
  public Void visit(TypeToken.StringKeywordToken token) {
    return null;
  }

  @Override
  public Void visit(KeywordToken.IfKeywordToken token) {
    return null;
  }

  @Override
  public Void visit(KeywordToken.ElseIfKeywordToken token) {
    return null;
  }

  @Override
  public Void visit(KeywordToken.ElseKeywordToken token) {
    return null;
  }

  @Override
  public Void visit(KeywordToken.WhileKeywordToken token) {
    return null;
  }

  @Override
  public Void visit(KeywordToken.InputKeywordToken token) {
    return null;
  }

  @Override
  public Void visit(KeywordToken.PrintKeywordToken token) {
    return null;
  }

  @Override
  public Void visit(KeywordToken.NewKeywordToken token) {
    return null;
  }

  @Override
  public Void visit(KeywordToken.ReturnKeywordToken token) {
    return null;
  }

  @Override
  public Void visit(OperatorToken.LessThan token) {
    return null;
  }

  @Override
  public Void visit(OperatorToken.GreaterThan token) {
    return null;
  }

  @Override
  public Void visit(OperatorToken.Equal token) {
    return null;
  }

  @Override
  public Void visit(OperatorToken.Minus token) {
    return null;
  }

  @Override
  public Void visit(OperatorToken.Plus token) {
    return null;
  }

  @Override
  public Void visit(OperatorToken.Ampersand token) {
    return null;
  }

  @Override
  public Void visit(OperatorToken.Arrow token) {
    return null;
  }

  @Override
  public Void visit(OperatorToken.EqualEqual token) {
    return null;
  }

  @Override
  public Void visit(OperatorToken.NotEqual token) {
    return null;
  }

  @Override
  public Void visit(OperatorToken.LessThanOrEqual token) {
    return null;
  }

  @Override
  public Void visit(OperatorToken.GreaterThanOrEqual token) {
    return null;
  }

  @Override
  public Void visit(OperatorToken.BitShiftLeft token) {
    return null;
  }

  @Override
  public Void visit(OperatorToken.BitShiftRight token) {
    return null;
  }

  @Override
  public Void visit(StringToken token) {
    return null;
  }

  @Override
  public Void visit(SymbolToken.Comma token) {
    return null;
  }

  @Override
  public Void visit(SymbolToken.SemiColon token) {
    return null;
  }

  @Override
  public Void visit(SymbolToken.LeftBrace token) {
    return null;
  }

  @Override
  public Void visit(SymbolToken.RightBrace token) {
    return null;
  }

  @Override
  public Void visit(SymbolToken.LeftBracket token) {
    return null;
  }

  @Override
  public Void visit(SymbolToken.RightBracket token) {
    return null;
  }

  @Override
  public Void visit(SymbolToken.RightParen token) {
    return null;
  }

  @Override
  public Void visit(SymbolToken.Caret token) {
    return null;
  }

  @Override
  public Void visit(SymbolToken.Colon token) {
    return null;
  }

  @Override
  public Void visit(SymbolToken.Period token) {
    return null;
  }

  @Override
  public Void visit(WhitespaceToken token) {
    return null;
  }

  @Override
  public Void visit(KeywordToken.ClassKeywordToken token) {
    return null;
  }

  @Override
  public Void visit(KeywordToken.ProgramKeywordToken token) {
    return null;
  }

  @Override
  public Void visit(KeywordToken.VarKeywordToken token) {
    return null;
  }

}
