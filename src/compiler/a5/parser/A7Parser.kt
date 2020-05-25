package compiler.a5.parser

import compiler.a5.grammar.A5GrammarRules
import compiler.parser.ParseTree
import compiler.parser.ParseTreeBuilder
import compiler.parser.Symbol.NonTerminal.Pgm
import compiler.parser.Symbol.Terminal

object A7Parser {
    fun parse(inputName: String, terminals: List<Terminal>): ParseTree {
        val llTable = A5GrammarRules.build()

        return ParseTreeBuilder()
                .setStartSymbol(Pgm(), llTable)
                .setInputSourceName(inputName)
                .setTerminals(terminals)
                .build()
    }
}
