package compiler.parser.visitors

import compiler.parser.Language.Token.TypedToken
import compiler.parser.Language.Token.TypedToken.*

interface TypedTokenVisitor<T> {
    fun accept(token: TypedToken<T>) = when (token) {
        is FloatToken -> visit(token)
        is IdentifierToken -> visit(token)
        is IntegerToken -> visit(token)
        is StringToken -> visit(token)
    }

    fun visit(token: FloatToken): T
    fun visit(token: IntegerToken): T
    fun visit(token: StringToken): T
    fun visit(token: IdentifierToken): T
}
