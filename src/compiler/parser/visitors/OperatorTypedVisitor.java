package compiler.parser.visitors;

import static compiler.lexer.token.OperatorToken.*;

public interface OperatorTypedVisitor<T> {
  T visit(LessThan token);

  T visit(GreaterThan token);

  T visit(Asterisk token);

  T visit(Equal token);

  T visit(Minus token);

  T visit(Plus token);

  T visit(Ampersand token);

  T visit(Arrow token);

  T visit(EqualEqual token);

  T visit(NotEqual token);

  T visit(LessThanOrEqual token);

  T visit(GreaterThanOrEqual token);

  T visit(BitShiftLeft token);

  T visit(BitShiftRight token);
}
