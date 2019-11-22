package interpreter;

import compiler.lexer.token.Token;
import compiler.parser.AbstractGrammarNode;

public class Interpreter {

  public static void execute(AbstractGrammarNode tree) throws Exception {
    if (!(tree instanceof Token)) {
      throw new Exception("Grammar node in AST?");
    }

    final SymbolTable symtab = new SymbolTable();
    SymbolTableVisitor.build((Token) tree, symtab);
    ((Token) tree).accept(new TokenInterpreter(symtab));
    System.out.println("Symbol table: \n" + symtab.toString());
  }

}
