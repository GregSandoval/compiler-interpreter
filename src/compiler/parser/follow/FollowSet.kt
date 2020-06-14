package compiler.parser.follow

import compiler.parser.NonTerminalClass
import compiler.parser.ProductionRules
import compiler.parser.Symbol.NonTerminal.ExpressionList
import compiler.parser.Symbol.NonTerminal.MoreExpressions
import compiler.parser.first.FirstSet
import compiler.parser.first.TerminalClass
import compiler.parser.follow.VisitedState.*

enum class VisitedState {
    Processing,
    Visited,
    Unvisited,
}

object FollowSet {
    fun findFollowSet(
            productionsByLHS: ProductionRules,
            derivesEpsilon: Set<NonTerminalClass>,
            first: Map<NonTerminalClass, Set<TerminalClass>>
    ): Map<NonTerminalClass, Set<TerminalClass>> {
        val followSets = HashMap<NonTerminalClass, MutableSet<TerminalClass>>()
        val visited = HashMap<NonTerminalClass, VisitedState>()
        val lhss = HashSet<NonTerminalClass>()

        for ((lhs) in productionsByLHS.getProductions())
            lhss.add(lhs)

        for (lhs in lhss) {

            if (visited[lhs] === Processing)
                visited[lhs] = Unvisited

            followSets[lhs] = findFollowSetOfLHS(
                    lhs,
                    productionsByLHS,
                    first,
                    derivesEpsilon,
                    followSets,
                    visited
            )

            visited[lhs] = Visited
        }

        println("================ Follow Set =====================")
        println(followSets
                .map { "${it.key.simpleName}=${it.value.map { clazz -> clazz.simpleName ?: "No Class Name" }.sorted()}" }
                .sorted()
                .joinToString("\n")
        )
        println("=================================================")
        return followSets
    }

    private fun findFollowSetOfLHS(
            lhs: NonTerminalClass,
            productions: ProductionRules,
            first: Map<NonTerminalClass, Set<TerminalClass>>,
            derivesEpsilon: Set<NonTerminalClass>,
            followSets: HashMap<NonTerminalClass, MutableSet<TerminalClass>>,
            visited: HashMap<NonTerminalClass, VisitedState>
    ): MutableSet<TerminalClass> {
        var follow = followSets[lhs]

        if (follow === null) {
            follow = HashSet()
            followSets[lhs] = follow
        }

        when (visited[lhs]) {
            Processing -> return follow
            Visited -> return follow
            else -> visited[lhs] = Processing
        }

        if (lhs === MoreExpressions::class) {
            println(MoreExpressions::class.simpleName)
            for (reverseLHS in productions.getLHS(lhs)) {
                for (reverseRHS in productions[reverseLHS]) {
                    if (reverseRHS.none { it()::class === lhs })
                        continue
                    val nonTerminalSubSequence = reverseRHS
                            .dropWhile { it()::class !== lhs }
                            .drop(1)

                    println("${reverseLHS.simpleName}->${nonTerminalSubSequence.map { it()::class.simpleName ?: "SimpleName" }}")
                }
            }
            println()
        }

        if (lhs === ExpressionList::class) {
            println(ExpressionList::class.simpleName)
            for (reverseLHS in productions.getLHS(lhs)) {
                for (reverseRHS in productions[reverseLHS]) {
                    if (reverseRHS.none { it()::class === lhs })
                        continue

                    val nonTerminalSubSequence = reverseRHS
                            .dropWhile { it()::class !== lhs }
                            .drop(1)

                    println("${reverseLHS.simpleName}->${nonTerminalSubSequence.map { it()::class.simpleName ?: "SimpleName" }}")
                }
            }
            println()
        }

        for (reverseLHS in productions.getLHS(lhs)) {
            for (reverseRHS in productions[reverseLHS]) {
                if (reverseRHS.none { it()::class === lhs })
                    continue

                val nonTerminalSubSequence = reverseRHS
                        .dropWhile { it()::class !== lhs }
                        .drop(1)

                val f = FirstSet.first(
                        nonTerminalSubSequence,
                        derivesEpsilon,
                        first
                )

                if (lhs === ExpressionList::class || lhs === MoreExpressions::class) {
                    println("First: " + lhs.simpleName)
                    println(f.map { it.simpleName ?: "Simple" }.sorted())
                    println()
                }
                follow.addAll(f)

                if (nonTerminalSubSequence.all { it()::class in derivesEpsilon }) {
                    follow.addAll(findFollowSetOfLHS(
                            reverseLHS,
                            productions,
                            first,
                            derivesEpsilon,
                            followSets,
                            visited
                    ))
                }
            }
        }
        return HashSet(follow)
    }

}
