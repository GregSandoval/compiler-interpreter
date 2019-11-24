package compiler.parser;

import java.util.LinkedList;

public abstract class AbstractGrammarNode {
  private static int UUID_COUNTER = 0;
  public AbstractGrammarNode parent;
  public AbstractGrammarNode left;
  public AbstractGrammarNode right;
  public String UUID;
  public LinkedList<AbstractGrammarNode> children = new LinkedList<>();

  public AbstractGrammarNode() {
    this.UUID = "" + UUID_COUNTER++;
  }
}

