package compiler.parser.visitors;

import compiler.lexer.token.IdentifierToken;

public interface IdentifierTypedVisitor<T> {
  T visit(IdentifierToken token);
}
