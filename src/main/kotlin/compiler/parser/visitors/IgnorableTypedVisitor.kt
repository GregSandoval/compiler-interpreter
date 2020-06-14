package compiler.parser.visitors

import compiler.parser.Symbol.Terminal.Ignorable
import compiler.parser.Symbol.Terminal.Ignorable.*
import compiler.parser.Symbol.Terminal.Keyword.Type.Void


interface IgnorableTypedVisitor<T> {
    fun accept(token: Ignorable) = when (token) {
        is CommentTerminal -> visit(token)
        is EOFTerminal -> visit(token)
        is WhitespaceTerminal -> visit(token)
    }

    fun visit(token: CommentTerminal): T
    fun visit(token: EOFTerminal): T
    fun visit(token: WhitespaceTerminal): T
    fun visit(token: Void): T
}
