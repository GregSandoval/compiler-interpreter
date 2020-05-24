package compiler.parser

import compiler.parser.Symbol.NonTerminal
import compiler.parser.Symbol.NonTerminal.ParseTreeSentinel
import compiler.parser.Symbol.Terminal
import compiler.parser.Symbol.Terminal.Ignorable.EOFTerminal

class ParseTreeBuilder {
    private var root: TreeNode? = null

    fun setStartSymbol(startSymbol: NonTerminal): ParseTreeBuilderFirstStep {
        return object : ParseTreeBuilderFirstStep {
            override fun setInputSourceName(inputName: String): ParseTreeBuilderLastStep {
                return object : ParseTreeBuilderLastStep {
                    override fun build(terminals: List<Terminal>): TreeNode {
                        val EOF = EOFTerminal()
                        val root = ParseTreeSentinel()
                        this@ParseTreeBuilder.root = root
                        root.children.push(EOF)
                        root.children.push(startSymbol)
                        root.children.forEach { it.parent = this@ParseTreeBuilder.root }
                        ParserBuilder()
                                .setStartSymbol(startSymbol)
                                .setEOF(EOF)
                                .onGrammarRuleApplication(object : GrammarRuleApplicationListener {
                                    override fun accept(p1: TreeNode, p2: Terminal, p3: List<TreeNode>) {
                                        this@ParseTreeBuilder.AttachToTree(p1, p2, p3)
                                    }
                                })
                                .createParser()
                                .parse(inputName, terminals)
                        return root
                    }
                }
            }
        }
    }

    private fun AttachToTree(top: TreeNode, terminal: Terminal, rhs: List<TreeNode>) {
        if (top is Terminal && top !is EOFTerminal) {
            terminal.parent = top.parent
            val siblings = top.parent!!.children
            val topIndex = top.parent!!.children.indexOf(top)
            siblings[topIndex] = terminal
        }
        if (top !is Terminal) {
            top.children.addAll(rhs)
            rhs.forEach { it.parent = top }
        }
    }

    interface ParseTreeBuilderFirstStep {
        fun setInputSourceName(inputName: String): ParseTreeBuilderLastStep
    }

    interface ParseTreeBuilderLastStep {
        @Throws(Exception::class)
        fun build(terminals: List<Terminal>): TreeNode
    }

}
