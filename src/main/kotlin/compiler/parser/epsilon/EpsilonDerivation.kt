package compiler.parser.epsilon

import compiler.parser.NonTerminalClass
import compiler.parser.ProductionRules
import compiler.parser.Symbol.NonTerminal
import compiler.parser.Symbol.Terminal
import compiler.parser.epsilon.EpsilonDerivation.State.*

object EpsilonDerivation {
    enum class State {
        True,
        False,
        Processing,
    }

    fun findAll(productions: ProductionRules): Set<NonTerminalClass> {
        val derivations = HashMap<NonTerminalClass, State>()

        for ((lhs) in productions.getProductions())
            findAll(lhs, productions, derivations)

        val result = HashSet<NonTerminalClass>()
        loop@ for ((nonTerminal, state) in derivations) {
            when (state) {
                True -> result += nonTerminal
                False -> continue@loop
                Processing -> throw Exception("Terminal still processing: $nonTerminal")
            }
        }

        println("=========== Non Terminals deriving epsilon =============")
        println(result
                .map { it.simpleName }
                .sortedWith(compareBy { it })
                .joinToString("\n"))
        println("========================================================")
        return result
    }

    fun findAll(lhs: NonTerminalClass, productions: ProductionRules, derivations: MutableMap<NonTerminalClass, State>): Boolean {
        val derivedEpsilon = derivations[lhs]

        when (derivedEpsilon) {
            True -> return true
            False -> return false
            Processing -> return false
        }

        derivations[lhs] = Processing
        var derivesEpsilon = false
        nextRHS@ for (rhs in productions[lhs]) {
            nextSymbolInRHS@ for (symbolFn in rhs) {
                val symbol = symbolFn()

                if (symbol is Terminal)
                    continue@nextRHS

                val nonTerminal = symbol as NonTerminal

                if (findAll(nonTerminal::class, productions, derivations))
                    continue@nextSymbolInRHS

                continue@nextRHS
            }
            derivesEpsilon = true
            break@nextRHS
        }


        derivations[lhs] = when (derivesEpsilon) {
            true -> True
            false -> False
        }

        return derivesEpsilon
    }
}
