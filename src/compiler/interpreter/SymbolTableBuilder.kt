package compiler.interpreter

import compiler.parser.Symbol

object SymbolTableBuilder {

    fun build(node: Symbol.Terminal): SymbolTable {
        val symtab = SymbolTable()
        SymbolTableBuilderVisitor(symtab).accept(node)
        return symtab
    }

}
