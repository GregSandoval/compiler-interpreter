package compiler.a5.parser

import compiler.a5.grammar.A7Grammar
import compiler.parser.*
import compiler.parser.Symbol.NonTerminal.Program
import compiler.parser.Symbol.Terminal
import compiler.parser.epsilon.EpsilonDerivation
import compiler.parser.first.FirstSet
import compiler.parser.follow.FollowSet
import compiler.parser.lltable.LLTable

object A7Parser {
    fun parse(terminals: List<Terminal>): ParseTree {
        val productions = A7Grammar.build()

        val derivesEpsilon = EpsilonDerivation.findAll(productions)

        val first = FirstSet.findAll(productions, derivesEpsilon)

        val follow = FollowSet.findAll(productions, derivesEpsilon, first)

        val llTable = LLTable(productions, first, follow, derivesEpsilon)

        return ParseTreeBuilder()
                .setStartSymbol(Program(), llTable)
                .setTerminals(terminals)
                .build()
    }
}
