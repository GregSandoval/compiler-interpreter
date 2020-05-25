package compiler.parser

import compiler.parser.Symbol.NonTerminal
import compiler.parser.Symbol.Terminal
import java.util.*
import java.util.stream.Collectors

typealias BeforeRuleListerner = (List<TreeNode>, Terminal) -> Unit
typealias UnexpectedRuleListener = (TreeNode, Terminal) -> Unit
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
        private val onPredictionNotFoundError: PredictionNotFoundListener,
        private val onGrammarRuleApplication: NonTerminalReplacedListener,
        private val llTable: LLTable
) {

    fun parse(tokensIn: List<Terminal>) {
        val stack = LinkedList<Symbol>()
        val tokens = LinkedList(tokensIn)
        stack.push(startSymbol)

        while (!tokens.isEmpty() && !stack.isEmpty()) {
            beforeRuleApplication(stack, tokens.peek())

            val rhs = when (val top = stack.pop()) {
                is Terminal -> matchTerminal(top, tokens)
                is NonTerminal -> applyProduction(top, tokens)
            }

            rhs.reversed().forEach(stack::push)
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

    fun applyProduction(top: NonTerminal, tokens: LinkedList<Terminal>): List<Symbol> {
        val token = tokens.peek()
        val rhs = this.llTable.getRHS(top, token::class)

        if (rhs == null) {
            onPredictionNotFoundError(top, token)
            throw PredictiveParserException(top, token, this.llTable)
        }

        onGrammarRuleApplication(top, token, rhs)

        return rhs
    }

    fun matchTerminal(top: Terminal, tokens: LinkedList<Terminal>): List<Symbol> {
        val token = tokens.pop()

        if (isEqual(token, top)) {
            onGrammarRuleApplication(top, token, emptyList())
            return emptyList()
        }

        onUnexpectedToken(top, token)
        throw UnexpectedToken(top, token)
    }

    fun isEqual(terminal: Terminal, top: TreeNode): Boolean {
        return terminal.javaClass == top.javaClass
    }
}
