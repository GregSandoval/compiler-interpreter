package compiler.parser

import java.util.*

abstract class TreeNode {
    var parent: TreeNode? = null
    var UUID: String
    var children = LinkedList<TreeNode>()

    companion object {
        private var UUID_COUNTER = 0
    }

    init {
        UUID = "" + UUID_COUNTER++
    }
}
