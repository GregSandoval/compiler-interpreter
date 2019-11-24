package compiler.parser;

import compiler.lexer.token.*;

import java.util.List;

import static compiler.lexer.token.KeywordToken.*;
import static compiler.lexer.token.OperatorToken.*;
import static compiler.lexer.token.SymbolToken.*;

public interface TokenEvaluator {
  // Primitives
  Float visit(FloatToken token) throws Exception;

  Integer visit(IntegerToken token) throws Exception;

  String visit(StringToken token) throws Exception;


  // Relational
  Boolean visit(LessThan token) throws Exception;

  Boolean visit(GreaterThan token) throws Exception;

  Boolean visit(EqualEqual token) throws Exception;

  Boolean visit(NotEqual token) throws Exception;

  Boolean visit(LessThanOrEqual token) throws Exception;

  Boolean visit(GreaterThanOrEqual token) throws Exception;


  // Operators
  Object visit(Asterisk token) throws Exception;

  Number visit(Minus token) throws Exception;

  Number visit(Plus token) throws Exception;

  Object visit(Ampersand token) throws Exception;

  Number visit(BitShiftLeft token) throws Exception;

  Number visit(BitShiftRight token) throws Exception;

  Number visit(Caret token) throws Exception;

  Number visit(ForwardSlash token) throws Exception;

  default Void visit(StringKeywordToken token) throws Exception {
    return visitChildren(token);
  }

  default Void visit(TokenNodeElement node) throws Exception {
    return null;
  }

  default Void visit(CommentToken token) throws Exception {
    return visitChildren(token);
  }

  default Void visit(EOFToken token) throws Exception {
    return visitChildren(token);
  }

  default Object visit(IdentifierToken token) throws Exception {
    return visitChildren(token);
  }

  default Void visit(ProgramKeywordToken token) throws Exception {
    return visitChildren(token);
  }

  default Void visit(MainKeywordToken token) throws Exception {
    return visitChildren(token);
  }

  default Void visit(FunctionKeywordToken token) throws Exception {
    return visitChildren(token);
  }

  default Void visit(ClassKeywordToken token) throws Exception {
    return visitChildren(token);
  }

  default Void visit(FloatKeywordToken token) throws Exception {
    return visitChildren(token);
  }

  default Void visit(IntegerKeywordToken token) throws Exception {
    return visitChildren(token);
  }

  default Void visit(IfKeywordToken token) throws Exception {
    return visitChildren(token);
  }

  default Void visit(ElseIfKeywordToken token) throws Exception {
    return visitChildren(token);
  }

  default Void visit(ElseKeywordToken token) throws Exception {
    return visitChildren(token);
  }

  default Void visit(WhileKeywordToken token) throws Exception {
    return visitChildren(token);
  }

  default Void visit(InputKeywordToken token) throws Exception {
    return visitChildren(token);
  }

  default Void visit(PrintKeywordToken token) throws Exception {
    return visitChildren(token);
  }

  default Void visit(NewKeywordToken token) throws Exception {
    return visitChildren(token);
  }

  default Void visit(ReturnKeywordToken token) throws Exception {
    return visitChildren(token);
  }

  default Void visit(VarKeywordToken token) throws Exception {
    return visitChildren(token);
  }

  default Void visit(Equal token) throws Exception {
    return visitChildren(token);
  }

  default Void visit(Arrow token) throws Exception {
    return visitChildren(token);
  }

  default Void visit(Comma token) throws Exception {
    return visitChildren(token);
  }

  default Void visit(SemiColon token) throws Exception {
    return visitChildren(token);
  }

  default Void visit(LeftBrace token) throws Exception {
    return visitChildren(token);
  }

  default Void visit(RightBrace token) throws Exception {
    return visitChildren(token);
  }

  default Void visit(LeftBracket token) throws Exception {
    return visitChildren(token);
  }

  default Void visit(RightBracket token) throws Exception {
    return visitChildren(token);
  }

  List<Object> visit(LeftParen token) throws Exception;

  default Void visit(RightParen token) throws Exception {
    return visitChildren(token);
  }

  default Void visit(Colon token) throws Exception {
    return visitChildren(token);
  }

  default Void visit(Period token) throws Exception {
    return visitChildren(token);
  }

  default Void visit(WhitespaceToken token) throws Exception {
    return visitChildren(token);
  }

  default Void visitChildren(Token token) throws Exception {
    for (final var child : token.children) {
      if (!(child instanceof Token)) {
        throw new Exception("Non token in ast?");
      }

      ((Token) child).accept(this);
    }
    return null;
  }
}
