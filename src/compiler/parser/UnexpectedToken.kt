package compiler.parser

import compiler.parser.Language.Token

class UnexpectedToken(top: TreeNode, token: Token, inputName: String) : Exception(formatError(top, token, inputName)) {
    companion object {
        private fun formatError(top: TreeNode, token: Token, inputName: String): String {
            val grammarClassName = top.javaClass.simpleName
            val tokenClassName = token.javaClass.simpleName
            val tokenLineNumber = token.lineInfo.number
            return "\nUnexpected token; Expected a $grammarClassName but found a $tokenClassName\n\tat $inputName($inputName:$tokenLineNumber)"
        }
    }
}
