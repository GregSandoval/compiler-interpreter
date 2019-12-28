package compiler.lexer.token

import compiler.parser.TokenEvaluator
import compiler.parser.visitors.TokenVisitor

class IntegerToken(str: String) : TypedToken<Int>(str, 3) {
    override fun parse(str: String): Int {
        return str.toInt()
    }

    public override fun toStringExtra(): String? {
        return " int= $value"
    }

    override fun <T> accept(visitor: TokenVisitor<T>): T {
        return visitor.visit(this)
    }

    @Throws(Exception::class)
    override fun accept(visitor: TokenEvaluator): Number {
        return visitor.visit(this)
    }

    companion object {
        val sentinel = IntegerToken("0")
    }
}
