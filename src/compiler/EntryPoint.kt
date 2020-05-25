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
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.util.*

object EntryPoint {
    var inputName: String? = null
    val exception: String? = null

    @Throws(Exception::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val settings = processArgs(args)
        val terminals: List<Terminal>

        inputName = settings.sourceFileName
        terminals = A5Lexer.lex(settings.sourceText)

        if (exception != null) {
            throw Exception(exception)
        }

        val parseTree = A7Parser.parse(settings.sourceFileName, terminals)

        var root = parseTree.getRoot()

        // Serialize current PST
        TreeVisualizer.toImage(root, settings.pstFileName)

        // Transform PST to AST (in-place)
        AbstractSyntaxTreeBuilder.fromParseTree(root)

        // Serialize ASt
        TreeVisualizer.toImage(root, settings.astFileName)
        root = root.children[0]

        Interpreter.execute(root)
    }

    @Throws(IOException::class)
    private fun processArgs(args: Array<String>): UserInput {
        var pstFileName = "pst"
        var astFileName = "ast"
        var sourceFileName = "std.in"
        var sourceText: String? = null

        for (arg in args) {
            val split = arg.split("=".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

            if (split.size != 2)
                continue

            val key = split[0]
            val value = split[1]
            when (key) {
                "--pst-name" -> pstFileName = value
                "--ast-name" -> astFileName = value
                "--file" -> {
                    sourceFileName = value
                    sourceText = Files.readString(Path.of(sourceFileName))
                }
            }
        }

        if (sourceText == null) {
            throw Exception("Source file name is required. Use --file flag to specify source.")
        }

        return UserInput(pstFileName, astFileName, sourceFileName, sourceText)
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


    private data class UserInput(var pstFileName: String = "pst",
                                 var astFileName: String = "ast",
                                 var sourceFileName: String = "std.in",
                                 var sourceText: String)
}
