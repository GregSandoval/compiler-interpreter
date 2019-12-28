package compiler.parser.visitors

import compiler.lexer.token.Token.IgnoredTokens
import compiler.lexer.token.Token.IgnoredTokens.*
import compiler.lexer.token.Token.KeywordToken.TypeToken.VoidToken


interface IgnorableTypedVisitor<T> {
    fun accept(token: IgnoredTokens) = when (token) {
        is CommentToken -> visit(token)
        is EOFToken -> visit(token)
        is WhitespaceToken -> visit(token)
    }

    fun visit(token: CommentToken): T
    fun visit(token: EOFToken): T
    fun visit(token: WhitespaceToken): T
    fun visit(token: VoidToken): T
}
