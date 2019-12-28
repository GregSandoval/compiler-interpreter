package compiler.interpreter

import compiler.lexer.token.IdentifierToken
import compiler.lexer.token.KeywordToken.VarKeywordToken
import compiler.lexer.token.OperatorToken.Equal
import compiler.lexer.token.Token
import compiler.lexer.token.TypeToken
import compiler.lexer.token.TypeToken.*
import compiler.parser.visitors.TokenTypedAdapterVisitor

class SymbolTableBuilder private constructor(private val symtab: SymbolTable) : TokenTypedAdapterVisitor<TypeToken?> {
    override fun visit(token: VarKeywordToken): TypeToken? {
        val paren = token.children[0]
        for (equalOrDataType in paren.children) {
            var identifier: IdentifierToken
            var type: TypeToken
            if (equalOrDataType is TypeToken) {
                type = equalOrDataType
                identifier = equalOrDataType.children[0] as IdentifierToken
            } else if (equalOrDataType is Equal) {
                type = equalOrDataType.children[0] as TypeToken
                identifier = type.children[0] as IdentifierToken
            } else {
                throw UnknownDataTypeException(equalOrDataType as Token)
            }
            if (symtab.hasSymbol(identifier)) {
                throw RuntimeException(identifier.javaClass.simpleName + " has already been declared")
            }
            symtab.setSymbolType(identifier, type)
            symtab.setSymbolValue(identifier, undefined)
        }
        return null
    }

    override fun visit(token: StringKeywordToken): TypeToken {
        return token
    }

    override fun visit(token: FloatKeywordToken): TypeToken {
        return token
    }

    override fun visit(token: IntegerKeywordToken): TypeToken {
        return token
    }

    override fun defaultValue(): TypeToken {
        return voidToken
    }

    companion object {
        val voidToken = VoidToken()
        val undefined = Undefined()
        fun build(node: Token, symtab: SymbolTable) {
            val visitor = SymbolTableBuilder(symtab)
            node.accept(visitor)
        }
    }

}
