package compiler.lexer

import compiler.lexer.token.KeywordTokenRecognizer
import compiler.lexer.token.Token
import compiler.lexer.token.Token.IgnoredTokens.*
import compiler.utils.TextCursor
import compiler.utils.TriConsumer
import java.util.*
import java.util.function.BiConsumer
import java.util.stream.Collectors

class Lexer(
        private val START_STATE: LexicalNode,
        private val transitionListeners: TriConsumer<LexicalNode, Char, LexicalNode>,
        private val tokenCreatedListeners: TriConsumer<LexicalNode, LexicalNode, Token>,
        private val unknownTokenListener: BiConsumer<String, TextCursor>
) {
    fun analyze(text: String): List<Token> {
        var CURRENT_STATE = START_STATE
        val tokens = ArrayList<Token>()
        val currentToken = StringBuilder()
        val cursor = TextCursor(text)
        for (letter in cursor) {
            val GOTO = CURRENT_STATE.ON(letter)
            transitionListeners.accept(CURRENT_STATE, letter, GOTO)
            if (GOTO === NonFinalState.END_OF_TERMINAL) {
                val token = (CURRENT_STATE as FinalState).getToken(currentToken.toString())
                token.lineNumber = cursor.getCursorLineNumber()
                token.linePosition = cursor.getCursorLinePosition() - currentToken.length
                tokens.add(token)
                tokenCreatedListeners.accept(CURRENT_STATE, GOTO, token)
                currentToken.setLength(0)
                cursor.rewind()
                CURRENT_STATE = START_STATE
                continue
            }
            if (GOTO === NonFinalState.FATAL_ERROR) {
                unknownTokenListener.accept(currentToken.toString() + letter, cursor)
                break
            }
            currentToken.append(letter)
            CURRENT_STATE = GOTO
        }
        // Add eof :)
        val eof = EOFToken()
        eof.linePosition = cursor.getCursorLinePosition()
        eof.lineNumber = cursor.getCursorLineNumber()
        tokens.add(eof)
        return tokens
                .stream()
                .filter { terminal -> terminal !is WhitespaceToken }
                .filter { terminal -> terminal !is CommentToken }
                .map(KeywordTokenRecognizer::get)
                .collect(Collectors.toList())
    }

}
