package compiler.utils

import java.util.*

/**
 * A java compliant iterator over the given string.
 * This iterator also knows which line/position number
 * it's on. Can also reverse.
 */
class TextCursor(text: String) : Iterator<Char>, Iterable<Char>, MutableIterator<Char> {
    private val text: CharArray = (text + "\n").toCharArray()
    private val lineNumbers: IntArray
    private val linePositions: IntArray
    private var cursor = -1
    override fun hasNext(): Boolean {
        return cursor + 1 < text.size
    }

    override fun next(): Char {
        if (cursor + 1 >= text.size) {
            throw NoSuchElementException()
        }
        return text[++cursor]
    }

    fun rewind() {
        if (cursor - 1 < 0) {
            throw NoSuchElementException()
        }
        cursor--
    }

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

    override fun iterator(): MutableIterator<Char> {
        return this
    }

    companion object {
        private fun isNewLine(letter: Char): Boolean {
            return letter == '\n' || letter == '\r'
        }
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

    override fun remove() {
        TODO("not implemented")
    }
}
