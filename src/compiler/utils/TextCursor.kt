package compiler.utils

import java.util.*

/**
 * A java compliant iterator over the given string.
 * This iterator also knows which line/position number
 * it's on. Can also reverse.
 */
interface PeekableIterator<T> : Iterator<T>, Iterable<T> {
    fun peek(): T
}

class TextCursor(text: String) : PeekableIterator<Char> {
    private val text: CharArray = text.toCharArray()
    private val lineNumbers: IntArray
    private val linePositions: IntArray
    private var cursor = -1

    fun getCursorLineNumber() = lineNumbers[cursor]

    fun getCursorLinePosition() = linePositions[cursor]

    fun getCurrentLineOfText(): String {
        val savedCursor = cursor
        val lineBuilder = StringBuilder()
        while (cursor != 0 && !isNewLine(text[cursor - 1])) {
            --cursor
        }
        while (hasNext() && !isNewLine(text[cursor])) {
            lineBuilder.append(text[cursor++])
        }
        cursor = savedCursor
        return lineBuilder.toString()
    }

    override fun iterator(): Iterator<Char> {
        return this
    }

    override fun hasNext(): Boolean {
        return cursor + 1 < text.size
    }

    override fun next(): Char {
        if (cursor + 1 >= text.size) {
            throw NoSuchElementException()
        }
        return text[++cursor]
    }

    override fun peek(): Char {
        if (cursor + 1 >= text.size) {
            throw NoSuchElementException()
        }
        return text[cursor + 1]
    }

    fun previous() {
        if (cursor - 1 < 0) {
            throw NoSuchElementException()
        }
        cursor--
    }

    private fun isNewLine(letter: Char): Boolean {
        return letter == '\n' || letter == '\r'
    }

    init {
        lineNumbers = IntArray(this.text.size)
        linePositions = IntArray(this.text.size)
        var line = 1
        var pos = 1
        for (i in this.text.indices) {
            lineNumbers[i] = line
            linePositions[i] = pos
            if (isNewLine(this.text[i])) {
                line++
                pos = 0
            }
            pos++
        }
    }

}
