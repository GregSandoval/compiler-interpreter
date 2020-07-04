package compiler.utils

/**
 * Random string utils.
 */
object StringUtils {
    fun escape(character: Char): String {
        return escape("" + character)
    }

    fun escape(string: String): String {
        return string
                .replace("\n", "\\n")
                .replace("\t", "\\t")
                .replace("\r", "\\r")
    }
}
