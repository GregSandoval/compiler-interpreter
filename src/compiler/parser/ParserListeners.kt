package compiler.parser

import compiler.parser.Symbols.Terminal
import compiler.utils.TriConsumer
import java.util.*
import java.util.function.BiConsumer

typealias BeforeRuleApplicationListener = BiConsumer<LinkedList<TreeNode>, Terminal>
typealias GeneralListener = BiConsumer<TreeNode, Terminal>
typealias GrammarRuleApplicationListener = TriConsumer<TreeNode, Terminal, List<TreeNode>>

object ParserListeners {
    fun BeforeRuleApplicationListenerIdentity(): BeforeRuleApplicationListener {
        return BeforeRuleApplicationListener { _, _ -> }
    }

    fun GeneralListenerIdentity(): GeneralListener {
        return GeneralListener { _, _ -> }
    }

    fun GrammarRuleApplicationIdentity(): GrammarRuleApplicationListener {
        return object : GrammarRuleApplicationListener {
            override fun accept(p1: TreeNode, p2: Terminal, p3: List<TreeNode>) {
            }
        }
    }
}
