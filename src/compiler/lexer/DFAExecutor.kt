package compiler.lexer

import compiler.a5.lexicon.DFA
import compiler.lexer.LexicalNode.ERROR
import compiler.utils.TextCursor
import compiler.utils.TriConsumer

typealias TransitionListener = TriConsumer<LexicalNode, Char, LexicalNode>

open class DFAExecutor(private val dfa: DFA, private val listeners: TransitionListener) {

    open fun execute(input: TextCursor): LexicalNode {
        var current = dfa.start
        while (input.hasNext()) {
            val next = current on input.peek()

            if (next == ERROR)
                break

            listeners.accept(current, input.next(), next)
            current = next
        }
        return current
    }

    private infix fun LexicalNode.on(character: Char): LexicalNode {
        return this@DFAExecutor.dfa[this, character]
    }
}