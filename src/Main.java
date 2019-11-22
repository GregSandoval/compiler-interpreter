import compiler.a5.grammar.A5GrammarNonTerminals;
import compiler.a5.grammar.A5GrammarRules;
import compiler.a5.lexicon.A5LexiconDFA;
import compiler.lexer.LexerBuilder;
import compiler.lexer.token.Token;
import compiler.parser.AbstractSyntaxTreeBuilder;
import compiler.parser.ParseTreeBuilder;
import compiler.utils.TextCursor;
import interpreter.Interpreter;
import visualization.TreeVisualizer;

import java.util.function.BiConsumer;

public class Main {
  private static String program = """
  prog

  main {
      var(
        int count = 0;
      )

      while(count < 10){
        print(count);
        count = count + 1;
      };

      print("count should be 10","");
      print("");
  }

""";
  private static String exception = null;

  public static void main(String[] args) throws Exception {
    final var tokens = new LexerBuilder()
        .setStartState(A5LexiconDFA.START)
        .onUnknownTokenFound(Main.logUnknownToken())
        .createLexer()
        .analyze(program);

    if (exception != null) {
      throw new Exception(exception);
    }

    // Prepare grammar rules
    A5GrammarRules.build();

    // Parse token stream
    var tree = new ParseTreeBuilder()
      .setStartSymbol(new A5GrammarNonTerminals.Pgm())
      .setInputSourceName("Main.java")
      .build(tokens);

    // Transform PST to AST (in-place)
    AbstractSyntaxTreeBuilder.fromParseTree(tree);

    // Serialize AST
    TreeVisualizer.toImage(tree, "ast");
    tree = tree.children.get(0);

    if(!(tree instanceof Token)){
      throw new Exception("Tree has reference to grammar node");
    }

    Interpreter.execute(tree);
  }

  private static BiConsumer<String, TextCursor> logUnknownToken() {
    return (unknownToken, cursor) -> {
      final var line = cursor.getCursorLineNumber();
      final var pos = cursor.getCursorLinePosition() - unknownToken.length();
      Main.exception = "\nCould not lex input: " + "Error occurred on line " +
        line +
        ", position " +
        pos +
        "; Unexpected symbol\n" +
        cursor.getCurrentLineOfText() +
        "\n" +
        " ".repeat(Math.max(0, pos)) +
        "^\n" +
        "\tat " + "Main.java" + "(" + "Main.java" + ":" + line + ")";
    };
  }
}
