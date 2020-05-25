package compiler.parser

import compiler.parser.Symbol.NonTerminal
import java.util.stream.Collectors
import kotlin.collections.set
import kotlin.reflect.KClass

class LLTable {
    private val LLTable: MutableMap<KClass<out Symbol>, MutableMap<TokenClass, List<NodeSupplier>>> = HashMap()

    fun <T : NonTerminal> on(nonTerminal: KClass<out T>, vararg first: TokenClass): ProductionBuilder<T> {
        return ProductionBuilder(nonTerminal, *first)
    }

    inner class ProductionBuilder<out T : NonTerminal>(private val nonTerminal: KClass<T>, private vararg val tokenClass: TokenClass) {
        fun useRHS(rest: List<NodeSupplier>) {
            for (token in tokenClass) {
                var table = LLTable[nonTerminal]

                if (table === null) {
                    table = HashMap()
                    LLTable[nonTerminal] = table
                }

                if (table.containsKey(token)) {
                    throw RuntimeException("Double stuffed LL Table: Rule: " + nonTerminal.simpleName + " Token: " + token.simpleName)
                }

                table[token] = rest
            }
        }

    }

    fun getRHS(nonTerminal: NonTerminal): Set<TokenClass> {
        var table = LLTable[nonTerminal::class]

        if (table === null) {
            table = HashMap()
            LLTable[nonTerminal::class] = table
        }

        return table.keys
    }

    fun getRHS(nonTerminal: NonTerminal, token: TokenClass): List<Symbol>? {
        val table = LLTable.getOrDefault(nonTerminal::class, emptyMap<TokenClass, List<NodeSupplier>>())
        val simpleRules = table[token] ?: return null

        return simpleRules
                .stream()
                .map { it() }
                .collect(Collectors.toUnmodifiableList())
    }
}
