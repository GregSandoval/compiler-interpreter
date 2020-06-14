package compiler.parser

import compiler.parser.Symbol.Terminal
import compiler.parser.Symbol.Terminal.Ignorable.*
import compiler.parser.Symbol.Terminal.Keyword.*
import compiler.parser.Symbol.Terminal.Keyword.Type.*
import compiler.parser.Symbol.Terminal.Operator.*
import compiler.parser.Symbol.Terminal.Punctuation.*
import compiler.parser.Symbol.Terminal.TypedTerminal.*

interface TokenEvaluator {
    fun accept(terminal: Terminal): Any = when (terminal) {
        is StringTerminal -> visit(terminal)
        is CommentTerminal -> visit(terminal)
        is EOFTerminal -> visit(terminal)
        is WhitespaceTerminal -> visit(terminal)
        is LessThan -> visit(terminal)
        is GreaterThan -> visit(terminal)
        is Asterisk -> visit(terminal)
        is Equal -> visit(terminal)
        is Minus -> visit(terminal)
        is Plus -> visit(terminal)
        is Ampersand -> visit(terminal)
        is Arrow -> visit(terminal)
        is EqualEqual -> visit(terminal)
        is NotEqual -> visit(terminal)
        is LessThanOrEqual -> visit(terminal)
        is GreaterThanOrEqual -> visit(terminal)
        is BitShiftLeft -> visit(terminal)
        is BitShiftRight -> visit(terminal)
        is Caret -> visit(terminal)
        is ForwardSlash -> visit(terminal)
        is ProgramKeyword -> visit(terminal)
        is MainKeyword -> visit(terminal)
        is FunctionKeyword -> visit(terminal)
        is ClassKeyword -> visit(terminal)
        is IfKeyword -> visit(terminal)
        is ElseIfKeyword -> visit(terminal)
        is ElseKeyword -> visit(terminal)
        is WhileKeyword -> visit(terminal)
        is InputKeyword -> visit(terminal)
        is PrintKeyword -> visit(terminal)
        is NewKeyword -> visit(terminal)
        is ReturnKeyword -> visit(terminal)
        is VarKeyword -> visit(terminal)
        is Void -> visit(terminal)
        is FloatKeyword -> visit(terminal)
        is IntegerKeyword -> visit(terminal)
        is StringKeyword -> visit(terminal)
        is Comma -> visit(terminal)
        is SemiColon -> visit(terminal)
        is LeftBrace -> visit(terminal)
        is RightBrace -> visit(terminal)
        is LeftBracket -> visit(terminal)
        is RightBracket -> visit(terminal)
        is LeftParen -> visit(terminal)
        is RightParen -> visit(terminal)
        is Colon -> visit(terminal)
        is Period -> visit(terminal)
        is FloatTerminal -> visit(terminal)
        is IdentifierTerminal -> visit(terminal)
        is IntegerTerminal -> visit(terminal)
    }

    // Primitives
    fun visit(token: FloatTerminal): Float

    fun visit(token: IntegerTerminal): Int

    fun visit(token: StringTerminal): String

    // Relational
    fun visit(token: LessThan): Boolean

    fun visit(token: GreaterThan): Boolean

    fun visit(token: EqualEqual): Boolean

    fun visit(token: NotEqual): Boolean

    fun visit(token: LessThanOrEqual): Boolean

    fun visit(token: GreaterThanOrEqual): Boolean

    // Operators
    fun visit(token: Asterisk): Any

    fun visit(token: Minus): Number

    fun visit(token: Plus): Number

    fun visit(token: Ampersand): Any

    fun visit(token: BitShiftLeft): Number

    fun visit(token: BitShiftRight): Number

    fun visit(token: Caret): Number

    fun visit(token: ForwardSlash): Number

    fun visit(token: StringKeyword) {
        visitChildren(token)
    }

    fun visit(node: TokenNodeElement) {
    }

    fun visit(token: CommentTerminal) {
        visitChildren(token)
    }

    fun visit(token: EOFTerminal) {
        visitChildren(token)
    }

    fun visit(token: IdentifierTerminal): Any

    fun visit(token: ProgramKeyword) {
        visitChildren(token)
    }

    fun visit(token: MainKeyword) {
        visitChildren(token)
    }

    fun visit(token: FunctionKeyword) {
        visitChildren(token)
    }

    fun visit(token: ClassKeyword) {
        visitChildren(token)
    }

    fun visit(token: FloatKeyword) {
        visitChildren(token)
    }

    fun visit(token: IntegerKeyword) {
        visitChildren(token)
    }

    fun visit(token: IfKeyword) {
        visitChildren(token)
    }

    fun visit(token: ElseIfKeyword) {
        visitChildren(token)
    }

    fun visit(token: ElseKeyword) {
        visitChildren(token)
    }

    fun visit(token: WhileKeyword) {
        visitChildren(token)
    }

    fun visit(token: InputKeyword): String

    fun visit(token: PrintKeyword) {
        visitChildren(token)
    }

    fun visit(token: NewKeyword) {
        visitChildren(token)
    }

    fun visit(token: ReturnKeyword) {
        visitChildren(token)
    }

    fun visit(token: VarKeyword) {
        visitChildren(token)
    }

    fun visit(token: Equal) {
        visitChildren(token)
    }

    fun visit(token: Arrow) {
        visitChildren(token)
    }

    fun visit(token: Comma) {
        visitChildren(token)
    }

    fun visit(token: SemiColon) {
        visitChildren(token)
    }

    fun visit(token: LeftBrace) {
        visitChildren(token)
    }

    fun visit(token: RightBrace) {
        visitChildren(token)
    }

    fun visit(token: LeftBracket) {
        visitChildren(token)
    }

    fun visit(token: RightBracket) {
        visitChildren(token)
    }

    fun visit(token: LeftParen): List<Any>

    fun visit(token: RightParen) {
        visitChildren(token)
    }

    fun visit(token: Colon) {
        visitChildren(token)
    }

    fun visit(token: Period) {
        visitChildren(token)
    }

    fun visit(token: WhitespaceTerminal) {
        visitChildren(token)
    }

    fun visit(token: Void) {
        visitChildren(token)
    }

    fun visitChildren(terminal: Terminal) {
        for (child in terminal.children) {
            if (child !is Terminal) {
                throw Exception("Non token in ast")
            }
            this.accept(child)
        }
    }
}
