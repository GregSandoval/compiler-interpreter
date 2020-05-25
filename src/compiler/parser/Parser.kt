package compiler.parser

import compiler.parser.Symbol.NonTerminal
import compiler.parser.Symbol.Terminal
import java.util.*
import java.util.stream.Collectors

typealias BeforeRuleListerner = (List<TreeNode>, Terminal) -> Unit
typealias UnexpectedRuleListener = (TreeNode, Terminal) -> Unit
typealias UnknownNonTerminal = UnexpectedRuleListener
typealias PredictionNotFoundListener = UnexpectedRuleListener
typealias NonTerminalReplacedListener = (TreeNode, Terminal, List<TreeNode>) -> Unit

fun <P1> ((P1) -> Unit).andThen(next: (P1) -> Unit): ((P1) -> Unit) {
    return { p1 -> this(p1); next(p1) }
}

fun <P1, P2> ((P1, P2) -> Unit).andThen(next: (P1, P2) -> Unit): ((P1, P2) -> Unit) {
    return { p1, p2 -> this(p1, p2); next(p1, p2) }
}

fun <P1, P2, P3> ((P1, P2, P3) -> Unit).andThen(next: (P1, P2, P3) -> Unit): ((P1, P2, P3) -> Unit) {
    return { p1, p2, p3 -> this(p1, p2, p3); next(p1, p2, p3) }
}

class Parser(
        private val startSymbol: NonTerminal,
        private val beforeRuleApplication: BeforeRuleListerner,
        private val onUnexpectedToken: UnexpectedRuleListener,
        private val onUnknownGrammarRule: UnknownNonTerminal,
        private val onPredictionNotFoundError: PredictionNotFoundListener,
        private val onGrammarRuleApplication: NonTerminalReplacedListener
) {

    @Throws(Exception::class)
    fun parse(inputName: String, tokensIn: List<Terminal>, llTable: LLTable) {
        val stack = LinkedList<TreeNode>()
        val tokens = LinkedList(tokensIn)
        stack.push(startSymbol)

        while (!tokens.isEmpty() && !stack.isEmpty()) {
            val token = tokens.peek()

            beforeRuleApplication(stack, token)

            val top = stack.pop()

            if (top is Terminal && isEqual(token, top)) {
                tokens.pop()
                onGrammarRuleApplication(top, token, emptyList())
                continue
            }

            if (top is Terminal) {
                onUnexpectedToken(top, token)
                throw UnexpectedToken(top, token, inputName)
            }

            if (top !is NonTerminal) {
                onUnknownGrammarRule(top, token)
                throw RuntimeException("Node is neither Token nor Grammar rule: $top")
            }

            val rhs = llTable.getRHS(top, token.javaClass)

            if (rhs == null) {
                onPredictionNotFoundError(top, token)
                throw PredictiveParserException(top, token, llTable)
            }

            onGrammarRuleApplication(top, token, rhs)

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
