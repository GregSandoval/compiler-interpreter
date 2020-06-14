package compiler.parser

import compiler.parser.Symbol.NonTerminal
import compiler.parser.Symbol.Terminal
import kotlin.reflect.KClass

typealias NonTerminalClass = KClass<out NonTerminal>
typealias ProductionRHS = List<NodeSupplier>
typealias ProductionRHSs = MutableList<ProductionRHS>
typealias Productions = MutableMap<NonTerminalClass, ProductionRHSs>

class ProductionRules {
    private val productions: Productions = HashMap()
    private val reverseProductions: MutableMap<NonTerminalClass, MutableSet<NonTerminalClass>> = HashMap()

    operator fun <NT : NonTerminal> set(lhs: KClass<NT>, rhs: List<NodeSupplier>) {
        var nonTerminalProductions = this.productions[lhs]

        if (nonTerminalProductions === null) {
            nonTerminalProductions = ArrayList()
            this.productions[lhs] = nonTerminalProductions
        }

        nonTerminalProductions.add(rhs)
        for (symbolFn in rhs) {
            val symbol = symbolFn()

            if (symbol is Terminal) {
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
    }

    operator fun <NT : NonTerminal> get(nonTerminal: KClass<NT>): ProductionRHSs {
        var rhs = this.productions[nonTerminal]

        if (rhs === null) {
            rhs = ArrayList()
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

    fun getProductions(): Productions {
        return productions
    }

}
