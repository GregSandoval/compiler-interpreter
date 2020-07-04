package visualization

import compiler.parser.TreeNode
import visualization.dotfile.TreeDotFileSerializer

object TreeVisualizer {
    fun toImage(tree: TreeNode, fileName: String) {
        TreeDotFileSerializer.serialize(tree, "$fileName.dot")
        var p = ProcessBuilder("dot", "-Tpng", "$fileName.dot", "-o", "$fileName.png")
                .start()
        p.waitFor()
        p = ProcessBuilder("rm", "$fileName.dot")
                .start()
        p.waitFor()
        println("Generated tree image, file name: $fileName.png")
    }
}
