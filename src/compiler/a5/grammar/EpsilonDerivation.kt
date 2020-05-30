package compiler.a5.grammar

import compiler.a5.grammar.EpsilonDerivation.State.*
import compiler.parser.Symbol.NonTerminal
import compiler.parser.Symbol.Terminal

object EpsilonDerivation {
    enum class State {
        True,
        False,
        Processing,
    }

    fun epsilonDerivations(productions: ProductionRules): Map<NonTerminalClass, Boolean> {
        val derivations = HashMap<NonTerminalClass, State>()

        for ((lhs) in productions)
            derivesEpsilon(lhs, productions, derivations)

        val result = HashMap<NonTerminalClass, Boolean>()
        for ((nonTerminal, state) in derivations) {
            when (state) {
                True -> result[nonTerminal] = true
                False -> result[nonTerminal] = false
                Processing -> throw Exception("Terminal still processing: $nonTerminal")
            }
        }
        return result
    }

    fun derivesEpsilon(lhs: NonTerminalClass, productions: ProductionRules, derivations: MutableMap<NonTerminalClass, State>): Boolean {
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

                if (derivesEpsilon(nonTerminal::class, productions, derivations))
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
