package compiler.interpreter.types

import compiler.interpreter.symboltable.SymbolTable
import compiler.parser.Symbol.Terminal

object TypeChecker {

    fun validate(tree: Terminal, symtab: SymbolTable) {
        TypeCheckerVisitor(symtab).accept(tree)
    }

}
