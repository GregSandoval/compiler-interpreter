package compiler.parser

import compiler.lexer.token.Token
import compiler.lexer.token.Token.IgnorableTokens.EOFToken
import compiler.parser.ParserListeners.BeforeRuleApplicationListenerIdentity
import compiler.parser.ParserListeners.GeneralListenerIdentity
import compiler.parser.ParserListeners.GrammarRuleApplicationIdentity

class ParserBuilder {
    fun setStartSymbol(startSymbol: GrammarNode): ParserBuilderLastStep {
        return ParserBuilderLastStep(startSymbol)
    }

    class ParserBuilderLastStep constructor(private val startSymbol: GrammarNode) {
        private var beforeRuleApplication = BeforeRuleApplicationListenerIdentity()
        private var onGrammarRuleApplication = GrammarRuleApplicationIdentity()
        private var onPredictionNotFoundError = GeneralListenerIdentity()
        private var onUnknownGrammarRule = GeneralListenerIdentity()
        private var onUnexpectedToken = GeneralListenerIdentity()
        private var eof: Token = EOFToken()
        fun beforeRuleApplication(beforeRuleApplication: BeforeRuleApplicationListener): ParserBuilderLastStep {
            this.beforeRuleApplication = this.beforeRuleApplication.andThen(beforeRuleApplication)
            return this
        }

        fun onUnexpectedToken(onUnexpectedToken: GeneralListener): ParserBuilderLastStep {
            this.onUnexpectedToken = this.onUnexpectedToken.andThen(onUnexpectedToken)
            return this
        }

        fun onUnknownGrammarRule(onUnknownGrammarRule: GeneralListener): ParserBuilderLastStep {
            this.onUnknownGrammarRule = this.onUnknownGrammarRule.andThen(onUnknownGrammarRule)
            return this
        }

        fun onPredictionNotFoundError(onPredictionNotFoundError: GeneralListener): ParserBuilderLastStep {
            this.onPredictionNotFoundError = this.onPredictionNotFoundError.andThen(onPredictionNotFoundError)
            return this
        }

        fun onGrammarRuleApplication(onGrammarRuleApplication: GrammarRuleApplicationListener): ParserBuilderLastStep {
            this.onGrammarRuleApplication = this.onGrammarRuleApplication.andThen(onGrammarRuleApplication)
            return this
        }

        fun setEOF(eof: Token): ParserBuilderLastStep {
            this.eof = eof
            return this
        }

        fun createParser(): Parser {
            return Parser(
                    eof,
                    startSymbol,
                    beforeRuleApplication,
                    onUnexpectedToken,
                    onUnknownGrammarRule,
                    onPredictionNotFoundError,
                    onGrammarRuleApplication
            )
        }

    }
}
