package compiler.parser

import compiler.lexer.token.Token
import compiler.utils.TriConsumer
import java.util.*
import java.util.function.BiConsumer

typealias BeforeRuleApplicationListener = BiConsumer<LinkedList<AbstractGrammarNode>, Token>
typealias GeneralListener = BiConsumer<AbstractGrammarNode, Token>
typealias GrammarRuleApplicationListener = TriConsumer<AbstractGrammarNode, Token, List<AbstractGrammarNode>>

object ParserListeners {
    fun BeforeRuleApplicationListenerIdentity(): BeforeRuleApplicationListener {
        return BeforeRuleApplicationListener { _, _ -> }
    }

    fun GeneralListenerIdentity(): GeneralListener {
        return GeneralListener { _, _ -> }
    }

    fun GrammarRuleApplicationIdentity(): GrammarRuleApplicationListener {
        return object : GrammarRuleApplicationListener {
            override fun accept(p1: AbstractGrammarNode, p2: Token, p3: List<AbstractGrammarNode>) {
            }
        }
    }
}
