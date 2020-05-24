package compiler.parser

import compiler.EntryPoint
import compiler.parser.Language.Token
import compiler.utils.TextCursor

open class UserException : RuntimeException {
    constructor(message: String, token: Token) : super(message + "\n" + renderLineNumber(token, EntryPoint.inputName))
    constructor(message: String, cursor: TextCursor) : super(message + "\n" + renderLineNumber(cursor, EntryPoint.inputName))

    companion object {
        private fun renderLineNumber(token: Token, inputName: String?): String {
            return "\tat " + inputName + "(" + inputName + ":" + token.lineInfo.number + ")"
        }

        private fun renderLineNumber(cursor: TextCursor, inputName: String?): String {
            return "\tat " + inputName + "(" + inputName + ":" + cursor.getCursorLineNumber() + ")"
        }
    }
}
