package compiler.parser.visitors

import compiler.lexer.token.SymbolToken.*

interface SymbolTypedVisitor<T> {
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
