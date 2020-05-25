package compiler.parser

import compiler.parser.Symbol.NonTerminal
import compiler.parser.Symbol.Terminal
import compiler.parser.Symbol.Terminal.Ignorable.EOFTerminal

class ParseTree(startSymbol: NonTerminal, llTable: LLTable, inputName: String, terminals: List<Terminal>) {
    private val root: TreeNode = NonTerminal.ParseTreeSentinel()
    private val EOF = EOFTerminal()

    init {
        root.children.push(EOF)
        root.children.push(startSymbol)
        root.children.forEach { it.parent = root }
        ParserBuilder()
                .setStartSymbol(startSymbol)
                .setNonTerminalReplacedListener(this::link)
                .createParser()
                .parse(inputName, terminals, llTable)
    }

    fun getRoot(): TreeNode {
        return this.root
    }

    fun link(top: TreeNode, token: Terminal, rhs: List<TreeNode>) {
        if (top is Terminal && top !is EOFTerminal) {
            token.parent = top.parent
            val siblings = top.parent!!.children
            val topIndex = top.parent!!.children.indexOf(top)
            siblings[topIndex] = token
        }

        if (top !is Terminal) {
            top.children.addAll(rhs)
            rhs.forEach { it.parent = top }
        }
    }
}
