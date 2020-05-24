package compiler.parser.visitors

import compiler.parser.Symbol.Terminal
import compiler.parser.Symbol.Terminal.Ignorable.*
import compiler.parser.Symbol.Terminal.Keyword.*
import compiler.parser.Symbol.Terminal.Keyword.Type.*
import compiler.parser.Symbol.Terminal.Operator.*
import compiler.parser.Symbol.Terminal.Punctuation.*
import compiler.parser.Symbol.Terminal.TypedTerminal.*

interface TokenTypedAdapterVisitor<T> : TokenVisitor<T> {
    fun defaultValue(): T

    override fun visit(token: CommentTerminal): T {
        return visitChildren(token)
    }

    override fun visit(token: EOFTerminal): T {
        return visitChildren(token)
    }

    override fun visit(token: WhitespaceTerminal): T {
        return visitChildren(token)
    }

    override fun visit(token: IdentifierTerminal): T {
        return visitChildren(token)
    }

    override fun visit(token: ProgramKeyword): T {
        return visitChildren(token)
    }

    override fun visit(token: MainKeyword): T {
        return visitChildren(token)
    }

    override fun visit(token: FunctionKeyword): T {
        return visitChildren(token)
    }

    override fun visit(token: ClassKeyword): T {
        return visitChildren(token)
    }

    override fun visit(token: FloatKeyword): T {
        return visitChildren(token)
    }

    override fun visit(token: IntegerKeyword): T {
        return visitChildren(token)
    }

    override fun visit(token: StringKeyword): T {
        return visitChildren(token)
    }

    override fun visit(token: Void): T {
        return visitChildren(token)
    }

    override fun visit(token: IfKeyword): T {
        return visitChildren(token)
    }

    override fun visit(token: ElseIfKeyword): T {
        return visitChildren(token)
    }

    override fun visit(token: ElseKeyword): T {
        return visitChildren(token)
    }

    override fun visit(token: WhileKeyword): T {
        return visitChildren(token)
    }

    override fun visit(token: InputKeyword): T {
        return visitChildren(token)
    }

    override fun visit(token: PrintKeyword): T {
        return visitChildren(token)
    }

    override fun visit(token: NewKeyword): T {
        return visitChildren(token)
    }

    override fun visit(token: ReturnKeyword): T {
        return visitChildren(token)
    }

    override fun visit(token: VarKeyword): T {
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

    override fun visit(token: FloatTerminal): T {
        return visitChildren(token)
    }

    override fun visit(token: IntegerTerminal): T {
        return visitChildren(token)
    }

    override fun visit(token: StringTerminal): T {
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

    fun visitChildren(terminal: Terminal): T {
        for (child in terminal.children) {
            this.accept(child as Terminal)
        }
        return defaultValue()
    }
}
