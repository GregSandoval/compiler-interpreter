package compiler.interpreter

import compiler.interpreter.symboltable.IdentifierUsedBeforeInitializationException
import compiler.interpreter.symboltable.OperatorUsageUndefined
import compiler.interpreter.symboltable.SymbolTable
import compiler.interpreter.symboltable.undefined
import compiler.parser.Symbol.Terminal
import compiler.parser.Symbol.Terminal.Keyword
import compiler.parser.Symbol.Terminal.Keyword.*
import compiler.parser.Symbol.Terminal.Operator.*
import compiler.parser.Symbol.Terminal.Punctuation.LeftBrace
import compiler.parser.Symbol.Terminal.Punctuation.LeftParen
import compiler.parser.Symbol.Terminal.TypedTerminal.*
import compiler.parser.TokenEvaluator
import compiler.parser.TreeNode
import java.util.*
import kotlin.math.pow

class InterpreterVisitor(private val symtab: SymbolTable) : TokenEvaluator {
    private val scanner = Scanner(System.`in`)

    override fun visit(token: InputKeyword): String {
        return scanner.nextLine()
    }

    override fun visit(token: WhileKeyword) {
        val paren = token.children[0] as LeftParen
        val body = token.children[1] as LeftBrace
        while ((this.accept(paren) as List<Boolean>)[0]) {
            this.accept(body)
        }
    }

    override fun visit(token: IfKeyword) {
        val paren = token.children[0] as LeftParen
        val body = token.children[1] as LeftBrace
        if ((this.accept(paren) as List<Boolean>)[0]) {
            this.accept(body)
            return
        }
        if (token.children.size < 3) {
            return
        }
        this.accept(token.children[2] as Terminal)
    }

    override fun visit(token: ElseIfKeyword) {
        val paren = token.children[0] as LeftParen
        val body = token.children[1] as LeftBrace
        if ((this.accept(paren) as List<Boolean>)[0]) {
            this.accept(body)
            return
        }
        if (token.children.size < 3) {
            return
        }
        this.accept(token.children[2] as Terminal)
    }

    override fun visit(token: ElseKeyword) {
        val body = token.children[0] as LeftBrace
        this.accept(body)
        return
    }

    override fun visit(token: IdentifierTerminal): Any {
        if (token.parent is Type) {
            return token
        }
        val value = symtab.getValue(token)

        if (value === undefined) {
            throw IdentifierUsedBeforeInitializationException(token)
        }

        return value
    }

    override fun visit(token: Equal) {
        val identifier: Terminal
        val identifierOrType = token.children[0] as Terminal
        identifier = if (identifierOrType is Keyword) {
            token.children[0].children[0] as Terminal
        } else {
            token.children[0] as Terminal
        }

        val value = this.accept(token.children[1] as Terminal)

        if (!symtab.hasSymbol(identifier)) {
            throw Exception(identifier.str + " is not defined")
        }

        symtab.setSymbolValue(identifier, value)
    }

    override fun visit(token: LeftParen): List<Any> {
        val result: MutableList<Any> = ArrayList()
        for (child in token.children) {
            result.add(this.accept(child as Terminal))
        }
        return result
    }

    override fun visit(token: PrintKeyword) {
        val evaluatedParameters = this.accept(token.children[0] as LeftParen);

        if (evaluatedParameters !is List<*>) {
            throw Exception("Expected print() to return a list of values, instead found ${evaluatedParameters::class.simpleName}")
        }

        val text = evaluatedParameters
                .map { it.toString() }
                .joinToString("")
                .replace("\\\\n".toRegex(), System.lineSeparator())

        print(text)
    }

    // Primitives
    override fun visit(token: FloatTerminal): Float {
        return token.value
    }

    override fun visit(token: IntegerTerminal): Int {
        return token.value
    }

    override fun visit(token: StringTerminal): String {
        return token.str
    }

    // Relations
    override fun visit(token: LessThan): Boolean {
        return comparePrimitiveTokens(
                token.children[0],
                token.children[1],
                { it < 0 }
        )
    }

    override fun visit(token: GreaterThan): Boolean {
        return comparePrimitiveTokens(
                token.children[0],
                token.children[1],
                { it > 0 }
        )
    }

    override fun visit(token: EqualEqual): Boolean {
        return comparePrimitiveTokens(
                token.children[0],
                token.children[1],
                { it == 0 }
        )
    }

    override fun visit(token: NotEqual): Boolean {
        return comparePrimitiveTokens(
                token.children[0],
                token.children[1],
                { it != 0 }
        )
    }

    override fun visit(token: LessThanOrEqual): Boolean {
        return comparePrimitiveTokens(
                token.children[0],
                token.children[1],
                { it <= 0 }
        )
    }

    override fun visit(token: GreaterThanOrEqual): Boolean {
        return comparePrimitiveTokens(
                token.children[0],
                token.children[1],
                { it >= 0 }
        )
    }

    // Operators
    override fun visit(token: Asterisk): Any {
        if (token.children.size == 1) {
            val pointer = token.children[0] as Terminal
            val address = symtab.getValue(pointer) as String
            return symtab.getValueAtAddress(address)!!
        }
        val leftToken = token.children[0] as Terminal
        val rightToken = token.children[1] as Terminal
        return numberBiFunction(
                leftToken,
                rightToken,
                { left, right -> left * right },
                { left, right -> left * right },
                { left, right -> OperatorUsageUndefined(token, left, right) }
        )!!
    }

