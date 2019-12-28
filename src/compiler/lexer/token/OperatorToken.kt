package compiler.lexer.token

import compiler.parser.TokenEvaluator
import compiler.parser.visitors.TokenVisitor

abstract class OperatorToken private constructor(str: String, UUID: Int) : Token(str, UUID) {
    class LessThan : OperatorToken("<", 31) {
        override fun <T> accept(visitor: TokenVisitor<T>): T {
            return visitor.visit(this)
        }

        @Throws(Exception::class)
        override fun accept(visitor: TokenEvaluator): Boolean {
            return visitor.visit(this)
        }
    }

    class GreaterThan : OperatorToken(">", 32) {
        override fun <T> accept(visitor: TokenVisitor<T>): T {
            return visitor.visit(this)
        }

        @Throws(Exception::class)
        override fun accept(visitor: TokenEvaluator): Boolean {
            return visitor.visit(this)
        }
    }

    class Asterisk : OperatorToken("*", 41) {
        override fun <T> accept(visitor: TokenVisitor<T>): T {
            return visitor.visit(this)
        }

        @Throws(Exception::class)
        override fun accept(visitor: TokenEvaluator): Any {
            return visitor.visit(this)
        }
    }

    class Equal : OperatorToken("=", 45) {
        override fun <T> accept(visitor: TokenVisitor<T>): T {
            return visitor.visit(this)
        }

        @Throws(Exception::class)
        override fun accept(visitor: TokenEvaluator) {
            return visitor.visit(this)
        }
    }

    class Minus : OperatorToken("-", 46) {
        override fun <T> accept(visitor: TokenVisitor<T>): T {
            return visitor.visit(this)
        }

        @Throws(Exception::class)
        override fun accept(visitor: TokenEvaluator): Number {
            return visitor.visit(this)
        }
    }

    class Plus : OperatorToken("+", 47) {
        override fun <T> accept(visitor: TokenVisitor<T>): T {
            return visitor.visit(this)
        }

        @Throws(Exception::class)
        override fun accept(visitor: TokenEvaluator): Number {
            return visitor.visit(this)
        }
    }

    class Ampersand : OperatorToken("&", 49) {
        override fun <T> accept(visitor: TokenVisitor<T>): T {
            return visitor.visit(this)
        }

        @Throws(Exception::class)
        override fun accept(visitor: TokenEvaluator): Any {
            return visitor.visit(this)
        }
    }

    class Arrow : OperatorToken("->", 51) {
        override fun <T> accept(visitor: TokenVisitor<T>): T {
            return visitor.visit(this)
        }

        @Throws(Exception::class)
        override fun accept(visitor: TokenEvaluator) {
            return visitor.visit(this)
        }
    }

    class EqualEqual : OperatorToken("==", 52) {
        override fun <T> accept(visitor: TokenVisitor<T>): T {
            return visitor.visit(this)
        }

        @Throws(Exception::class)
        override fun accept(visitor: TokenEvaluator): Boolean {
            return visitor.visit(this)
        }
    }

    class NotEqual : OperatorToken("!=", 53) {
        override fun <T> accept(visitor: TokenVisitor<T>): T {
            return visitor.visit(this)
        }

        @Throws(Exception::class)
        override fun accept(visitor: TokenEvaluator): Boolean {
            return visitor.visit(this)
        }
    }

    class LessThanOrEqual : OperatorToken("<=", 54) {
        override fun <T> accept(visitor: TokenVisitor<T>): T {
            return visitor.visit(this)
        }

        @Throws(Exception::class)
        override fun accept(visitor: TokenEvaluator): Boolean {
            return visitor.visit(this)
        }
    }

    class GreaterThanOrEqual : OperatorToken(">=", 55) {
        override fun <T> accept(visitor: TokenVisitor<T>): T {
            return visitor.visit(this)
        }

        @Throws(Exception::class)
        override fun accept(visitor: TokenEvaluator): Boolean {
            return visitor.visit(this)
        }
    }

    class BitShiftLeft : OperatorToken("<<", 56) {
        override fun <T> accept(visitor: TokenVisitor<T>): T {
            return visitor.visit(this)
        }

        @Throws(Exception::class)
        override fun accept(visitor: TokenEvaluator): Number {
            return visitor.visit(this)
        }
    }

    class BitShiftRight : OperatorToken(">>", 57) {
        override fun <T> accept(visitor: TokenVisitor<T>): T {
            return visitor.visit(this)
        }

        @Throws(Exception::class)
        override fun accept(visitor: TokenEvaluator): Number {
            return visitor.visit(this)
        }
    }

    class Caret : OperatorToken("^", 42) {
        override fun <T> accept(visitor: TokenVisitor<T>): T {
            return visitor.visit(this)
        }

        @Throws(Exception::class)
        override fun accept(visitor: TokenEvaluator): Number {
            return visitor.visit(this)
        }
    }

    class ForwardSlash : OperatorToken("/", 48) {
        override fun <T> accept(visitor: TokenVisitor<T>): T {
            return visitor.visit(this)
        }

        @Throws(Exception::class)
        override fun accept(visitor: TokenEvaluator): Number {
            return visitor.visit(this)
        }
    }
}
