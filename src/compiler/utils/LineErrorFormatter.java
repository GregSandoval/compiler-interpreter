package compiler.utils;

import compiler.lexer.token.Token;

public class LineErrorFormatter {
  public static String renderLineNumber(Token token, String inputName) {
    return "\tat " + inputName + "(" + inputName + ":" + token.getLineNumber() + ")";
  }

  public static String renderLineNumber(TextCursor cursor, String inputName) {
    return "\tat " + inputName + "(" + inputName + ":" + cursor.getCursorLineNumber() + ")";
  }
}
