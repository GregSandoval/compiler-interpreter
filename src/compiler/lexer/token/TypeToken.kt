package compiler.lexer.token

import compiler.parser.TokenEvaluator
import compiler.parser.visitors.TokenVisitor

sealed class TypeToken constructor(str: String, UUID: Int) : KeywordToken(str, UUID) {
    class VoidToken : TypeToken("void", -20) {
        override fun <T> accept(visitor: TokenVisitor<T>): T {
            return visitor.visit(this)
        }

        @Throws(Exception::class)
        override fun accept(visitor: TokenEvaluator) {
            return visitor.visit(this)
        }
    }

    class FloatKeywordToken : TypeToken("float", 13) {
        override fun <T> accept(visitor: TokenVisitor<T>): T {
            return visitor.visit(this)
        }

        @Throws(Exception::class)
        override fun accept(visitor: TokenEvaluator) {
            return visitor.visit(this)
        }
    }

    class IntegerKeywordToken : TypeToken("int", 16) {
        override fun <T> accept(visitor: TokenVisitor<T>): T {
            return visitor.visit(this)
        }

        @Throws(Exception::class)
        override fun accept(visitor: TokenEvaluator) {
            return visitor.visit(this)
        }
    }

    class StringKeywordToken : TypeToken("string", 17) {
        override fun <T> accept(visitor: TokenVisitor<T>): T {
            return visitor.visit(this)
        }

        @Throws(Exception::class)
        override fun accept(visitor: TokenEvaluator) {
            return visitor.visit(this)
        }
    }
}
