package compiler.parser

typealias SymbolVisitor<T> = (TreeNode) -> T

object ParseTreeVisitor {

    fun <T> postordervisit(tree: TreeNode?, visitor: SymbolVisitor<T>) {
        if (tree == null) {
            return
        }

        for (child in tree.children) {
            postordervisit(child, visitor)
        }

        visitor(tree)
    }

}
