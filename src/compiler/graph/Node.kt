package compiler.graph

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
abstract class Node<T : Node<T>>(private val name: String, onError: Function<T, Optional<T>>) {
    private val onError: Supplier<Optional<T>> = Supplier { onError.apply(me()) }
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
                .orElseGet(onError)
                .orElseThrow { NoEdgeFound(this, character) }
    }

    override fun toString(): String {
        return name
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
