package compiler.parser.visitors

import compiler.lexer.token.FloatToken
import compiler.lexer.token.IntegerToken
import compiler.lexer.token.StringToken

interface PrimitiveTypedVisitor<T> {
    fun visit(token: FloatToken): T
    fun visit(token: IntegerToken): T
    fun visit(token: StringToken): T
}
