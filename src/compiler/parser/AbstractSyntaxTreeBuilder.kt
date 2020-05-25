package compiler.parser

import compiler.a5.grammar.PstToAstNonTerminalVisitor
import compiler.a5.grammar.PstToAstTokenVisitor


object AbstractSyntaxTreeBuilder {

    fun fromParseTree(tree: TreeNode) {
        ParseTreeVisitor.postordervisit(tree, createVisitor())
    }

    fun createVisitor(): (TreeNode) -> Unit {
        val tokenVisitor = PstToAstTokenVisitor()
        val nonTerminalVisitor = PstToAstNonTerminalVisitor()

        return fun(tree: TreeNode) {
            // Remove NonTerminals that derive epsilon
            trim(tree)

            // Contract NonTerminal chains
            contract(tree)

            if (tree is Symbol.NonTerminal) {
                nonTerminalVisitor.accept(tree)
            }

            if (tree is Symbol.Terminal) {
                tokenVisitor.accept(tree)
            }
        }

    }

    private fun trim(tree: TreeNode) {
        tree.children.removeIf { it is Symbol.NonTerminal && it.children.isEmpty() }
    }

    private fun contract(tree: TreeNode) {
        if (tree is Symbol.NonTerminal && tree.children.size == 1) {
            val child = tree.children[0]
            child.parent = tree.parent
            tree.parent!!.children[tree.parent!!.children.indexOf(tree)] = child
            // IMPORTANT DO NOT REMOVE
            tree.children.clear()
        }
    }

}
