package compiler.lexer;

import compiler.lexer.token.Token;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public final class AlexHydrator {
  private final String pattern =
    "\\(Tok: (\\d+) lin= (\\d+),(\\d+) str = \"([^\"]*)\"";
  private final Pattern tokenPattern = Pattern.compile(pattern);
  private final int STRING_TOKEN = 5;
  private final Lexer lexer;

  public AlexHydrator(Lexer lexer) {
    this.lexer = lexer;
  }

  public List<Token> hydrate(String serializedTokenStream) {
    final var matcher = this.tokenPattern.matcher(serializedTokenStream);
    final var program = new ArrayList<String>();
    final var lineNumbers = new ArrayList<Integer>();
    final var linePositions = new ArrayList<Integer>();

    while (matcher.find()) {
      final var id = Integer.parseInt(matcher.group(1));
      final var lineNumber = Integer.parseInt(matcher.group(2));
      final var linePosition = Integer.parseInt(matcher.group(3));

      var value = matcher.group(4);

      if (id == STRING_TOKEN)
        value = "\"" + value + "\"";

      program.add(value);
      lineNumbers.add(lineNumber);
      linePositions.add(linePosition);
    }

    final var tokens = this.lexer.analyze(String.join(" ", program));

    for (int i = 0; i < tokens.size(); i++) {
      Token token = tokens.get(i);
      token.setLineNumber(lineNumbers.get(i));
      token.setLinePosition(linePositions.get(i));
    }

    return tokens;
  }

}
