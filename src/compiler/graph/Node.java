package compiler.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Represents a node within the graph.
 * Uses 'curiously recurring template pattern' to return
 * subclass instances from the base class methods.
 * <p>
 * This node holds all outgoing edges. Each edge has a corresponding
 * predicate function, which determines if an edge should be 'walked'
 *
 * @param <T> A subclass of node
 */
public abstract class Node<T extends Node<T>> {
  private final Supplier<Optional<T>> onError;
  private final List<Function<Character, T>> transitions = new ArrayList<>();
  private final String name;

  protected Node(String name, Function<T, Optional<T>> onError) {
    this.name = name;
    this.onError = () -> onError.apply(me());
  }

  public EdgeBuilder ON(Predicate<Character> predicate) {
    return new EdgeBuilder(predicate);
  }

  public T ON(Character character) {
    return this.transitions
      .stream()
      .map(transitionFunc -> transitionFunc.apply(character))
      .filter(Objects::nonNull)
      .findFirst()
      .or(onError)
      .orElseThrow(() -> new NoEdgeFound(this, character));
  }

  @Override
  public String toString() {
    return name;
  }

  protected abstract T me();

  public class EdgeBuilder {
    private Predicate<Character> predicates;

    private EdgeBuilder(Predicate<Character> seedPredicate) {
      this.predicates = seedPredicate;
    }

    public EdgeBuilder AND(Predicate<Character> predicate) {
      this.predicates = this.predicates.and(predicate);
      return this;
    }

    public EdgeBuilder OR(Predicate<Character> predicate) {
      this.predicates = this.predicates.or(predicate);
      return this;
    }

    public void GOTO(T end) {
      transitions.add(character -> predicates.test(character) ? end : null);
    }
  }
}

