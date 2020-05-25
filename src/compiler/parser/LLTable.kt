package compiler.parser

import java.util.*
import java.util.stream.Collectors
import kotlin.collections.HashMap
import kotlin.collections.set
import kotlin.reflect.KClass

class LLTable {
    private val LLTable: MutableMap<KClass<out Symbol>, MutableMap<TokenClass, List<NodeSupplier>>> = HashMap()

    inner class RuleBuilderSecondStep constructor(private val nonTerminal: Symbol.NonTerminal, private vararg val tokenClass: TokenClass) {
        fun useRHS(vararg rest: NodeSupplier): RuleBuilderFirstStep {
            val rhs = ArrayList(listOf(*rest))
            for (token in tokenClass) {
                var table = LLTable[nonTerminal::class]

                if (table === null) {
                    table = HashMap()
                    LLTable[nonTerminal::class] = table
                }

                if (table.containsKey(token)) {
                    throw RuntimeException("Double stuffed LL Table: Rule: " + nonTerminal.javaClass.simpleName + " Token: " + token.simpleName)
                }

                table[token] = rhs
            }
            return RuleBuilderFirstStep(nonTerminal)
        }

    }

    inner class RuleBuilderFirstStep(private val nonTerminal: Symbol.NonTerminal) {
        fun on(vararg first: TokenClass): RuleBuilderSecondStep {
            return RuleBuilderSecondStep(nonTerminal, *first)
        }
    }

    fun on(nonTerminal: Symbol.NonTerminal, vararg first: TokenClass): RuleBuilderSecondStep {
        return RuleBuilderSecondStep(nonTerminal, *first)
    }

    fun getRHS(nonTerminal: Symbol.NonTerminal): Set<TokenClass> {
        var table = LLTable[nonTerminal::class]

        if (table === null) {
            table = HashMap()
            LLTable[nonTerminal::class] = table
        }

        return table.keys
    }

    fun getRHS(nonTerminal: Symbol.NonTerminal, token: TokenClass): List<TreeNode>? {
        val table = LLTable.getOrDefault(nonTerminal::class, emptyMap<TokenClass, List<NodeSupplier>>())
        val simpleRules = table[token] ?: return null

        return simpleRules
                .stream()
                .map { it() }
                .collect(Collectors.toUnmodifiableList())
    }
}
