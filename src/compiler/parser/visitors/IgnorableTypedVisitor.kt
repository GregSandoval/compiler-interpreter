package compiler.parser.visitors

import compiler.lexer.token.CommentToken
import compiler.lexer.token.EOFToken
import compiler.lexer.token.TypeToken.VoidToken
import compiler.lexer.token.WhitespaceToken

interface IgnorableTypedVisitor<T> {
    fun visit(token: CommentToken): T
    fun visit(token: EOFToken): T
    fun visit(token: WhitespaceToken): T
    fun visit(token: VoidToken): T
}
