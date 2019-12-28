package compiler.parser

import compiler.lexer.token.Token
import java.util.*
import java.util.stream.Collectors.toUnmodifiableList

typealias NodeSupplier = () -> AbstractGrammarNode
typealias TokenClass = Class<out Token>

sealed class GrammarNode : AbstractGrammarNode() {
    class NULL_NODE : GrammarNode()
    class ParseTreeSentinel : GrammarNode()
    class Pgm : GrammarNode()
    class Main : GrammarNode()
    class BBlock : GrammarNode()
    class Vargroup : GrammarNode()
    class PPvarlist : GrammarNode()
    class Varlist : GrammarNode()
    class Varitem : GrammarNode()
    class Varitem_Suffix : GrammarNode()
    class Vardecl : GrammarNode()
    class Simplekind : GrammarNode()
    class BaseKind : GrammarNode()
    class Classid : GrammarNode()
    class Varspec : GrammarNode()
    class Varid : GrammarNode()
    class Arrspec : GrammarNode()
    class KKint : GrammarNode()
    class Deref_id : GrammarNode()
    class Deref : GrammarNode()
    class Varinit : GrammarNode()
    class BBexprs : GrammarNode()
    class Exprlist : GrammarNode()
    class Moreexprs : GrammarNode()
    class Classdecl : GrammarNode()
    class Classdef : GrammarNode()
    class Classdef_Suffix : GrammarNode()
    class BBClassitems : GrammarNode()
    class Classheader : GrammarNode()
    class Classmom : GrammarNode()
    class Classitems : GrammarNode()
    class Classgroup : GrammarNode()
    class Class_ctrl : GrammarNode()
    class Interfaces : GrammarNode()
    class Mddecls : GrammarNode()
    class Mdheader : GrammarNode()
    class Md_id : GrammarNode()
    class Fcndefs : GrammarNode()
    class Fcndef : GrammarNode()
    class Fcnheader : GrammarNode()
    class Fcnid : GrammarNode()
    class Retkind : GrammarNode()
    class PParmlist : GrammarNode()
    class Varspecs : GrammarNode()
    class More_varspecs : GrammarNode()
    class PPonly : GrammarNode()
    class Stmts : GrammarNode()
    class Stmt : GrammarNode()
    class StasgnOrFcall : GrammarNode()
    class StasgnOrFcall_Suffix : GrammarNode()
    class Stasgn_Suffix : GrammarNode()
    class Lval_Suffix : GrammarNode()
    class Lval : GrammarNode()
    class LvalOrFcall : GrammarNode()
    class LvalOrFcall_Suffix : GrammarNode()
    class Lval_Tail : GrammarNode()
    class KKexpr : GrammarNode()
    class Fcall : GrammarNode()
    class PPexprs : GrammarNode()
    class Stif : GrammarNode()
    class Elsepart : GrammarNode()
    class Stwhile : GrammarNode()
    class Stprint : GrammarNode()
    class Stinput : GrammarNode()
    class Strtn : GrammarNode()
    class Strtn_Suffix : GrammarNode()
    class PPexpr : GrammarNode()
    class Expr : GrammarNode()
    class Expr_Tail : GrammarNode()
    class Rterm : GrammarNode()
    class Rterm_Tail : GrammarNode()
    class Term : GrammarNode()
    class Term_Tail : GrammarNode()
    class Fact : GrammarNode()
    class BaseLiteral : GrammarNode()
    class Addrof_id : GrammarNode()
    class Oprel : GrammarNode()
    class Lthan : GrammarNode()
    class Gthan : GrammarNode()
    class Opadd : GrammarNode()
    class Opmul : GrammarNode()
    class Epsilon : GrammarNode()

    init {
        if (!LLTable.containsKey(this.javaClass)) {
            LLTable[this.javaClass] = HashMap()
        }
    }

    fun getRHS(token: TokenClass): List<AbstractGrammarNode>? {
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
                if (LLTable[this@GrammarNode.javaClass]!!.containsKey(token)) {
                    throw RuntimeException("Double stuffed LL Table: Rule: " + this@GrammarNode.javaClass.simpleName + " Token: " + token.simpleName)
                }
                LLTable[this@GrammarNode.javaClass]!![token] = rhs
            }
            return RuleBuilderFirstStep()
        }

    }

    companion object {
        private val LLTable: MutableMap<Class<out GrammarNode>, MutableMap<TokenClass, List<NodeSupplier>>> =
                HashMap()
    }

    override fun toString(): String {
        return this.javaClass.simpleName
    }

}
