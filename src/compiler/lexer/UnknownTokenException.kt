package compiler.lexer

import compiler.parser.UserException
import compiler.utils.TextCursor
import kotlin.math.max

class UnknownTokenException(unknownToken: String, cursor: TextCursor) : UserException(formatError(unknownToken, cursor), cursor)

private fun formatError(unknownToken: String, cursor: TextCursor): String {
    val line = cursor.getCursorLineNumber()
    val pos = cursor.getCursorLinePosition() - unknownToken.length
    return "\nCould not lex input: Error occurred on line $line, position $pos; Unexpected symbol:\n${cursor.getCurrentLineOfText()}\n${" ".repeat(max(0, pos))}^"
}
