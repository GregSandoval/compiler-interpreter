package compiler.parser.visitors

import compiler.lexer.token.OperatorToken.*

interface OperatorTypedVisitor<T> {
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
