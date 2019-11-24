package visualization;

import compiler.parser.AbstractGrammarNode;
import visualization.dotfile.TreeDotFileSerializer;

import java.io.IOException;

public class TreeVisualizer {
  public static void toImage(AbstractGrammarNode tree, String name) throws IOException, InterruptedException {
    TreeDotFileSerializer.serialize(tree, name + ".dot");

    var p = new ProcessBuilder("dot", "-Tpng", name + ".dot", "-o", name + ".png")
      .start();

    p.waitFor();

    p = new ProcessBuilder("rm", name + ".dot")
      .start();
    p.waitFor();

    System.out.println("Generated parse tree image, file name: " + name + ".png");
  }

}
