package compiler.lexer.token

import compiler.parser.TokenEvaluator
import compiler.parser.visitors.TokenVisitor

class FloatToken(str: String) : TypedToken<Float>(str, 4) {
    override fun parse(str: String): Float {
        return str.toFloat()
    }

    public override fun toStringExtra(): String? {
        return " flo= $value"
    }

    override fun <T> accept(visitor: TokenVisitor<T>): T {
        return visitor.visit(this)
    }

    @Throws(Exception::class)
    override fun accept(visitor: TokenEvaluator): Float {
        return visitor.visit(this)
    }

    companion object {
        val sentinel = FloatToken("0")
    }
}
