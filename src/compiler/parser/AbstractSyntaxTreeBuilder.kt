package compiler.parser

import compiler.a5.grammar.GrammarNodeVisitor
import compiler.a5.grammar.PstToAstGrammarVisitor
import compiler.a5.grammar.PstToAstTokenVisitor
import compiler.parser.Language.Grammar
import compiler.parser.Language.Token
import compiler.parser.visitors.TokenVisitor

object AbstractSyntaxTreeBuilder {
    fun fromParseTree(tree: TreeNode?) {
        val tokenVisitor = PstToAstTokenVisitor()
        val grammarVisitor = PstToAstGrammarVisitor()
        postordervisit(tree, tokenVisitor, grammarVisitor)
    }

    fun postordervisit(tree: TreeNode?, tokenVisitor: TokenVisitor<*>, grammarVisitor: GrammarNodeVisitor) {
        if (tree == null) {
            return
        }
        for (child in tree.children) {
            postordervisit(child, tokenVisitor, grammarVisitor)
        }
        trim(tree)
        contract(tree)
        if (tree is Grammar) {
            grammarVisitor.accept(tree)
        }
        if (tree is Token) {
            tokenVisitor.accept(tree)
        }
    }

    private fun trim(tree: TreeNode) {
        tree.children.removeIf { it is Grammar && it.children.isEmpty() }
    }

    private fun contract(tree: TreeNode) {
        if (tree is Grammar && tree.children.size == 1) {
            val child = tree.children[0]
            child.parent = tree.parent
            tree.parent!!.children[tree.parent!!.children.indexOf(tree)] = child
            // IMPORTANT DO NOT REMOVE
            tree.children.clear()
        }
    }
}
