package compiler.parser;


public interface TokenNodeElement {
  void accept(TokenVisitor visitor);

  Object accept(TokenEvaluator visitor) throws Exception;
}
