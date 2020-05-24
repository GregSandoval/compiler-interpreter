package compiler.interpreter

import compiler.parser.Language.Token
import compiler.parser.Language.Token.KeywordToken.TypeToken
import java.util.*
import java.util.stream.Collectors

class SymbolTable {
    private val symtab: MutableMap<String, Any?> = HashMap()
    private val types: MutableMap<String, TypeToken> = HashMap()

    fun getSymbolType(token: Token): TypeToken {
        return types[hashcode(token)] ?: throw UndeclaredIdentifierException(token)
    }

    fun setSymbolType(token: Token, type: TypeToken) {
        val oldType = types.put(hashcode(token), type)
        if (oldType != null) {
            throw RuntimeException(token.str + "'s datatype cannot be redefined")
        }
    }

    fun setSymbolValue(token: Token, value: Any?) {
        symtab[hashcode(token)] = value
    }

    fun hasSymbol(token: Token): Boolean {
        return symtab.containsKey(hashcode(token))
    }

    fun getValue(token: Token): Any {
        return symtab[hashcode(token)] ?: throw UndeclaredIdentifierException(token)
    }

    fun getAddress(token: Token): String {
        return hashcode(token)
    }

    fun getValueAtAddress(address: String?): Any? {
        return symtab[address]
    }

    fun removeSymbol(token: Token): Any? {
        return symtab.remove(hashcode(token))
    }

    private fun hashcode(token: Token): String {
        return token.str + ":" + token.javaClass.simpleName
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
