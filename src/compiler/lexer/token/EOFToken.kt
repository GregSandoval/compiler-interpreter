package compiler.lexer.token

import compiler.parser.TokenEvaluator
import compiler.parser.visitors.TokenVisitor

class EOFToken : Token("", 0) {
    override fun <T> accept(visitor: TokenVisitor<T>): T {
        return visitor.visit(this)
    }

    @Throws(Exception::class)
    override fun accept(visitor: TokenEvaluator) {
        return visitor.visit(this)
    }
}
