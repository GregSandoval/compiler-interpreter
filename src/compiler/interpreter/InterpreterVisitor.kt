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
import kotlin.reflect.KClass

class InterpreterVisitor(private val symtab: SymbolTable) : TokenEvaluator {
    private val scanner = Scanner(System.`in`)

    override fun visit(token: InputKeyword): String {
        return scanner.nextLine()
    }

    override fun visit(token: WhileKeyword) {
        val paren = token.children[0].expectedClass(LeftParen::class)
        val body = token.children[1].expectedClass(LeftBrace::class)

        while (paren.evaluateToList(Boolean::class)[0]) {
            body.evaluate()
        }
    }

    override fun visit(token: IfKeyword) {
        val paren = token.children[0].expectedClass(LeftParen::class)
        val body = token.children[1].expectedClass(LeftBrace::class)
        val condition = paren.evaluateToList(Boolean::class)

        if (condition[0]) {
            body.evaluate()
            return
        }

        if (token.children.size < 3) {
            return
        }

        this.accept(token.children[2] as Terminal)
    }

    override fun visit(token: ElseIfKeyword) {
        val paren = token.children[0].expectedClass(LeftParen::class)
        val body = token.children[1].expectedClass(LeftBrace::class)
        val condition = paren.evaluateToList(Boolean::class)

        if (condition[0]) {
            body.evaluate()
            return
        }

        if (token.children.size < 3) {
            return
        }

        this.accept(token.children[2] as Terminal)
    }

    override fun visit(token: ElseKeyword) {
        token.children[0]
                .expectedClass(LeftBrace::class)
                .evaluate()
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
        val identifierOrType = token.children[0].expectedClass(Terminal::class)

        val identifier = when (identifierOrType) {
            is Keyword -> token.children[0].children[0].expectedClass(Terminal::class)
            else -> token.children[0].expectedClass(Terminal::class)
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
        print(token.children[0]
                .evaluateToList(Any::class)
                .map { it.toString() }
                .joinToString("")
                .replace("\\\\n".toRegex(), System.lineSeparator())
        )
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
            val pointer = token.children[0].expectedClass(Terminal::class)
            val address = symtab.getValue(pointer).expectedClass(String::class)
            return symtab.getValueAtAddress(address)!!
        }

        val leftToken = token.children[0].expectedClass(Terminal::class)
        val rightToken = token.children[1].expectedClass(Terminal::class)
        return strictNumberBiFunction(
                leftToken,
                rightToken,
                { left, right -> left * right },
                { left, right -> left * right },
                { left, right -> OperatorUsageUndefined(token, left, right) }
        )
    }

    override fun visit(token: Minus): Number {
        val leftToken = token.children[0].expectedClass(Terminal::class)
        val rightToken = token.children[1].expectedClass(Terminal::class)
        return strictNumberBiFunction(
                leftToken,
                rightToken,
                { left, right -> left - right },
                { left, right -> left - right },
                { left, right -> OperatorUsageUndefined(token, left, right) }
        )
    }

    override fun visit(token: Plus): Number {
        val leftToken = token.children[0].expectedClass(Terminal::class)
        val rightToken = token.children[1].expectedClass(Terminal::class)
        return strictNumberBiFunction(
                leftToken,
                rightToken,
                { a, b -> a + b },
                { a, b -> a + b },
                { left, right -> OperatorUsageUndefined(token, left, right) }
        )
    }

    override fun visit(token: BitShiftLeft): Number {
        val leftToken = token.children[0].expectedClass(Terminal::class)
        val rightToken = token.children[1].expectedClass(Terminal::class)
        return strictNumberBiFunction(
                leftToken,
                rightToken,
                { left, right -> throw OperatorUsageUndefined(token, left, right) },
                { left, right -> left shl right },
                { left, right -> OperatorUsageUndefined(token, left, right) }
        )
    }

    override fun visit(token: BitShiftRight): Number {
        val leftToken = token.children[0].expectedClass(Terminal::class)
        val rightToken = token.children[1].expectedClass(Terminal::class)
        return strictNumberBiFunction(
                leftToken,
                rightToken,
                { left, right -> throw OperatorUsageUndefined(token, left, right) },
                { left, right -> left shr right },
                { left, right -> OperatorUsageUndefined(token, left, right) }
        )
    }

