package compiler.a5.lexicon

import compiler.lexer.FinalState
import compiler.lexer.LexicalNode
import compiler.lexer.NonFinalState
import compiler.lexer.token.*
import compiler.lexer.token.OperatorToken.*
import compiler.lexer.token.SymbolToken.*

/**
 * This file creates the DFA nodes and edges.
 */
class A5LexiconDFA {

    fun build(): LexicalNode {
        // DFA Grammar
        val START = NonFinalState("START")
        val IDENTIFIER = FinalState("IDENTIFIER", ::IdentifierToken)
        val INTEGER = FinalState("INTEGER", ::IntegerToken)
        val MAYBE_FLOAT = NonFinalState("MAYBE_FLOAT")
        val FLOAT = FinalState("FLOAT", ::FloatToken)
        val OPENING_STRING = NonFinalState("OPENING_STRING")
        val STRING_CONTENTS = NonFinalState("STRING_CONTENTS")
        val CLOSING_STRING = FinalState("CLOSING_STRING", ::StringToken)
        val WHITESPACE = FinalState("WHITESPACE", ::WhitespaceToken)
        val COMMENT = FinalState("COMMENT", ::CommentToken)

        // Unpaired delimiters
        val COMMA = FinalState("COMMA", ::Comma)
        val SEMI_COLON = FinalState("SEMI_COLON", ::SemiColon)

        // Paired delimiters
        val LESS_THAN = FinalState("LESS_THAN", ::LessThan)
        val GREATER_THAN = FinalState("GREATER_THAN", ::GreaterThan)
        val LEFT_BRACE = FinalState("LEFT_BRACE", ::LeftBrace)
        val RIGHT_BRACE = FinalState("RIGHT_BRACE", ::RightBrace)
        val LEFT_BRACKET = FinalState("LEFT_BRACKET", ::LeftBracket)
        val RIGHT_BRACKET = FinalState("RIGHT_BRACKET", ::RightBracket)
        val LEFT_PAREN = FinalState("LEFT_PAREN", ::LeftParen)
        val RIGHT_PAREN = FinalState("RIGHT_PAREN", ::RightParen)

        // Other punctuation tokens
        val ASTERISK = FinalState("ASTERISK", ::Asterisk)
        val CARET = FinalState("CARET", ::Caret)
        val COLON = FinalState("COLON", ::Colon)
        val PERIOD = FinalState("PERIOD", ::Period)
        val EQUAL = FinalState("EQUAL", ::Equal)
        val MINUS = FinalState("MINUS", ::Minus)
        val PLUS = FinalState("PLUS", ::Plus)
        val FORWARD_SLASH = FinalState("FORWARD_SLASH", ::ForwardSlash)
        val AND = FinalState("AND", ::Ampersand)
        val EXCLAMATION_MARK = NonFinalState("EXCLAMATION_MARK")

        // Multi character operators
        val OP_ARROW = FinalState("OP_ARROW", ::Arrow)
        val OP_EQUAL = FinalState("OP_EQUAL", ::EqualEqual)
        val OP_NEGATE = FinalState("OP_NEGATE", ::NotEqual)
        val OP_LESS_THAN = FinalState("OP_LESS_THAN", ::LessThanOrEqual)
        val OP_GREATER_THAN = FinalState("OP_GREATER_THAN", ::GreaterThanOrEqual)
        val OP_SHIFT_LEFT = FinalState("OP_SHIFT_LEFT", ::BitShiftLeft)
        val OP_SHIFT_RIGHT = FinalState("OP_SHIFT_RIGHT", ::BitShiftRight)

        // WHITESPACE STATE
        START.ON(AWhitespace).OR(ALineSeparator).GOTO(WHITESPACE)
        WHITESPACE.ON(AWhitespace).OR(ANewline).GOTO(WHITESPACE)

        // IDENTIFIER
        START.ON(ALetter).OR(AUnderscore).GOTO(IDENTIFIER)
        IDENTIFIER.ON(AUnderscore).OR(ADigit.or(ALetter)).GOTO(IDENTIFIER)

        // INTEGER
        START.ON(ADigit).GOTO(INTEGER)
        INTEGER.ON(ADigit).GOTO(INTEGER)
        PLUS.ON(ADigit).GOTO(INTEGER)
        MINUS.ON(ADigit).GOTO(INTEGER)

        // FLOAT
        INTEGER.ON(APeriod).GOTO(MAYBE_FLOAT)
        MAYBE_FLOAT.ON(ADigit).GOTO(FLOAT)
        FLOAT.ON(ADigit).GOTO(FLOAT)

        // STRING
        START.ON(AQuote).GOTO(OPENING_STRING)
        OPENING_STRING.ON(ANotNewline).AND(AQuote.negate()).GOTO(STRING_CONTENTS)
        OPENING_STRING.ON(AQuote).GOTO(CLOSING_STRING)
        STRING_CONTENTS.ON(ANotNewline).AND(AQuote.negate()).GOTO(STRING_CONTENTS)
        STRING_CONTENTS.ON(AQuote).GOTO(CLOSING_STRING)

        // COMMENT
        FORWARD_SLASH.ON(AForwardSlash).GOTO(COMMENT)
        COMMENT.ON(ANotNewline).GOTO(COMMENT)

        // UNPAIRED DELIMITERS
        START.ON(AComma).GOTO(COMMA)
        START.ON(ASemiColon).GOTO(SEMI_COLON)

        // PAIRED DELIMITERS
        START.ON(ALessThan).GOTO(LESS_THAN)
        START.ON(AGreaterThan).GOTO(GREATER_THAN)
        START.ON(ALeftBrace).GOTO(LEFT_BRACE)
        START.ON(ARightBrace).GOTO(RIGHT_BRACE)
        START.ON(ALeftBracket).GOTO(LEFT_BRACKET)
        START.ON(ARightBracket).GOTO(RIGHT_BRACKET)
        START.ON(ALeftParen).GOTO(LEFT_PAREN)
        START.ON(ARightParen).GOTO(RIGHT_PAREN)

        // OTHER PUNCTUATION
        START.ON(AAsterisk).GOTO(ASTERISK)
        START.ON(ACaret).GOTO(CARET)
        START.ON(AColon).GOTO(COLON)
        START.ON(APeriod).GOTO(PERIOD)
        START.ON(AEqual).GOTO(EQUAL)
        START.ON(AMinus).GOTO(MINUS)
        START.ON(APlus).GOTO(PLUS)
        START.ON(AForwardSlash).GOTO(FORWARD_SLASH)
        START.ON(AAnd).GOTO(AND)
        START.ON(AExclamationMark).GOTO(EXCLAMATION_MARK)

        // MULTI CHARACTER OPERATORS
        MINUS.ON(AGreaterThan).GOTO(OP_ARROW)
        EQUAL.ON(AEqual).GOTO(OP_EQUAL)
        EXCLAMATION_MARK.ON(AEqual).GOTO(OP_NEGATE)
        LESS_THAN.ON(AEqual).GOTO(OP_LESS_THAN)
        GREATER_THAN.ON(AEqual).GOTO(OP_GREATER_THAN)
        LESS_THAN.ON(ALessThan).GOTO(OP_SHIFT_LEFT)
        GREATER_THAN.ON(AGreaterThan).GOTO(OP_SHIFT_RIGHT)
        return START
    }
}
