package compiler.lexer.token

import compiler.parser.AbstractGrammarNode

/**
 * The base class for all token classes.
 * All tokens should extend this class.
 */
sealed class Token(val str: String, var lineInfo: LineInfo = LineInfo(0, 0)) : AbstractGrammarNode() {

    sealed class IgnorableTokens(str: String) : Token(str) {
        class CommentToken(str: String) : IgnorableTokens(str)
        class EOFToken : IgnorableTokens("")
        class WhitespaceToken(str: String) : IgnorableTokens(str)
    }

    sealed class TypedToken<Value>(str: String, val value: Value) : Token(str) {
        class IdentifierToken(str: String) : TypedToken<String>(str, str) {
            constructor() : this("Sentinel")
        }

        class FloatToken(str: String) : TypedToken<Float>(str, str.toFloat()) {
            constructor() : this("-0")
        }

        class IntegerToken(str: String) : TypedToken<Int>(str, str.toInt()) {
            constructor() : this("-0")
        }

        class StringToken(str: String) : TypedToken<String>(str.replace("\"", ""), str) {
            constructor() : this("Sentinel")
        }
    }

    sealed class OperatorToken(str: String) : Token(str) {
        class LessThan : OperatorToken("<")
        class GreaterThan : OperatorToken(">")
        class Asterisk : OperatorToken("*")
        class Equal : OperatorToken("=")
        class Minus : OperatorToken("-")
        class Plus : OperatorToken("+")
        class Ampersand : OperatorToken("&")
        class Arrow : OperatorToken("->")
        class EqualEqual : OperatorToken("==")
        class NotEqual : OperatorToken("!=")
        class LessThanOrEqual : OperatorToken("<=")
        class GreaterThanOrEqual : OperatorToken(">=")
        class BitShiftLeft : OperatorToken("<<")
        class BitShiftRight : OperatorToken(">>")
        class Caret : OperatorToken("^")
        class ForwardSlash : OperatorToken("/")
    }

    sealed class SymbolToken(str: String) : Token(str) {
        class Comma : SymbolToken(",")
        class SemiColon : SymbolToken(";")
        class LeftBrace : SymbolToken("{")
        class RightBrace : SymbolToken("}")
        class LeftBracket : SymbolToken("[")
        class RightBracket : SymbolToken("]")
        class LeftParen : SymbolToken("(")
        class RightParen : SymbolToken(")")
        class Colon : SymbolToken(":")
        class Period : SymbolToken(".")
    }

    sealed class KeywordToken(str: String) : Token(str) {
        class ProgramKeywordToken : KeywordToken("prog")
        class MainKeywordToken : KeywordToken("main")
        class FunctionKeywordToken : KeywordToken("fcn")
        class ClassKeywordToken : KeywordToken("class")
        class IfKeywordToken : KeywordToken("if")
        class ElseIfKeywordToken : KeywordToken("elseif")
        class ElseKeywordToken : KeywordToken("else")
        class WhileKeywordToken : KeywordToken("while")
        class InputKeywordToken : KeywordToken("input")
        class PrintKeywordToken : KeywordToken("print")
        class NewKeywordToken : KeywordToken("new")
        class ReturnKeywordToken : KeywordToken("return")
        class VarKeywordToken : KeywordToken("var")

        sealed class TypeToken(str: String) : KeywordToken(str) {
            class VoidToken : TypeToken("void")
            class FloatKeywordToken : TypeToken("float")
            class IntegerKeywordToken : TypeToken("int")
            class StringKeywordToken : TypeToken("string")
        }
    }

}