    override fun visit(token: Caret): Number {
        val leftToken = token.children[0].expectedClass(Terminal::class)
        val rightToken = token.children[1].expectedClass(Terminal::class)
        return strictNumberBiFunction(
                leftToken,
                rightToken,
                { a, b -> a.pow(b) },
                { a, b -> a.toDouble().pow(b.toDouble()) },
                { left, right -> OperatorUsageUndefined(token, left, right) }
        )
    }

    override fun visit(token: ForwardSlash): Number {
        val leftToken = token.children[0].expectedClass(Terminal::class)
        val rightToken = token.children[1].expectedClass(Terminal::class)
        return strictNumberBiFunction(
                leftToken,
                rightToken,
                { left, right -> left / right },
                { left, right -> left / right },
                { left, right -> OperatorUsageUndefined(token, left, right) }
        )
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
        return strictPrimitiveBiFunction(
                leftToken,
                rightToken,
                { f1, f2 -> f1.compareTo(f2) },
                { x, y -> x.compareTo(y) },
                { obj, anotherString -> obj.compareTo(anotherString) },
                { left, right -> classNotCompatibleException(left, right) }
        )
    }

    fun <T> strictPrimitiveBiFunction(
            leftToken: TreeNode,
            rightToken: TreeNode,
            onFloat: (Float, Float) -> T,
            onInt: (Int, Int) -> T,
            onString: (String, String) -> T,
            onError: (Any, Any) -> Exception
    ): T {
        val result = primitiveBiFunction(leftToken, rightToken, onFloat, onInt, onString)

        if (result != null)
            return result

        var left = leftToken.evaluateTo(Any::class)
        var right = rightToken.evaluateTo(Any::class)

        while (left is List<*>) {
            left = left[0]!!
        }

        while (right is List<*>) {
            right = right[0]!!
        }

        throw onError(left, right)
    }


    fun <T> primitiveBiFunction(
            leftToken: TreeNode,
            rightToken: TreeNode,
            onFloat: (Float, Float) -> T,
            onInt: (Int, Int) -> T,
            onString: (String, String) -> T
    ): T? {
        var left = leftToken.evaluateTo(Any::class)
        var right = rightToken.evaluateTo(Any::class)

        val result = numberBiFunction(
                leftToken,
                rightToken,
                onFloat,
                onInt
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

        return null
    }

    fun <T> strictNumberBiFunction(
            leftToken: TreeNode,
            rightToken: TreeNode,
            onFloat: (Float, Float) -> T,
            onInt: (Int, Int) -> T,
            onError: (Any, Any) -> Exception
    ): T {
        val result = numberBiFunction(leftToken, rightToken, onFloat, onInt)

        if (result !== null)
            return result

        var left = leftToken.evaluateTo(Any::class)
        var right = rightToken.evaluateTo(Any::class)

        while (left is List<*>) {
            left = left[0]!!
        }
        while (right is List<*>) {
            right = right[0]!!
        }

        throw onError(left, right)
    }

    fun <T> numberBiFunction(
            leftToken: TreeNode,
            rightToken: TreeNode,
            onFloat: (Float, Float) -> T,
            onInt: (Int, Int) -> T
    ): T? {
        var left = leftToken.evaluateTo(Any::class)
        var right = rightToken.evaluateTo(Any::class)

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

        return null
    }

    fun <T : Any> Any.evaluateTo(expectedType: KClass<out T>): T {
        val token = this.expectedClass(Terminal::class)
        return this@InterpreterVisitor.accept(token).expectedClass(expectedType)
    }

    fun Any.evaluate() {
        val token = this.expectedClass(Terminal::class)
        this@InterpreterVisitor.accept(token)
    }

    fun <T : Any> Any.evaluateToList(expectedType: KClass<out T>): List<T> {
        val token = this.expectedClass(Terminal::class)
        val value = this@InterpreterVisitor.accept(token).expectedClass(List::class)

        if (value.all { expectedType.isInstance(it) }) {
            return value as List<T>
        }

        throw Exception("Expected List<${expectedType.simpleName}> but found List<%> where % = ${value.map { it!!::class }}")
    }

    fun classNotCompatibleException(left: Any, right: Any): Exception {
        return Exception("Type ${left.javaClass.simpleName} and ${right.javaClass.simpleName} are cannot be used with operator")
    }

}

private fun <T : Any> Any.expectedClass(clazz: KClass<out T>): T {
    if (clazz.isInstance(this)) {
        return this as T
    }

    throw Exception("Expected ${clazz.simpleName} but found${this::class.simpleName}.")
}

