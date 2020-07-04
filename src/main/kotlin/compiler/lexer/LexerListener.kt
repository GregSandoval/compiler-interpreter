package compiler.lexer

import compiler.dfa.FinalStateListener
import compiler.dfa.NonFinalStateListener
import compiler.dfa.TransitionListener
import compiler.parser.andThen

class LexerListener(
        val onTransition: TransitionListener = { _, _, _ -> },
        val onFinalState: FinalStateListener = { _ -> },
        val onTokenCreated: TokenCreatedListener = { _, _ -> },
        val onNonFinalState: NonFinalStateListener = { _, _ -> }
) {

    fun onTransition(listener: TransitionListener): LexerListener {
        return LexerListener(
                this.onTransition.andThen(listener),
                this.onFinalState,
                this.onTokenCreated,
                this.onNonFinalState
        )
    }

    fun onFinalState(listener: FinalStateListener): LexerListener {
        return LexerListener(
                this.onTransition,
                this.onFinalState.andThen(listener),
                this.onTokenCreated,
                this.onNonFinalState
        )
    }

    fun onTokenCreated(listener: TokenCreatedListener): LexerListener {
        return LexerListener(
                this.onTransition,
                this.onFinalState,
                this.onTokenCreated.andThen(listener),
                this.onNonFinalState
        )
    }

    fun onNonFinalState(listener: NonFinalStateListener): LexerListener {
        return LexerListener(
                this.onTransition,
                this.onFinalState,
                this.onTokenCreated,
                this.onNonFinalState.andThen(listener)
        )
    }
}
