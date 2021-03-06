package compiler.parser.lltable

import compiler.parser.*
import compiler.parser.Symbol.NonTerminal
import compiler.parser.first.FirstSet
import compiler.parser.first.TerminalClass
import kotlin.collections.HashMap
import kotlin.collections.List
import kotlin.collections.Map
import kotlin.collections.MutableMap
import kotlin.collections.Set
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.collections.iterator
import kotlin.collections.map
import kotlin.collections.set
import kotlin.reflect.KClass

class LLTable() {
    private val llTable: MutableMap<KClass<out Symbol>, MutableMap<TokenClass, List<NodeSupplier>>> = HashMap()

    constructor(
            productions: ProductionRules,
            first: Map<NonTerminalClass, Set<TerminalClass>>,
            follow: Map<NonTerminalClass, Set<TerminalClass>>,
            derivesEpsilon: Set<NonTerminalClass>
    ) : this() {
        for ((lhs, rhss) in productions.getProductions()) {
            var table = llTable[lhs]

            if (table === null) {
                table = HashMap()
                llTable[lhs] = table
            }

            for (rhs in rhss) {
                val firstTerminals = when (rhs.isEmpty()) {
                    true -> follow[lhs] ?: throw Exception("${lhs.simpleName} derives epsilon but is followed by nothing.")
                    false -> FirstSet.first(rhs, derivesEpsilon, first)
                }

                for (terminal in firstTerminals) {
                    val existingRHS = table[terminal]

                    if (existingRHS === null) {
                        table[terminal] = rhs
                        continue
                    }

                    val rhsSymbols = rhs
                            .map { it()::class }
                    val existingRHSSymbols = existingRHS
                            .map { it()::class }

                    if (rhsSymbols != existingRHSSymbols)
                        throw RuntimeException(
                                "Double stuffed LL Table:\nNonTerminal: ${lhs.simpleName}. \nToken: ${terminal.simpleName}. \nExisting RHS: $existingRHS. \nNew RHS: $rhs"
                        )
                }
            }
        }
    }

    fun getRHS(nonTerminal: NonTerminal): Set<TokenClass> {
        var table = llTable[nonTerminal::class]

        if (table === null) {
            table = HashMap()
            llTable[nonTerminal::class] = table
        }

        return table.keys
    }

    fun getRHS(nonTerminal: NonTerminal, token: TokenClass): List<Symbol>? {
        var table = llTable[nonTerminal::class]

        if (table === null) {
            table = HashMap()
            llTable[nonTerminal::class] = table
        }

        val simpleRules = table[token] ?: return null

        return simpleRules
                .map { it() }
    }
}
