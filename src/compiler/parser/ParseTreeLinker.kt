package compiler.parser

import compiler.parser.Symbol.Terminal
import compiler.parser.Symbol.Terminal.Ignorable.EOFTerminal

object ParseTreeLinker {
    fun link(top: TreeNode, token: Terminal, rhs: List<TreeNode>) {
        if (top is Terminal && top !is EOFTerminal) {
            token.parent = top.parent
            val siblings = top.parent!!.children
            val topIndex = top.parent!!.children.indexOf(top)
            siblings[topIndex] = token
        }

        if (top !is Terminal) {
            top.children.addAll(rhs)
            rhs.forEach { it.parent = top }
        }
    }
}
