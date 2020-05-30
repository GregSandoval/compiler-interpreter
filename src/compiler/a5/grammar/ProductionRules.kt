package compiler.a5.grammar

import compiler.parser.NodeSupplier
import compiler.parser.Symbol.NonTerminal
import kotlin.collections.MutableMap.MutableEntry
import kotlin.reflect.KClass

typealias NonTerminalClass = KClass<out NonTerminal>
typealias ProductionRHS = List<NodeSupplier>
typealias ProductionRHSs = MutableList<ProductionRHS>
typealias Productions = MutableMap<NonTerminalClass, ProductionRHSs>

class ProductionRules : Iterable<MutableEntry<NonTerminalClass, ProductionRHSs>> {
    private val productions: Productions = HashMap()

    operator fun <NT : NonTerminal> set(nonTerminal: KClass<NT>, rhs: List<NodeSupplier>) {
        var nonTerminalProductions = this.productions[nonTerminal]

        if (nonTerminalProductions == null) {
            nonTerminalProductions = ArrayList()
            this.productions[nonTerminal] = nonTerminalProductions
        }

        nonTerminalProductions.add(rhs)
    }

    operator fun <NT : NonTerminal> get(nonTerminal: KClass<NT>): ProductionRHSs {
        var rhs = this.productions[nonTerminal]

        if (rhs == null) {
            rhs = mutableListOf()
            this.productions[nonTerminal] = rhs
        }

        return rhs
    }

    override fun iterator(): Iterator<MutableEntry<NonTerminalClass, ProductionRHSs>> {
        return productions.iterator()
    }

}
