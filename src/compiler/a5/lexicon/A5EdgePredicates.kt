package compiler.a5.lexicon

import java.util.function.Predicate

/**
 * A grouping of commonly used functions for detecting character classes.
 * Used to define the conditions for when the lexer should transition
 * to a different state.
 */
typealias CharPredicate = Predicate<Char>

fun isSymbol(char: Char) = CharPredicate { char == it }

// CHARACTER SETS
val ALowerCaseLetter = CharPredicate { it in 'a'..'z' }
val AUpperCaseLetter = CharPredicate { it in 'A'..'Z' }
val ALetter = ALowerCaseLetter.or(AUpperCaseLetter)
val ADigit = CharPredicate { it in '0'..'9' }

// WHITE SPACE AND NEWLINES
val ATab = isSymbol('\t')
val ANewline = isSymbol('\n')
val ACarriageReturn = isSymbol('\r')
val ASpace = isSymbol(' ')
val ALineSeparator = ANewline.or(ACarriageReturn)
val AWhitespace = ATab.or(ASpace)

// UNPAIRED DELIMITERS
val AComma = isSymbol(',')
val ASemiColon = isSymbol(';')

// PAIRED DELIMITERS
val ALessThan = isSymbol('<')
val AGreaterThan = isSymbol('>')
val ALeftBrace = isSymbol('{')
val ARightBrace = isSymbol('}')
val ALeftBracket = isSymbol('[')
val ARightBracket = isSymbol(']')
val ALeftParen = isSymbol('(')
val ARightParen = isSymbol(')')

// OTHER PUNCTUATION
val AAsterisk = isSymbol('*')
val ACaret = isSymbol('^')
val AColon = isSymbol(':')
val APeriod = isSymbol('.')
val AEqual = isSymbol('=')
val AMinus = isSymbol('-')
val APlus = isSymbol('+')
val AForwardSlash = isSymbol('/')
val AAnd = isSymbol('&')
val AQuote = isSymbol('"')
val AUnderscore = isSymbol('_')
val AExclamationMark = isSymbol('!')

// EVERYTHING EXCEPT LINE FEED
val ANotNewline = ALineSeparator.negate()
