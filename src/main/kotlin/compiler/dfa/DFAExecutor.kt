package compiler.dfa

import compiler.dfa.LexicalNode.ERROR
import compiler.lexer.LexerListener
import compiler.utils.TextCursor

typealias TransitionListener = (LexicalNode, Char, LexicalNode) -> Unit

open class DFAExecutor(private val dfa: DFA, private val listener: LexerListener) {
    fun execute(input: TextCursor): LexicalNode {
        var current = dfa.start
        while (input.hasNext()) {
            val next = current on input.peek()

            if (next is ERROR)
                break

            listener.onTransition(current, input.next(), next)
            current = next
        }
        return current
    }

    private infix fun LexicalNode.on(character: Char): LexicalNode {
        return this@DFAExecutor.dfa[this, character]
    }
}
