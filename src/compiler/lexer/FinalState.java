package compiler.lexer;

import compiler.lexer.token.Token;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Represents a final state within the DFA.
 * This class takes constructor for a Token,
 * creating it when getToken is called
 */
public class FinalState extends LexicalNode {
  private Function<String, Token> constructorWithString;
  private Supplier<Token> constructorNoArgs;

  public FinalState(String name, Function<String, Token> constructor) {
    super(name);
    this.constructorWithString = constructor;
  }

  public FinalState(String name, Supplier<Token> constructor) {
    super(name);
    this.constructorNoArgs = constructor;
  }

  public Token getToken(String str) {
    if (constructorNoArgs != null) {
      return constructorNoArgs.get();
    }
    return this.constructorWithString.apply(str);
  }
}
