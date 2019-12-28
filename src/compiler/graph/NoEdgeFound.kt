package compiler.graph

/**
 * Default behavior for when trying to 'walk' from one
 * edge to another, and no edge was found within the graph.
 */
class NoEdgeFound(node: Node<*>, character: Char) : RuntimeException("No transition found for $node on character: $character")
