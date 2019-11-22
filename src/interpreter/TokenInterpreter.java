package interpreter;

import compiler.lexer.token.FloatToken;
import compiler.lexer.token.IntegerToken;
import compiler.lexer.token.StringToken;
import compiler.lexer.token.Token;
import compiler.parser.AbstractGrammarNode;
import compiler.parser.TokenEvaluator;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import static compiler.lexer.token.KeywordToken.PrintKeywordToken;
import static compiler.lexer.token.KeywordToken.WhileKeywordToken;
import static compiler.lexer.token.OperatorToken.*;
import static compiler.lexer.token.SymbolToken.*;

public class TokenInterpreter implements TokenEvaluator {
  @Override
  public Void visit(WhileKeywordToken token) throws Exception {
    final var paren = (LeftParen) token.children.get(0);
    final var body = (LeftBrace) token.children.get(1);

    while ((Integer) paren.accept(this).get(0) != 0) {
      body.accept(this);
      Thread.sleep(500);
    }

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
    ((LeftParen) token.children.get(0))
      .accept(this)
      .forEach(System.out::println);
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
  public Number visit(Asterisk token) throws Exception {
    return numberBiFunction(
      token.children.get(0),
      token.children.get(1),
      (left, right) -> left * right,
      (left, right) -> left * right,
      (left, right) -> null
    );
  }

  @Override
  public Number visit(Minus token) throws Exception {
    return numberBiFunction(
      token.children.get(0),
      token.children.get(1),
      (left, right) -> left - right,
      (left, right) -> left - right,
      (left, right) -> null
    );
  }

  @Override
  public Number visit(Plus token) throws Exception {
    return numberBiFunction(
      token.children.get(0),
      token.children.get(1),
      Float::sum,
      Integer::sum,
      (left, right) -> null
    );
  }

  @Override
  public Number visit(BitShiftLeft token) throws Exception {
    return numberBiFunction(
      token.children.get(0),
      token.children.get(1),
      (left, right) -> left,
      (left, right) -> left << right,
      (left, right) -> null
    );
  }

  @Override
  public Number visit(BitShiftRight token) throws Exception {
    return numberBiFunction(
      token.children.get(0),
      token.children.get(1),
      (left, right) -> left,
      (left, right) -> left >> right,
      (left, right) -> null
    );
  }

  @Override
  public Number visit(Caret token) throws Exception {
    return numberBiFunction(
      token.children.get(0),
      token.children.get(1),
      Math::pow,
      Math::pow,
      (left, right) -> null
    );
  }

  @Override
  public Number visit(ForwardSlash token) throws Exception {
    return numberBiFunction(
      token.children.get(0),
      token.children.get(1),
      (left, right) -> left / right,
      (left, right) -> left / right,
      (left, right) -> null
    );
  }

  @Override
  public Number visit(Ampersand token) throws Exception {
    throw new Exception("Symbol table isn't implemented");
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
    var right = evaluate(Object.class, rightToken.children.get(1));

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

    if (left instanceof List) {
      left = ((List) left).get(0);
    }

    if (right instanceof List) {
      right = ((List) right).get(0);
    }

    if (left instanceof String && right instanceof String) {
      return onString.apply((String) left, ((String) right));
    }

    final var exception = onError.apply(left, right);

    if (exception != null) {
      throw exception;
    }

    throw new Exception(
      "Type " + left.getClass().getSimpleName() +
        " and " + right.getClass().getSimpleName() +
        " are not comparable"
    );
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

    if (left instanceof List) {
      left = ((List) left).get(0);
    }

    if (right instanceof List) {
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

    throw new Exception(
      "Type " + left.getClass().getSimpleName() +
        " and " + right.getClass().getSimpleName() +
        " are not comparable"
    );
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
