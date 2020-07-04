package compiler.lexer

import compiler.dfa.DFA
import compiler.dfa.DFARepeatingExecutor
import compiler.lexer.token.KeywordTokenRecognizer
import compiler.parser.Symbol.Terminal
import compiler.parser.Symbol.Terminal.Ignorable
import compiler.utils.TextCursor
import java.util.*
import java.util.stream.Collectors

typealias TokenCreatedListener = (Terminal, TextCursor) -> Unit

class Lexer(private var dfa: DFA, private val listener: LexerListener) {
    fun lex(text: String): List<Terminal> {
        val tokens = ArrayList<Terminal>()
        val cursor = TextCursor(text)

        val listener = this.listener.onFinalState {
            val token = it.getToken(cursor)
            tokens.add(token)
            this.listener.onTokenCreated(token, cursor)
        }

        val executor = DFARepeatingExecutor(dfa, listener)
        executor.execute(cursor)

        return tokens
                .stream()
                .filter { terminal -> terminal !is Ignorable }
                .map(KeywordTokenRecognizer::get)
                .collect(Collectors.toList())
    }
}
