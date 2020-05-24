package compiler.parser

import compiler.lexer.token.LineInfo
import compiler.parser.Language.Token
import java.util.*
import java.util.stream.Collectors.toUnmodifiableList

typealias NodeSupplier = () -> TreeNode
typealias TokenClass = Class<out Token>

sealed class Language : TreeNode() {
    sealed class Grammar : Language() {
        class NULL_NODE : Grammar()
        class ParseTreeSentinel : Grammar()
        class Pgm : Grammar()
        class Main : Grammar()
        class BBlock : Grammar()
        class Vargroup : Grammar()
        class PPvarlist : Grammar()
        class Varlist : Grammar()
        class Varitem : Grammar()
        class Varitem_Suffix : Grammar()
        class Vardecl : Grammar()
        class Simplekind : Grammar()
        class BaseKind : Grammar()
        class Classid : Grammar()
        class Varspec : Grammar()
        class Varid : Grammar()
        class Arrspec : Grammar()
        class KKint : Grammar()
        class Deref_id : Grammar()
        class Deref : Grammar()
        class Varinit : Grammar()
        class BBexprs : Grammar()
        class Exprlist : Grammar()
        class Moreexprs : Grammar()
        class Classdecl : Grammar()
        class Classdef : Grammar()
        class Classdef_Suffix : Grammar()
        class BBClassitems : Grammar()
        class Classheader : Grammar()
        class Classmom : Grammar()
        class Classitems : Grammar()
        class Classgroup : Grammar()
        class Class_ctrl : Grammar()
        class Interfaces : Grammar()
        class Mddecls : Grammar()
        class Mdheader : Grammar()
        class Md_id : Grammar()
        class Fcndefs : Grammar()
        class Fcndef : Grammar()
        class Fcnheader : Grammar()
        class Fcnid : Grammar()
        class Retkind : Grammar()
        class PParmlist : Grammar()
        class Varspecs : Grammar()
        class More_varspecs : Grammar()
        class PPonly : Grammar()
        class Stmts : Grammar()
        class Stmt : Grammar()
        class StasgnOrFcall : Grammar()
        class StasgnOrFcall_Suffix : Grammar()
        class Stasgn_Suffix : Grammar()
        class Lval_Suffix : Grammar()
        class Lval : Grammar()
        class LvalOrFcall : Grammar()
        class LvalOrFcall_Suffix : Grammar()
        class Lval_Tail : Grammar()
        class KKexpr : Grammar()
        class Fcall : Grammar()
        class PPexprs : Grammar()
        class Stif : Grammar()
        class Elsepart : Grammar()
        class Stwhile : Grammar()
        class Stprint : Grammar()
        class Stinput : Grammar()
        class Strtn : Grammar()
        class Strtn_Suffix : Grammar()
        class PPexpr : Grammar()
        class Expr : Grammar()
        class Expr_Tail : Grammar()
        class Rterm : Grammar()
        class Rterm_Tail : Grammar()
        class Term : Grammar()
        class Term_Tail : Grammar()
        class Fact : Grammar()
        class BaseLiteral : Grammar()
        class Addrof_id : Grammar()
        class Oprel : Grammar()
        class Lthan : Grammar()
        class Gthan : Grammar()
        class Opadd : Grammar()
        class Opmul : Grammar()
        class Epsilon : Grammar()

        init {
            if (!LLTable.containsKey(this.javaClass)) {
                LLTable[this.javaClass] = HashMap()
            }
        }

        fun getRHS(token: TokenClass): List<TreeNode>? {
            val table = LLTable.getOrDefault(this.javaClass, emptyMap<TokenClass, List<NodeSupplier>>())
            val simpleRules = table[token] ?: return null

            return simpleRules
                    .stream()
                    .map { it() }
                    .collect(toUnmodifiableList())
        }

        fun getRHS(): Set<TokenClass> = LLTable[this.javaClass]!!.keys

        fun on(vararg first: TokenClass): RuleBuilderSecondStep {
            return RuleBuilderSecondStep(*first)
        }

        inner class RuleBuilderFirstStep {
            fun on(vararg first: TokenClass): RuleBuilderSecondStep {
                return RuleBuilderSecondStep(*first)
            }
        }

        inner class RuleBuilderSecondStep constructor(private vararg val firstItems: TokenClass) {
            fun useRHS(vararg rest: NodeSupplier): RuleBuilderFirstStep {
                val rhs = ArrayList(listOf(*rest))
                for (token in firstItems) {
                    if (LLTable[this@Grammar.javaClass]!!.containsKey(token)) {
                        throw RuntimeException("Double stuffed LL Table: Rule: " + this@Grammar.javaClass.simpleName + " Token: " + token.simpleName)
                    }
                    LLTable[this@Grammar.javaClass]!![token] = rhs
                }
                return RuleBuilderFirstStep()
            }

        }

        companion object {
            private val LLTable: MutableMap<Class<out Language>, MutableMap<TokenClass, List<NodeSupplier>>> =
                    HashMap()
        }

        override fun toString(): String {
            return this.javaClass.simpleName
        }


    }

    sealed class Token(val str: String, var lineInfo: LineInfo = LineInfo(0, 0)) : Language() {

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


}

