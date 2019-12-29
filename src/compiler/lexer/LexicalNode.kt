package compiler.lexer

import compiler.lexer.token.Token
import compiler.lexer.token.Token.IgnorableTokens.CommentToken
import compiler.lexer.token.Token.IgnorableTokens.WhitespaceToken
import compiler.lexer.token.Token.OperatorToken.*
import compiler.lexer.token.Token.SymbolToken.*
import compiler.lexer.token.Token.TypedToken.*

typealias NoArgConstructor = () -> Token

typealias SingleArgConstructor = (String) -> Token

sealed class LexicalNode {

    object ERROR : LexicalNode()
    sealed class NonFinalState : LexicalNode() {
        object START : NonFinalState()
        object MAYBE_FLOAT : NonFinalState()
        object OPENING_STRING : NonFinalState()
        object STRING_CONTENTS : NonFinalState()
        object EXCLAMATION_MARK : NonFinalState()
        object END_OF_TERMINAL : NonFinalState()
        object FATAL_ERROR : NonFinalState()
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
            object IDENTIFIER : FinalStateSingleArg(::IdentifierToken)
            object INTEGER : FinalStateSingleArg(::IntegerToken)
            object FLOAT : FinalStateSingleArg(::FloatToken)
            object WHITESPACE : FinalStateSingleArg(::WhitespaceToken)
            object COMMENT : FinalStateSingleArg(::CommentToken)
            object CLOSING_STRING : FinalStateSingleArg(::StringToken)
        }
    }

    override fun toString(): String {
        return this.javaClass.simpleName
    }
}
