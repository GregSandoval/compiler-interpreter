package compiler.parser

import compiler.a5.grammar.PstToAstNonTerminalVisitor
import compiler.a5.grammar.PstToAstTokenVisitor
import compiler.parser.Symbol.NonTerminal
import compiler.parser.Symbol.Terminal

object AbstractSyntaxTreeBuilder {
    fun fromParseTree(tree: TreeNode) {
        postordervisit(tree, PstToAstTokenVisitor(), PstToAstNonTerminalVisitor())
    }

    fun postordervisit(tree: TreeNode?, tokenVisitor: PstToAstTokenVisitor, nonTerminalVisitor: PstToAstNonTerminalVisitor) {
        if (tree == null) {
            return
        }

        for (child in tree.children) {
            postordervisit(child, tokenVisitor, nonTerminalVisitor)
        }

        // Remove NonTerminals that derive epsilon
        trim(tree)

        // Contract NonTerminal chains
        contract(tree)

        if (tree is NonTerminal) {
            nonTerminalVisitor.accept(tree)
        }

        if (tree is Terminal) {
            tokenVisitor.accept(tree)
        }
    }

    private fun trim(tree: TreeNode) {
        tree.children.removeIf { it is NonTerminal && it.children.isEmpty() }
    }

    private fun contract(tree: TreeNode) {
        if (tree is NonTerminal && tree.children.size == 1) {
            val child = tree.children[0]
            child.parent = tree.parent
            tree.parent!!.children[tree.parent!!.children.indexOf(tree)] = child
            // IMPORTANT DO NOT REMOVE
            tree.children.clear()
        }
    }
}
