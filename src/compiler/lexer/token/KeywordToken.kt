package compiler.lexer.token

import compiler.parser.TokenEvaluator
import compiler.parser.visitors.TokenVisitor

abstract class KeywordToken constructor(str: String, UUID: Int) : Token(str, UUID) {
    class ProgramKeywordToken : KeywordToken("prog", 10) {
        override fun <T> accept(visitor: TokenVisitor<T>): T {
            return visitor.visit(this)
        }

        @Throws(Exception::class)
        override fun accept(visitor: TokenEvaluator) {
            return visitor.visit(this)
        }
    }

    class MainKeywordToken : KeywordToken("main", 11) {
        override fun <T> accept(visitor: TokenVisitor<T>): T {
            return visitor.visit(this)
        }

        @Throws(Exception::class)
        override fun accept(visitor: TokenEvaluator) {
            return visitor.visit(this)
        }
    }

    class FunctionKeywordToken : KeywordToken("fcn", 12) {
        override fun <T> accept(visitor: TokenVisitor<T>): T {
            return visitor.visit(this)
        }

        @Throws(Exception::class)
        override fun accept(visitor: TokenEvaluator) {
            return visitor.visit(this)
        }
    }

    class ClassKeywordToken : KeywordToken("class", 13) {
        override fun <T> accept(visitor: TokenVisitor<T>): T {
            return visitor.visit(this)
        }

        @Throws(Exception::class)
        override fun accept(visitor: TokenEvaluator) {
            return visitor.visit(this)
        }
    }

    class IfKeywordToken : KeywordToken("if", 18) {
        override fun <T> accept(visitor: TokenVisitor<T>): T {
            return visitor.visit(this)
        }

        @Throws(Exception::class)
        override fun accept(visitor: TokenEvaluator) {
            return visitor.visit(this)
        }
    }

    class ElseIfKeywordToken : KeywordToken("elseif", 19) {
        override fun <T> accept(visitor: TokenVisitor<T>): T {
            return visitor.visit(this)
        }

        @Throws(Exception::class)
        override fun accept(visitor: TokenEvaluator) {
            return visitor.visit(this)
        }
    }

    class ElseKeywordToken : KeywordToken("else", 20) {
        override fun <T> accept(visitor: TokenVisitor<T>): T {
            return visitor.visit(this)
        }

        @Throws(Exception::class)
        override fun accept(visitor: TokenEvaluator) {
            return visitor.visit(this)
        }
    }

    class WhileKeywordToken : KeywordToken("while", 21) {
        override fun <T> accept(visitor: TokenVisitor<T>): T {
            return visitor.visit(this)
        }

        @Throws(Exception::class)
        override fun accept(visitor: TokenEvaluator) {
            return visitor.visit(this)
        }
    }

    class InputKeywordToken : KeywordToken("input", 22) {
        override fun <T> accept(visitor: TokenVisitor<T>): T {
            return visitor.visit(this)
        }

        @Throws(Exception::class)
        override fun accept(visitor: TokenEvaluator): String {
            return visitor.visit(this)
        }
    }

    class PrintKeywordToken : KeywordToken("print", 23) {
        override fun <T> accept(visitor: TokenVisitor<T>): T {
            return visitor.visit(this)
        }

        @Throws(Exception::class)
        override fun accept(visitor: TokenEvaluator) {
            return visitor.visit(this)
        }
    }

    class NewKeywordToken : KeywordToken("new", 24) {
        override fun <T> accept(visitor: TokenVisitor<T>): T {
            return visitor.visit(this)
        }

        @Throws(Exception::class)
        override fun accept(visitor: TokenEvaluator) {
            return visitor.visit(this)
        }
    }

    class ReturnKeywordToken : KeywordToken("return", 25) {
        override fun <T> accept(visitor: TokenVisitor<T>): T {
            return visitor.visit(this)
        }

        @Throws(Exception::class)
        override fun accept(visitor: TokenEvaluator) {
            return visitor.visit(this)
        }
    }

    class VarKeywordToken : KeywordToken("var", 2) {
        override fun <T> accept(visitor: TokenVisitor<T>): T {
            return visitor.visit(this)
        }

        @Throws(Exception::class)
        override fun accept(visitor: TokenEvaluator) {
            return visitor.visit(this)
        }
    }
}
