package interpreter;

import compiler.lexer.token.Token;
import compiler.parser.AbstractGrammarNode;

public class Interpreter {

  public static void execute(AbstractGrammarNode tree) throws Exception {
    if (!(tree instanceof Token)) {
      throw new Exception("Grammar node in AST?");
    }

    ((Token) tree).accept(new TokenInterpreter());
  }
}
