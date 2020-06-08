package compiler.a5.parser

import compiler.a5.grammar.A7Grammar
import compiler.parser.EpsilonDerivation
import compiler.parser.LLTable
import compiler.parser.ParseTree
import compiler.parser.ParseTreeBuilder
import compiler.parser.Symbol.NonTerminal.Program
import compiler.parser.Symbol.Terminal

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
