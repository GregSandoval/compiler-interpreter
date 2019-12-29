package compiler.parser.visitors

import compiler.lexer.token.Token

interface TokenVisitor<T> :
        IgnorableTypedVisitor<T>,
        KeywordTypedVisitor<T>,
        OperatorTypedVisitor<T>,
        TypedTokenVisitor<T>,
        SymbolTypedVisitor<T> {
    fun accept(token: Token) = when (token) {
        is Token.SymbolToken -> this.accept(token)
        is Token.IgnorableTokens -> this.accept(token)
        is Token.OperatorToken -> this.accept(token)
        is Token.KeywordToken -> this.accept(token)
        is Token.TypedToken.FloatToken -> visit(token)
        is Token.TypedToken.IdentifierToken -> visit(token)
        is Token.TypedToken.IntegerToken -> visit(token)
        is Token.TypedToken.StringToken -> visit(token)
    }
}
