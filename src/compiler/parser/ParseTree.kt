package compiler.parser

import compiler.parser.Symbol.NonTerminal
import compiler.parser.Symbol.Terminal
import compiler.parser.Symbol.Terminal.Ignorable.EOFTerminal

class ParseTree(startSymbol: NonTerminal, llTable: LLTable, terminals: List<Terminal>) {
    private val tree: TreeNode = NonTerminal.ParseTreeSentinel()
    private val EOF = EOFTerminal()

    init {
        tree.children.push(EOF)
        tree.children.push(startSymbol)
        tree.children.forEach { it.parent = tree }
        ParserBuilder()
                .setStartSymbol(startSymbol)
                .setNonTerminalReplacedListener(this::link)
                .setLLTable(llTable)
                .createParser()
                .parse(terminals)
    }

    fun getTree(): TreeNode {
        return this.tree
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
