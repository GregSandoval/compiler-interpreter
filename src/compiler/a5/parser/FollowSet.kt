package compiler.a5.parser

import compiler.a5.grammar.NonTerminalClass
import compiler.a5.grammar.ProductionRules

object FollowSet {
    fun findAll(
            productionsByLHS: ProductionRules,
            derivesEpsilon: Set<NonTerminalClass>,
            first: Map<NonTerminalClass, Set<TerminalClass>>
    ): Map<NonTerminalClass, Set<TerminalClass>> {
        val followSets = HashMap<NonTerminalClass, MutableSet<TerminalClass>>()
        val visited = HashSet<NonTerminalClass>()
        val lhss = HashSet<NonTerminalClass>()

        for((lhs) in productionsByLHS.getProductions())
            lhss.add(lhs)

        for (lhs in lhss) {
            val lhsFollow = findAll(
                    lhs,
                    productionsByLHS,
                    first,
                    derivesEpsilon,
                    followSets,
                    visited
            )
            followSets[lhs] = lhsFollow
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

    private fun findAll(
            lhs: NonTerminalClass,
            productions: ProductionRules,
            first: Map<NonTerminalClass, Set<TerminalClass>>,
            derivesEpsilon: Set<NonTerminalClass>,
            followSets: HashMap<NonTerminalClass, MutableSet<TerminalClass>>,
            visited: MutableSet<NonTerminalClass>

    ): MutableSet<TerminalClass> {
        var follow = followSets[lhs]

        if (follow === null) {
            follow = HashSet()
            followSets[lhs] = follow
        }

        if (lhs in visited) {
            return HashSet(follow)
        }

        visited += lhs
        for (reverseLHS in productions.getLHS(lhs)) {
            for (reverseRHS in productions[reverseLHS]) {
                if (reverseRHS.none { it()::class === lhs })
                    continue

                val nonTerminalSubSequence = reverseRHS
                        .dropWhile { it()::class !== lhs }
                        .drop(1)

                follow.addAll(FirstSet.first(
                        nonTerminalSubSequence,
                        derivesEpsilon,
                        first
                ))

                if (nonTerminalSubSequence.all { it()::class in derivesEpsilon }) {
                    follow.addAll(findAll(
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
