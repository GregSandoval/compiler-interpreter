package compiler.parser

import compiler.a5.grammar.GrammarNodeElement
import compiler.lexer.token.Token
import java.util.*
import java.util.stream.Collectors.toUnmodifiableList

typealias NodeSupplier = () -> AbstractGrammarNode
typealias TokenClass = Class<out Token>

abstract class GrammarNode : AbstractGrammarNode(), GrammarNodeElement {
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
