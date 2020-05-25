package compiler.parser

import compiler.a5.grammar.NonTerminalVisitor
import compiler.a5.grammar.PstToAstGrammarVisitor
import compiler.a5.grammar.PstToAstTokenVisitor
import compiler.parser.Symbol.NonTerminal
import compiler.parser.Symbol.Terminal
import compiler.parser.visitors.TokenVisitor

object AbstractSyntaxTreeBuilder {
    fun fromParseTree(tree: TreeNode?) {
        val tokenVisitor = PstToAstTokenVisitor()
        val grammarVisitor = PstToAstGrammarVisitor()
        postordervisit(tree, tokenVisitor, grammarVisitor)
    }

    fun postordervisit(tree: TreeNode?, tokenVisitor: TokenVisitor<*>, grammarVisitor: NonTerminalVisitor) {
        if (tree == null) {
            return
        }
        for (child in tree.children) {
            postordervisit(child, tokenVisitor, grammarVisitor)
        }
        trim(tree)
        contract(tree)
        if (tree is NonTerminal) {
            grammarVisitor.accept(tree)
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
