package compiler.a5.parser

import compiler.a5.grammar.NonTerminalClass
import compiler.a5.grammar.ProductionRules
import compiler.parser.NodeSupplier
import compiler.parser.Symbol.NonTerminal
import compiler.parser.Symbol.Terminal
import kotlin.reflect.KClass

typealias TerminalClass = KClass<out Terminal>

object FirstSet {

    fun findAll(productions: ProductionRules, derivesEpsilon: Set<NonTerminalClass>): Map<NonTerminalClass, Set<TerminalClass>> {
        val visited = HashSet<NonTerminalClass>()
        val result = HashMap<NonTerminalClass, MutableSet<TerminalClass>>()

        for ((lhs) in productions) {
            findAll(lhs, productions, derivesEpsilon, result, visited)
        }


        println("=========== First of Non Terminals =============")
        println(result
                .map { "${it.key.simpleName}=[${it.value.map { it.simpleName ?: "No Class Name" }.sorted().joinToString(", ")}]" }
                .sorted()
                .joinToString("\n"))
        println("================================================")

        return result
    }

    fun first(
            productionSequence: Sequence<NodeSupplier>,
            derivesEpsilon: Set<NonTerminalClass>,
            first: Map<NonTerminalClass, Set<TerminalClass>>
    ): Set<TerminalClass> {
        val subsequenceFirst = HashSet<TerminalClass>()

        if (productionSequence.none()) {
            return subsequenceFirst
        }

        for (symbolFn in productionSequence) {
            val symbol = symbolFn()

            if (symbol is Terminal) {
                subsequenceFirst.add((symbol as Terminal)::class)
                break
            }

            val nonTerminal = symbol as NonTerminal

            val nonTerminalFirst = first[nonTerminal::class]

            if (nonTerminalFirst !== null)
                subsequenceFirst.addAll(nonTerminalFirst)

            if (nonTerminal::class !in derivesEpsilon) {
                break
            }
        }
        return subsequenceFirst
    }

    fun findAll(
            lhs: NonTerminalClass,
            productions: ProductionRules,
            derivesEpsilon: Set<NonTerminalClass>,
            first: MutableMap<NonTerminalClass, MutableSet<TerminalClass>>,
            visited: MutableSet<NonTerminalClass>
    ): Set<TerminalClass> {
        var firstOfLHS = first[lhs]

        if (firstOfLHS === null) {
            firstOfLHS = HashSet()
            first[lhs] = firstOfLHS
        }

        if (lhs in visited)
            return firstOfLHS

        visited.add(lhs)
        nextProduction@ for (rhs in productions[lhs]) {
            nextRHSSymbol@ for (symbolFn in rhs) {
                val symbol = symbolFn()

                if (symbol is Terminal) {
                    firstOfLHS.add((symbol as Terminal)::class)
                    continue@nextProduction
                }

                val nonTerminal = symbol as NonTerminal

                firstOfLHS.addAll(findAll(nonTerminal::class, productions, derivesEpsilon, first, visited))

                if (nonTerminal::class in derivesEpsilon) {
                    continue@nextRHSSymbol
                }

                continue@nextProduction
            }
        }

        return firstOfLHS
    }


}
