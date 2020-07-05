package compiler.utils

fun <P1> ((P1) -> Unit).andThen(next: (P1) -> Unit): ((P1) -> Unit) {
    return { p1 -> this(p1); next(p1) }
}

fun <P1, P2> ((P1, P2) -> Unit).andThen(next: (P1, P2) -> Unit): ((P1, P2) -> Unit) {
    return { p1, p2 -> this(p1, p2); next(p1, p2) }
}

fun <P1, P2, P3> ((P1, P2, P3) -> Unit).andThen(next: (P1, P2, P3) -> Unit): ((P1, P2, P3) -> Unit) {
    return { p1, p2, p3 -> this(p1, p2, p3); next(p1, p2, p3) }
}

/**
 * A grouping of commonly used functions for detecting character classes.
 * Used to define the conditions for when the lexer should transition
 * to a different state.
 */
typealias Predicate<T> = (T) -> Boolean

infix fun <T> Predicate<T>.or(other: Predicate<in T>): Predicate<T> {
    return { input: T -> this(input) || other(input) }
}

infix fun <T> Predicate<T>.and(other: Predicate<in T>): Predicate<T> {
    return { input: T -> this(input) && other(input) }
}

operator fun <T> Predicate<T>.not(): Predicate<T> {
    return { input: T -> !this(input) }
}

