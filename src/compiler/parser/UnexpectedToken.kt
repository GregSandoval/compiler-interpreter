package compiler.parser

import compiler.parser.Symbol.Terminal

class UnexpectedToken(top: TreeNode, terminal: Terminal, inputName: String) : Exception(formatError(top, terminal, inputName)) {
    companion object {
        private fun formatError(top: TreeNode, terminal: Terminal, inputName: String): String {
            val grammarClassName = top.javaClass.simpleName
            val tokenClassName = terminal.javaClass.simpleName
            val tokenLineNumber = terminal.lineInfo.number
            return "\nUnexpected token; Expected a $grammarClassName but found a $tokenClassName\n\tat $inputName($inputName:$tokenLineNumber)"
        }
    }
}
