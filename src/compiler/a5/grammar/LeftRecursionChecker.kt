package compiler.a5.grammar

import compiler.parser.Symbol.NonTerminal
import compiler.parser.Symbol.Terminal
import kotlin.reflect.KClass

object LeftRecursionChecker {

    fun checkForLeftRecursion(productions: ProductionRules): Boolean {
        for ((lhs) in productions.getProductions()) {
            if (checkForLeftRecursion(lhs, productions)) {
                return true
            }
        }
        return false
    }

    fun checkForLeftRecursion(lhs: KClass<out NonTerminal>, productions: ProductionRules): Boolean {
        for (rhs in productions[lhs]) {
            loop@ for (symbolFn in rhs) {
                val symbol = symbolFn()

                if (symbol is Terminal)
                    break@loop

                val nonTerminal = (symbol as NonTerminal)::class

                if (nonTerminal === lhs)
                    return true

                if (!derivesEpsilon(nonTerminal, productions)) {
                    return true
                }

                continue@loop
            }
        }
        return true
    }

    fun derivesEpsilon(lhs: KClass<out NonTerminal>, productions: ProductionRules): Boolean {
        return false
    }
}

