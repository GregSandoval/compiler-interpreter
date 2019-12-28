package compiler.parser.visitors

import compiler.lexer.token.Token.TypedToken

interface TypedTokenVisitor<T> {
    fun accept(token: TypedToken<T>) = when (token) {
        is TypedToken.FloatToken -> visit(token)
        is TypedToken.IdentifierToken -> visit(token)
        is TypedToken.IntegerToken -> visit(token)
        is TypedToken.StringToken -> visit(token)
    }

    fun visit(token: TypedToken.FloatToken): T
    fun visit(token: TypedToken.IntegerToken): T
    fun visit(token: TypedToken.StringToken): T
    fun visit(token: TypedToken.IdentifierToken): T
}
