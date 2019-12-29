package compiler.lexer

import compiler.a5.lexicon.DFA

class LexerBuilder {
    private var onTransition: TransitionListener = object : TransitionListener {
        override fun accept(p1: LexicalNode, p2: Char, p3: LexicalNode) {
        }
    }
    private var onFinalState: FinalStateListener = FinalStateListener { }
    private var onTokenCreated: TokenCreatedListener = TokenCreatedListener { _, _ -> }
    private var onNonFinalState = NonFinalStateListener {_, _ -> }

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
