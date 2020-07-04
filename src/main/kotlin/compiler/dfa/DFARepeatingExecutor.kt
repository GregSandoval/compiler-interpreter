package compiler.dfa

import compiler.dfa.LexicalNode.FinalState
import compiler.dfa.LexicalNode.NonFinalState
import compiler.dfa.LexicalNode.NonFinalState.START
import compiler.lexer.LexerListener
import compiler.utils.TextCursor

typealias FinalStateListener = (FinalState) -> Unit
typealias NonFinalStateListener = (TextCursor, NonFinalState) -> Unit

class DFARepeatingExecutor(dfa: DFA, private val listener: LexerListener) {
    private val executor = DFAExecutor(dfa, listener)

    fun execute(input: TextCursor): LexicalNode {
        while (input.hasNext()) {
            when (val state = executor.execute(input)) {
                is FinalState -> listener.onFinalState(state)
                is NonFinalState -> listener.onNonFinalState(input, state)
            }
        }
        return START
    }

}
