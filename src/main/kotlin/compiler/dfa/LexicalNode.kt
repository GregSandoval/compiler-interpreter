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

    class ERROR : LexicalNode()
    sealed class NonFinalState : LexicalNode() {
        class START : NonFinalState()
        class MAYBE_FLOAT : NonFinalState()
        class OPENING_STRING : NonFinalState()
        class STRING_CONTENTS : NonFinalState()
        class EXCLAMATION_MARK : NonFinalState()
    }

    sealed class FinalState(val constructor: SingleArgConstructor) : LexicalNode() {
        sealed class FinalStateNoArg(constructor: NoArgConstructor) : FinalState({ constructor() }) {
            class COMMA : FinalStateNoArg(::Comma)
            class SEMI_COLON : FinalStateNoArg(::SemiColon)
            class LESS_THAN : FinalStateNoArg(::LessThan)
            class GREATER_THAN : FinalStateNoArg(::GreaterThan)
            class LEFT_BRACE : FinalStateNoArg(::LeftBrace)
            class RIGHT_BRACE : FinalStateNoArg(::RightBrace)
            class LEFT_BRACKET : FinalStateNoArg(::LeftBracket)
            class RIGHT_BRACKET : FinalStateNoArg(::RightBracket)
            class LEFT_PAREN : FinalStateNoArg(::LeftParen)
            class RIGHT_PAREN : FinalStateNoArg(::RightParen)
            class ASTERISK : FinalStateNoArg(::Asterisk)
            class CARET : FinalStateNoArg(::Caret)
            class COLON : FinalStateNoArg(::Colon)
            class PERIOD : FinalStateNoArg(::Period)
            class EQUAL : FinalStateNoArg(::Equal)
            class MINUS : FinalStateNoArg(::Minus)
            class PLUS : FinalStateNoArg(::Plus)
            class FORWARD_SLASH : FinalStateNoArg(::ForwardSlash)
            class AND : FinalStateNoArg(::Ampersand)
            class OP_ARROW : FinalStateNoArg(::Arrow)
            class OP_EQUAL : FinalStateNoArg(::EqualEqual)
            class OP_NEGATE : FinalStateNoArg(::NotEqual)
            class OP_LESS_THAN : FinalStateNoArg(::LessThanOrEqual)
            class OP_GREATER_THAN : FinalStateNoArg(::GreaterThanOrEqual)
            class OP_SHIFT_LEFT : FinalStateNoArg(::BitShiftLeft)
            class OP_SHIFT_RIGHT : FinalStateNoArg(::BitShiftRight)
            class MODULUS : FinalStateNoArg(::Modulus)
        }

        sealed class FinalStateSingleArg(constructor: SingleArgConstructor) : FinalState(constructor) {
            class IDENTIFIER : FinalStateSingleArg(::IdentifierTerminal)
            class INTEGER : FinalStateSingleArg(::IntegerTerminal)
            class FLOAT : FinalStateSingleArg(::FloatTerminal)
            class WHITESPACE : FinalStateSingleArg(::WhitespaceTerminal)
            class COMMENT : FinalStateSingleArg(::CommentTerminal)
            class CLOSING_STRING : FinalStateSingleArg(::StringTerminal)
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
