package compiler.lexer;

/**
 * Represents a non final state within the DFA.
 */
public class NonFinalState extends LexicalNode {
  public static final NonFinalState END_OF_TERMINAL = new NonFinalState("END_OF_TERMINAL");
  public static final NonFinalState FATAL_ERROR = new NonFinalState("FATAL_ERROR");

  public NonFinalState(String name) {
    super(name);
  }

}
