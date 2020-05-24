package compiler.interpreter

import compiler.parser.Symbol.Terminal
import compiler.parser.Symbol.Terminal.Keyword.Type
import java.util.*
import java.util.stream.Collectors

class SymbolTable {
    private val symtab: MutableMap<String, Any?> = HashMap()
    private val types: MutableMap<String, Type> = HashMap()

    fun getSymbolType(terminal: Terminal): Type {
        return types[hashcode(terminal)] ?: throw UndeclaredIdentifierException(terminal)
    }

    fun setSymbolType(terminal: Terminal, type: Type) {
        val oldType = types.put(hashcode(terminal), type)
        if (oldType != null) {
            throw RuntimeException(terminal.str + "'s datatype cannot be redefined")
        }
    }

    fun setSymbolValue(terminal: Terminal, value: Any?) {
        symtab[hashcode(terminal)] = value
    }

    fun hasSymbol(terminal: Terminal): Boolean {
        return symtab.containsKey(hashcode(terminal))
    }

    fun getValue(terminal: Terminal): Any {
        return symtab[hashcode(terminal)] ?: throw UndeclaredIdentifierException(terminal)
    }

    fun getAddress(terminal: Terminal): String {
        return hashcode(terminal)
    }

    fun getValueAtAddress(address: String?): Any? {
        return symtab[address]
    }

    fun removeSymbol(terminal: Terminal): Any? {
        return symtab.remove(hashcode(terminal))
    }

    private fun hashcode(terminal: Terminal): String {
        return terminal.str + ":" + terminal.javaClass.simpleName
    }

    override fun toString(): String {
        return symtab.entries
                .stream()
                .map {
                    it.key + ":" + types[it.key]!!.str + " = " + it.value.toString()
                }
                .collect(Collectors.joining("\n"))
    }
}
