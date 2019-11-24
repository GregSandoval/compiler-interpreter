package compiler.parser;

import compiler.lexer.token.*;
import compiler.lexer.token.KeywordToken.*;
import compiler.lexer.token.SymbolToken.*;

import static compiler.lexer.token.OperatorToken.*;

public interface TokenVisitor {
  default void visit(TokenNodeElement node) {
    throw new RuntimeException("Visitor has not implementation for: " + node);
  }

  default void visit(CommentToken token) {
    throw new RuntimeException("Not implemented: visit(CommentToken token); this: " + this);
  }

  default void visit(EOFToken token) {
    throw new RuntimeException("Not implemented: visit(EOFToken token); this: " + this);
  }

  default void visit(FloatToken token) {
    throw new RuntimeException("Not implemented: visit(FloatToken token); this: " + this);
  }

  default void visit(IntegerToken token) {
    throw new RuntimeException("Not implemented: visit(IntegerToken token); this: " + this);
  }

  default void visit(IdentifierToken token) {
    throw new RuntimeException("Not implemented: visit(IdentifierToken token); this: " + this);
  }

  default void visit(ProgramKeywordToken token) {
    throw new RuntimeException("Not implemented: visit(ProgramKeywordToken token); this: " + this);
  }

  default void visit(MainKeywordToken token) {
    throw new RuntimeException("Not implemented: visit(MainKeywordToken token); this: " + this);
  }

  default void visit(FunctionKeywordToken token) {
    throw new RuntimeException("Not implemented: visit(FunctionKeywordToken token); this: " + this);
  }

  default void visit(ClassKeywordToken token) {
    throw new RuntimeException("Not implemented: visit(ClassKeywordToken token); this: " + this);
  }

  default void visit(FloatKeywordToken token) {
    throw new RuntimeException("Not implemented: visit(FloatKeywordToken token); this: " + this);
  }

  default void visit(IntegerKeywordToken token) {
    throw new RuntimeException("Not implemented: visit(IntegerKeywordToken token); this: " + this);
  }

  default void visit(StringKeywordToken token) {
    throw new RuntimeException("Not implemented: visit(StringKeywordToken token); this: " + this);
  }

  default void visit(IfKeywordToken token) {
    throw new RuntimeException("Not implemented: visit(IfKeywordToken token); this: " + this);
  }

  default void visit(ElseIfKeywordToken token) {
    throw new RuntimeException("Not implemented: visit(ElseIfKeywordToken token); this: " + this);
  }

  default void visit(ElseKeywordToken token) {
    throw new RuntimeException("Not implemented: visit(ElseKeywordToken token); this: " + this);
  }

  default void visit(WhileKeywordToken token) {
    throw new RuntimeException("Not implemented: visit(WhileKeywordToken token); this: " + this);
  }

  default void visit(InputKeywordToken token) {
    throw new RuntimeException("Not implemented: visit(InputKeywordToken token); this: " + this);
  }

  default void visit(PrintKeywordToken token) {
    throw new RuntimeException("Not implemented: visit(PrintKeywordToken token); this: " + this);
  }

  default void visit(NewKeywordToken token) {
    throw new RuntimeException("Not implemented: visit(NewKeywordToken token); this: " + this);
  }

  default void visit(ReturnKeywordToken token) {
    throw new RuntimeException("Not implemented: visit(ReturnKeywordToken token); this: " + this);
  }

  default void visit(VarKeywordToken token) {
    throw new RuntimeException("Not implemented: visit(VarKeywordToken token); this: " + this);
  }

  default void visit(LessThan token) {
    throw new RuntimeException("Not implemented: visit(LessThan token); this: " + this);
  }

  default void visit(GreaterThan token) {
    throw new RuntimeException("Not implemented: visit(GreaterThan token); this: " + this);
  }

  default void visit(Asterisk token) {
    throw new RuntimeException("Not implemented: visit(Asterisk token); this: " + this);
  }

  default void visit(Equal token) {
    throw new RuntimeException("Not implemented: visit(Equal token); this: " + this);
  }

  default void visit(Minus token) {
    throw new RuntimeException("Not implemented: visit(Minus token); this: " + this);
  }

  default void visit(Plus token) {
    throw new RuntimeException("Not implemented: visit(Plus token); this: " + this);
  }

  default void visit(Ampersand token) {
    throw new RuntimeException("Not implemented: visit(Ampersand token); this: " + this);
  }

  default void visit(Arrow token) {
    throw new RuntimeException("Not implemented: visit(Arrow token); this: " + this);
  }

  default void visit(EqualEqual token) {
    throw new RuntimeException("Not implemented: visit(EqualEqual token); this: " + this);
  }

  default void visit(NotEqual token) {
    throw new RuntimeException("Not implemented: visit(NotEqual token); this: " + this);
  }

  default void visit(LessThanOrEqual token) {
    throw new RuntimeException("Not implemented: visit(LessThanOrEqual token); this: " + this);
  }

  default void visit(GreaterThanOrEqual token) {
    throw new RuntimeException("Not implemented: visit(GreaterThanOrEqual token); this: " + this);
  }

  default void visit(BitShiftLeft token) {
    throw new RuntimeException("Not implemented: visit(BitShiftLeft token); this: " + this);
  }

  default void visit(BitShiftRight token) {
    throw new RuntimeException("Not implemented: visit(BitShiftRight token); this: " + this);
  }

  default void visit(StringToken token) {
    throw new RuntimeException("Not implemented: visit(StringToken token); this: " + this);
  }

  default void visit(Comma token) {
    throw new RuntimeException("Not implemented: visit(Comma token); this: " + this);
  }

  default void visit(SemiColon token) {
    throw new RuntimeException("Not implemented: visit(SemiColon token); this: " + this);
  }

  default void visit(SymbolToken.LeftBrace token) {
    throw new RuntimeException("Not implemented: visit(LeftBrace token); this: " + this);
  }

  default void visit(SymbolToken.RightBrace token) {
    throw new RuntimeException("Not implemented: visit(RightBrace token); this: " + this);
  }

  default void visit(SymbolToken.LeftBracket token) {
    throw new RuntimeException("Not implemented: visit(LeftBracket token); this: " + this);
  }

  default void visit(SymbolToken.RightBracket token) {
    throw new RuntimeException("Not implemented: visit(RightBracket token); this: " + this);
  }

  default void visit(SymbolToken.LeftParen token) {
    throw new RuntimeException("Not implemented: visit(LeftParen token); this: " + this);
  }

  default void visit(RightParen token) {
    throw new RuntimeException("Not implemented: visit(RightParen token); this: " + this);
  }

  default void visit(Caret token) {
    throw new RuntimeException("Not implemented: visit(Caret token); this: " + this);
  }

  default void visit(Colon token) {
    throw new RuntimeException("Not implemented: visit(Colon token); this: " + this);
  }

  default void visit(Period token) {
    throw new RuntimeException("Not implemented: visit(Period token); this: " + this);
  }

  default void visit(ForwardSlash token) {
    throw new RuntimeException("Not implemented: visit(ForwardSlash token); this: " + this);
  }

  default void visit(WhitespaceToken token) {
    throw new RuntimeException("Not implemented: visit(WhitespaceToken token); this: " + this);
  }
}
