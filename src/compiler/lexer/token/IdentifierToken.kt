package compiler.lexer.token

import compiler.parser.TokenEvaluator
import compiler.parser.visitors.TokenVisitor

class IdentifierToken(s: String) : TypedToken<String>(s, 2) {
    override fun parse(str: String): String {
        return str
    }

    override fun <T> accept(visitor: TokenVisitor<T>): T {
        return visitor.visit(this)
    }

    @Throws(Exception::class)
    override fun accept(visitor: TokenEvaluator): Any {
        return visitor.visit(this)
    }

    companion object {
        val sentinel = IdentifierToken("Sentinel")
    }
}
