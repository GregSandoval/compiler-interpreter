package compiler

import compiler.a5.grammar.A7Grammar
import compiler.a5.grammar.EpsilonDerivation
import compiler.a5.lexer.A5Lexer
import compiler.a5.parser.A7Parser
import compiler.a5.parser.FirstSet
import compiler.a5.parser.FollowSet
import compiler.interpreter.Interpreter
import compiler.interpreter.SymbolTableBuilder
import compiler.interpreter.TypeChecker
import compiler.parser.ASTValidator
import compiler.parser.AbstractSyntaxTreeBuilder
import compiler.parser.Symbol.Terminal
import visualization.TreeVisualizer

object EntryPoint {
    var inputName: String? = null

    @JvmStatic
    fun main(args: Array<String>) {
        val inputs = CommandLine.processInputs(args)
        inputName = inputs.sourceFileName

        val terminals = A5Lexer.lex(inputs.sourceText)
        val parseTree = A7Parser.parse(terminals)

        var tree = parseTree.getTree()

        val productions = A7Grammar.build()

        val derivesEpsilon = EpsilonDerivation.findAll(productions)

        val first = FirstSet.findAll(productions, derivesEpsilon)

        val follow = FollowSet.findAll(productions, derivesEpsilon, first)

        println("================ Follow Set =====================")
        println(follow
                .map { "${it.key.simpleName}=${it.value.map { clazz -> "${clazz.simpleName}" }.sorted()}" }
                .sorted()
                .joinToString("\n")
        )
        println("=================================================")

        // Serialize PST to image
        TreeVisualizer.toImage(tree, inputs.pstFileName)

        // Transform PST to AST (in-place)
        AbstractSyntaxTreeBuilder.fromParseTree(tree)

        // Validate new AST contains only terminals
        ASTValidator.validate(tree)

        // Serialize AST to image
        TreeVisualizer.toImage(tree, inputs.astFileName)

        // Bypass sentinel node, not needed for execution
        tree = tree.children[0] as Terminal

        // Build symbol table from AST
        val symtab = SymbolTableBuilder.build(tree)

        // Log initial symtab state
        println(symtab)

        // Check types within AST
        TypeChecker.validate(tree, symtab)

        // Execute AST
        Interpreter.execute(tree, symtab)

        // Log final symtab state
        println()
        println(symtab)
    }

}
