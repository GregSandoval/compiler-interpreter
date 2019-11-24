package compiler.utils;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A java compliant iterator over the given string.
 * This iterator also knows which line/position number
 * it's on. Can also reverse.
 */
public class TextCursor implements Iterator<Character>, Iterable<Character> {
  private final char[] text;
  private final int[] lineNumbers;
  private final int[] linePositions;

  private int cursor = -1;

  public TextCursor(String text) {
    this.text = (text + "\n").toCharArray();
    this.lineNumbers = new int[this.text.length];
    this.linePositions = new int[this.text.length];
    int line = 1;
    int pos = 1;
    for (var i = 0; i < this.text.length; i++) {
      lineNumbers[i] = line;
      linePositions[i] = pos;
      if (isNewLine(this.text[i])) {
        line++;
        pos = 0;
      }
      pos++;
    }

  }

  private static boolean isNewLine(char letter) {
    return letter == '\n' || letter == '\r' || letter == '\f';
  }

  @Override
  public boolean hasNext() {
    return this.cursor + 1 < this.text.length;
  }

  @Override
  public Character next() {
    if (cursor + 1 >= this.text.length) {
      throw new NoSuchElementException();
    }

    return text[++cursor];
  }

  public void rewind() {
    if (cursor - 1 < 0) {
      throw new NoSuchElementException();
    }

    cursor--;
  }

  public int getCursorLineNumber() {
    return this.lineNumbers[cursor];
  }

  public int getCursorLinePosition() {
    return this.linePositions[cursor];
  }

  public String getCurrentLineOfText() {
    final var savedCursor = cursor;
    final var lineBuilder = new StringBuilder();
    while (cursor != 0 && !isNewLine(this.text[cursor - 1])) {
      --cursor;
    }

    while (hasNext() && !isNewLine(this.text[cursor])) {
      lineBuilder.append(this.text[cursor++]);
    }

    cursor = savedCursor;
    return lineBuilder.toString();
  }

  @Override
  public Iterator<Character> iterator() {
    return this;
  }
}
