package compiler.parser

import java.util.*

abstract class AbstractGrammarNode {
    var parent: AbstractGrammarNode? = null
    var left: AbstractGrammarNode? = null
    var right: AbstractGrammarNode? = null
    var UUID: String
    var children = LinkedList<AbstractGrammarNode>()

    companion object {
        private var UUID_COUNTER = 0
    }

    init {
        UUID = "" + UUID_COUNTER++
    }
}
