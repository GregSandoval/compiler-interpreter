package compiler.parser

import compiler.EntryPoint
import compiler.parser.Symbol.Terminal
import compiler.utils.TextCursor

open class UserException : RuntimeException {
    constructor(message: String, terminal: Terminal) : super(message + "\n" + renderLineNumber(terminal, EntryPoint.inputName))
    constructor(message: String, cursor: TextCursor) : super(message + "\n" + renderLineNumber(cursor, EntryPoint.inputName))

    companion object {
        private fun renderLineNumber(terminal: Terminal, inputName: String?): String {
            return "\tat " + inputName + "(" + inputName + ":" + terminal.lineInfo.number + ")"
        }

        private fun renderLineNumber(cursor: TextCursor, inputName: String?): String {
            return "\tat " + inputName + "(" + inputName + ":" + cursor.getCursorLineNumber() + ")"
        }
    }
}
