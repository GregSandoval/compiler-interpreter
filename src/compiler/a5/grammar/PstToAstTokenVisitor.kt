package compiler.a5.grammar

import compiler.lexer.token.Token.IgnoredTokens.*
import compiler.lexer.token.Token.KeywordToken.*
import compiler.lexer.token.Token.KeywordToken.TypeToken.*
import compiler.lexer.token.Token.OperatorToken.*
import compiler.lexer.token.Token.SymbolToken.*
import compiler.lexer.token.Token.TypedToken.*
import compiler.parser.PstToAstHelpers
import compiler.parser.visitors.TokenVisitor

class PstToAstTokenVisitor : TokenVisitor<Unit> {
    override fun visit(token: ForwardSlash) {
        PstToAstHelpers.hoist(token)
    }

    override fun visit(token: Asterisk) {
        PstToAstHelpers.hoist(token)
    }

    override fun visit(token: LeftParen) {
    }

    override fun visit(token: IdentifierToken) {
    }

    override fun visit(token: CommentToken) {
    }

    override fun visit(token: EOFToken) {
    }

    override fun visit(token: FloatToken) {
    }

    override fun visit(token: IntegerToken) {
    }

    override fun visit(token: MainKeywordToken) {
    }

    override fun visit(token: FunctionKeywordToken) {
    }

    override fun visit(token: FloatKeywordToken) {
    }

    override fun visit(token: IntegerKeywordToken) {
    }

    override fun visit(token: StringKeywordToken) {
    }

    override fun visit(token: IfKeywordToken) {
    }

    override fun visit(token: ElseIfKeywordToken) {
    }

    override fun visit(token: ElseKeywordToken) {
    }

    override fun visit(token: WhileKeywordToken) {
    }

    override fun visit(token: InputKeywordToken) {
    }

    override fun visit(token: PrintKeywordToken) {
    }

    override fun visit(token: NewKeywordToken) {
    }

    override fun visit(token: ReturnKeywordToken) {
    }

    override fun visit(token: LessThan) {
    }

    override fun visit(token: GreaterThan) {
    }

    override fun visit(token: Equal) {
    }

    override fun visit(token: Minus) {
    }

    override fun visit(token: Plus) {
    }

    override fun visit(token: Ampersand) {
    }

    override fun visit(token: Arrow) {
    }

    override fun visit(token: EqualEqual) {
    }

    override fun visit(token: NotEqual) {
    }

    override fun visit(token: LessThanOrEqual) {
    }

    override fun visit(token: GreaterThanOrEqual) {
    }

    override fun visit(token: BitShiftLeft) {
    }

    override fun visit(token: BitShiftRight) {
    }

    override fun visit(token: StringToken) {
    }

    override fun visit(token: Comma) {
    }

    override fun visit(token: SemiColon) {
    }

    override fun visit(token: LeftBrace) {
    }

    override fun visit(token: RightBrace) {
    }

    override fun visit(token: LeftBracket) {
    }

    override fun visit(token: RightBracket) {
    }

    override fun visit(token: RightParen) {
    }

    override fun visit(token: Caret) {
    }

    override fun visit(token: Colon) {
    }

    override fun visit(token: Period) {
    }

    override fun visit(token: WhitespaceToken) {
    }

    override fun visit(token: ClassKeywordToken) {
    }

    override fun visit(token: ProgramKeywordToken) {
    }

    override fun visit(token: VarKeywordToken) {
    }

    override fun visit(token: VoidToken) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
