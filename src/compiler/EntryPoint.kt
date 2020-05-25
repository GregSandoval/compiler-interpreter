package compiler

import compiler.a5.lexer.A5Lexer
import compiler.a5.parser.A7Parser
import compiler.interpreter.Interpreter
import compiler.parser.ASTValidator
import compiler.parser.AbstractSyntaxTreeBuilder
import visualization.TreeVisualizer

object EntryPoint {
    var inputName: String? = null

    @Throws(Exception::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val inputs = CommandLine.processInputs(args)
        inputName = inputs.sourceFileName

        val terminals = A5Lexer.lex(inputs.sourceText)
        val parseTree = A7Parser.parse(inputs.sourceFileName, terminals)

        var root = parseTree.getRoot()

        // Serialize PST to image
        TreeVisualizer.toImage(root, inputs.pstFileName)

        // Transform PST to AST (in-place)
        AbstractSyntaxTreeBuilder.fromParseTree(root)

        // Validate new AST contains only terminals
        ASTValidator.validate(root)

        // Serialize AST to image
        TreeVisualizer.toImage(root, inputs.astFileName)

        // Bypass sentinel node, not needed for execution
        root = root.children[0]

        // Execute AST
        Interpreter.execute(root)
    }
}
