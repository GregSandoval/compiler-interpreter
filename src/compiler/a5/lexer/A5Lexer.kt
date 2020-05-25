package compiler.a5.lexer

import compiler.a5.lexicon.A5LexiconDFA
import compiler.lexer.LexerBuilder
import compiler.lexer.LexicalNode
import compiler.lexer.UnknownTokenException
import compiler.parser.Symbol.Terminal
import compiler.utils.TextCursor

object A5Lexer {
    fun lex(text: String): List<Terminal> {
        return LexerBuilder()
                .setDFA(A5LexiconDFA())
                .onUnknownTokenFound(this::logUnknownToken)
                .createLexer()
                .lex(text)
    }

    fun logUnknownToken(cursor: TextCursor, state: LexicalNode.NonFinalState) {
        throw UnknownTokenException(cursor.getCurrentSentence(), cursor)
    }
}
