package compiler.parser

import compiler.lexer.token.LineInfo
import compiler.parser.Symbol.Terminal

typealias NodeSupplier = () -> TreeNode
typealias TokenClass = Class<out Terminal>

sealed class Symbol : TreeNode() {

    sealed class NonTerminal : Symbol() {
        class NULL_NODE : NonTerminal()
        class ParseTreeSentinel : NonTerminal()
        class Pgm : NonTerminal()
        class Main : NonTerminal()
        class BBlock : NonTerminal()
        class Vargroup : NonTerminal()
        class PPvarlist : NonTerminal()
        class Varlist : NonTerminal()
        class Varitem : NonTerminal()
        class Varitem_Suffix : NonTerminal()
        class Vardecl : NonTerminal()
        class Simplekind : NonTerminal()
        class BaseKind : NonTerminal()
        class Classid : NonTerminal()
        class Varspec : NonTerminal()
        class Varid : NonTerminal()
        class Arrspec : NonTerminal()
        class KKint : NonTerminal()
        class Deref_id : NonTerminal()
        class Deref : NonTerminal()
        class Varinit : NonTerminal()
        class BBexprs : NonTerminal()
        class Exprlist : NonTerminal()
        class Moreexprs : NonTerminal()
        class Classdecl : NonTerminal()
        class Classdef : NonTerminal()
        class Classdef_Suffix : NonTerminal()
        class BBClassitems : NonTerminal()
        class Classheader : NonTerminal()
        class Classmom : NonTerminal()
        class Classitems : NonTerminal()
        class Classgroup : NonTerminal()
        class Class_ctrl : NonTerminal()
        class Interfaces : NonTerminal()
        class Mddecls : NonTerminal()
        class Mdheader : NonTerminal()
        class Md_id : NonTerminal()
        class Fcndefs : NonTerminal()
        class Fcndef : NonTerminal()
        class Fcnheader : NonTerminal()
        class Fcnid : NonTerminal()
        class Retkind : NonTerminal()
        class PParmlist : NonTerminal()
        class Varspecs : NonTerminal()
        class More_varspecs : NonTerminal()
        class PPonly : NonTerminal()
        class Stmts : NonTerminal()
        class Stmt : NonTerminal()
        class StasgnOrFcall : NonTerminal()
        class StasgnOrFcall_Suffix : NonTerminal()
        class Stasgn_Suffix : NonTerminal()
        class Lval_Suffix : NonTerminal()
        class Lval : NonTerminal()
        class LvalOrFcall : NonTerminal()
        class LvalOrFcall_Suffix : NonTerminal()
        class Lval_Tail : NonTerminal()
        class KKexpr : NonTerminal()
        class Fcall : NonTerminal()
        class PPexprs : NonTerminal()
        class Stif : NonTerminal()
        class Elsepart : NonTerminal()
        class Stwhile : NonTerminal()
        class Stprint : NonTerminal()
        class Stinput : NonTerminal()
        class Strtn : NonTerminal()
        class Strtn_Suffix : NonTerminal()
        class PPexpr : NonTerminal()
        class Expr : NonTerminal()
        class Expr_Tail : NonTerminal()
        class Rterm : NonTerminal()
        class Rterm_Tail : NonTerminal()
        class Term : NonTerminal()
        class Term_Tail : NonTerminal()
        class Fact : NonTerminal()
        class BaseLiteral : NonTerminal()
        class Addrof_id : NonTerminal()
        class Oprel : NonTerminal()
        class Lthan : NonTerminal()
        class Gthan : NonTerminal()
        class Opadd : NonTerminal()
        class Opmul : NonTerminal()
        class Epsilon : NonTerminal()

        override fun toString(): String {
            return this.javaClass.simpleName
        }


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
}

