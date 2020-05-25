package visualization

import compiler.parser.TreeNode
import visualization.dotfile.TreeDotFileSerializer

object TreeVisualizer {
    fun toImage(tree: TreeNode, name: String) {
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
