package compiler.parser.visitors

import compiler.parser.Symbol.Terminal

interface TokenVisitor<T> :
        IgnorableTypedVisitor<T>,
        KeywordTypedVisitor<T>,
        OperatorTypedVisitor<T>,
        TypedTokenVisitor<T>,
        SymbolTypedVisitor<T> {
    fun accept(terminal: Terminal) = when (terminal) {
        is Terminal.Punctuation -> this.accept(terminal)
        is Terminal.Ignorable -> this.accept(terminal)
        is Terminal.Operator -> this.accept(terminal)
        is Terminal.Keyword -> this.accept(terminal)
        is Terminal.TypedTerminal.FloatTerminal -> visit(terminal)
        is Terminal.TypedTerminal.IdentifierTerminal -> visit(terminal)
        is Terminal.TypedTerminal.IntegerTerminal -> visit(terminal)
        is Terminal.TypedTerminal.StringTerminal -> visit(terminal)
    }
}
