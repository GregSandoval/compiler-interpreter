package compiler.interpreter

import compiler.interpreter.symboltable.SymbolTable
import compiler.parser.Symbol.Terminal

object Interpreter {

    fun execute(tree: Terminal, symtab: SymbolTable) {
        println("\n============ Program Execution ==============")
        InterpreterVisitor(symtab).accept(tree)
        println("\n=============================================")
    }

}
