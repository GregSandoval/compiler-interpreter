import compiler.a5.grammar.A5GrammarNonTerminals;
import compiler.a5.grammar.A5GrammarRules;
import compiler.a5.lexicon.A5LexiconDFA;
import compiler.interpreter.Interpreter;
import compiler.lexer.AlexHydrator;
import compiler.lexer.LexerBuilder;
import compiler.lexer.UnknownTokenException;
import compiler.lexer.token.Token;
import compiler.parser.AbstractGrammarNode;
import compiler.parser.AbstractSyntaxTreeBuilder;
import compiler.parser.GrammarNode;
import compiler.parser.ParseTreeBuilder;
import compiler.utils.TextCursor;
import visualization.TreeVisualizer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.BiConsumer;
import java.util.regex.Pattern;

public class Main {
  private static String exception = null;

  public static void main(String[] args) throws Exception {
    final var settings = processArgs(args);
    final List<Token> tokens;

    if (settings.tokens != null) {
      // Passed in token stream
      tokens = settings.tokens;
    } else {
      // Tokenize file
      tokens = new LexerBuilder()
        .setStartState(A5LexiconDFA.START)
        .onUnknownTokenFound(Main.logUnknownToken(settings.inputName))
        .createLexer()
        .analyze(settings.inputText);
    }

    if (exception != null) {
      throw new Exception(exception);
    }

    // Prepare grammar rules
    A5GrammarRules.build();

    // Parse token stream
    var tree = new ParseTreeBuilder()
      .setStartSymbol(new A5GrammarNonTerminals.Pgm())
      .setInputSourceName(settings.inputName)
      .build(tokens);

    // Serialize current PST
    TreeVisualizer.toImage(tree, settings.pstOut);

    // Transform PST to AST (in-place)
    AbstractSyntaxTreeBuilder.fromParseTree(tree);

    // Serialize ASt
    TreeVisualizer.toImage(tree, settings.astOut);
    tree = tree.children.get(0);

    Interpreter.execute(tree);
  }

  private static ParserSettings processArgs(String[] args) throws IOException {
    final var settings = new ParserSettings();
    for (final var arg : args) {
      if (arg.equals("--alex")) {
        final var scanner = new Scanner(System.in).useDelimiter(Pattern.compile("$"));
        final var serializedTokens = scanner.hasNext() ? scanner.next() : "";
        final var lexer = new LexerBuilder()
          .setStartState(A5LexiconDFA.START)
          .createLexer();

        settings.tokens = new AlexHydrator(lexer).hydrate(serializedTokens);
        scanner.close();
        continue;
      }
      final var split = arg.split("=");

      if (split.length != 2)
        continue;

      final var key = split[0];
      final var value = split[1];
      switch (key) {
        case "--pst-name" -> settings.pstOut = value;
        case "--ast-name" -> settings.astOut = value;
        case "--file" -> settings.inputText = Files.readString(Path.of(settings.inputName = value));
      }
    }

    if (settings.inputText == null && settings.tokens == null) {
      try (final var scanner = new Scanner(System.in).useDelimiter(Pattern.compile("$"))) {
        settings.inputText = scanner.hasNext() ? scanner.next() : "";
      }
    }
    return settings;
  }

  public static void validateAST(AbstractGrammarNode tree) {
    System.out.println("Validating AST contains only tokens...");
    final var unhandledNodes = new ArrayList<GrammarNode>();
    validateAST(tree, unhandledNodes);

    if (unhandledNodes.isEmpty()) {
      System.out.println("AST contains only tokens!");
      return;
    }

    if (unhandledNodes.size() == 1 && !(unhandledNodes.get(0) instanceof ParseTreeBuilder.ParseTreeSentinel))
      System.out.println("Uh-oh; AST contains grammar nodes! Need to add more logic to these nodes:" + unhandledNodes);
  }

  public static void validateAST(AbstractGrammarNode tree, List<GrammarNode> unhandledNodes) {
    if (tree == null) {
      return;
    }
    if (tree instanceof GrammarNode) {
      unhandledNodes.add((GrammarNode) tree);
    }
    for (final var child : tree.children) {
      validateAST(child, unhandledNodes);
    }
  }

  private static BiConsumer<String, TextCursor> logUnknownToken(String inputName) {
    return (unknownToken, cursor) -> {
      throw new UnknownTokenException(unknownToken, cursor, inputName);
    };
  }

  private static class ParserSettings {
    private String pstOut = "pst";
    private String astOut = "ast";
    private String inputName = "std.in";
    private String inputText = null;
    private List<Token> tokens = null;
  }
}
