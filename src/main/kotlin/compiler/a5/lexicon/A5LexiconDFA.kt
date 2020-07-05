package compiler.a5.lexicon

import compiler.dfa.DFA
import compiler.dfa.LexicalNode.FinalState.FinalStateNoArg.*
import compiler.dfa.LexicalNode.FinalState.FinalStateSingleArg.*
import compiler.dfa.LexicalNode.NonFinalState.*
import compiler.utils.and
import compiler.utils.not
import compiler.utils.or

/**
 * This file creates the DFA nodes and edges
 */

class A5LexiconDFA : DFA(START()) {
    init {
        // WHITESPACE STATE
        START::class to ::WHITESPACE on (AWhitespace or ALineSeparator)
        WHITESPACE::class to ::WHITESPACE on (AWhitespace or ANewline)

        // IDENTIFIER
        START::class to ::IDENTIFIER on (ALetter or AUnderscore)
        IDENTIFIER::class to ::IDENTIFIER on (AUnderscore or ADigit or ALetter)

        // INTEGER
        START::class to ::INTEGER on ADigit
        INTEGER::class to ::INTEGER on ADigit
        PLUS::class to ::INTEGER on ADigit
        MINUS::class to ::INTEGER on ADigit

        // FLOAT
        INTEGER::class to ::MAYBE_FLOAT on APeriod
        MAYBE_FLOAT::class to ::FLOAT on ADigit
        FLOAT::class to ::FLOAT on ADigit

        // STRING
        START::class to ::OPENING_STRING on AQuote
        OPENING_STRING::class to ::STRING_CONTENTS on (ANotNewline and !AQuote)
        OPENING_STRING::class to ::CLOSING_STRING on AQuote
        STRING_CONTENTS::class to ::STRING_CONTENTS on (ANotNewline and !AQuote)
        STRING_CONTENTS::class to ::CLOSING_STRING on AQuote

        // COMMENT
        FORWARD_SLASH::class to ::COMMENT on AForwardSlash
        COMMENT::class to ::COMMENT on ANotNewline

        // UNPAIRED DELIMITERS
        START::class to ::COMMA on AComma
        START::class to ::SEMI_COLON on ASemiColon

        // PAIRED DELIMITERS
        START::class to ::LESS_THAN on ALessThan
        START::class to ::GREATER_THAN on AGreaterThan
        START::class to ::LEFT_BRACE on ALeftBrace
        START::class to ::RIGHT_BRACE on ARightBrace
        START::class to ::LEFT_BRACKET on ALeftBracket
        START::class to ::RIGHT_BRACKET on ARightBracket
        START::class to ::LEFT_PAREN on ALeftParen
        START::class to ::RIGHT_PAREN on ARightParen

        // OTHER PUNCTUATION
        START::class to ::ASTERISK on AAsterisk
        START::class to ::CARET on ACaret
        START::class to ::COLON on AColon
        START::class to ::PERIOD on APeriod
        START::class to ::EQUAL on AEqual
        START::class to ::MINUS on AMinus
        START::class to ::PLUS on APlus
        START::class to ::FORWARD_SLASH on AForwardSlash
        START::class to ::AND on AAnd
        START::class to ::EXCLAMATION_MARK on AExclamationMark
        START::class to ::MODULUS on APercent

        // MULTI CHARACTER OPERATORS
        MINUS::class to ::OP_ARROW on AGreaterThan
        EQUAL::class to ::OP_EQUAL on AEqual
        EXCLAMATION_MARK::class to ::OP_NEGATE on AEqual
        LESS_THAN::class to ::OP_LESS_THAN on AEqual
        GREATER_THAN::class to ::OP_GREATER_THAN on AEqual
        LESS_THAN::class to ::OP_SHIFT_LEFT on ALessThan
        GREATER_THAN::class to ::OP_SHIFT_RIGHT on AGreaterThan
    }
}
