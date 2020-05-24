package compiler.lexer

import compiler.a5.lexicon.DFA
import compiler.lexer.token.KeywordTokenRecognizer
import compiler.parser.Symbols.Terminal
import compiler.parser.Symbols.Terminal.Ignorable
import compiler.utils.TextCursor
import java.util.*
import java.util.function.BiConsumer
import java.util.stream.Collectors

typealias TokenCreatedListener = BiConsumer<Terminal, TextCursor>

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
            tokenCreatedListeners.accept(token, cursor)
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
