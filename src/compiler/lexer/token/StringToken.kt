package compiler.lexer.token

import compiler.parser.TokenEvaluator
import compiler.parser.visitors.TokenVisitor

class StringToken(str: String) : Token(str.replace("\"", ""), 5) {
    override fun <T> accept(visitor: TokenVisitor<T>): T {
        return visitor.visit(this)
    }

    @Throws(Exception::class)
    override fun accept(visitor: TokenEvaluator): Any {
        return str
    }

    companion object {
        val sentinel = StringToken("")
    }
}
