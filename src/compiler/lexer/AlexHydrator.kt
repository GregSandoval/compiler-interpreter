package compiler.lexer

import compiler.lexer.token.Token
import java.util.*
import java.util.regex.Pattern

class AlexHydrator(private val lexer: Lexer) {
    private val pattern = "\\(Tok: (\\d+) lin= (\\d+),(\\d+) str = \"([^\"]*)\""
    private val tokenPattern = Pattern.compile(pattern)
    private val STRING_TOKEN = 5

    fun hydrate(serializedTokenStream: String): List<Token> {
        val matcher = tokenPattern.matcher(serializedTokenStream)
        val program = ArrayList<String>()
        val lineNumbers = ArrayList<Int>()
        val linePositions = ArrayList<Int>()
        while (matcher.find()) {
            val id = matcher.group(1).toInt()
            val lineNumber = matcher.group(2).toInt()
            val linePosition = matcher.group(3).toInt()
            var value = matcher.group(4)
            if (id == STRING_TOKEN) value = "\"" + value + "\""
            program.add(value)
            lineNumbers.add(lineNumber)
            linePositions.add(linePosition)
        }
        val tokens = lexer.lex(java.lang.String.join(" ", program))
        for (i in tokens.indices) {
            val token = tokens[i]
            token.lineNumber = lineNumbers[i]
            token.linePosition = linePositions[i]
        }
        return tokens
    }

}
