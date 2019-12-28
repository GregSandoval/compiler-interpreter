package compiler.lexer

/**
 * Represents a non final state within the DFA.
 */
class NonFinalState(name: String) : LexicalNode(name) {
    companion object {
        val END_OF_TERMINAL = NonFinalState("END_OF_TERMINAL")
        val FATAL_ERROR = NonFinalState("FATAL_ERROR")
    }
}
