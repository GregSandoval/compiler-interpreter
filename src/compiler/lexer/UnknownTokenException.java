package compiler.lexer;

import compiler.utils.LineErrorFormatter;
import compiler.utils.TextCursor;

public class UnknownTokenException extends RuntimeException {

  public UnknownTokenException(String unknownToken, TextCursor cursor, String inputName) {
    super(formatError(unknownToken, cursor, inputName));
  }

  private static String formatError(String unknownToken, TextCursor cursor, String inputName) {
    final var line = cursor.getCursorLineNumber();
    final var pos = cursor.getCursorLinePosition() - unknownToken.length();
    return
      "\n" +
        "Could not lex input: " + "Error occurred on line " + line + ", position " + pos + "; Unexpected symbol:" +
        "\n" +
        cursor.getCurrentLineOfText() +
        "\n" +
        " ".repeat(Math.max(0, pos)) + "^" +
        "\n" + LineErrorFormatter.renderLineNumber(cursor, inputName);
  }
}
