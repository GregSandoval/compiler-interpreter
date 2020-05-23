package compiler.interpreter

import compiler.lexer.token.Token
import compiler.lexer.token.Token.KeywordToken.*
import compiler.lexer.token.Token.KeywordToken.TypeToken.*
import compiler.lexer.token.Token.OperatorToken
import compiler.lexer.token.Token.OperatorToken.*
import compiler.lexer.token.Token.SymbolToken.LeftParen
import compiler.lexer.token.Token.TypedToken.*
import compiler.parser.visitors.TokenTypedAdapterVisitor

class TypeChecker private constructor(private val symtab: SymbolTable) : TokenTypedAdapterVisitor<TypeToken> {
    private val voidType = VoidToken()
    private val floatType = FloatKeywordToken()
    private val stringType = StringKeywordToken()
    private val integerType = IntegerKeywordToken()
    override fun visit(token: Asterisk): TypeToken {
        return if (token.children.size == 1) {
            this.accept(token.children[0] as Token)
        } else checkBinaryOperator(token)
    }

    override fun visit(token: Ampersand): TypeToken {
        return if (token.children.size == 1) {
            this.accept(token.children[0] as Token)
        } else voidType
    }

    override fun visit(token: Equal): TypeToken {
        var lval = token.children[0] as Token
        val rval = token.children[1] as Token
        val lvalType = this.accept(lval)
        val rvalType = this.accept(rval)
        when {
            lvalType is FloatKeywordToken && rvalType is FloatKeywordToken -> return lvalType
            lvalType is IntegerKeywordToken && rvalType is IntegerKeywordToken -> return lvalType
            lvalType is FloatKeywordToken && rvalType is IntegerKeywordToken -> return lvalType
            lval is TypeToken -> lval = lval.children[0] as Token
        }
        if (lvalType.javaClass != rvalType.javaClass) {
            throw AssignmentTypeException(lval, rval, lvalType, rvalType)
        }
        return lvalType
    }

    override fun visit(token: Minus): TypeToken {
        return checkBinaryOperator(token)
    }

    override fun visit(token: Plus): TypeToken {
        return checkBinaryOperator(token)
    }

    override fun visit(token: ForwardSlash): TypeToken {
        return checkBinaryOperator(token)
    }

    override fun visit(token: Caret): TypeToken {
        return checkBinaryOperator(token)
    }

    override fun visit(token: LeftParen): TypeToken {
        if (token.parent is VarKeywordToken) {
            for (child in token.children) {
                this.accept(child as Token)
            }
            return voidType
        }
        if (token.parent is OperatorToken || token.parent is LeftParen) {
            val type: TypeToken = this.accept(token.children[0] as Token)
            if (type.javaClass == voidType.javaClass) {
                throw RuntimeException("Operator returned void type?")
            }
            return type
        }
        return voidType
    }

    override fun visit(token: InputKeywordToken): TypeToken {
        return copyLineInfo(token, stringType)
    }

    override fun visit(token: IdentifierToken): TypeToken {
        return symtab.getSymbolType(token)
    }

    override fun visit(token: StringKeywordToken): TypeToken {
        return token
    }

    override fun visit(token: StringToken): TypeToken {
        return copyLineInfo(token, stringType)
    }

    override fun visit(token: FloatKeywordToken): TypeToken {
        return token
    }

    override fun visit(token: FloatToken): TypeToken {
        return copyLineInfo(token, floatType)
    }

    override fun visit(token: IntegerKeywordToken): TypeToken {
        return token
    }

    override fun visit(token: IntegerToken): TypeToken {
        return copyLineInfo(token, integerType)
    }

    private fun checkBinaryOperator(operator: OperatorToken): TypeToken {
        val left: TypeToken = this.accept(operator.children[0] as Token)
        val right: TypeToken = this.accept(operator.children[1] as Token)
        when {
            left is FloatKeywordToken && right is FloatKeywordToken -> {
                return left
            }
            left is IntegerKeywordToken && right is IntegerKeywordToken -> {
                return left
            }
            left is FloatKeywordToken && right is IntegerKeywordToken -> {
                return left
            }
            left is IntegerKeywordToken && right is FloatKeywordToken -> {
                return right
            }
            left.javaClass != right.javaClass -> {
                throw OperatorTypeException(operator, left, right)
            }
            else -> return left
        }
    }

    override fun defaultValue(): TypeToken {
        return voidType
    }

    companion object {
        fun check(tree: Token, symtab: SymbolTable) {
            TypeChecker(symtab).accept(tree)
        }

        private fun copyLineInfo(from: Token, to: TypeToken): TypeToken {
            to.lineInfo = from.lineInfo.copy()
            return to
        }
    }

}
