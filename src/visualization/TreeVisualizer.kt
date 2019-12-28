package visualization

import compiler.parser.AbstractGrammarNode
import visualization.dotfile.TreeDotFileSerializer
import java.io.IOException

object TreeVisualizer {
    @JvmStatic
    @Throws(IOException::class, InterruptedException::class)
    fun toImage(tree: AbstractGrammarNode, name: String) {
        TreeDotFileSerializer.serialize(tree, "$name.dot")
        var p = ProcessBuilder("dot", "-Tpng", "$name.dot", "-o", "$name.png")
                .start()
        p.waitFor()
        p = ProcessBuilder("rm", "$name.dot")
                .start()
        p.waitFor()
        println("Generated parse tree image, file name: $name.png")
    }
}
