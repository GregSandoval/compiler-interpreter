package compiler.parser.visitors

import compiler.lexer.token.IdentifierToken

interface IdentifierTypedVisitor<T> {
    fun visit(token: IdentifierToken): T
}
