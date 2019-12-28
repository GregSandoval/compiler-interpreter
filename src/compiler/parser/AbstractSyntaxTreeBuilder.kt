package compiler.parser

import compiler.a5.grammar.GrammarNodeVisitor
import compiler.a5.grammar.PstToAstGrammarVisitor
import compiler.a5.grammar.PstToAstTokenVisitor
import compiler.lexer.token.Token
import compiler.parser.visitors.TokenVisitor

object AbstractSyntaxTreeBuilder {
    fun fromParseTree(tree: AbstractGrammarNode?) {
        val tokenVisitor = PstToAstTokenVisitor()
        val grammarVisitor = PstToAstGrammarVisitor()
        postordervisit(tree, tokenVisitor, grammarVisitor)
    }

    fun postordervisit(tree: AbstractGrammarNode?, tokenVisitor: TokenVisitor<*>, grammarVisitor: GrammarNodeVisitor) {
        if (tree == null) {
            return
        }
        for (child in tree.children) {
            postordervisit(child, tokenVisitor, grammarVisitor)
        }
        trim(tree)
        contract(tree)
        if (tree is GrammarNode) {
            grammarVisitor.accept(tree)
        }
        if (tree is Token) {
            tree.accept(tokenVisitor)
        }
    }

    private fun trim(tree: AbstractGrammarNode) {
        tree.children.removeIf { it is GrammarNode && it.children.isEmpty() }
    }

    private fun contract(tree: AbstractGrammarNode) {
        if (tree is GrammarNode && tree.children.size == 1) {
            val child = tree.children[0]
            child.parent = tree.parent
            tree.parent!!.children[tree.parent!!.children.indexOf(tree)] = child
            // IMPORTANT DO NOT REMOVE
            tree.children.clear()
        }
    }
}
