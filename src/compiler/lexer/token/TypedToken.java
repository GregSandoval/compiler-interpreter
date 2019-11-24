package compiler.lexer.token;

/**
 * Represents a token whose value is some generic type that
 * is known at compile time. Tokens that have an associated
 * value (that is not string) should extend this class.
 *
 * @param <Value>
 */
public abstract class TypedToken<Value> extends Token {
  public final Value value;

  protected TypedToken(String str, int tokenID) {
    super(str, tokenID);
    this.value = parse(str);
  }

  protected abstract Value parse(String str);
}
