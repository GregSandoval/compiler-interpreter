package compiler.utils

/**
 * A functional interface that mimics java's BiConsumer class,
 * Adds a third parameter.
 */
@FunctionalInterface
interface TriConsumer<P1, P2, P3> {
    fun accept(p1: P1, p2: P2, p3: P3)
    fun andThen(after: TriConsumer<in P1, in P2, in P3>): TriConsumer<P1, P2, P3> {
        return object : TriConsumer<P1, P2, P3> {
            override fun accept(p1: P1, p2: P2, p3: P3) {
                this@TriConsumer.accept(p1, p2, p3)
                after.accept(p1, p2, p3)
            }
        }
    }
}
