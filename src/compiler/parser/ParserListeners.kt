package compiler.parser

import compiler.parser.Language.Token
import compiler.utils.TriConsumer
import java.util.*
import java.util.function.BiConsumer

typealias BeforeRuleApplicationListener = BiConsumer<LinkedList<TreeNode>, Token>
typealias GeneralListener = BiConsumer<TreeNode, Token>
typealias GrammarRuleApplicationListener = TriConsumer<TreeNode, Token, List<TreeNode>>

object ParserListeners {
    fun BeforeRuleApplicationListenerIdentity(): BeforeRuleApplicationListener {
        return BeforeRuleApplicationListener { _, _ -> }
    }

    fun GeneralListenerIdentity(): GeneralListener {
        return GeneralListener { _, _ -> }
    }

    fun GrammarRuleApplicationIdentity(): GrammarRuleApplicationListener {
        return object : GrammarRuleApplicationListener {
            override fun accept(p1: TreeNode, p2: Token, p3: List<TreeNode>) {
            }
        }
    }
}
