package compiler.parser

import compiler.parser.Symbols.Terminal
import compiler.parser.Symbols.Terminal.Ignorable.*
import compiler.parser.Symbols.Terminal.Keyword.*
import compiler.parser.Symbols.Terminal.Keyword.Type.*
import compiler.parser.Symbols.Terminal.Operator.*
import compiler.parser.Symbols.Terminal.Punctuation.*
import compiler.parser.Symbols.Terminal.TypedTerminal.*

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
    @Throws(Exception::class)
    fun visit(token: FloatTerminal): Float

    @Throws(Exception::class)
    fun visit(token: IntegerTerminal): Int

    @Throws(Exception::class)
    fun visit(token: StringTerminal): String

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
    fun visit(token: StringKeyword) {
        visitChildren(token)
    }

    @Throws(Exception::class)
    fun visit(node: TokenNodeElement) {
    }

    @Throws(Exception::class)
    fun visit(token: CommentTerminal) {
        visitChildren(token)
    }

    @Throws(Exception::class)
    fun visit(token: EOFTerminal) {
        visitChildren(token)
    }

    @Throws(Exception::class)
    fun visit(token: IdentifierTerminal): Any

    @Throws(Exception::class)
    fun visit(token: ProgramKeyword) {
        visitChildren(token)
    }

    @Throws(Exception::class)
    fun visit(token: MainKeyword) {
        visitChildren(token)
    }

    @Throws(Exception::class)
    fun visit(token: FunctionKeyword) {
        visitChildren(token)
    }

    @Throws(Exception::class)
    fun visit(token: ClassKeyword) {
        visitChildren(token)
    }

    @Throws(Exception::class)
    fun visit(token: FloatKeyword) {
        visitChildren(token)
    }

    @Throws(Exception::class)
    fun visit(token: IntegerKeyword) {
        visitChildren(token)
    }

    @Throws(Exception::class)
    fun visit(token: IfKeyword) {
        visitChildren(token)
    }

    @Throws(Exception::class)
    fun visit(token: ElseIfKeyword) {
        visitChildren(token)
    }

    @Throws(Exception::class)
    fun visit(token: ElseKeyword) {
        visitChildren(token)
    }

    @Throws(Exception::class)
    fun visit(token: WhileKeyword) {
        visitChildren(token)
    }

    @Throws(Exception::class)
    fun visit(token: InputKeyword): String

    @Throws(Exception::class)
    fun visit(token: PrintKeyword) {
        visitChildren(token)
    }

    @Throws(Exception::class)
    fun visit(token: NewKeyword) {
        visitChildren(token)
    }

    @Throws(Exception::class)
    fun visit(token: ReturnKeyword) {
        visitChildren(token)
    }

    @Throws(Exception::class)
    fun visit(token: VarKeyword) {
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
    fun visit(token: WhitespaceTerminal) {
        visitChildren(token)
    }

    @Throws(Exception::class)
    fun visit(token: Void) {
        visitChildren(token)
    }

    @Throws(Exception::class)
    fun visitChildren(terminal: Terminal) {
        for (child in terminal.children) {
            if (child !is Terminal) {
                throw Exception("Non token in ast")
            }
            this.accept(child)
        }
    }
}
