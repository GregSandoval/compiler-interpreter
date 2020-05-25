package compiler.parser

import compiler.EntryPoint.inputName
import compiler.parser.Symbol.Terminal

class UnexpectedToken(top: TreeNode, terminal: Terminal) : Exception(formatError(top, terminal))

private fun formatError(top: TreeNode, terminal: Terminal): String {
    val grammarClassName = top.javaClass.simpleName
    val tokenClassName = terminal.javaClass.simpleName
    val tokenLineNumber = terminal.lineInfo.number
    return "\nUnexpected token; Expected a $grammarClassName but found a $tokenClassName\n\tat $inputName($inputName:$tokenLineNumber)"
}
