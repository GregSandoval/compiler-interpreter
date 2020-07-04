package compiler.lexer

import compiler.dfa.DFA
import compiler.dfa.FinalStateListener
import compiler.dfa.NonFinalStateListener
import compiler.dfa.TransitionListener

class LexerBuilder {
    private var listener = LexerListener()

    fun onTransition(onTransition: TransitionListener): LexerBuilder {
        listener = this.listener.onTransition(onTransition)
        return this
    }

    fun onTokenCreated(onTokenCreated: TokenCreatedListener): LexerBuilder {
        listener = this.listener.onTokenCreated(onTokenCreated)
        return this
    }

    fun onFinalState(onFinalState: FinalStateListener): LexerBuilder {
        listener = this.listener.onFinalState(onFinalState)
        return this
    }

    fun onUnknownTokenFound(onUnknownTokenFound: NonFinalStateListener): LexerBuilder {
        listener = this.listener.onNonFinalState(onUnknownTokenFound)
        return this
    }

    fun createLexer(dfa: DFA): Lexer {
        return Lexer(dfa, listener)
    }
}
