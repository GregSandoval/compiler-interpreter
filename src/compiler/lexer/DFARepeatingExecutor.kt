package compiler.lexer

import compiler.a5.lexicon.DFA
import compiler.lexer.LexicalNode.FinalState
import compiler.lexer.LexicalNode.NonFinalState
import compiler.lexer.LexicalNode.NonFinalState.START
import compiler.utils.TextCursor
import java.util.function.BiConsumer
import java.util.function.Consumer

typealias FinalStateListener = Consumer<FinalState>
typealias NonFinalStateListener = BiConsumer<TextCursor, NonFinalState>

class DFARepeatingExecutor(
        private val dfa: DFA,
        private val transitionListeners: TransitionListener,
        private val finalStateListeners: FinalStateListener,
        private val nonFinalStateListener: NonFinalStateListener
) : DFAExecutor(dfa, transitionListeners) {
    override fun execute(input: TextCursor): LexicalNode {
        while (input.hasNext()) {
            when (val state = super.execute(input)) {
                is FinalState -> finalStateListeners.accept(state)
                is NonFinalState -> nonFinalStateListener.accept(input, state)
            }
        }
        return START
    }
}
