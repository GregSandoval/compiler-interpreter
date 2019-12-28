package compiler.lexer.token

import compiler.parser.AbstractGrammarNode
import compiler.parser.TokenNodeElement

/**
 * The base class for all token classes.
 * All tokens should extend this class.
 */
abstract class Token constructor(val str: String, private val tokenID: Int) : AbstractGrammarNode(), TokenNodeElement {
    var lineNumber = 0
    var linePosition = 0

    override fun toString(): String {
        val format = "(Tok: %s lin= %s,%s str = \"%s\"%s)"
        return String.format(format, tokenID, lineNumber, linePosition, str, toStringExtra())
    }

    protected open fun toStringExtra(): String? {
        return ""
    }

}
