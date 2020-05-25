package compiler.interpreter

import compiler.parser.Symbol.Terminal

object TypeChecker {

    fun validate(tree: Terminal, symtab: SymbolTable) {
        TypeCheckerVisitor(symtab).accept(tree)
    }

}
