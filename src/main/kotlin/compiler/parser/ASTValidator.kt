package compiler.parser

import java.util.*

object ASTValidator {

    fun validate(tree: TreeNode) {
        print("Validating AST contains only terminals...")
        val unhandledNodes = ArrayList<Symbol.NonTerminal>()
        validate(tree, unhandledNodes)

        if (unhandledNodes.isEmpty()) {
            println("AST contains only tokens!")
            return
        }

        if (unhandledNodes.size == 1 && unhandledNodes[0] !is Symbol.NonTerminal.ParseTreeSentinel)
            println("Uh-oh; AST contains grammar nodes! Need to add more logic to these nodes:$unhandledNodes")

        println("validated!")
    }

    fun validate(tree: TreeNode?, unhandledNodes: MutableList<Symbol.NonTerminal>) {
        if (tree == null) {
            return
        }
        if (tree is Symbol.NonTerminal) {
            unhandledNodes.add(tree)
        }
        for (child in tree.children) {
            validate(child, unhandledNodes)
        }
    }

}
