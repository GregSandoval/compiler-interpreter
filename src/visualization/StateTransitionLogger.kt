package visualization

import compiler.lexer.token.Token
import compiler.parser.AbstractGrammarNode
import java.util.*
import java.util.stream.Collectors

object StateTransitionLogger {
    fun logBeforeState(stack: LinkedList<AbstractGrammarNode>, token: Token) {
        println("Stack: " + format(stack))
    }

    fun logGrammarRuleApplication(top: AbstractGrammarNode, token: Token, rhs: List<AbstractGrammarNode>) {
        println("Move : " + format(top) + "(" + format(token) + ") => " + format(rhs) + "\n")
    }

    fun logUnexpectedToken(top: AbstractGrammarNode, token: Token) {
        println("Expected: " + format(top) + " but found: " + format(token))
    }

    fun logUnknownGrammarRule(top: AbstractGrammarNode, token: Token) {
        println("\n")
        println("Grammar contains rule not in Grammar hierarchy: " + format(top))
    }

    fun logPredictionNotFound(top: AbstractGrammarNode, token: Token) {
        println("\n")
        println("User error; Rule: " + format(top) + " has no entry for " + format(token))
    }

    fun format(rules: List<AbstractGrammarNode>): String {
        val strings = rules.stream()
                .map<String>(this::format)
                .collect(Collectors.toList())
        return "[" + java.lang.String.join(", ", strings) + "]"
    }

    fun format(rule: AbstractGrammarNode): String {
        return if (rule is Token) rule.javaClass.simpleName else rule.toString()
    }
}
