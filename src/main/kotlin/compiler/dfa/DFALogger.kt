package compiler.dfa

import compiler.dfa.LexicalNode.ERROR
import compiler.parser.Symbol.Terminal
import compiler.utils.TextCursor

object DFALogger {
    fun logTransition(start: LexicalNode, character: Char, end: LexicalNode) {
        if (end !is ERROR)
            System.out.printf("%-19s = %-5s=> %-15s\n", start, "'${escape(character)}'", end);
    }

    fun logAcceptedToken(token: Terminal, text: TextCursor) {
        System.out.println("Accepted token value: \"" + escape(token.str) + "\"\n");
    }

    fun escape(letter: Char): String {
        return this.escape("$letter")
    }

    fun escape(text: String): String {
        return text.replace("\n", "\\n")
    }
}
