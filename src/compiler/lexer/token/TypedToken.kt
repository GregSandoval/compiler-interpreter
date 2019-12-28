package compiler.lexer.token

/**
 * Represents a token whose value is some generic type that
 * is known at compile time. Tokens that have an associated
 * value (that is not string) should extend this class.
 *
 * @param <Value>
</Value> */
abstract class TypedToken<Value> constructor(str: String, tokenID: Int) : Token(str, tokenID) {
    val value = parse(str)

    abstract fun parse(str: String): Value
}
