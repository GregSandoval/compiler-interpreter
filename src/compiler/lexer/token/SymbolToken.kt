package compiler.lexer.token

import compiler.parser.TokenEvaluator
import compiler.parser.visitors.TokenVisitor

abstract class SymbolToken(str: String, UUID: Int) : Token(str, UUID) {
    class Comma : SymbolToken(",", 6) {
        override fun <T> accept(visitor: TokenVisitor<T>): T {
            return visitor.visit(this)
        }

        @Throws(Exception::class)
        override fun accept(visitor: TokenEvaluator) {
            return visitor.visit(this)
        }
    }

    class SemiColon : SymbolToken(";", 7) {
        override fun <T> accept(visitor: TokenVisitor<T>): T {
            return visitor.visit(this)
        }

        @Throws(Exception::class)
        override fun accept(visitor: TokenEvaluator) {
            return visitor.visit(this)
        }
    }

    class LeftBrace : SymbolToken("{", 33) {
        override fun <T> accept(visitor: TokenVisitor<T>): T {
            return visitor.visit(this)
        }

        @Throws(Exception::class)
        override fun accept(visitor: TokenEvaluator) {
            return visitor.visit(this)
        }
    }

    class RightBrace : SymbolToken("}", 34) {
        @Throws(Exception::class)
        override fun accept(visitor: TokenEvaluator) {
            return visitor.visit(this)
        }

        override fun <T> accept(visitor: TokenVisitor<T>): T {
            return visitor.visit(this)
        }
    }

    class LeftBracket : SymbolToken("[", 35) {
        override fun <T> accept(visitor: TokenVisitor<T>): T {
            return visitor.visit(this)
        }

        @Throws(Exception::class)
        override fun accept(visitor: TokenEvaluator) {
            return visitor.visit(this)
        }
    }

    class RightBracket : SymbolToken("]", 36) {
        override fun <T> accept(visitor: TokenVisitor<T>): T {
            return visitor.visit(this)
        }

        @Throws(Exception::class)
        override fun accept(visitor: TokenEvaluator) {
            return visitor.visit(this)
        }
    }

    class LeftParen : SymbolToken("(", 37) {
        override fun <T> accept(visitor: TokenVisitor<T>): T {
            return visitor.visit(this)
        }

        @Throws(Exception::class)
        override fun accept(visitor: TokenEvaluator): List<Any> {
            return visitor.visit(this)
        }
    }

    class RightParen : SymbolToken(")", 38) {
        override fun <T> accept(visitor: TokenVisitor<T>): T {
            return visitor.visit(this)
        }

        @Throws(Exception::class)
        override fun accept(visitor: TokenEvaluator) {
            return visitor.visit(this)
        }
    }

    class Colon : SymbolToken(":", 43) {
        override fun <T> accept(visitor: TokenVisitor<T>): T {
            return visitor.visit(this)
        }

        @Throws(Exception::class)
        override fun accept(visitor: TokenEvaluator) {
            return visitor.visit(this)
        }
    }

    class Period : SymbolToken(".", 44) {
        override fun <T> accept(visitor: TokenVisitor<T>): T {
            return visitor.visit(this)
        }

        @Throws(Exception::class)
        override fun accept(visitor: TokenEvaluator) {
            return visitor.visit(this)
        }
    }
}
