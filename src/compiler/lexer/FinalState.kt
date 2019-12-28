package compiler.lexer

import compiler.lexer.token.Token

/**
 * Represents a final state within the DFA.
 * This class takes constructor for a Token,
 * creating it when getToken is called
 */
class FinalState : LexicalNode {
    private val constructor: (String) -> Token

    constructor(name: String, constructor: (String) -> Token) : super(name) {
        this.constructor = constructor
    }

    constructor(name: String, constructor: () -> Token) : super(name) {
        this.constructor = { constructor.invoke() }
    }

    fun getToken(str: String): Token {
        return constructor(str)
    }
}
