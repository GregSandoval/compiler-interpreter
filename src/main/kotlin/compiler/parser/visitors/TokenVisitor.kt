package compiler.parser.visitors

import compiler.parser.Symbol.Terminal
import compiler.parser.Symbol.Terminal.*
import compiler.parser.Symbol.Terminal.TypedTerminal.*

interface TokenVisitor<T> :
        IgnorableTypedVisitor<T>,
        KeywordTypedVisitor<T>,
        OperatorTypedVisitor<T>,
        TypedTokenVisitor<T>,
        SymbolTypedVisitor<T> {
    fun accept(terminal: Terminal) = when (terminal) {
        is Punctuation -> this.accept(terminal)
        is Ignorable -> this.accept(terminal)
        is Operator -> this.accept(terminal)
        is Keyword -> this.accept(terminal)
        is FloatTerminal -> visit(terminal)
        is IdentifierTerminal -> visit(terminal)
        is IntegerTerminal -> visit(terminal)
        is StringTerminal -> visit(terminal)
    }
}
