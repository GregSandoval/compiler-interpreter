package compiler.parser.visitors;

import compiler.lexer.token.TypeToken;

import static compiler.lexer.token.KeywordToken.*;

public interface KeywordTypedVisitor<T> {
  T visit(ProgramKeywordToken token);

  T visit(MainKeywordToken token);

  T visit(FunctionKeywordToken token);

  T visit(ClassKeywordToken token);

  T visit(TypeToken.FloatKeywordToken token);

  T visit(TypeToken.IntegerKeywordToken token);

  T visit(TypeToken.StringKeywordToken token);

  T visit(IfKeywordToken token);

  T visit(ElseIfKeywordToken token);

  T visit(ElseKeywordToken token);

  T visit(WhileKeywordToken token);

  T visit(InputKeywordToken token);

  T visit(PrintKeywordToken token);

  T visit(NewKeywordToken token);

  T visit(ReturnKeywordToken token);

  T visit(VarKeywordToken token);
}
