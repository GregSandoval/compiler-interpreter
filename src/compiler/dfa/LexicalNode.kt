package compiler.dfa

import compiler.lexer.token.LineInfo
import compiler.parser.Symbol.Terminal
import compiler.parser.Symbol.Terminal.Ignorable.CommentTerminal
import compiler.parser.Symbol.Terminal.Ignorable.WhitespaceTerminal
import compiler.parser.Symbol.Terminal.Operator.*
import compiler.parser.Symbol.Terminal.Punctuation.*
import compiler.parser.Symbol.Terminal.TypedTerminal.*
import compiler.utils.TextCursor

typealias NoArgConstructor = () -> Terminal

typealias SingleArgConstructor = (String) -> Terminal

sealed class LexicalNode {

    object ERROR : LexicalNode()
    sealed class NonFinalState : LexicalNode() {
        object START : NonFinalState()
        object MAYBE_FLOAT : NonFinalState()
        object OPENING_STRING : NonFinalState()
        object STRING_CONTENTS : NonFinalState()
        object EXCLAMATION_MARK : NonFinalState()
    }

    sealed class FinalState(val constructor: SingleArgConstructor) : LexicalNode() {
        sealed class FinalStateNoArg(constructor: NoArgConstructor) : FinalState({ constructor() }) {
            object COMMA : FinalStateNoArg(::Comma)
            object SEMI_COLON : FinalStateNoArg(::SemiColon)
            object LESS_THAN : FinalStateNoArg(::LessThan)
            object GREATER_THAN : FinalStateNoArg(::GreaterThan)
            object LEFT_BRACE : FinalStateNoArg(::LeftBrace)
            object RIGHT_BRACE : FinalStateNoArg(::RightBrace)
            object LEFT_BRACKET : FinalStateNoArg(::LeftBracket)
            object RIGHT_BRACKET : FinalStateNoArg(::RightBracket)
            object LEFT_PAREN : FinalStateNoArg(::LeftParen)
            object RIGHT_PAREN : FinalStateNoArg(::RightParen)
            object ASTERISK : FinalStateNoArg(::Asterisk)
            object CARET : FinalStateNoArg(::Caret)
            object COLON : FinalStateNoArg(::Colon)
            object PERIOD : FinalStateNoArg(::Period)
            object EQUAL : FinalStateNoArg(::Equal)
            object MINUS : FinalStateNoArg(::Minus)
            object PLUS : FinalStateNoArg(::Plus)
            object FORWARD_SLASH : FinalStateNoArg(::ForwardSlash)
            object AND : FinalStateNoArg(::Ampersand)
            object OP_ARROW : FinalStateNoArg(::Arrow)
            object OP_EQUAL : FinalStateNoArg(::EqualEqual)
            object OP_NEGATE : FinalStateNoArg(::NotEqual)
            object OP_LESS_THAN : FinalStateNoArg(::LessThanOrEqual)
            object OP_GREATER_THAN : FinalStateNoArg(::GreaterThanOrEqual)
            object OP_SHIFT_LEFT : FinalStateNoArg(::BitShiftLeft)
            object OP_SHIFT_RIGHT : FinalStateNoArg(::BitShiftRight)
        }

        sealed class FinalStateSingleArg(constructor: SingleArgConstructor) : FinalState(constructor) {
            object IDENTIFIER : FinalStateSingleArg(::IdentifierTerminal)
            object INTEGER : FinalStateSingleArg(::IntegerTerminal)
            object FLOAT : FinalStateSingleArg(::FloatTerminal)
            object WHITESPACE : FinalStateSingleArg(::WhitespaceTerminal)
            object COMMENT : FinalStateSingleArg(::CommentTerminal)
            object CLOSING_STRING : FinalStateSingleArg(::StringTerminal)
        }

        fun getToken(cursor: TextCursor): Terminal {
            val text = cursor.getCurrentSentence()
            val token = constructor(text)
            token.lineInfo = LineInfo(cursor.getCursorLineNumber(), cursor.getCursorLinePosition())
            return token
        }
    }

    override fun toString(): String {
        return this.javaClass.simpleName
    }

}
