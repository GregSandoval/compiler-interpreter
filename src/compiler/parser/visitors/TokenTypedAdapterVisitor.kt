package compiler.parser.visitors

import compiler.parser.Language.Token
import compiler.parser.Language.Token.IgnorableTokens.*
import compiler.parser.Language.Token.KeywordToken.*
import compiler.parser.Language.Token.KeywordToken.TypeToken.*
import compiler.parser.Language.Token.OperatorToken.*
import compiler.parser.Language.Token.SymbolToken.*
import compiler.parser.Language.Token.TypedToken.*

interface TokenTypedAdapterVisitor<T> : TokenVisitor<T> {
    fun defaultValue(): T

    override fun visit(token: CommentToken): T {
        return visitChildren(token)
    }

    override fun visit(token: EOFToken): T {
        return visitChildren(token)
    }

    override fun visit(token: WhitespaceToken): T {
        return visitChildren(token)
    }

    override fun visit(token: IdentifierToken): T {
        return visitChildren(token)
    }

    override fun visit(token: ProgramKeywordToken): T {
        return visitChildren(token)
    }

    override fun visit(token: MainKeywordToken): T {
        return visitChildren(token)
    }

    override fun visit(token: FunctionKeywordToken): T {
        return visitChildren(token)
    }

    override fun visit(token: ClassKeywordToken): T {
        return visitChildren(token)
    }

    override fun visit(token: FloatKeywordToken): T {
        return visitChildren(token)
    }

    override fun visit(token: IntegerKeywordToken): T {
        return visitChildren(token)
    }

    override fun visit(token: StringKeywordToken): T {
        return visitChildren(token)
    }

    override fun visit(token: VoidToken): T {
        return visitChildren(token)
    }

    override fun visit(token: IfKeywordToken): T {
        return visitChildren(token)
    }

    override fun visit(token: ElseIfKeywordToken): T {
        return visitChildren(token)
    }

    override fun visit(token: ElseKeywordToken): T {
        return visitChildren(token)
    }

    override fun visit(token: WhileKeywordToken): T {
        return visitChildren(token)
    }

    override fun visit(token: InputKeywordToken): T {
        return visitChildren(token)
    }

    override fun visit(token: PrintKeywordToken): T {
        return visitChildren(token)
    }

    override fun visit(token: NewKeywordToken): T {
        return visitChildren(token)
    }

    override fun visit(token: ReturnKeywordToken): T {
        return visitChildren(token)
    }

    override fun visit(token: VarKeywordToken): T {
        return visitChildren(token)
    }

    override fun visit(token: LessThan): T {
        return visitChildren(token)
    }

    override fun visit(token: GreaterThan): T {
        return visitChildren(token)
    }

    override fun visit(token: Asterisk): T {
        return visitChildren(token)
    }

    override fun visit(token: Equal): T {
        return visitChildren(token)
    }

    override fun visit(token: Minus): T {
        return visitChildren(token)
    }

    override fun visit(token: Plus): T {
        return visitChildren(token)
    }

    override fun visit(token: Ampersand): T {
        return visitChildren(token)
    }

    override fun visit(token: Arrow): T {
        return visitChildren(token)
    }

    override fun visit(token: EqualEqual): T {
        return visitChildren(token)
    }

    override fun visit(token: NotEqual): T {
        return visitChildren(token)
    }

    override fun visit(token: LessThanOrEqual): T {
        return visitChildren(token)
    }

    override fun visit(token: GreaterThanOrEqual): T {
        return visitChildren(token)
    }

    override fun visit(token: BitShiftLeft): T {
        return visitChildren(token)
    }

    override fun visit(token: BitShiftRight): T {
        return visitChildren(token)
    }

    override fun visit(token: FloatToken): T {
        return visitChildren(token)
    }

    override fun visit(token: IntegerToken): T {
        return visitChildren(token)
    }

    override fun visit(token: StringToken): T {
        return visitChildren(token)
    }

    override fun visit(token: Comma): T {
        return visitChildren(token)
    }

    override fun visit(token: SemiColon): T {
        return visitChildren(token)
    }

    override fun visit(token: LeftBrace): T {
        return visitChildren(token)
    }

    override fun visit(token: RightBrace): T {
        return visitChildren(token)
    }

    override fun visit(token: LeftBracket): T {
        return visitChildren(token)
    }

    override fun visit(token: RightBracket): T {
        return visitChildren(token)
    }

    override fun visit(token: LeftParen): T {
        return visitChildren(token)
    }

    override fun visit(token: RightParen): T {
        return visitChildren(token)
    }

    override fun visit(token: Caret): T {
        return visitChildren(token)
    }

    override fun visit(token: Colon): T {
        return visitChildren(token)
    }

    override fun visit(token: Period): T {
        return visitChildren(token)
    }

    override fun visit(token: ForwardSlash): T {
        return visitChildren(token)
    }

    fun visitChildren(token: Token): T {
        for (child in token.children) {
            this.accept(child as Token)
        }
        return defaultValue()
    }
}
