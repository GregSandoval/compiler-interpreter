package visualization

import compiler.parser.Symbol.Terminal
import compiler.parser.TreeNode
import java.util.*
import java.util.stream.Collectors

object StateTransitionLogger {
    fun logBeforeState(stack: LinkedList<TreeNode>, terminal: Terminal) {
        println("Stack: " + format(stack))
    }

    fun logGrammarRuleApplication(top: TreeNode, terminal: Terminal, rhs: List<TreeNode>) {
        println("Move : " + format(top) + "(" + format(terminal) + ") => " + format(rhs) + "\n")
    }

    fun logUnexpectedToken(top: TreeNode, terminal: Terminal) {
        println("Expected: " + format(top) + " but found: " + format(terminal))
    }

    fun logUnknownGrammarRule(top: TreeNode, terminal: Terminal) {
        println("\n")
        println("Grammar contains rule not in Grammar hierarchy: " + format(top))
    }

    fun logPredictionNotFound(top: TreeNode, terminal: Terminal) {
        println("\n")
        println("User error; Rule: " + format(top) + " has no entry for " + format(terminal))
    }

    fun format(rules: List<TreeNode>): String {
        val strings = rules.stream()
                .map<String>(this::format)
                .collect(Collectors.toList())
        return "[" + java.lang.String.join(", ", strings) + "]"
    }

    fun format(rule: TreeNode): String {
        return if (rule is Terminal) rule.javaClass.simpleName else rule.toString()
    }
}
