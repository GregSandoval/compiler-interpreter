package compiler.parser

import compiler.parser.Symbol.NonTerminal

class ParserBuilder {
    private var nonTerminalReplacedListener: NonTerminalReplacedListener = { _, _, _ -> }
    private var predictionNotFoundListener: PredictionNotFoundListener = { _, _ -> }
    private var unexpectedRuleListener: UnexpectedRuleListener = { _, _ -> }
    private var beforeRuleListener: BeforeRuleListerner = { _, _ -> }
    private lateinit var startSymbol: NonTerminal

    fun setStartSymbol(startSymbol: NonTerminal): ParserBuilder {
        this.startSymbol = startSymbol
        return this
    }

    fun setBeforeRuleListener(listener: BeforeRuleListerner): ParserBuilder {
        this.beforeRuleListener = this.beforeRuleListener.andThen(listener)
        return this
    }

    fun setUnexpectedRuleListener(listener: UnexpectedRuleListener): ParserBuilder {
        this.unexpectedRuleListener = this.unexpectedRuleListener.andThen(listener)
        return this
    }

    fun setPredictionNotFoundListener(listener: PredictionNotFoundListener): ParserBuilder {
        this.predictionNotFoundListener = this.predictionNotFoundListener.andThen(listener)
        return this
    }

    fun setNonTerminalReplacedListener(listener: NonTerminalReplacedListener): ParserBuilder {
        this.nonTerminalReplacedListener = this.nonTerminalReplacedListener.andThen(listener)
        return this
    }

    fun createParser(): Parser {
        return Parser(
                startSymbol,
                beforeRuleListener,
                unexpectedRuleListener,
                predictionNotFoundListener,
                nonTerminalReplacedListener
        )
    }

}

