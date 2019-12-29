package compiler.parser

import compiler.lexer.token.Token
import compiler.lexer.token.Token.IgnorableTokens.EOFToken
import compiler.parser.GrammarNode.ParseTreeSentinel

class ParseTreeBuilder {
    private var root: AbstractGrammarNode? = null

    fun setStartSymbol(startSymbol: GrammarNode): ParseTreeBuilderFirstStep {
        return object : ParseTreeBuilderFirstStep {
            override fun setInputSourceName(inputName: String): ParseTreeBuilderLastStep {
                return object : ParseTreeBuilderLastStep {
                    override fun build(tokens: List<Token>): AbstractGrammarNode {
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
                                    override fun accept(p1: AbstractGrammarNode, p2: Token, p3: List<AbstractGrammarNode>) {
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

    private fun AttachToTree(top: AbstractGrammarNode, token: Token, rhs: List<AbstractGrammarNode>) {
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
        fun build(tokens: List<Token>): AbstractGrammarNode
    }

}
