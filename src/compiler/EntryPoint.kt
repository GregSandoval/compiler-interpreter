package compiler

import compiler.a5.grammar.A5GrammarNonTerminals
import compiler.a5.grammar.A5GrammarRules
import compiler.a5.lexicon.A5LexiconDFA
import compiler.interpreter.Interpreter
import compiler.lexer.AlexHydrator
import compiler.lexer.LexerBuilder
import compiler.lexer.UnknownTokenException
import compiler.lexer.token.Token
import compiler.parser.AbstractGrammarNode
import compiler.parser.AbstractSyntaxTreeBuilder
import compiler.parser.GrammarNode
import compiler.parser.ParseTreeBuilder
import compiler.utils.TextCursor
import visualization.TreeVisualizer
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.util.*
import java.util.function.BiConsumer
import java.util.regex.Pattern

object EntryPoint {
    var inputName: String? = null
    val exception: String? = null

    @Throws(Exception::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val settings = processArgs(args)
        val tokens: List<Token>

        if (settings.tokens != null) {
            // Passed in token stream
            tokens = settings.tokens!!
        } else {
            // Tokenize file
            tokens = LexerBuilder()
                    .setStartState(A5LexiconDFA().build())
                    .onUnknownTokenFound(BiConsumer(this::logUnknownToken))
                    .createLexer()
                    .analyze(settings.inputText!!)
        }

        if (exception != null) {
            throw Exception(exception)
        }

        // Prepare grammar rules
        A5GrammarRules.build()

        inputName = settings.inputName
        // Parse token stream
        var tree = ParseTreeBuilder()
                .setStartSymbol(A5GrammarNonTerminals.Pgm())
                .setInputSourceName(settings.inputName)
                .build(tokens)

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
            if (arg == "--alex") {
                val scanner = Scanner(System.`in`).useDelimiter(Pattern.compile("$"))
                val serializedTokens = if (scanner.hasNext()) scanner.next() else ""
                val lexer = LexerBuilder()
                        .setStartState(A5LexiconDFA().build())
                        .createLexer()

                settings.tokens = AlexHydrator(lexer).hydrate(serializedTokens)
                scanner.close()
                continue
            }
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

        if (settings.inputText == null && settings.tokens == null) {
            Scanner(System.`in`)
                    .useDelimiter(Pattern.compile("$"))
                    .use { settings.inputText = if (it.hasNext()) it.next() else "" }
        }
        return settings
    }

    fun validateAST(tree: AbstractGrammarNode) {
        println("Validating AST contains only tokens...")
        val unhandledNodes = ArrayList<GrammarNode>()
        validateAST(tree, unhandledNodes)

        if (unhandledNodes.isEmpty()) {
            println("AST contains only tokens!")
            return
        }

        if (unhandledNodes.size == 1 && unhandledNodes[0] !is ParseTreeBuilder.ParseTreeSentinel)
            println("Uh-oh; AST contains grammar nodes! Need to add more logic to these nodes:$unhandledNodes")
    }

    fun validateAST(tree: AbstractGrammarNode?, unhandledNodes: MutableList<GrammarNode>) {
        if (tree == null) {
            return
        }
        if (tree is GrammarNode) {
            unhandledNodes.add(tree)
        }
        for (child in tree.children) {
            validateAST(child, unhandledNodes)
        }
    }

    private fun logUnknownToken(unknownToken: String, cursor: TextCursor) {
        throw UnknownTokenException(unknownToken, cursor)
    }

    private class ParserSettings {
        var pstOut = "pst"
        var astOut = "ast"
        var inputName = "std.in"
        var inputText: String? = null
        var tokens: List<Token>? = null
    }
}
