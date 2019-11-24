package compiler.lexer.token;

import compiler.parser.AbstractGrammarNode;
import compiler.parser.TokenNodeElement;

/**
 * The base class for all token classes.
 * All tokens should extend this class.
 */
public abstract class Token extends AbstractGrammarNode implements TokenNodeElement {
  private final String value;
  private final int tokenID;
  private int lineNumber;
  private int linePosition;

  protected Token(String value, int tokenID) {
    this.value = value;
    this.tokenID = tokenID;
  }

  public int getLineNumber() {
    return this.lineNumber;
  }

  public void setLineNumber(int lineNumber) {
    this.lineNumber = lineNumber;
  }

  public int getLinePosition() {
    return this.linePosition;
  }

  public void setLinePosition(int linePosition) {
    this.linePosition = linePosition;
  }

  public final String toString() {
    final var format = "(Tok: %s lin= %s,%s str = \"%s\"%s)";
    return String.format(format, tokenID, lineNumber, linePosition, value, toStringExtra());
  }

  public String getValue() {
    return this.value;
  }

  protected String toStringExtra() {
    return "";
  }

}


