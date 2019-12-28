package compiler.parser.visitors

import compiler.lexer.token.KeywordToken.*
import compiler.lexer.token.TypeToken.*

interface KeywordTypedVisitor<T> {
    fun visit(token: ProgramKeywordToken): T
    fun visit(token: MainKeywordToken): T
    fun visit(token: FunctionKeywordToken): T
    fun visit(token: ClassKeywordToken): T
    fun visit(token: FloatKeywordToken): T
    fun visit(token: IntegerKeywordToken): T
    fun visit(token: StringKeywordToken): T
    fun visit(token: IfKeywordToken): T
    fun visit(token: ElseIfKeywordToken): T
    fun visit(token: ElseKeywordToken): T
    fun visit(token: WhileKeywordToken): T
    fun visit(token: InputKeywordToken): T
    fun visit(token: PrintKeywordToken): T
    fun visit(token: NewKeywordToken): T
    fun visit(token: ReturnKeywordToken): T
    fun visit(token: VarKeywordToken): T
}
