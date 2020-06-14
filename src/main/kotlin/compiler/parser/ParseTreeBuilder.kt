package compiler.parser

import compiler.parser.Symbol.NonTerminal
import compiler.parser.Symbol.Terminal
import compiler.parser.lltable.LLTable

class ParseTreeBuilder {
    private lateinit var startSymbol: NonTerminal
    private lateinit var llTable: LLTable
    private lateinit var terminals: List<Terminal>

    fun setStartSymbol(startSymbol: NonTerminal, llTable: LLTable): ParseTreeBuilder {
        this.startSymbol = startSymbol
        this.llTable = llTable
        return this
    }

    fun setTerminals(terminals: List<Terminal>): ParseTreeBuilder {
        this.terminals = terminals
        return this
    }


    fun build(): ParseTree {
        return ParseTree(this.startSymbol, this.llTable, this.terminals)
    }

}
