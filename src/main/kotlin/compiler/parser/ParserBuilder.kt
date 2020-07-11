package compiler.parser

import compiler.parser.Symbol.NonTerminal
import compiler.parser.lltable.LLTable

class ParserBuilder {
    private var parserListener = ParserListener()
    private lateinit var startSymbol: NonTerminal
    private lateinit var llTable: LLTable

    fun setBeforeRuleListener(listener: BeforeRuleListerner): ParserBuilder {
        this.parserListener = this.parserListener.onBeforeRule(listener)
        return this
    }

    fun setUnexpectedRuleListener(listener: UnexpectedRuleListener): ParserBuilder {
        this.parserListener = this.parserListener.onUnexpectedRule(listener)
        return this
    }

    fun setPredictionNotFoundListener(listener: PredictionNotFoundListener): ParserBuilder {
        this.parserListener = this.parserListener.onPredictionNotFound(listener)
        return this
    }

    fun setNonTerminalReplacedListener(listener: NonTerminalReplacedListener): ParserBuilder {
        this.parserListener = this.parserListener.onNonTerminalReplaced(listener)
        return this
    }

    fun setStartSymbol(startSymbol: NonTerminal): ParserBuilder {
        this.startSymbol = startSymbol
        return this
    }

    fun setLLTable(llTable: LLTable): ParserBuilder {
        this.llTable = llTable
        return this
    }

    fun createParser(): Parser {
        return Parser(startSymbol, parserListener, llTable)
    }

}

