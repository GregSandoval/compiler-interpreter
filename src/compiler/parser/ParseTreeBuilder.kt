package compiler.parser

import compiler.parser.Language.Grammar
import compiler.parser.Language.Grammar.ParseTreeSentinel
import compiler.parser.Language.Token
import compiler.parser.Language.Token.IgnorableTokens.EOFToken

class ParseTreeBuilder {
    private var root: TreeNode? = null

    fun setStartSymbol(startSymbol: Grammar): ParseTreeBuilderFirstStep {
        return object : ParseTreeBuilderFirstStep {
            override fun setInputSourceName(inputName: String): ParseTreeBuilderLastStep {
                return object : ParseTreeBuilderLastStep {
                    override fun build(tokens: List<Token>): TreeNode {
                        val EOF = EOFToken()
                        val root = ParseTreeSentinel()
                        this@ParseTreeBuilder.root = root
                        root.children.push(EOF)
                        root.children.push(startSymbol)
                        root.children.forEach { it.parent = this@ParseTreeBuilder.root }
                        ParserBuilder()
                                .setStartSymbol(startSymbol)
                                .setEOF(EOF)
                                .onGrammarRuleApplication(object : GrammarRuleApplicationListener {
                                    override fun accept(p1: TreeNode, p2: Token, p3: List<TreeNode>) {
                                        this@ParseTreeBuilder.AttachToTree(p1, p2, p3)
                                    }
                                })
                                .createParser()
                                .parse(inputName, tokens)
                        return root
                    }
                }
            }
        }
    }

    private fun AttachToTree(top: TreeNode, token: Token, rhs: List<TreeNode>) {
        if (top is Token && top !is EOFToken) {
            token.parent = top.parent
            val siblings = top.parent!!.children
            val topIndex = top.parent!!.children.indexOf(top)
            siblings[topIndex] = token
        }
        if (top !is Token) {
            top.children.addAll(rhs)
            rhs.forEach { it.parent = top }
        }
    }

    interface ParseTreeBuilderFirstStep {
        fun setInputSourceName(inputName: String): ParseTreeBuilderLastStep
    }

    interface ParseTreeBuilderLastStep {
        @Throws(Exception::class)
        fun build(tokens: List<Token>): TreeNode
    }

}
