package compiler.lexer.token;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static compiler.lexer.token.KeywordToken.*;

public class KeywordTokenRecognizer {
  private static final Map<String, Supplier<KeywordToken>> keywords = new HashMap<>();

  static {
    keywords.put("prog", ProgramKeywordToken::new);
    keywords.put("main", MainKeywordToken::new);
    keywords.put("fcn", FunctionKeywordToken::new);
    keywords.put("class", ClassKeywordToken::new);
    keywords.put("float", FloatKeywordToken::new);
    keywords.put("int", IntegerKeywordToken::new);
    keywords.put("string", StringKeywordToken::new);
    keywords.put("if", IfKeywordToken::new);
    keywords.put("elseif", ElseIfKeywordToken::new);
    keywords.put("else", ElseKeywordToken::new);
    keywords.put("while", WhileKeywordToken::new);
    keywords.put("input", InputKeywordToken::new);
    keywords.put("print", PrintKeywordToken::new);
    keywords.put("new", NewKeywordToken::new);
    keywords.put("return", ReturnKeywordToken::new);
    keywords.put("var", VarKeywordToken::new);
  }

  private KeywordTokenRecognizer() {

  }

  public static Token get(Token identifier) {
    final var tokenValue = identifier.getValue();
    final var supplier = keywords.get(tokenValue);

    if (supplier == null) {
      return identifier;
    }

    final var keyword = supplier.get();

    keyword.setLineNumber(identifier.getLineNumber());
    keyword.setLinePosition(identifier.getLinePosition());
    return keyword;
  }
}
