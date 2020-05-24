package compiler.parser

import compiler.parser.Symbol.NonTerminal
import compiler.parser.Symbol.Terminal
import java.util.*
import java.util.stream.Collectors

class Parser(
        private val startSymbol: NonTerminal,
        private val beforeRuleApplication: BeforeRuleApplicationListener,
        private val onUnexpectedToken: GeneralListener,
        private val onUnknownGrammarRule: GeneralListener,
        private val onPredictionNotFoundError: GeneralListener,
        private val onGrammarRuleApplication: GrammarRuleApplicationListener
) {

    @Throws(Exception::class)
    fun parse(inputName: String, tokensIn: List<Terminal>, llTable: LLTable) {
        val stack = LinkedList<TreeNode>()
        val tokens = LinkedList(tokensIn)
        stack.push(startSymbol)
        while (!tokens.isEmpty() && !stack.isEmpty()) {
            beforeRuleApplication.accept(stack, tokens.peek())
            val token = tokens.peek()
            val top = stack.pop()
            if (top is Terminal && isEqual(token, top)) {
                tokens.pop()
                onGrammarRuleApplication.accept(top, token, emptyList())
                continue
            }
            if (top is Terminal) {
                onUnexpectedToken.accept(top, token)
                throw UnexpectedToken(top, token, inputName)
            }
            if (top !is NonTerminal) {
                onUnknownGrammarRule.accept(top, token)
                throw RuntimeException("Node is neither Token nor Grammar rule: $top")
            }

            val rhs = llTable.getRHS(top, token.javaClass)

            if (rhs == null) {
                onPredictionNotFoundError.accept(top, token)
                throw PredictiveParserException(top, token, llTable)
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

    fun isEqual(terminal: Terminal, top: TreeNode): Boolean {
        return terminal.javaClass == top.javaClass
    }
}
