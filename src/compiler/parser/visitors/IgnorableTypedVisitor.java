package compiler.parser.visitors;

import compiler.lexer.token.CommentToken;
import compiler.lexer.token.EOFToken;
import compiler.lexer.token.WhitespaceToken;

public interface IgnorableTypedVisitor<T> {
  T visit(CommentToken comment);

  T visit(EOFToken comment);

  T visit(WhitespaceToken comment);
}
