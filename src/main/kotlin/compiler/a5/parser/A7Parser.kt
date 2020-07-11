package compiler.a5.parser

import compiler.a5.grammar.A7Grammar
import compiler.parser.ParseTree
import compiler.parser.ParserBuilder
import compiler.parser.Symbol.NonTerminal.Program
import compiler.parser.Symbol.Terminal
import compiler.parser.epsilon.EpsilonDerivation
import compiler.parser.first.FirstSet
import compiler.parser.follow.FollowSet
import compiler.parser.lltable.LLTable

class A7Parser {
    private val productions = A7Grammar()
    private val derivesEpsilon = EpsilonDerivation.findAll(productions)
    private val first = FirstSet.findAll(productions, derivesEpsilon)
    private val follow = FollowSet.findFollowSet(productions, derivesEpsilon, first)
    private val llTable = LLTable(productions, first, follow, derivesEpsilon)

    private val parser = ParserBuilder()
            .setStartSymbol(Program())
            .setLLTable(llTable)
            .createParser()

    fun parse(terminals: List<Terminal>): ParseTree {
        return this.parser.parse(terminals)
    }
}
