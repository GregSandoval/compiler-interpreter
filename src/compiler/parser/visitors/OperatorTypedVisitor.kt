package compiler.parser.visitors

import compiler.lexer.token.Token.OperatorToken
import compiler.lexer.token.Token.OperatorToken.*

interface OperatorTypedVisitor<T> {
    fun accept(token: OperatorToken) = when (token) {
        is LessThan -> visit(token)
        is GreaterThan -> visit(token)
        is Asterisk -> visit(token)
        is Equal -> visit(token)
        is Minus -> visit(token)
        is Plus -> visit(token)
        is Ampersand -> visit(token)
        is Arrow -> visit(token)
        is EqualEqual -> visit(token)
        is NotEqual -> visit(token)
        is LessThanOrEqual -> visit(token)
        is GreaterThanOrEqual -> visit(token)
        is BitShiftLeft -> visit(token)
        is BitShiftRight -> visit(token)
        is Caret -> visit(token)
        is ForwardSlash -> visit(token)
    }

    fun visit(token: LessThan): T
    fun visit(token: GreaterThan): T
    fun visit(token: Asterisk): T
    fun visit(token: Equal): T
    fun visit(token: Minus): T
    fun visit(token: Plus): T
    fun visit(token: Ampersand): T
    fun visit(token: Arrow): T
    fun visit(token: EqualEqual): T
    fun visit(token: NotEqual): T
    fun visit(token: LessThanOrEqual): T
    fun visit(token: GreaterThanOrEqual): T
    fun visit(token: BitShiftLeft): T
    fun visit(token: BitShiftRight): T
    fun visit(token: Caret): T
    fun visit(token: ForwardSlash): T
}
