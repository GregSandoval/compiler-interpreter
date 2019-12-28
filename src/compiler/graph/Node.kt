package compiler.graph

import compiler.lexer.LexicalNode
import compiler.lexer.LexicalNode.NonFinalState.END_OF_TERMINAL
import compiler.lexer.LexicalNode.NonFinalState.FATAL_ERROR
import java.util.*
import java.util.function.Function
import java.util.function.Predicate
import java.util.function.Supplier

/**
 * Represents a node within the graph.
 * Uses 'curiously recurring template pattern' to return
 * subclass instances from the base class methods.
 *
 *
 * This node holds all outgoing edges. Each edge has a corresponding
 * predicate function, which determines if an edge should be 'walked'
 *
 * @param <T> A subclass of node
</T> */
abstract class Node<T : Node<T>> {
    private val onError = Supplier { routeErrorToCustomStates(me()) }
    private val transitions: ArrayList<Function<Char, Optional<T>>> = ArrayList()

    fun ON(predicate: Predicate<Char>): EdgeBuilder {
        return EdgeBuilder(predicate)
    }

    fun ON(character: Char): T {
        return transitions
                .stream()
                .map { it.apply(character) }
                .filter { it.isPresent }
                .findFirst()
                .orElseGet { routeErrorToCustomStates(me()) }
                .get()
    }

    private fun routeErrorToCustomStates(from: T): Optional<T> {
        return when (from) {
            is LexicalNode.FinalState -> Optional.of(END_OF_TERMINAL as T)
            else -> Optional.of(FATAL_ERROR as T)
        }
    }

    override fun toString(): String {
        return this.javaClass.simpleName
    }

    protected abstract fun me(): T
    inner class EdgeBuilder(private var predicates: Predicate<Char>) {
        fun AND(predicate: Predicate<Char>): EdgeBuilder {
            predicates = predicates.and(predicate)
            return this
        }

        fun OR(predicate: Predicate<Char>): EdgeBuilder {
            predicates = predicates.or(predicate)
            return this
        }

        fun GOTO(end: T) {
            transitions += Function { if (predicates.test(it)) Optional.of(end) else Optional.empty() }
        }

    }

}
