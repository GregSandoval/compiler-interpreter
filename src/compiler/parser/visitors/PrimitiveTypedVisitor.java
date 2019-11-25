package compiler.parser.visitors;

import compiler.lexer.token.FloatToken;
import compiler.lexer.token.IntegerToken;
import compiler.lexer.token.StringToken;

public interface PrimitiveTypedVisitor<T> {
  T visit(FloatToken token);

  T visit(IntegerToken token);

  T visit(StringToken token);
}
