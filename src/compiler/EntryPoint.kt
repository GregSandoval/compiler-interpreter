package compiler

import compiler.a5.lexer.A5Lexer
import compiler.a5.parser.A7Parser
import compiler.interpreter.Interpreter
import compiler.parser.ASTValidator
import compiler.parser.AbstractSyntaxTreeBuilder
import compiler.parser.Symbol.Terminal
import visualization.TreeVisualizer

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

        ASTValidator.validate(root)

        // Serialize ASt
        TreeVisualizer.toImage(root, inputs.astFileName)
        root = root.children[0]

        Interpreter.execute(root)
    }
}
