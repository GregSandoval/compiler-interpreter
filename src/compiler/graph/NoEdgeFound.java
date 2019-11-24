package compiler.graph;

/**
 * Default behavior for when trying to 'walk' from one
 * edge to another, and no edge was found within the graph.
 */
public class NoEdgeFound extends RuntimeException {
  public NoEdgeFound(Node node, Character character) {
    super("No transition found for " + node + " on character: " + character);
  }
}
