package compiler.interpreter

import compiler.lexer.token.Token
import compiler.parser.AbstractGrammarNode

object Interpreter {
    fun execute(tree: AbstractGrammarNode?) {
        if (tree !is Token) {
            throw Exception("Grammar node in AST?")
        }
        val symtab = SymbolTable()
        SymbolTableBuilder.build(tree, symtab)
        TypeChecker.check(tree, symtab)
        println()
        println()
        println("Symbol table: \n$symtab\n")
        println("Program output:")
        TokenInterpreter.interpret(tree, symtab)
        println()
        println()
        println("Symbol table: \n$symtab\n")
    }
}
