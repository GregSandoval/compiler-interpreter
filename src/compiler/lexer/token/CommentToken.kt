package compiler.lexer.token

import compiler.parser.TokenEvaluator
import compiler.parser.visitors.TokenVisitor

class CommentToken(str: String?) : Token(str!!, 1) {
    override fun <T> accept(visitor: TokenVisitor<T>): T {
        return visitor.visit(this)
    }

    @Throws(Exception::class)
    override fun accept(visitor: TokenEvaluator) {
        return visitor.visit(this)
    }
}
