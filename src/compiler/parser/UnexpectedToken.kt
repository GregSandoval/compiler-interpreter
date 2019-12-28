package compiler.parser

import compiler.lexer.token.Token

class UnexpectedToken(top: AbstractGrammarNode, token: Token, inputName: String) : Exception(formatError(top, token, inputName)) {
    companion object {
        private fun formatError(top: AbstractGrammarNode, token: Token, inputName: String): String {
            val grammarClassName = top.javaClass.simpleName
            val tokenClassName = token.javaClass.simpleName
            val tokenLineNumber = token.lineNumber
            return "\nUnexpected token; Expected a $grammarClassName but found a $tokenClassName\n\tat $inputName($inputName:$tokenLineNumber)"
        }
    }
}
