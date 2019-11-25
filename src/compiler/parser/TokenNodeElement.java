package compiler.parser;


import compiler.parser.visitors.TokenVisitor;

public interface TokenNodeElement {
  <T> T accept(TokenVisitor<T> visitor);

  Object accept(TokenEvaluator visitor) throws Exception;
}
