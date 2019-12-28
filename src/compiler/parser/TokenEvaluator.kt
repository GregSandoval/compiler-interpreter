package compiler.parser

import compiler.lexer.token.*
import compiler.lexer.token.KeywordToken.*
import compiler.lexer.token.OperatorToken.*
import compiler.lexer.token.SymbolToken.*
import compiler.lexer.token.TypeToken.*

interface TokenEvaluator {
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
    fun visitChildren(token: Token) {
        for (child in token.children) {
            if (child !is Token) {
                throw Exception("Non token in ast")
            }
            child.accept(this)
        }
    }
}
