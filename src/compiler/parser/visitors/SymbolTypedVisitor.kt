package compiler.parser.visitors

import compiler.parser.Symbols.Terminal
import compiler.parser.Symbols.Terminal.Punctuation.*

interface SymbolTypedVisitor<T> {
    fun accept(terminal: Terminal.Punctuation) = when (terminal) {
        is Comma -> visit(terminal)
        is SemiColon -> visit(terminal)
        is LeftBrace -> visit(terminal)
        is RightBrace -> visit(terminal)
        is LeftBracket -> visit(terminal)
        is RightBracket -> visit(terminal)
        is LeftParen -> visit(terminal)
        is RightParen -> visit(terminal)
        is Colon -> visit(terminal)
        is Period -> visit(terminal)
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
