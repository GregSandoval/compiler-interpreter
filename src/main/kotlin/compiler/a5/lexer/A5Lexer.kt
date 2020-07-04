package compiler.a5.lexer

import compiler.a5.lexicon.A5LexiconDFA
import compiler.dfa.DFALogger
import compiler.dfa.LexicalNode
import compiler.lexer.LexerBuilder
import compiler.parser.Symbol.Terminal
import compiler.utils.TextCursor

class A5Lexer {
    private val lexer = LexerBuilder()
            .onTransition(DFALogger::logTransition)
            .onTokenCreated(DFALogger::logAcceptedToken)
            .onUnknownTokenFound(this::logUnknownToken)
            .createLexer(A5LexiconDFA())


    fun lex(text: String): List<Terminal> {
        return this.lexer.lex(text)
    }

    private fun logUnknownToken(cursor: TextCursor, state: LexicalNode.NonFinalState) {
        throw UnknownTokenException(cursor.getCurrentSentence(), cursor)
    }
}
