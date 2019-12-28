package compiler.lexer

import compiler.graph.Node
import compiler.lexer.NonFinalState.Companion.END_OF_TERMINAL
import java.util.*
import java.util.function.Function

/**
 * An adapter between a lexer DFA and the undlerying graph.
 * The classes overrides the default behavior for errors
 * Stop the default error handling, instead reroutes all
 * missing edges to error state (if not final state)
 */
open class LexicalNode constructor(name: String) : Node<LexicalNode>(name, Function(::routeErrorToCustomStates)) {

    override fun me(): LexicalNode {
        return this
    }

    companion object {
        private fun routeErrorToCustomStates(from: LexicalNode): Optional<LexicalNode> {
            return when (from) {
                is FinalState -> Optional.of(END_OF_TERMINAL)
                else -> Optional.of(NonFinalState.FATAL_ERROR)
            }
        }
    }
}
