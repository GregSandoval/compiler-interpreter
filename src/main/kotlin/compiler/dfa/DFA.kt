package compiler.dfa

import compiler.dfa.LexicalNode.ERROR
import compiler.utils.Predicate
import java.util.*
import kotlin.reflect.KClass

typealias StateConstructor = () -> LexicalNode
typealias EdgeFunction = (Char) -> Optional<StateConstructor>

typealias Transitions = MutableList<EdgeFunction>
typealias DFATable = MutableMap<KClass<out LexicalNode>, Transitions>

open class DFA(val start: StateConstructor) {
    private val dfa: DFATable = HashMap()

    operator fun get(start: LexicalNode, character: Char): LexicalNode {
        val transitions = this.dfa.getOrElse(start::class, ::mutableListOf)
        return transitions
                .stream()
                .map { it(character) }
                .filter { it.isPresent }
                .findFirst()
                .orElseGet { Optional.of(::ERROR) }
                .get()
                .invoke()
    }

    protected infix fun KClass<out LexicalNode>.to(end: StateConstructor): EdgeBuilderAlt {
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
