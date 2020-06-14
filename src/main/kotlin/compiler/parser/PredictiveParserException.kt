package compiler.parser

import compiler.parser.Symbol.NonTerminal
import compiler.parser.Symbol.Terminal
import compiler.parser.lltable.LLTable
import java.util.stream.Collectors

class PredictiveParserException(nonTerminalNode: NonTerminal, terminal: Terminal, llTable: LLTable) :
        UserException(formatMessage(nonTerminalNode, terminal, llTable), terminal)

private fun formatMessage(nonTerminalRule: NonTerminal, terminal: Terminal, llTable: LLTable): String {
    val tokenClassName = terminal.javaClass.simpleName
    val grammarClassName = nonTerminalRule.javaClass.simpleName
    val expectedTokens = llTable
            .getRHS(nonTerminalRule)
            .stream()
            .map { it.simpleName }
            .collect(Collectors.joining(" or "))
    return "\nLL Table missing entry exception; $nonTerminalRule($tokenClassName) = undefined\n$grammarClassName expected $expectedTokens but found $tokenClassName"
}
