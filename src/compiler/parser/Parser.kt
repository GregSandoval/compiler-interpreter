package compiler.parser

import compiler.parser.Language.Grammar
import compiler.parser.Language.Token
import java.util.*
import java.util.stream.Collectors

class Parser(
        eof: Token,
        startSymbol: Grammar,
        beforeRuleApplication: BeforeRuleApplicationListener,
        onUnexpectedToken: GeneralListener,
        onUnknownGrammarRule: GeneralListener,
        onPredictionNotFoundError: GeneralListener,
        onGrammarRuleApplication: GrammarRuleApplicationListener
) {
    private val startSymbol: Grammar
    private val eof: TreeNode
    private val beforeRuleApplication: BeforeRuleApplicationListener
    private val onUnexpectedToken: GeneralListener
    private val onUnknownGrammarRule: GeneralListener
    private val onPredictionNotFoundError: GeneralListener
    private val onGrammarRuleApplication: GrammarRuleApplicationListener

    @Throws(Exception::class)
    fun parse(inputName: String, tokensIn: List<Token>) {
        val stack = LinkedList<TreeNode>()
        val tokens = LinkedList(tokensIn)
        stack.push(startSymbol)
        while (!tokens.isEmpty() && !stack.isEmpty()) {
            beforeRuleApplication.accept(stack, tokens.peek())
            val token = tokens.peek()
            val top = stack.pop()
            if (top is Token && isEqual(token, top)) {
                tokens.pop()
                onGrammarRuleApplication.accept(top, token, emptyList())
                continue
            }
            if (top is Token) {
                onUnexpectedToken.accept(top, token)
                throw UnexpectedToken(top, token, inputName)
            }
            if (top !is Grammar) {
                onUnknownGrammarRule.accept(top, token)
                throw RuntimeException("Node is neither Token nor Grammar rule: $top")
            }
            val rhs = top.getRHS(token.javaClass)

            if (rhs == null) {
                onPredictionNotFoundError.accept(top, token)
                throw PredictiveParserException(top, token)
            }

            onGrammarRuleApplication.accept(top, token, rhs)
            for (i in rhs.indices.reversed()) {
                stack.push(rhs[i])
            }
        }
        if (tokens.isEmpty() && stack.isEmpty()) {
            println("Parsing successful")
        }
        if (!stack.isEmpty()) {
            throw Exception("Failed to parse input; Expected tokens but received EOF;")
        }
        if (!tokens.isEmpty()) {
            throw Exception(
                    "Failed to parse input; Expected grammar rule but found none.\n" +
                            "Remaining tokens: " + tokens.stream()
                            .map { it.javaClass.simpleName }
                            .collect(Collectors.toList())
            )
        }
    }

    fun isEqual(token: Token, top: TreeNode): Boolean {
        return token.javaClass == top.javaClass
    }

    init {
        this.eof = eof
        this.startSymbol = startSymbol
        this.beforeRuleApplication = beforeRuleApplication
        this.onUnexpectedToken = onUnexpectedToken
        this.onUnknownGrammarRule = onUnknownGrammarRule
        this.onPredictionNotFoundError = onPredictionNotFoundError
        this.onGrammarRuleApplication = onGrammarRuleApplication
    }
}
