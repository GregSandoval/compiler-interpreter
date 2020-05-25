package compiler.parser

import compiler.parser.Symbol.NonTerminal
import compiler.parser.Symbol.NonTerminal.ParseTreeSentinel
import compiler.parser.Symbol.Terminal
import compiler.parser.Symbol.Terminal.Ignorable.EOFTerminal

class ParseTreeBuilder {
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
                .setNonTerminalReplacedListener(ParseTreeLinker::link)
                .createParser()
                .parse(inputName, terminals, llTable)
        return root
    }

}
