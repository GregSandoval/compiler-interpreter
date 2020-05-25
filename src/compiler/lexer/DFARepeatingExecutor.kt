package compiler.lexer

import compiler.a5.lexicon.DFA
import compiler.lexer.LexicalNode.FinalState
import compiler.lexer.LexicalNode.NonFinalState
import compiler.lexer.LexicalNode.NonFinalState.START
import compiler.utils.TextCursor

typealias FinalStateListener = (FinalState) -> Unit
typealias NonFinalStateListener = (TextCursor, NonFinalState) -> Unit

class DFARepeatingExecutor(
        private val dfa: DFA,
        private val transitionListeners: TransitionListener,
        private val finalStateListeners: FinalStateListener,
        private val nonFinalStateListener: NonFinalStateListener
) : DFAExecutor(dfa, transitionListeners) {

    override fun execute(input: TextCursor): LexicalNode {
        while (input.hasNext()) {
            when (val state = super.execute(input)) {
                is FinalState -> finalStateListeners(state)
                is NonFinalState -> nonFinalStateListener(input, state)
            }
        }
        return START
    }

}