    override fun visit(token: Minus): Number {
        val leftToken = token.children[0] as Terminal
        val rightToken = token.children[1] as Terminal
        return numberBiFunction(
                leftToken,
                rightToken,
                { left, right -> left - right },
                { left, right -> left - right },
                { left, right -> OperatorUsageUndefined(token, left, right) }
        )!!
    }

    override fun visit(token: Plus): Number {
        val leftToken = token.children[0] as Terminal
        val rightToken = token.children[1] as Terminal
        return numberBiFunction(
                leftToken,
                rightToken,
                { a, b -> java.lang.Float.sum(a, b) },
                { a, b -> Integer.sum(a, b) },
                { left, right -> OperatorUsageUndefined(token, left, right) }
        )!!
    }

    override fun visit(token: BitShiftLeft): Number {
        val leftToken = token.children[0] as Terminal
        val rightToken = token.children[1] as Terminal
        return numberBiFunction(
                leftToken,
                rightToken,
                { left, _ -> left },
                { left, right -> left shl right },
                { left, right -> OperatorUsageUndefined(token, left, right) }
        )!!
    }

    override fun visit(token: BitShiftRight): Number {
        val leftToken = token.children[0] as Terminal
        val rightToken = token.children[1] as Terminal
        return numberBiFunction(
                leftToken,
                rightToken,
                { left, _ -> left },
                { left, right -> left shr right },
                { left, right -> OperatorUsageUndefined(token, left, right) }
        )!!
    }

    override fun visit(token: Caret): Number {
        val leftToken = token.children[0] as Terminal
        val rightToken = token.children[1] as Terminal
        return numberBiFunction(
                leftToken,
                rightToken,
                { a, b -> a.toDouble().pow(b.toDouble()) },
                { a, b -> a.toDouble().pow(b.toDouble()) },
                { left, right -> OperatorUsageUndefined(token, left, right) }
        )!!
    }

    override fun visit(token: ForwardSlash): Number {
        val leftToken = token.children[0] as Terminal
        val rightToken = token.children[1] as Terminal
        return numberBiFunction(
                leftToken,
                rightToken,
                { left, right -> left / right },
                { left, right -> left / right },
                { left, right -> OperatorUsageUndefined(token, left, right) }
        )!!
    }

    override fun visit(token: Ampersand): Any {
        return symtab.getAddress((token.children[0] as Terminal))
    }

    fun comparePrimitiveTokens(leftToken: TreeNode, rightToken: TreeNode, comparator: (Int) -> Boolean): Boolean {
        return comparator(comparePrimitiveTokens(leftToken, rightToken))
    }

    fun comparePrimitiveTokens(
            leftToken: TreeNode,
            rightToken: TreeNode
    ): Int {
        return primitiveBiFunction(
                leftToken,
                rightToken,
                { f1, f2 -> f1.compareTo(f2) },
                { x, y -> x.compareTo(y) },
                { obj, anotherString -> obj.compareTo(anotherString) },
                { left, right -> classNotCompatibleException(left, right) }
        )!!
    }

    fun <T> primitiveBiFunction(
            leftToken: TreeNode,
            rightToken: TreeNode,
            onFloat: (Float, Float) -> T,
            onInt: (Int, Int) -> T,
            onString: (String, String) -> T,
            onError: (Any, Any) -> Exception?
    ): T? {
        var left = evaluate(Any::class.java, leftToken)
        var right = evaluate(Any::class.java, rightToken)
        val result = numberBiFunction(
                leftToken,
                rightToken,
                onFloat,
                onInt,
                { _, _ -> null }
        )
        if (result != null) {
            return result
        }
        while (left is List<*>) {
            left = left[0]!!
        }
        while (right is List<*>) {
            right = right[0]!!
        }
        if (left is String && right is String) {
            return onString(left, right)
        }
        val exception = onError(left, right)

        if (exception != null) {
            throw exception
        }

        return null
    }

    fun <T> numberBiFunction(
            leftToken: TreeNode,
            rightToken: TreeNode,
            onFloat: (Float, Float) -> T,
            onInt: (Int, Int) -> T,
            onError: (Any, Any) -> Exception?
    ): T? {
        var left = evaluate(Any::class.java, leftToken)
        var right = evaluate(Any::class.java, rightToken)
        while (left is List<*>) {
            left = left[0]!!
        }
        while (right is List<*>) {
            right = right[0]!!
        }
        if (left is Number && right is Number) {
            if (left is Float || right is Float) {
                return onFloat(left.toFloat(), right.toFloat())
            }
            if (left is Int || right is Int) {
                return onInt(left.toInt(), right.toInt())
            }
        }
        val exception = onError(left, right)
        if (exception != null) {
            throw exception
        }
        return null
    }

    fun <T> evaluate(valueType: Class<T>, node: TreeNode): T {
        val token = assertClass(Terminal::class.java, node)
        return assertClass(valueType, this.accept(token))
    }

    fun <T> assertClass(clazz: Class<T>, obj: Any): T {
        if (clazz.isInstance(obj)) {
            return clazz.cast(obj)
        }
        throw Exception("${clazz.simpleName} expected but found${obj.javaClass.simpleName}")
    }

    fun classNotCompatibleException(left: Any, right: Any): Exception {
        return Exception("Type ${left.javaClass.simpleName} and ${right.javaClass.simpleName} are cannot be used with operator")
    }

}
