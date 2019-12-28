package compiler.a5.lexicon

import compiler.lexer.LexicalNode
import compiler.lexer.LexicalNode.NonFinalState.END_OF_TERMINAL
import compiler.lexer.LexicalNode.NonFinalState.FATAL_ERROR
import java.util.*
import java.util.function.Predicate

typealias EdgeFunction = (Char) -> Optional<LexicalNode>

typealias Transitions = MutableList<EdgeFunction>
typealias DFATable = MutableMap<LexicalNode, Transitions>

abstract class DFA(
        public val startState: LexicalNode,
        private val dfa: DFATable = HashMap()
) {


    operator fun get(start: LexicalNode, character: Char): LexicalNode {
        val transitions = this.dfa.getOrElse(start, ::mutableListOf)
        return transitions
                .stream()
                .map { it(character) }
                .filter { it.isPresent }
                .findFirst()
                .orElseGet { routeErrorToCustomStates(start) }
                .get()

    }

    private fun routeErrorToCustomStates(start: LexicalNode): Optional<LexicalNode> {
        return when (start) {
            is LexicalNode.FinalState -> Optional.of(END_OF_TERMINAL)
            else -> Optional.of(FATAL_ERROR)
        }
    }

    protected infix fun LexicalNode.to(end: LexicalNode): EdgeBuilderAlt {
        return object : EdgeBuilderAlt {
            override fun on(predicate: Predicate<Char>) {
                val edges = dfa.getOrPut(this@to, ::mutableListOf)
                edges += { if (predicate.test(it)) Optional.of(end) else Optional.empty() }
            }
        }
    }

    protected interface EdgeBuilderAlt {
        infix fun on(predicate: Predicate<Char>)
    }
}
