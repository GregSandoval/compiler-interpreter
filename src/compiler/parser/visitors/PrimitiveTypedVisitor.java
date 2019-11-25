package compiler.parser.visitors;

import compiler.lexer.token.FloatToken;
import compiler.lexer.token.IntegerToken;
import compiler.lexer.token.StringToken;

public interface PrimitiveTypedVisitor<FloatType, IntegerType, StringType> {
  FloatType visit(FloatToken token);

  IntegerType visit(IntegerToken token);

  StringType visit(StringToken token);
}
