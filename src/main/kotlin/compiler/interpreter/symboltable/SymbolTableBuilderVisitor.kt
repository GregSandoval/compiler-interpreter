package compiler.interpreter.symboltable

import compiler.interpreter.Undefined
import compiler.interpreter.types.UnknownDataTypeException
import compiler.parser.Symbol.Terminal
import compiler.parser.Symbol.Terminal.Keyword.Type
import compiler.parser.Symbol.Terminal.Keyword.Type.*
import compiler.parser.Symbol.Terminal.Keyword.VarKeyword
import compiler.parser.Symbol.Terminal.Operator.Equal
import compiler.parser.Symbol.Terminal.TypedTerminal.IdentifierTerminal
import compiler.parser.visitors.TokenTypedAdapterVisitor

val undefined = Undefined()
val voidToken = Void()

class SymbolTableBuilderVisitor constructor(private val symtab: SymbolTable) : TokenTypedAdapterVisitor<Type?> {

    override fun visit(token: VarKeyword): Type? {
        val paren = token.children[0]
        for (equalOrDataType in paren.children) {
            var identifier: IdentifierTerminal
            var type: Type
            if (equalOrDataType is Type) {
                type = equalOrDataType
                identifier = equalOrDataType.children[0] as IdentifierTerminal
            } else if (equalOrDataType is Equal) {
                type = equalOrDataType.children[0] as Type
                identifier = type.children[0] as IdentifierTerminal
            } else {
                throw UnknownDataTypeException(equalOrDataType as Terminal)
            }
            if (symtab.hasSymbol(identifier)) {
                throw RuntimeException(identifier.javaClass.simpleName + " has already been declared")
            }
            symtab.setSymbolType(identifier, type)
            symtab.setSymbolValue(identifier, undefined)
        }
        return null
    }

    override fun visit(token: StringKeyword): Type {
        return token
    }

    override fun visit(token: FloatKeyword): Type {
        return token
    }

    override fun visit(token: IntegerKeyword): Type {
        return token
    }

    override fun defaultValue(): Type {
        return voidToken
    }
}
