package compiler.lexer.token

import compiler.parser.AbstractGrammarNode

/**
 * The base class for all token classes.
 * All tokens should extend this class.
 */
sealed class Token constructor(val str: String, val tokenID: Int, var lineNumber: Int = 0, var linePosition: Int = 0) : AbstractGrammarNode() {
    sealed class IgnoredTokens constructor(str: String, UUID: Int) : Token(str, UUID) {
        class CommentToken(str: String) : IgnoredTokens(str, 1)
        class EOFToken : IgnoredTokens("", 0)
        class WhitespaceToken(str: String) : IgnoredTokens(str, 100)
    }

    sealed class OperatorToken constructor(str: String, UUID: Int) : Token(str, UUID) {
        class LessThan : OperatorToken("<", 31)
        class GreaterThan : OperatorToken(">", 32)
        class Asterisk : OperatorToken("*", 41)
        class Equal : OperatorToken("=", 45)
        class Minus : OperatorToken("-", 46)
        class Plus : OperatorToken("+", 47)
        class Ampersand : OperatorToken("&", 49)
        class Arrow : OperatorToken("->", 51)
        class EqualEqual : OperatorToken("==", 52)
        class NotEqual : OperatorToken("!=", 53)
        class LessThanOrEqual : OperatorToken("<=", 54)
        class GreaterThanOrEqual : OperatorToken(">=", 55)
        class BitShiftLeft : OperatorToken("<<", 56)
        class BitShiftRight : OperatorToken(">>", 57)
        class Caret : OperatorToken("^", 42)
        class ForwardSlash : OperatorToken("/", 48)
    }

    sealed class KeywordToken constructor(str: String, UUID: Int) : Token(str, UUID) {
        class ProgramKeywordToken : KeywordToken("prog", 10)
        class MainKeywordToken : KeywordToken("main", 11)
        class FunctionKeywordToken : KeywordToken("fcn", 12)
        class ClassKeywordToken : KeywordToken("class", 13)
        class IfKeywordToken : KeywordToken("if", 18)
        class ElseIfKeywordToken : KeywordToken("elseif", 19)
        class ElseKeywordToken : KeywordToken("else", 20)
        class WhileKeywordToken : KeywordToken("while", 21)
        class InputKeywordToken : KeywordToken("input", 22)
        class PrintKeywordToken : KeywordToken("print", 23)
        class NewKeywordToken : KeywordToken("new", 24)
        class ReturnKeywordToken : KeywordToken("return", 25)
        class VarKeywordToken : KeywordToken("var", 2)

        sealed class TypeToken constructor(str: String, UUID: Int) : KeywordToken(str, UUID) {
            class VoidToken : TypeToken("void", -20)
            class FloatKeywordToken : TypeToken("float", 13)
            class IntegerKeywordToken : TypeToken("int", 16)
            class StringKeywordToken : TypeToken("string", 17)
        }
    }

    sealed class SymbolToken(str: String, UUID: Int) : Token(str, UUID) {
        class Comma : SymbolToken(",", 6)
        class SemiColon : SymbolToken(";", 7)
        class LeftBrace : SymbolToken("{", 33)
        class RightBrace : SymbolToken("}", 34)
        class LeftBracket : SymbolToken("[", 35)
        class RightBracket : SymbolToken("]", 36)
        class LeftParen : SymbolToken("(", 37)
        class RightParen : SymbolToken(")", 38)
        class Colon : SymbolToken(":", 43)
        class Period : SymbolToken(".", 44)
    }


    sealed class TypedToken<Value> constructor(str: String, tokenID: Int, val value: Value) : Token(str, tokenID) {

        class FloatToken(str: String) : TypedToken<Float>(str, 4, str.toFloat()) {
            companion object {
                val sentinel = FloatToken("0")
            }
        }

        class IdentifierToken(str: String) : TypedToken<String>(str, 2, str) {
            companion object {
                val sentinel = IdentifierToken("Sentinel")
            }
        }

        class IntegerToken(str: String) : TypedToken<Int>(str, 3, str.toInt()) {
            companion object {
                val sentinel = IntegerToken("0")
            }
        }

        class StringToken(str: String) : TypedToken<String>(str.replace("\"", ""), 5, str) {
            companion object {
                val sentinel = StringToken("")
            }
        }
    }

}
