package compiler.a5.grammar

import compiler.parser.PstToAstHelpers
import compiler.parser.Symbol.Terminal.Ignorable.*
import compiler.parser.Symbol.Terminal.Keyword.*
import compiler.parser.Symbol.Terminal.Keyword.Type.*
import compiler.parser.Symbol.Terminal.Operator.*
import compiler.parser.Symbol.Terminal.Punctuation.*
import compiler.parser.Symbol.Terminal.TypedTerminal.*
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

    override fun visit(token: IdentifierTerminal) {
    }

    override fun visit(token: CommentTerminal) {
    }

    override fun visit(token: EOFTerminal) {
    }

    override fun visit(token: FloatTerminal) {
    }

    override fun visit(token: IntegerTerminal) {
    }

    override fun visit(token: MainKeyword) {
    }

    override fun visit(token: FunctionKeyword) {
    }

    override fun visit(token: FloatKeyword) {
    }

    override fun visit(token: IntegerKeyword) {
    }

    override fun visit(token: StringKeyword) {
    }

    override fun visit(token: IfKeyword) {
    }

    override fun visit(token: ElseIfKeyword) {
    }

    override fun visit(token: ElseKeyword) {
    }

    override fun visit(token: WhileKeyword) {
    }

    override fun visit(token: InputKeyword) {
    }

    override fun visit(token: PrintKeyword) {
    }

    override fun visit(token: NewKeyword) {
    }

    override fun visit(token: ReturnKeyword) {
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

    override fun visit(token: StringTerminal) {
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

    override fun visit(token: WhitespaceTerminal) {
    }

    override fun visit(token: ClassKeyword) {
    }

    override fun visit(token: ProgramKeyword) {
    }

    override fun visit(token: VarKeyword) {
    }

    override fun visit(token: Void) {
    }

}
