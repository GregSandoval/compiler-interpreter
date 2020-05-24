package compiler.parser.visitors

import compiler.parser.Symbols.Terminal.TypedTerminal
import compiler.parser.Symbols.Terminal.TypedTerminal.*

interface TypedTokenVisitor<T> {
    fun accept(token: TypedTerminal<T>) = when (token) {
        is FloatTerminal -> visit(token)
        is IdentifierTerminal -> visit(token)
        is IntegerTerminal -> visit(token)
        is StringTerminal -> visit(token)
    }

    fun visit(token: FloatTerminal): T
    fun visit(token: IntegerTerminal): T
    fun visit(token: StringTerminal): T
    fun visit(token: IdentifierTerminal): T
}
