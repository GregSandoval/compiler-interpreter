package compiler.lexer;

import compiler.parser.UserException;
import compiler.utils.TextCursor;

public class UnknownTokenException extends UserException {

  public UnknownTokenException(String unknownToken, TextCursor cursor) {
    super(formatError(unknownToken, cursor), cursor);
  }

  private static String formatError(String unknownToken, TextCursor cursor) {
    final var line = cursor.getCursorLineNumber();
    final var pos = cursor.getCursorLinePosition() - unknownToken.length();
    return
      "\n" +
        "Could not lex input: " + "Error occurred on line " + line + ", position " + pos + "; Unexpected symbol:" +
        "\n" +
        cursor.getCurrentLineOfText() +
        "\n" +
        " ".repeat(Math.max(0, pos)) + "^";
  }
}
