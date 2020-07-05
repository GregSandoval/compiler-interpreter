package compiler.parser

class ParserListener(
        val onNonTerminalReplaced: NonTerminalReplacedListener = { _, _, _ -> },
        val onPredictionNotFound: PredictionNotFoundListener = { _, _ -> },
        val onUnexpectedRule: UnexpectedRuleListener = { _, _ -> },
        val onBeforeRule: BeforeRuleListerner = { _, _ -> }
) {

    fun onNonTerminalReplaced(listener: NonTerminalReplacedListener): ParserListener {
        return ParserListener(
                onNonTerminalReplaced.andThen(listener),
                onPredictionNotFound,
                onUnexpectedRule,
                onBeforeRule
        )
    }

    fun onPredictionNotFound(listener: PredictionNotFoundListener): ParserListener {
        return ParserListener(
                onNonTerminalReplaced,
                onPredictionNotFound.andThen(listener),
                onUnexpectedRule,
                onBeforeRule
        )
    }

    fun onUnexpectedRule(listener: UnexpectedRuleListener): ParserListener {
        return ParserListener(
                onNonTerminalReplaced,
                onPredictionNotFound,
                onUnexpectedRule.andThen(listener),
                onBeforeRule
        )
    }

    fun onBeforeRule(listener: BeforeRuleListerner): ParserListener {
        return ParserListener(
                onNonTerminalReplaced,
                onPredictionNotFound,
                onUnexpectedRule,
                onBeforeRule.andThen(listener)
        )
    }
}
