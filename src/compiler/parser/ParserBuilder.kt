package compiler.parser

import compiler.parser.ParserListeners.BeforeRuleApplicationListenerIdentity
import compiler.parser.ParserListeners.GeneralListenerIdentity
import compiler.parser.ParserListeners.GrammarRuleApplicationIdentity
import compiler.parser.Symbol.NonTerminal

class ParserBuilder {
    private var beforeRuleApplication = BeforeRuleApplicationListenerIdentity()
    private var onGrammarRuleApplication = GrammarRuleApplicationIdentity()
    private var onPredictionNotFoundError = GeneralListenerIdentity()
    private var onUnknownGrammarRule = GeneralListenerIdentity()
    private var onUnexpectedToken = GeneralListenerIdentity()
    private lateinit var startSymbol: NonTerminal

    fun setStartSymbol(startSymbol: NonTerminal): ParserBuilder {
        this.startSymbol = startSymbol
        return this
    }


    fun beforeRuleApplication(beforeRuleApplication: BeforeRuleApplicationListener): ParserBuilder {
        this.beforeRuleApplication = this.beforeRuleApplication.andThen(beforeRuleApplication)
        return this
    }

    fun onUnexpectedToken(onUnexpectedToken: GeneralListener): ParserBuilder {
        this.onUnexpectedToken = this.onUnexpectedToken.andThen(onUnexpectedToken)
        return this
    }

    fun onUnknownGrammarRule(onUnknownGrammarRule: GeneralListener): ParserBuilder {
        this.onUnknownGrammarRule = this.onUnknownGrammarRule.andThen(onUnknownGrammarRule)
        return this
    }

    fun onPredictionNotFoundError(onPredictionNotFoundError: GeneralListener): ParserBuilder {
        this.onPredictionNotFoundError = this.onPredictionNotFoundError.andThen(onPredictionNotFoundError)
        return this
    }

    fun onGrammarRuleApplication(onGrammarRuleApplication: GrammarRuleApplicationListener): ParserBuilder {
        this.onGrammarRuleApplication = this.onGrammarRuleApplication.andThen(onGrammarRuleApplication)
        return this
    }

    fun createParser(): Parser {
        return Parser(
                startSymbol,
                beforeRuleApplication,
                onUnexpectedToken,
                onUnknownGrammarRule,
                onPredictionNotFoundError,
                onGrammarRuleApplication
        )
    }

}
