package compiler.lexer

import compiler.a5.lexicon.DFA
import compiler.lexer.token.KeywordTokenRecognizer
import compiler.parser.Symbol.Terminal
import compiler.parser.Symbol.Terminal.Ignorable
import compiler.parser.andThen
import compiler.utils.TextCursor
import java.util.*
import java.util.stream.Collectors

typealias TokenCreatedListener = (Terminal, TextCursor) -> Unit

class Lexer(
        private var dfa: DFA,
        private var transitionListeners: TransitionListener,
        private var finalStateListeners: FinalStateListener,
        private var nonFinalStateListeners: NonFinalStateListener,
        private var tokenCreatedListeners: TokenCreatedListener
) {

    fun lex(text: String): List<Terminal> {
        val tokens = ArrayList<Terminal>()
        val cursor = TextCursor(text)

        finalStateListeners = finalStateListeners.andThen {
            val token = it.getToken(cursor)
            tokens.add(token)
            tokenCreatedListeners(token, cursor)
        }

        DFARepeatingExecutor(
                dfa,
                transitionListeners,
                finalStateListeners,
                nonFinalStateListeners
        ).execute(cursor)

        return tokens
                .stream()
                .filter { terminal -> terminal !is Ignorable }
                .map(KeywordTokenRecognizer::get)
                .collect(Collectors.toList())
    }
}
