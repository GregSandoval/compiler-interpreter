package compiler

import compiler.a5.lexer.A5Lexer
import compiler.a5.parser.A7Parser
import compiler.interpreter.Interpreter
import compiler.parser.AbstractSyntaxTreeBuilder
import compiler.parser.Symbol.NonTerminal
import compiler.parser.Symbol.NonTerminal.ParseTreeSentinel
import compiler.parser.Symbol.Terminal
import compiler.parser.TreeNode
import visualization.TreeVisualizer
import java.util.*

object EntryPoint {
    var inputName: String? = null
    val exception: String? = null

    @Throws(Exception::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val inputs = CommandLine.processInputs(args)
        val terminals: List<Terminal>

        inputName = inputs.sourceFileName
        terminals = A5Lexer.lex(inputs.sourceText)

        if (exception != null) {
            throw Exception(exception)
        }

        val parseTree = A7Parser.parse(inputs.sourceFileName, terminals)

        var root = parseTree.getRoot()

        // Serialize current PST
        TreeVisualizer.toImage(root, inputs.pstFileName)

        // Transform PST to AST (in-place)
        AbstractSyntaxTreeBuilder.fromParseTree(root)

        // Serialize ASt
        TreeVisualizer.toImage(root, inputs.astFileName)
        root = root.children[0]

        Interpreter.execute(root)
    }

    fun validateAST(tree: TreeNode) {
        println("Validating AST contains only tokens...")
        val unhandledNodes = ArrayList<NonTerminal>()
        validateAST(tree, unhandledNodes)

        if (unhandledNodes.isEmpty()) {
            println("AST contains only tokens!")
            return
        }

        if (unhandledNodes.size == 1 && unhandledNodes[0] !is ParseTreeSentinel)
            println("Uh-oh; AST contains grammar nodes! Need to add more logic to these nodes:$unhandledNodes")
    }

    fun validateAST(tree: TreeNode?, unhandledNodes: MutableList<NonTerminal>) {
        if (tree == null) {
            return
        }
        if (tree is NonTerminal) {
            unhandledNodes.add(tree)
        }
        for (child in tree.children) {
            validateAST(child, unhandledNodes)
        }
    }


}
