package compiler.parser;

import compiler.EntryPoint;
import compiler.lexer.token.Token;
import compiler.utils.TextCursor;

public class UserException extends RuntimeException {
  public UserException(String message, Token token) {
    super(message + "\n" + renderLineNumber(token, EntryPoint.inputName));
  }

  public UserException(String message, TextCursor token) {
    super(message + "\n" + renderLineNumber(token, EntryPoint.inputName));
  }

  private static String renderLineNumber(Token token, String inputName) {
    return "\tat " + inputName + "(" + inputName + ":" + token.getLineNumber() + ")";
  }

  private static String renderLineNumber(TextCursor cursor, String inputName) {
    return "\tat " + inputName + "(" + inputName + ":" + cursor.getCursorLineNumber() + ")";
  }
}
