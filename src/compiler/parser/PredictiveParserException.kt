package compiler.parser

import compiler.parser.Symbols.NonTerminal
import compiler.parser.Symbols.Terminal
import java.util.stream.Collectors

class PredictiveParserException(nonTerminalNode: NonTerminal, terminal: Terminal) : UserException(formatMessage(nonTerminalNode, terminal), terminal) {
    companion object {
        private fun formatMessage(nonTerminalRule: NonTerminal, terminal: Terminal): String {
            val tokenClassName = terminal.javaClass.simpleName
            val grammarClassName = nonTerminalRule.javaClass.simpleName
            val expectedTokens = nonTerminalRule
                    .getRHS()
                    .stream()
                    .map { it.simpleName }
                    .collect(Collectors.joining(" or "))
            return "\nLL Table missing entry exception; $nonTerminalRule($tokenClassName) = undefined\n$grammarClassName expected $expectedTokens but found $tokenClassName"
        }
    }
}
