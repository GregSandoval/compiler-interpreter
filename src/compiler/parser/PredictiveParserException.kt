package compiler.parser

import compiler.parser.Language.Grammar
import compiler.parser.Language.Token
import java.util.stream.Collectors

class PredictiveParserException(grammarNode: Grammar, token: Token) : UserException(formatMessage(grammarNode, token), token) {
    companion object {
        private fun formatMessage(grammarRule: Grammar, token: Token): String {
            val tokenClassName = token.javaClass.simpleName
            val grammarClassName = grammarRule.javaClass.simpleName
            val expectedTokens = grammarRule
                    .getRHS()
                    .stream()
                    .map { it.simpleName }
                    .collect(Collectors.joining(" or "))
            return "\nLL Table missing entry exception; $grammarRule($tokenClassName) = undefined\n$grammarClassName expected $expectedTokens but found $tokenClassName"
        }
    }
}
