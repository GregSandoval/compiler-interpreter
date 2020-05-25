package compiler.a5.lexicon

import compiler.lexer.LexicalNode
import compiler.lexer.LexicalNode.ERROR
import java.util.*

typealias EdgeFunction = (Char) -> Optional<LexicalNode>

typealias Transitions = MutableList<EdgeFunction>
typealias DFATable = MutableMap<LexicalNode, Transitions>

abstract class DFA(
        val start: LexicalNode,
        private val dfa: DFATable = HashMap()
) {


    operator fun get(start: LexicalNode, character: Char): LexicalNode {
        val transitions = this.dfa.getOrElse(start, ::mutableListOf)
        return transitions
                .stream()
                .map { it(character) }
                .filter { it.isPresent }
                .findFirst()
                .orElseGet { Optional.of(ERROR) }
                .get()
    }

    protected infix fun LexicalNode.to(end: LexicalNode): EdgeBuilderAlt {
        return object : EdgeBuilderAlt {
            override fun on(predicate: Predicate<Char>) {
                val edges = dfa.getOrPut(this@to, ::mutableListOf)
                edges += { if (predicate(it)) Optional.of(end) else Optional.empty() }
            }
        }
    }

    protected interface EdgeBuilderAlt {
        infix fun on(predicate: Predicate<Char>)
    }
}
