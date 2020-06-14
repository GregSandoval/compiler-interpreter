package compiler.parser.visitors

import compiler.parser.Symbol.Terminal.Keyword
import compiler.parser.Symbol.Terminal.Keyword.*
import compiler.parser.Symbol.Terminal.Keyword.Type.*


interface KeywordTypedVisitor<T> {
    fun accept(token: Keyword) = when (token) {
        is ProgramKeyword -> visit(token)
        is MainKeyword -> visit(token)
        is FunctionKeyword -> visit(token)
        is ClassKeyword -> visit(token)
        is IfKeyword -> visit(token)
        is ElseIfKeyword -> visit(token)
        is ElseKeyword -> visit(token)
        is WhileKeyword -> visit(token)
        is InputKeyword -> visit(token)
        is PrintKeyword -> visit(token)
        is NewKeyword -> visit(token)
        is ReturnKeyword -> visit(token)
        is VarKeyword -> visit(token)
        is Void -> visit(token)
        is FloatKeyword -> visit(token)
        is IntegerKeyword -> visit(token)
        is StringKeyword -> visit(token)
    }

    fun visit(token: ProgramKeyword): T
    fun visit(token: MainKeyword): T
    fun visit(token: FunctionKeyword): T
    fun visit(token: ClassKeyword): T
    fun visit(token: FloatKeyword): T
    fun visit(token: IntegerKeyword): T
    fun visit(token: StringKeyword): T
    fun visit(token: IfKeyword): T
    fun visit(token: ElseIfKeyword): T
    fun visit(token: ElseKeyword): T
    fun visit(token: WhileKeyword): T
    fun visit(token: InputKeyword): T
    fun visit(token: PrintKeyword): T
    fun visit(token: NewKeyword): T
    fun visit(token: ReturnKeyword): T
    fun visit(token: VarKeyword): T
    fun visit(token: Void): T
}
