package compiler.a5.parser

import compiler.a5.grammar.NonTerminalClass
import compiler.a5.grammar.ProductionRules

object FollowSet {
    fun findAll(
            productionsByLHS: ProductionRules,
            derivesEpsilon: Set<NonTerminalClass>,
            first: Map<NonTerminalClass, Set<TerminalClass>>
    ): Map<NonTerminalClass, Set<TerminalClass>> {
        val result = HashMap<NonTerminalClass, MutableSet<TerminalClass>>()
        val visited = HashSet<NonTerminalClass>()

        for ((lhs) in productionsByLHS) {
            val lhsFollow = findAll(
                    lhs,
                    productionsByLHS,
                    first,
                    derivesEpsilon,
                    result,
                    visited
            )
            result[lhs] = lhsFollow
        }

        return result
    }

    private fun findAll(
            lhs: NonTerminalClass,
            productions: ProductionRules,
            first: Map<NonTerminalClass, Set<TerminalClass>>,
            derivesEpsilon: Set<NonTerminalClass>,
            follows: HashMap<NonTerminalClass, MutableSet<TerminalClass>>,
            visited: MutableSet<NonTerminalClass>

    ): MutableSet<TerminalClass> {
        var follow = follows[lhs]

        if (follow === null) {
            follow = HashSet()
            follows[lhs] = follow
        }

        if (lhs in visited) {
            return follow
        }

        visited += lhs
        for (reverseLHS in productions.getLHS(lhs)) {
            for (reverseRHS in productions[reverseLHS]) {
                val nonTerminalSubSequence = reverseRHS
                        .asSequence()
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
                            follows,
                            visited
                    ))
                }
            }
        }
        return follow
    }

}
