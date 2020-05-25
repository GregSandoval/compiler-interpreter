package compiler.interpreter

import compiler.parser.Symbol.Terminal
import compiler.parser.Symbol.Terminal.Keyword.*
import compiler.parser.Symbol.Terminal.Keyword.Type.*
import compiler.parser.Symbol.Terminal.Operator
import compiler.parser.Symbol.Terminal.Operator.*
import compiler.parser.Symbol.Terminal.Punctuation.LeftParen
import compiler.parser.Symbol.Terminal.TypedTerminal.*
import compiler.parser.visitors.TokenTypedAdapterVisitor

class TypeChecker private constructor(private val symtab: SymbolTable) : TokenTypedAdapterVisitor<Type> {
    private val voidType = Void()
    private val floatType = FloatKeyword()
    private val stringType = StringKeyword()
    private val integerType = IntegerKeyword()

    override fun visit(token: Asterisk): Type {
        return if (token.children.size == 1) {
            this.accept(token.children[0] as Terminal)
        } else checkBinaryOperator(token)
    }

    override fun visit(token: Ampersand): Type {
        return if (token.children.size == 1) {
            this.accept(token.children[0] as Terminal)
        } else voidType
    }

    override fun visit(token: Equal): Type {
        var lval = token.children[0] as Terminal
        val rval = token.children[1] as Terminal
        val lvalType = this.accept(lval)
        val rvalType = this.accept(rval)

        when {
            lvalType is FloatKeyword && rvalType is FloatKeyword -> return lvalType
            lvalType is IntegerKeyword && rvalType is IntegerKeyword -> return lvalType
            lvalType is FloatKeyword && rvalType is IntegerKeyword -> return lvalType
            lval is Type -> lval = lval.children[0] as Terminal
        }

        if (lvalType.javaClass != rvalType.javaClass) {
            throw AssignmentTypeException(lval, rval, lvalType, rvalType)
        }

        return lvalType
    }

    override fun visit(token: Minus): Type {
        return checkBinaryOperator(token)
    }

    override fun visit(token: Plus): Type {
        return checkBinaryOperator(token)
    }

    override fun visit(token: ForwardSlash): Type {
        return checkBinaryOperator(token)
    }

    override fun visit(token: Caret): Type {
        return checkBinaryOperator(token)
    }

    override fun visit(token: LeftParen): Type {
        if (token.parent is VarKeyword) {
            for (child in token.children) {
                this.accept(child as Terminal)
            }
            return voidType
        }
        if (token.parent is Operator || token.parent is LeftParen) {
            val type: Type = this.accept(token.children[0] as Terminal)
            if (type.javaClass == voidType.javaClass) {
                throw RuntimeException("Operator returned void type?")
            }
            return type
        }
        return voidType
    }

    override fun visit(token: InputKeyword): Type {
        return copyLineInfo(token, stringType)
    }

    override fun visit(token: IdentifierTerminal): Type {
        return symtab.getSymbolType(token)
    }

    override fun visit(token: StringKeyword): Type {
        return token
    }

    override fun visit(token: StringTerminal): Type {
        return copyLineInfo(token, stringType)
    }

    override fun visit(token: FloatKeyword): Type {
        return token
    }

    override fun visit(token: FloatTerminal): Type {
        return copyLineInfo(token, floatType)
    }

    override fun visit(token: IntegerKeyword): Type {
        return token
    }

    override fun visit(token: IntegerTerminal): Type {
        return copyLineInfo(token, integerType)
    }

    private fun checkBinaryOperator(operator: Operator): Type {
        val left: Type = this.accept(operator.children[0] as Terminal)
        val right: Type = this.accept(operator.children[1] as Terminal)
        return when {
            left is FloatKeyword && right is FloatKeyword -> left
            left is IntegerKeyword && right is IntegerKeyword -> left
            left is FloatKeyword && right is IntegerKeyword -> left
            left is IntegerKeyword && right is FloatKeyword -> right
            left.javaClass != right.javaClass -> throw OperatorTypeException(operator, left, right)
            else -> left
        }
    }

    override fun defaultValue(): Type {
        return voidType
    }

    companion object {
        fun check(tree: Terminal, symtab: SymbolTable) {
            TypeChecker(symtab).accept(tree)
        }

        private fun copyLineInfo(from: Terminal, to: Type): Type {
            to.lineInfo = from.lineInfo.copy()
            return to
        }
    }

}
