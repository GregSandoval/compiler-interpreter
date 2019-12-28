package compiler.parser.visitors

import compiler.lexer.token.Token.KeywordToken
import compiler.lexer.token.Token.KeywordToken.*
import compiler.lexer.token.Token.KeywordToken.TypeToken.*


interface KeywordTypedVisitor<T> {
    fun accept(token: KeywordToken) = when (token) {
        is ProgramKeywordToken -> visit(token)
        is MainKeywordToken -> visit(token)
        is FunctionKeywordToken -> visit(token)
        is ClassKeywordToken -> visit(token)
        is IfKeywordToken -> visit(token)
        is ElseIfKeywordToken -> visit(token)
        is ElseKeywordToken -> visit(token)
        is WhileKeywordToken -> visit(token)
        is InputKeywordToken -> visit(token)
        is PrintKeywordToken -> visit(token)
        is NewKeywordToken -> visit(token)
        is ReturnKeywordToken -> visit(token)
        is VarKeywordToken -> visit(token)
        is VoidToken -> visit(token)
        is FloatKeywordToken -> visit(token)
        is IntegerKeywordToken -> visit(token)
        is StringKeywordToken -> visit(token)
    }

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
    fun visit(token: VoidToken): T
}
