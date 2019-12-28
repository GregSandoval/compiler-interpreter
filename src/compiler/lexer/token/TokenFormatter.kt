package compiler.lexer.token


class TokenFormatter {
    fun toString(token: Token): String {
        return "(Tok: ${token.tokenID} lin= ${token.lineNumber},${token.linePosition} str = \"${token.str}\"${toStringExtra(token)})"
    }

    fun toStringExtra(token: Token): String {
        return when (token) {
            is Token.TypedToken.FloatToken -> " flo= ${token.value}"
            is Token.TypedToken.IntegerToken -> " int= ${token.value}"
            else -> ""
        }
    }
}

