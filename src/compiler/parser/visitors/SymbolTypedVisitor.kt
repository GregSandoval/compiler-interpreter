package compiler.parser.visitors

import compiler.lexer.token.Token
import compiler.lexer.token.Token.SymbolToken.*

interface SymbolTypedVisitor<T> {
    fun accept(token: Token.SymbolToken) = when (token) {
        is Comma -> visit(token)
        is SemiColon -> visit(token)
        is LeftBrace -> visit(token)
        is RightBrace -> visit(token)
        is LeftBracket -> visit(token)
        is RightBracket -> visit(token)
        is LeftParen -> visit(token)
        is RightParen -> visit(token)
        is Colon -> visit(token)
        is Period -> visit(token)
    }

    fun visit(token: Comma): T
    fun visit(token: SemiColon): T
    fun visit(token: LeftBrace): T
    fun visit(token: RightBrace): T
    fun visit(token: LeftBracket): T
    fun visit(token: RightBracket): T
    fun visit(token: LeftParen): T
    fun visit(token: RightParen): T
    fun visit(token: Colon): T
    fun visit(token: Period): T
}
