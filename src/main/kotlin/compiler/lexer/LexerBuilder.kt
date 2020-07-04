package compiler.lexer

import compiler.dfa.DFA
import compiler.dfa.FinalStateListener
import compiler.dfa.NonFinalStateListener
import compiler.dfa.TransitionListener
import compiler.parser.andThen

class LexerBuilder {
    private var onTransition: TransitionListener = { _, _, _ -> }
    private var onFinalState: FinalStateListener = { _ -> }
    private var onTokenCreated: TokenCreatedListener = { _, _ -> }
    private var onNonFinalState: NonFinalStateListener = { _, _ -> }

    fun onTransition(onTransition: TransitionListener): LexerBuilder {
        this.onTransition = this.onTransition.andThen(onTransition)
        return this
    }

    fun onTokenCreated(onTokenCreated: TokenCreatedListener): LexerBuilder {
        this.onTokenCreated = this.onTokenCreated.andThen(onTokenCreated)
        return this
    }

    fun onFinalState(onFinalState: FinalStateListener): LexerBuilder {
        this.onFinalState = this.onFinalState.andThen(onFinalState)
        return this
    }

    fun onUnknownTokenFound(onUnknownTokenFound: NonFinalStateListener): LexerBuilder {
        this.onNonFinalState = this.onNonFinalState.andThen(onUnknownTokenFound)
        return this
    }

    fun <T : DFA> setDFA(dfa: T): LexerBuilderReady {
        return LexerBuilderReady(dfa)
    }

    inner class LexerBuilderReady(private var dfa: DFA) {
        fun onTransition(onTransition: TransitionListener): LexerBuilderReady {
            this@LexerBuilder.onTransition(onTransition)
            return this
        }

        fun onTokenCreated(onTokenCreated: TokenCreatedListener): LexerBuilderReady {
            this@LexerBuilder.onTokenCreated(onTokenCreated)
            return this
        }

        fun onUnknownTokenFound(onUnknownTokenFound: NonFinalStateListener): LexerBuilderReady {
            this@LexerBuilder.onUnknownTokenFound(onUnknownTokenFound)
            return this
        }

        fun createLexer(): Lexer {
            return Lexer(dfa, onTransition, onFinalState, onNonFinalState, onTokenCreated)
        }
    }
}
