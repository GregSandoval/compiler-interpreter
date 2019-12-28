package compiler.a5.lexicon

import compiler.lexer.LexicalNode
import compiler.lexer.LexicalNode.FinalState.FinalStateNoArg.*
import compiler.lexer.LexicalNode.FinalState.FinalStateSingleArg.*
import compiler.lexer.LexicalNode.NonFinalState.*
import java.util.*
import java.util.function.Predicate
import kotlin.collections.HashMap

typealias EdgeFunction = (Char) -> Optional<LexicalNode>

typealias Transitions = MutableList<EdgeFunction>
typealias DFA = MutableMap<LexicalNode, Transitions>

/**
 * This file creates the DFA nodes and edges
 */

data class A5LexiconDFA(val dfa: DFA = HashMap()) {
    init {
        // WHITESPACE STATE
        START to WHITESPACE on (AWhitespace or ALineSeparator)
        WHITESPACE to WHITESPACE on (AWhitespace or ANewline)

        // IDENTIFIER
        START to IDENTIFIER on (ALetter or AUnderscore)
        IDENTIFIER to IDENTIFIER on (AUnderscore or ADigit or ALetter)

        // INTEGER
        START to INTEGER on ADigit
        INTEGER to INTEGER on ADigit
        PLUS to INTEGER on ADigit
        MINUS to INTEGER on ADigit

        // FLOAT
        INTEGER to MAYBE_FLOAT on APeriod
        MAYBE_FLOAT to FLOAT on ADigit
        FLOAT to FLOAT on ADigit

        // STRING
        START to OPENING_STRING on AQuote
        OPENING_STRING to STRING_CONTENTS on (ANotNewline and !AQuote)
        OPENING_STRING to CLOSING_STRING on AQuote
        STRING_CONTENTS to STRING_CONTENTS on (ANotNewline and !AQuote)
        STRING_CONTENTS to CLOSING_STRING on AQuote

        // COMMENT
        FORWARD_SLASH to COMMENT on AForwardSlash
        COMMENT to COMMENT on ANotNewline

        // UNPAIRED DELIMITERS
        START to COMMA on AComma
        START to SEMI_COLON on ASemiColon

        // PAIRED DELIMITERS
        START to LESS_THAN on ALessThan
        START to GREATER_THAN on AGreaterThan
        START to LEFT_BRACE on ALeftBrace
        START to RIGHT_BRACE on ARightBrace
        START to LEFT_BRACKET on ALeftBracket
        START to RIGHT_BRACKET on ARightBracket
        START to LEFT_PAREN on ALeftParen
        START to RIGHT_PAREN on ARightParen

        // OTHER PUNCTUATION
        START to ASTERISK on AAsterisk
        START to CARET on ACaret
        START to COLON on AColon
        START to PERIOD on APeriod
        START to EQUAL on AEqual
        START to MINUS on AMinus
        START to PLUS on APlus
        START to FORWARD_SLASH on AForwardSlash
        START to AND on AAnd
        START to EXCLAMATION_MARK on AExclamationMark

        // MULTI CHARACTER OPERATORS
        MINUS to OP_ARROW on AGreaterThan
        EQUAL to OP_EQUAL on AEqual
        EXCLAMATION_MARK to OP_NEGATE on AEqual
        LESS_THAN to OP_LESS_THAN on AEqual
        GREATER_THAN to OP_GREATER_THAN on AEqual
        LESS_THAN to OP_SHIFT_LEFT on ALessThan
        GREATER_THAN to OP_SHIFT_RIGHT on AGreaterThan
    }

    private infix fun LexicalNode.to(end: LexicalNode): EdgeBuilderAlt {
        return object : EdgeBuilderAlt {
            override fun on(predicate: Predicate<Char>) {
                val edges = dfa.getOrPut(this@to, ::mutableListOf)
                edges += { if (predicate.test(it)) Optional.of(end) else Optional.empty() }
            }
        }
    }

    private interface EdgeBuilderAlt {
        infix fun on(predicate: Predicate<Char>)
    }
}
