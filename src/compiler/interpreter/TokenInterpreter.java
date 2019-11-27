package compiler.interpreter;

import compiler.lexer.token.*;
import compiler.parser.AbstractGrammarNode;
import compiler.parser.TokenEvaluator;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import static compiler.interpreter.SymbolTableVisitor.undefined;
import static compiler.lexer.token.KeywordToken.*;
import static compiler.lexer.token.OperatorToken.*;
import static compiler.lexer.token.SymbolToken.LeftBrace;
import static compiler.lexer.token.SymbolToken.LeftParen;

public class TokenInterpreter implements TokenEvaluator {
  private final Scanner scanner = new Scanner(System.in);
  private final SymbolTable symtab;

  public TokenInterpreter(SymbolTable symtab) {
    this.symtab = symtab;
  }

  @Override
  public String visit(InputKeywordToken token) throws Exception {
    return scanner.nextLine();
  }

  @Override
  public Void visit(WhileKeywordToken token) throws Exception {
    final var paren = (LeftParen) token.children.get(0);
    final var body = (LeftBrace) token.children.get(1);

    while ((Boolean) paren.accept(this).get(0)) {
      body.accept(this);
    }

    return null;
  }

  @Override
  public Void visit(IfKeywordToken token) throws Exception {
    final var paren = (LeftParen) token.children.get(0);
    final var body = (LeftBrace) token.children.get(1);

    if ((Boolean) paren.accept(this).get(0)) {
      body.accept(this);
      return null;
    }

    if (token.children.size() < 3) {
      return null;
    }

    ((Token) token.children.get(2)).accept(this);
    return null;
  }

  @Override
  public Void visit(ElseIfKeywordToken token) throws Exception {
    final var paren = (LeftParen) token.children.get(0);
    final var body = (LeftBrace) token.children.get(1);

    if ((Boolean) paren.accept(this).get(0)) {
      body.accept(this);
      return null;
    }

    if (token.children.size() < 3) {
      return null;
    }

    ((Token) token.children.get(2)).accept(this);
    return null;
  }

  @Override
  public Void visit(ElseKeywordToken token) throws Exception {
    final var body = (LeftBrace) token.children.get(0);
    body.accept(this);
    return null;
  }


  @Override
  public Object visit(IdentifierToken token) throws Exception {
    if (token.parent instanceof TypeToken) {
      return null;
    }

    final var value = symtab.getValue(token);

    if (value == undefined) {
      throw new IdentifierUsedBeforeInitializationException(token);
    }

    return value;
  }

  @Override
  public Void visit(Equal token) throws Exception {
    Token identifier;
    final var identifierOrType = (Token) token.children.get(0);

    if (identifierOrType instanceof KeywordToken) {
      identifier = (Token) token.children.get(0).children.get(0);
    } else {
      identifier = (Token) token.children.get(0);
    }

    final var value = ((Token) token.children.get(1)).accept(this);
    if (!symtab.hasSymbol(identifier)) {
      throw new Exception(identifier.getValue() + " is not defined");
    }
    symtab.setSymbolValue(identifier, value);
    return null;
  }

  @Override
  public List<Object> visit(LeftParen token) throws Exception {
    List<Object> result = new ArrayList<>();
    for (final var child : token.children) {
      result.add(((Token) child).accept(this));
    }
    return result;
  }

  @Override
  public Void visit(PrintKeywordToken token) throws Exception {
    System.out.print(
      ((LeftParen) token.children.get(0))
        .accept(this)
        .stream()
        .map(Object::toString)
        .collect(Collectors.joining(""))
      .replaceAll("\\\\n", System.lineSeparator())
    );
    return null;
  }

  // Primitives
  @Override
  public Float visit(FloatToken token) throws Exception {
    return token.value;
  }

  @Override
  public Integer visit(IntegerToken token) throws Exception {
    return token.value;
  }

  @Override
  public String visit(StringToken token) throws Exception {
    return token.getValue();
  }

  // Relations
  @Override
  public Boolean visit(LessThan token) throws Exception {
    return comparePrimitiveTokens(
      token.children.get(0),
      token.children.get(1),
      order -> order < 0
    );
  }

  @Override
  public Boolean visit(GreaterThan token) throws Exception {
    return comparePrimitiveTokens(
      token.children.get(0),
      token.children.get(1),
      order -> order > 0
    );
  }

  @Override
  public Boolean visit(EqualEqual token) throws Exception {
    return comparePrimitiveTokens(
      token.children.get(0),
      token.children.get(1),
      order -> order == 0
    );
  }

  @Override
  public Boolean visit(NotEqual token) throws Exception {
    return comparePrimitiveTokens(
      token.children.get(0),
      token.children.get(1),
      order -> order != 0
    );
  }

  @Override
  public Boolean visit(LessThanOrEqual token) throws Exception {
    return comparePrimitiveTokens(
      token.children.get(0),
      token.children.get(1),
      order -> order <= 0
    );
  }

  @Override
  public Boolean visit(GreaterThanOrEqual token) throws Exception {
    return comparePrimitiveTokens(
      token.children.get(0),
      token.children.get(1),
      order -> order >= 0
    );
  }

  // Operators
  @Override
  public Object visit(Asterisk token) throws Exception {
    if (token.children.size() == 1) {
      final var pointer = (Token) token.children.get(0);
      final var address = (String) this.symtab.getValue(pointer);
      return symtab.getValueAtAddress(address);
    }

    final var leftToken = (Token) token.children.get(0);
    final var rightToken = (Token) token.children.get(1);
    return numberBiFunction(
      leftToken,
      rightToken,
      (left, right) -> left * right,
      (left, right) -> left * right,
      (left, right) -> new OperatorUsageUndefined(token, left, right)
    );
  }

