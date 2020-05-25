package compiler.interpreter

import compiler.parser.Symbol.Terminal

object Interpreter {

    fun execute(tree: Terminal, symtab: SymbolTable) {
        InterpreterVisitor(symtab).accept(tree)
        println()
    }

}
