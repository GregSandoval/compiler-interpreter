package compiler.a5.lexicon

import compiler.lexer.LexicalNode
import compiler.lexer.LexicalNode.FinalState.FinalStateNoArg.*
import compiler.lexer.LexicalNode.FinalState.FinalStateSingleArg.*
import compiler.lexer.LexicalNode.NonFinalState.*

/**
 * This file creates the DFA nodes and edges.
 */
class A5LexiconDFA {
    fun build(): LexicalNode {
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
