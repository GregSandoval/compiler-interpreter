package compiler.lexer

import compiler.a5.lexicon.DFA
import compiler.lexer.token.KeywordTokenRecognizer
import compiler.lexer.token.Token
import compiler.lexer.token.Token.IgnorableTokens
import compiler.utils.TextCursor
import java.util.*
import java.util.function.BiConsumer
import java.util.stream.Collectors

typealias TokenCreatedListener = BiConsumer<Token, TextCursor>

class Lexer(
        private var dfa: DFA,
        private var transitionListeners: TransitionListener,
        private var finalStateListeners: FinalStateListener,
        private var nonFinalStateListeners: NonFinalStateListener,
        private var tokenCreatedListeners: TokenCreatedListener
) {

    fun lex(text: String): List<Token> {
        val tokens = ArrayList<Token>()
        val cursor = TextCursor(text)

        finalStateListeners = finalStateListeners.andThen {
            val token = it.getToken(cursor)
            tokens.add(token)
            tokenCreatedListeners.accept(token, cursor)
        }

        val executor = DFARepeatingExecutor(
                dfa,
                transitionListeners,
                finalStateListeners,
                nonFinalStateListeners
        )

        executor.execute(cursor)

        return tokens
                .stream()
                .filter { terminal -> terminal !is IgnorableTokens }
                .map(KeywordTokenRecognizer::get)
                .collect(Collectors.toList())
    }
}
