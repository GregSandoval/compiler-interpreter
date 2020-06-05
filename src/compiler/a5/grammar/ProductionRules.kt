package compiler.a5.grammar

import compiler.parser.NodeSupplier
import compiler.parser.Symbol
import compiler.parser.Symbol.NonTerminal
import kotlin.collections.MutableMap.MutableEntry
import kotlin.reflect.KClass

typealias NonTerminalClass = KClass<out NonTerminal>
typealias ProductionRHS = List<NodeSupplier>
typealias ProductionRHSs = MutableList<ProductionRHS>
typealias Productions = MutableMap<NonTerminalClass, ProductionRHSs>

class ProductionRules : Iterable<MutableEntry<NonTerminalClass, ProductionRHSs>> {
    private val productions: Productions = HashMap()
    private val reverseProductions: MutableMap<KClass<out NonTerminal>, MutableSet<KClass<out NonTerminal>>> = HashMap()

    operator fun <NT : NonTerminal> set(lhs: KClass<NT>, rhs: List<NodeSupplier>) {
        var nonTerminalProductions = this.productions[lhs]

        if (nonTerminalProductions == null) {
            nonTerminalProductions = ArrayList()
            this.productions[lhs] = nonTerminalProductions
        }

        for (symbolFn in rhs) {
            val symbol = symbolFn()

            if (symbol is Symbol.Terminal) {
                continue
            }

            val nonTerminal = symbol as NonTerminal
            var reverseLLTable = reverseProductions[nonTerminal::class]

            if (reverseLLTable === null) {
                reverseLLTable = HashSet()
                reverseProductions[nonTerminal::class] = reverseLLTable
            }

            reverseLLTable.add(lhs)
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

    fun getLHS(nonTerminal: NonTerminalClass): Set<NonTerminalClass> {
        var lhsSet = reverseProductions[nonTerminal]

        if (lhsSet === null) {
            lhsSet = HashSet()
            reverseProductions[nonTerminal] = lhsSet
        }

        return lhsSet
    }

    override fun iterator(): Iterator<MutableEntry<NonTerminalClass, ProductionRHSs>> {
        return productions.iterator()
    }

}
