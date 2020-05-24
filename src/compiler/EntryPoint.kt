package compiler

import compiler.a5.grammar.A5GrammarRules
import compiler.a5.lexicon.A5LexiconDFA
import compiler.interpreter.Interpreter
import compiler.lexer.LexerBuilder
import compiler.lexer.NonFinalStateListener
import compiler.lexer.UnknownTokenException
import compiler.parser.AbstractSyntaxTreeBuilder
import compiler.parser.ParseTreeBuilder
import compiler.parser.Symbol.NonTerminal
import compiler.parser.Symbol.NonTerminal.ParseTreeSentinel
import compiler.parser.Symbol.NonTerminal.Pgm
import compiler.parser.Symbol.Terminal
import compiler.parser.TreeNode
import compiler.utils.TextCursor
import visualization.TreeVisualizer
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.util.*
import java.util.regex.Pattern

object EntryPoint {
    var inputName: String? = null
    val exception: String? = null

    @Throws(Exception::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val settings = processArgs(args)
        val terminals: List<Terminal>

        inputName = settings.inputName
        if (settings.terminals != null) {
            // Passed in token stream
            terminals = settings.terminals!!
        } else {
            // Tokenize file
            terminals = LexerBuilder()
                    .setDFA(A5LexiconDFA())
                    .onUnknownTokenFound(NonFinalStateListener { cursor, _ -> logUnknownToken(cursor) })
                    .createLexer()
                    .lex(settings.inputText!!)
        }

        if (exception != null) {
            throw Exception(exception)
        }

        // Prepare grammar rules
        val llTable = A5GrammarRules.build()

        // Parse token stream
        var tree = ParseTreeBuilder()
                .setStartSymbol(Pgm(), llTable)
                .setInputSourceName(settings.inputName)
                .setTerminals(terminals)
                .build()

        // Serialize current PST
        TreeVisualizer.toImage(tree, settings.pstOut)

        // Transform PST to AST (in-place)
        AbstractSyntaxTreeBuilder.fromParseTree(tree)

        // Serialize ASt
        TreeVisualizer.toImage(tree, settings.astOut)
        tree = tree.children[0]

        Interpreter.execute(tree)
    }

    @Throws(IOException::class)
    private fun processArgs(args: Array<String>): ParserSettings {
        val settings = ParserSettings()
        for (arg in args) {
            val split = arg.split("=".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

            if (split.size != 2)
                continue

            val key = split[0]
            val value = split[1]
            when (key) {
                "--pst-name" -> settings.pstOut = value
                "--ast-name" -> settings.astOut = value
                "--file" -> {
                    settings.inputName = value
                    settings.inputText = Files.readString(Path.of(settings.inputName))
                }
            }
        }

        if (settings.inputText == null && settings.terminals == null) {
            Scanner(System.`in`)
                    .useDelimiter(Pattern.compile("$"))
                    .use { settings.inputText = if (it.hasNext()) it.next() else "" }
        }
        return settings
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

    private fun logUnknownToken(cursor: TextCursor) {
        throw UnknownTokenException(cursor.getCurrentSentence(), cursor)
    }

    private class ParserSettings {
        var pstOut = "pst"
        var astOut = "ast"
        var inputName = "std.in"
        var inputText: String? = null
        var terminals: List<Terminal>? = null
    }
}
