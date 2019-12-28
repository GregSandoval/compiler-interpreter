package compiler.lexer

import compiler.lexer.token.Token
import compiler.utils.TextCursor
import compiler.utils.TriConsumer
import java.util.function.BiConsumer

class LexerBuilder {
    private var onTransition: TriConsumer<LexicalNode, Char, LexicalNode> = object : TriConsumer<LexicalNode, Char, LexicalNode> {
        override fun accept(p1: LexicalNode, p2: Char, p3: LexicalNode) {
        }
    }
    private var onTokenCreated: TriConsumer<LexicalNode, LexicalNode, Token> = object : TriConsumer<LexicalNode, LexicalNode, Token> {
        override fun accept(p1: LexicalNode, p2: LexicalNode, p3: Token) {
        }
    }
    private var onUnknownTokenFound = BiConsumer<String, TextCursor> { _, _ -> }
    private var startState: LexicalNode? = null

    fun onTransition(onTransition: TriConsumer<LexicalNode, Char, LexicalNode>): LexerBuilder {
        this.onTransition = this.onTransition.andThen(onTransition)
        return this
    }

    fun onTokenCreated(onTokenCreated: TriConsumer<LexicalNode, LexicalNode, Token>): LexerBuilder {
        this.onTokenCreated = this.onTokenCreated.andThen(onTokenCreated)
        return this
    }

    fun onUnknownTokenFound(onUnknownTokenFound: BiConsumer<String, TextCursor>): LexerBuilder {
        this.onUnknownTokenFound = this.onUnknownTokenFound.andThen(onUnknownTokenFound)
        return this
    }

    fun setStartState(startState: LexicalNode): LexerBuilderReady {
        this.startState = startState
        return LexerBuilderReady()
    }

    inner class LexerBuilderReady {
        fun onTransition(onTransition: TriConsumer<LexicalNode, Char, LexicalNode>): LexerBuilderReady {
            this@LexerBuilder.onTransition(onTransition)
            return this
        }

        fun onTokenCreated(onTokenCreated: TriConsumer<LexicalNode, LexicalNode, Token>): LexerBuilderReady {
            this@LexerBuilder.onTokenCreated(onTokenCreated)
            return this
        }

        fun onUnknownTokenFound(onUnknownTokenFound: BiConsumer<String, TextCursor>): LexerBuilderReady {
            this@LexerBuilder.onUnknownTokenFound(onUnknownTokenFound)
            return this
        }

        fun createLexer(): Lexer {
            return Lexer(startState!!, onTransition, onTokenCreated, onUnknownTokenFound)
        }
    }
}