  @Override
  public Number visit(Minus token) throws Exception {
    final var leftToken = (Token) token.children.get(0);
    final var rightToken = (Token) token.children.get(1);
    return numberBiFunction(
      leftToken,
      rightToken,
      (left, right) -> left - right,
      (left, right) -> left - right,
      (left, right) -> new OperatorUsageUndefined(token, left, right)
    );
  }

  @Override
  public Number visit(Plus token) throws Exception {
    final var leftToken = (Token) token.children.get(0);
    final var rightToken = (Token) token.children.get(1);
    return numberBiFunction(
      leftToken,
      rightToken,
      Float::sum,
      Integer::sum,
      (left, right) -> new OperatorUsageUndefined(token, left, right)
    );
  }

  @Override
  public Number visit(BitShiftLeft token) throws Exception {
    final var leftToken = (Token) token.children.get(0);
    final var rightToken = (Token) token.children.get(1);
    return numberBiFunction(
      leftToken,
      rightToken,
      (left, right) -> left,
      (left, right) -> left << right,
      (left, right) -> new OperatorUsageUndefined(token, left, right)
    );
  }

  @Override
  public Number visit(BitShiftRight token) throws Exception {
    final var leftToken = (Token) token.children.get(0);
    final var rightToken = (Token) token.children.get(1);
    return numberBiFunction(
      leftToken,
      rightToken,
      (left, right) -> left,
      (left, right) -> left >> right,
      (left, right) -> new OperatorUsageUndefined(token, left, right)
    );
  }

  @Override
  public Number visit(Caret token) throws Exception {
    final var leftToken = (Token) token.children.get(0);
    final var rightToken = (Token) token.children.get(1);
    return numberBiFunction(
      leftToken,
      rightToken,
      Math::pow,
      Math::pow,
      (left, right) -> new OperatorUsageUndefined(token, left, right)
    );
  }

  @Override
  public Number visit(ForwardSlash token) throws Exception {
    final var leftToken = (Token) token.children.get(0);
    final var rightToken = (Token) token.children.get(1);
    return numberBiFunction(
      leftToken,
      rightToken,
      (left, right) -> left / right,
      (left, right) -> left / right,
      (left, right) -> new OperatorUsageUndefined(token, left, right)
    );
  }

  @Override
  public Object visit(Ampersand token) throws Exception {
    return symtab.getAddress((Token) token.children.get(0));
  }


  public boolean comparePrimitiveTokens(AbstractGrammarNode leftToken, AbstractGrammarNode rightToken, Function<Integer, Boolean> comparator) throws Exception {
    return comparator.apply(comparePrimitiveTokens(leftToken, rightToken));
  }

  public int comparePrimitiveTokens(
    AbstractGrammarNode leftToken,
    AbstractGrammarNode rightToken
  ) throws Exception {
    return primitiveBiFunction(
      leftToken,
      rightToken,
      Float::compare,
      Integer::compare,
      String::compareTo,
      TokenInterpreter::classNotCompatibleException
    );
  }

  public <T> T primitiveBiFunction(
    AbstractGrammarNode leftToken,
    AbstractGrammarNode rightToken,
    BiFunction<Float, Float, T> onFloat,
    BiFunction<Integer, Integer, T> onInt,
    BiFunction<String, String, T> onString,
    BiFunction<Object, Object, Exception> onError
  ) throws Exception {
    var left = evaluate(Object.class, leftToken);
    var right = evaluate(Object.class, rightToken);

    final var result = this.numberBiFunction(
      leftToken,
      rightToken,
      onFloat,
      onInt,
      (l, r) -> null
    );

    if (result != null) {
      return result;
    }

    while (left instanceof List) {
      left = ((List) left).get(0);
    }

    while (right instanceof List) {
      right = ((List) right).get(0);
    }

    if (left instanceof String && right instanceof String) {
      return onString.apply((String) left, ((String) right));
    }

    final var exception = onError.apply(left, right);

    if (exception != null) {
      throw exception;
    }

    return null;
  }

  public <T> T numberBiFunction(
    AbstractGrammarNode leftToken,
    AbstractGrammarNode rightToken,
    BiFunction<Float, Float, T> onFloat,
    BiFunction<Integer, Integer, T> onInt,
    BiFunction<Object, Object, Exception> onError
  ) throws Exception {
    var left = evaluate(Object.class, leftToken);
    var right = evaluate(Object.class, rightToken);

    while (left instanceof List) {
      left = ((List) left).get(0);
    }

    while (right instanceof List) {
      right = ((List) right).get(0);
    }

    if (left instanceof Number && right instanceof Number) {
      if (left instanceof Float || right instanceof Float) {
        return onFloat.apply(((Number) left).floatValue(), ((Number) right).floatValue());
      }

      if (left instanceof Integer || right instanceof Integer) {
        return onInt.apply(((Number) left).intValue(), ((Number) right).intValue());
      }
    }

    final var exception = onError.apply(left, right);

    if (exception != null) {
      throw exception;
    }

    return null;
  }

  public static Exception classNotCompatibleException(Object left, Object right) {
    return new Exception(
      "Type " + left.getClass().getSimpleName() +
        " and " + right.getClass().getSimpleName() +
        " are cannot be used with operator"
    );
  }

  public <T> T evaluate(Class<T> valueType, AbstractGrammarNode node) throws Exception {
    final var token = assertClass(Token.class, node);
    return assertClass(valueType, token.accept(this));
  }

  public <T> T assertClass(Class<T> clazz, Object object) throws Exception {
    if (clazz.isInstance(object)) {
      return clazz.cast(object);
    }

    throw new Exception(clazz.getSimpleName() + " expected but found" + object.getClass().getSimpleName());
  }
}
