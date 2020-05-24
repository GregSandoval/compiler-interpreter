package compiler.parser

import compiler.parser.Symbol.NonTerminal
import compiler.parser.Symbol.NonTerminal.ParseTreeSentinel
import compiler.parser.Symbol.Terminal
import compiler.parser.Symbol.Terminal.Ignorable.EOFTerminal

class ParseTreeBuilder : GrammarRuleApplicationListener {
    private lateinit var root: TreeNode
    private lateinit var startSymbol: NonTerminal
    private lateinit var llTable: LLTable
    private lateinit var inputName: String
    private lateinit var terminals: List<Terminal>

    fun setStartSymbol(startSymbol: NonTerminal, llTable: LLTable): ParseTreeBuilder {
        this.startSymbol = startSymbol
        this.llTable = llTable
        return this
    }

    fun setInputSourceName(inputName: String): ParseTreeBuilder {
        this.inputName = inputName
        return this
    }

    fun setTerminals(terminals: List<Terminal>): ParseTreeBuilder {
        this.terminals = terminals
        return this
    }


    fun build(): TreeNode {
        val EOF = EOFTerminal()
        val root = ParseTreeSentinel()
        this.root = root
        root.children.push(EOF)
        root.children.push(startSymbol)
        root.children.forEach { it.parent = this.root }
        ParserBuilder()
                .setStartSymbol(startSymbol)
                .onGrammarRuleApplication(this)
                .createParser()
                .parse(inputName, terminals, llTable)
        return root
    }

    override fun accept(p1: TreeNode, p2: Terminal, p3: List<TreeNode>) {
        if (p1 is Terminal && p1 !is EOFTerminal) {
            p2.parent = p1.parent
            val siblings = p1.parent!!.children
            val topIndex = p1.parent!!.children.indexOf(p1)
            siblings[topIndex] = p2
        }

        if (p1 !is Terminal) {
            p1.children.addAll(p3)
            p3.forEach { it.parent = p1 }
        }
    }

}
