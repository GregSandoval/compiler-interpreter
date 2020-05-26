package compiler.parser

import compiler.lexer.token.LineInfo
import compiler.parser.Symbol.Terminal
import kotlin.reflect.KClass

typealias NodeSupplier = () -> Symbol
typealias TokenClass = KClass<out Terminal>

sealed class Symbol : TreeNode() {

    sealed class NonTerminal : Symbol() {
        class NULL_NODE : NonTerminal()
        class ParseTreeSentinel : NonTerminal()
        class Program : NonTerminal()
        class Main : NonTerminal()
        class BasicBlock : NonTerminal()
        class VariableGroup : NonTerminal()
        class ParenthesizedVariabledList : NonTerminal()
        class VariableList : NonTerminal()
        class VariableItem : NonTerminal()
        class VariableItemSuffix : NonTerminal()
        class VariableDecleration : NonTerminal()
        class Simplekind : NonTerminal()
        class BaseKind : NonTerminal()
        class ClassIdentifier : NonTerminal()
        class VariableSpec : NonTerminal()
        class VariableIdentifier : NonTerminal()
        class ArraySpec : NonTerminal()
        class KKint : NonTerminal()
        class DereferencedIdentifier : NonTerminal()
        class Dereference : NonTerminal()
        class VariableInitializer : NonTerminal()
        class BracedExprersions : NonTerminal()
        class ExpressionList : NonTerminal()
        class MoreExpressions : NonTerminal()
        class Classdecl : NonTerminal()
        class ClassDefinition : NonTerminal()
        class ClassDefinitionSuffix : NonTerminal()
        class BracedClassItems : NonTerminal()
        class ClassHeader : NonTerminal()
        class ClassParent : NonTerminal()
        class ClassItems : NonTerminal()
        class ClassGroup : NonTerminal()
        class ClassVisibility : NonTerminal()
        class Interfaces : NonTerminal()
        class MethodDeclerations : NonTerminal()
        class MethodHeader : NonTerminal()
        class MethodIdentifier : NonTerminal()
        class FunctionDefinitions : NonTerminal()
        class FunctionDefinition : NonTerminal()
        class FunctionHeader : NonTerminal()
        class FunctionIdentifier : NonTerminal()
        class ReturnKind : NonTerminal()
        class ParenthesizedParameterList : NonTerminal()
        class VariableSpecs : NonTerminal()
        class MoreVariableSpecs : NonTerminal()
        class PPonly : NonTerminal()
        class Statements : NonTerminal()
        class Statement : NonTerminal()
        class AssignmentOrFunction : NonTerminal()
        class AssignmentOrFunctionSuffix : NonTerminal()
        class AssignmentSuffix : NonTerminal()
        class LeftValueSuffix : NonTerminal()
        class Lval : NonTerminal()
        class LeftValueOrFunction : NonTerminal()
        class LeftValueOrFunctionSuffix : NonTerminal()
        class Lval_Tail : NonTerminal()
        class BracketedExpression : NonTerminal()
        class Fcall : NonTerminal()
        class ParenthesizedExpressions : NonTerminal()
        class IfStatement : NonTerminal()
        class ElsePartialStatement : NonTerminal()
        class WhileStatement : NonTerminal()
        class PrintStatement : NonTerminal()
        class InputStatement : NonTerminal()
        class ReturnStatement : NonTerminal()
        class ReturnStatementSuffix : NonTerminal()
        class ParenthesizedExpression : NonTerminal()
        class Expression : NonTerminal()
        class ExpressionSuffix : NonTerminal()
        class Rterm : NonTerminal()
        class RtermSuffix : NonTerminal()
        class Term : NonTerminal()
        class TermSuffix : NonTerminal()
        class Fact : NonTerminal()
        class BaseLiteral : NonTerminal()
        class AddressOfIdentifier : NonTerminal()
        class RelationalOperator : NonTerminal()
        class Lthan : NonTerminal()
        class Gthan : NonTerminal()
        class PlusOrMinus : NonTerminal()
        class MultiplyOrDivideOrExponentiate : NonTerminal()
        class Epsilon : NonTerminal()
    }

    sealed class Terminal(val str: String, var lineInfo: LineInfo = LineInfo(0, 0)) : Symbol() {
        sealed class Ignorable(str: String) : Terminal(str) {
            class CommentTerminal(str: String) : Ignorable(str)
            class EOFTerminal : Ignorable("")
            class WhitespaceTerminal(str: String) : Ignorable(str)
        }

        sealed class TypedTerminal<Value>(str: String, val value: Value) : Terminal(str) {
            class IdentifierTerminal(str: String) : TypedTerminal<String>(str, str) {
                constructor() : this("Sentinel")
            }

            class FloatTerminal(str: String) : TypedTerminal<Float>(str, str.toFloat()) {
                constructor() : this("-0")
            }

            class IntegerTerminal(str: String) : TypedTerminal<Int>(str, str.toInt()) {
                constructor() : this("-0")
            }

            class StringTerminal(str: String) : TypedTerminal<String>(str.replace("\"", ""), str) {
                constructor() : this("Sentinel")
            }
        }

        sealed class Operator(str: String) : Terminal(str) {
            class LessThan : Operator("<")
            class GreaterThan : Operator(">")
            class Asterisk : Operator("*")
            class Equal : Operator("=")
            class Minus : Operator("-")
            class Plus : Operator("+")
            class Ampersand : Operator("&")
            class Arrow : Operator("->")
            class EqualEqual : Operator("==")
            class NotEqual : Operator("!=")
            class LessThanOrEqual : Operator("<=")
            class GreaterThanOrEqual : Operator(">=")
            class BitShiftLeft : Operator("<<")
            class BitShiftRight : Operator(">>")
            class Caret : Operator("^")
            class ForwardSlash : Operator("/")
        }

        sealed class Punctuation(str: String) : Terminal(str) {
            class Comma : Punctuation(",")
            class SemiColon : Punctuation(";")
            class Colon : Punctuation(":")
            class Period : Punctuation(".")
            class LeftBrace : Punctuation("{")
            class RightBrace : Punctuation("}")
            class LeftBracket : Punctuation("[")
            class RightBracket : Punctuation("]")
            class LeftParen : Punctuation("(")
            class RightParen : Punctuation(")")
        }

        sealed class Keyword(str: String) : Terminal(str) {
            class ProgramKeyword : Keyword("prog")
            class MainKeyword : Keyword("main")
            class FunctionKeyword : Keyword("fcn")
            class ClassKeyword : Keyword("class")
            class IfKeyword : Keyword("if")
            class ElseIfKeyword : Keyword("elseif")
            class ElseKeyword : Keyword("else")
            class WhileKeyword : Keyword("while")
            class InputKeyword : Keyword("input")
            class PrintKeyword : Keyword("print")
            class NewKeyword : Keyword("new")
            class ReturnKeyword : Keyword("return")
            class VarKeyword : Keyword("var")

            sealed class Type(str: String) : Keyword(str) {
                class Void : Type("void")
                class FloatKeyword : Type("float")
                class IntegerKeyword : Type("int")
                class StringKeyword : Type("string")
            }
        }

    }

    override fun toString(): String {
        return this.javaClass.simpleName
    }
}

