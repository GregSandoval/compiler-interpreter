package compiler.a5.grammar;

import compiler.lexer.token.*;
import compiler.parser.TokenNodeElement;
import compiler.parser.TokenVisitor;

import static compiler.parser.PstToAstHelpers.hoist;

public class PstToAstTokenVisitor implements TokenVisitor {

  @Override
  public void visit(SymbolToken.ForwardSlash forwardSlash) {
    hoist(forwardSlash);
  }

  @Override
  public void visit(OperatorToken.Asterisk asterisk) {
    hoist(asterisk);
  }

  @Override
  public void visit(SymbolToken.LeftParen token) {

  }

  @Override
  public void visit(IdentifierToken token) {

  }

  @Override
  public void visit(TokenNodeElement node) {

  }

  @Override
  public void visit(CommentToken token) {

  }

  @Override
  public void visit(EOFToken token) {

  }

  @Override
  public void visit(FloatToken token) {

  }

  @Override
  public void visit(IntegerToken token) {

  }

  @Override
  public void visit(KeywordToken.MainKeywordToken token) {

  }

  @Override
  public void visit(KeywordToken.FunctionKeywordToken token) {

  }

  @Override
  public void visit(KeywordToken.FloatKeywordToken token) {

  }

  @Override
  public void visit(KeywordToken.IntegerKeywordToken token) {

  }

  @Override
  public void visit(KeywordToken.StringKeywordToken token) {

  }

  @Override
  public void visit(KeywordToken.IfKeywordToken token) {

  }

  @Override
  public void visit(KeywordToken.ElseIfKeywordToken token) {

  }

  @Override
  public void visit(KeywordToken.ElseKeywordToken token) {

  }

  @Override
  public void visit(KeywordToken.WhileKeywordToken token) {

  }

  @Override
  public void visit(KeywordToken.InputKeywordToken token) {

  }

  @Override
  public void visit(KeywordToken.PrintKeywordToken token) {

  }

  @Override
  public void visit(KeywordToken.NewKeywordToken token) {

  }

  @Override
  public void visit(KeywordToken.ReturnKeywordToken token) {

  }

  @Override
  public void visit(OperatorToken.LessThan token) {

  }

  @Override
  public void visit(OperatorToken.GreaterThan token) {

  }

  @Override
  public void visit(OperatorToken.Equal token) {

  }

  @Override
  public void visit(OperatorToken.Minus token) {

  }

  @Override
  public void visit(OperatorToken.Plus token) {

  }

  @Override
  public void visit(OperatorToken.Ampersand token) {

  }

  @Override
  public void visit(OperatorToken.Arrow token) {

  }

  @Override
  public void visit(OperatorToken.EqualEqual token) {

  }

  @Override
  public void visit(OperatorToken.NotEqual token) {

  }

  @Override
  public void visit(OperatorToken.LessThanOrEqual token) {

  }

  @Override
  public void visit(OperatorToken.GreaterThanOrEqual token) {

  }

  @Override
  public void visit(OperatorToken.BitShiftLeft token) {

  }

  @Override
  public void visit(OperatorToken.BitShiftRight token) {

  }

  @Override
  public void visit(StringToken token) {

  }

  @Override
  public void visit(SymbolToken.Comma token) {

  }

  @Override
  public void visit(SymbolToken.SemiColon token) {

  }

  @Override
  public void visit(SymbolToken.LeftBrace token) {

  }

  @Override
  public void visit(SymbolToken.RightBrace token) {

  }

  @Override
  public void visit(SymbolToken.LeftBracket token) {

  }

  @Override
  public void visit(SymbolToken.RightBracket token) {

  }

  @Override
  public void visit(SymbolToken.RightParen token) {

  }

  @Override
  public void visit(SymbolToken.Caret token) {

  }

  @Override
  public void visit(SymbolToken.Colon token) {

  }

  @Override
  public void visit(SymbolToken.Period token) {

  }

  @Override
  public void visit(WhitespaceToken token) {

  }

  @Override
  public void visit(KeywordToken.ClassKeywordToken token) {

  }

  @Override
  public void visit(KeywordToken.ProgramKeywordToken token) {

  }

  @Override
  public void visit(KeywordToken.VarKeywordToken token) {

  }

}
