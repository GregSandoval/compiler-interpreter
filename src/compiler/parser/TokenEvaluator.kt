package compiler.parser

import compiler.lexer.token.Token
import compiler.lexer.token.Token.IgnoredTokens.*
import compiler.lexer.token.Token.KeywordToken.*
import compiler.lexer.token.Token.KeywordToken.TypeToken.*
import compiler.lexer.token.Token.OperatorToken.*
import compiler.lexer.token.Token.SymbolToken.*
import compiler.lexer.token.Token.TypedToken.*

interface TokenEvaluator {
    fun accept(token: Token): Any = when (token) {
        is StringToken -> visit(token)
        is CommentToken -> visit(token)
        is EOFToken -> visit(token)
        is WhitespaceToken -> visit(token)
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
        is Comma -> visit(token)
        is SemiColon -> visit(token)
        is LeftBrace -> visit(token)
        is RightBrace -> visit(token)
        is LeftBracket -> visit(token)
        is RightBracket -> visit(token)
        is LeftParen -> visit(token)
        is RightParen -> visit(token)
        is Colon -> visit(token)
        is Period -> visit(token)
        is FloatToken -> visit(token)
        is IdentifierToken -> visit(token)
        is IntegerToken -> visit(token)
    }

    // Primitives
    @Throws(Exception::class)
    fun visit(token: FloatToken): Float

    @Throws(Exception::class)
    fun visit(token: IntegerToken): Int

    @Throws(Exception::class)
    fun visit(token: StringToken): String

    // Relational
    @Throws(Exception::class)
    fun visit(token: LessThan): Boolean

    @Throws(Exception::class)
    fun visit(token: GreaterThan): Boolean

    @Throws(Exception::class)
    fun visit(token: EqualEqual): Boolean

    @Throws(Exception::class)
    fun visit(token: NotEqual): Boolean

    @Throws(Exception::class)
    fun visit(token: LessThanOrEqual): Boolean

    @Throws(Exception::class)
    fun visit(token: GreaterThanOrEqual): Boolean

    // Operators
    @Throws(Exception::class)
    fun visit(token: Asterisk): Any

    @Throws(Exception::class)
    fun visit(token: Minus): Number

    @Throws(Exception::class)
    fun visit(token: Plus): Number

    @Throws(Exception::class)
    fun visit(token: Ampersand): Any

    @Throws(Exception::class)
    fun visit(token: BitShiftLeft): Number

    @Throws(Exception::class)
    fun visit(token: BitShiftRight): Number

    @Throws(Exception::class)
    fun visit(token: Caret): Number

    @Throws(Exception::class)
    fun visit(token: ForwardSlash): Number

    @Throws(Exception::class)
    fun visit(token: StringKeywordToken) {
        visitChildren(token)
    }

    @Throws(Exception::class)
    fun visit(node: TokenNodeElement) {
    }

    @Throws(Exception::class)
    fun visit(token: CommentToken) {
        visitChildren(token)
    }

    @Throws(Exception::class)
    fun visit(token: EOFToken) {
        visitChildren(token)
    }

    @Throws(Exception::class)
    fun visit(token: IdentifierToken): Any

    @Throws(Exception::class)
    fun visit(token: ProgramKeywordToken) {
        visitChildren(token)
    }

    @Throws(Exception::class)
    fun visit(token: MainKeywordToken) {
        visitChildren(token)
    }

    @Throws(Exception::class)
    fun visit(token: FunctionKeywordToken) {
        visitChildren(token)
    }

    @Throws(Exception::class)
    fun visit(token: ClassKeywordToken) {
        visitChildren(token)
    }

    @Throws(Exception::class)
    fun visit(token: FloatKeywordToken) {
        visitChildren(token)
    }

    @Throws(Exception::class)
    fun visit(token: IntegerKeywordToken) {
        visitChildren(token)
    }

    @Throws(Exception::class)
    fun visit(token: IfKeywordToken) {
        visitChildren(token)
    }

    @Throws(Exception::class)
    fun visit(token: ElseIfKeywordToken) {
        visitChildren(token)
    }

    @Throws(Exception::class)
    fun visit(token: ElseKeywordToken) {
        visitChildren(token)
    }

    @Throws(Exception::class)
    fun visit(token: WhileKeywordToken) {
        visitChildren(token)
    }

    @Throws(Exception::class)
    fun visit(token: InputKeywordToken): String

    @Throws(Exception::class)
    fun visit(token: PrintKeywordToken) {
        visitChildren(token)
    }

    @Throws(Exception::class)
    fun visit(token: NewKeywordToken) {
        visitChildren(token)
    }

    @Throws(Exception::class)
    fun visit(token: ReturnKeywordToken) {
        visitChildren(token)
    }

    @Throws(Exception::class)
    fun visit(token: VarKeywordToken) {
        visitChildren(token)
    }

    @Throws(Exception::class)
    fun visit(token: Equal) {
        visitChildren(token)
    }

    @Throws(Exception::class)
    fun visit(token: Arrow) {
        visitChildren(token)
    }

    @Throws(Exception::class)
    fun visit(token: Comma) {
        visitChildren(token)
    }

    @Throws(Exception::class)
    fun visit(token: SemiColon) {
        visitChildren(token)
    }

    @Throws(Exception::class)
    fun visit(token: LeftBrace) {
        visitChildren(token)
    }

    @Throws(Exception::class)
    fun visit(token: RightBrace) {
        visitChildren(token)
    }

    @Throws(Exception::class)
    fun visit(token: LeftBracket) {
        visitChildren(token)
    }

    @Throws(Exception::class)
    fun visit(token: RightBracket) {
        visitChildren(token)
    }

    @Throws(Exception::class)
    fun visit(token: LeftParen): List<Any>

    @Throws(Exception::class)
    fun visit(token: RightParen) {
        visitChildren(token)
    }

    @Throws(Exception::class)
    fun visit(token: Colon) {
        visitChildren(token)
    }

    @Throws(Exception::class)
    fun visit(token: Period) {
        visitChildren(token)
    }

    @Throws(Exception::class)
    fun visit(token: WhitespaceToken) {
        visitChildren(token)
    }

    @Throws(Exception::class)
    fun visit(token: VoidToken) {
        visitChildren(token)
    }

    @Throws(Exception::class)
    fun visitChildren(token: Token) {
        for (child in token.children) {
            if (child !is Token) {
                throw Exception("Non token in ast")
            }
            this.accept(child)
        }
    }
}
