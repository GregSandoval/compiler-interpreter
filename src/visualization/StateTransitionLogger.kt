package visualization

import compiler.parser.Language.Token
import compiler.parser.TreeNode
import java.util.*
import java.util.stream.Collectors

object StateTransitionLogger {
    fun logBeforeState(stack: LinkedList<TreeNode>, token: Token) {
        println("Stack: " + format(stack))
    }

    fun logGrammarRuleApplication(top: TreeNode, token: Token, rhs: List<TreeNode>) {
        println("Move : " + format(top) + "(" + format(token) + ") => " + format(rhs) + "\n")
    }

    fun logUnexpectedToken(top: TreeNode, token: Token) {
        println("Expected: " + format(top) + " but found: " + format(token))
    }

    fun logUnknownGrammarRule(top: TreeNode, token: Token) {
        println("\n")
        println("Grammar contains rule not in Grammar hierarchy: " + format(top))
    }

    fun logPredictionNotFound(top: TreeNode, token: Token) {
        println("\n")
        println("User error; Rule: " + format(top) + " has no entry for " + format(token))
    }

    fun format(rules: List<TreeNode>): String {
        val strings = rules.stream()
                .map<String>(this::format)
                .collect(Collectors.toList())
        return "[" + java.lang.String.join(", ", strings) + "]"
    }

    fun format(rule: TreeNode): String {
        return if (rule is Token) rule.javaClass.simpleName else rule.toString()
    }
}
