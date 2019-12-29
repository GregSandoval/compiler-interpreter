package compiler.lexer

import compiler.a5.lexicon.DFA
import compiler.lexer.LexicalNode.FinalState
import compiler.lexer.LexicalNode.NonFinalState
import compiler.lexer.token.KeywordTokenRecognizer
import compiler.lexer.token.Token
import compiler.lexer.token.Token.IgnorableTokens
import compiler.utils.TextCursor
import compiler.utils.TriConsumer
import java.util.*
import java.util.function.BiConsumer
import java.util.stream.Collectors

class Lexer(
        private val dfa: DFA,
        private var transitionListeners: TriConsumer<LexicalNode, Char, LexicalNode>,
        private val tokenCreatedListeners: BiConsumer<LexicalNode, Token>,
        private val unknownTokenListener: BiConsumer<String, TextCursor>
) {

    fun lex(text: String): List<Token> {
        val tokens = ArrayList<Token>()
        val cursor = TextCursor(text)
        val executor = DFAExecutor(dfa, transitionListeners)
        loop@ while (cursor.hasNext()) {
            when (val state = executor.execute(cursor)) {
                is FinalState -> {
                    val token = state.getToken(cursor)
                    tokens.add(token)
                    tokenCreatedListeners.accept(state, token)
                }
                is NonFinalState -> {
                    unknownTokenListener.accept(cursor.getCurrentSentence(), cursor)
                    break@loop
                }
            }
        }

        return tokens
                .stream()
                .filter { terminal -> terminal !is IgnorableTokens }
                .map(KeywordTokenRecognizer::get)
                .collect(Collectors.toList())
    }
}
