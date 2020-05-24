package visualization.dotfile

import compiler.parser.Symbols.NonTerminal.NULL_NODE
import compiler.parser.Symbols.Terminal
import compiler.parser.Symbols.Terminal.Ignorable.EOFTerminal
import compiler.parser.Symbols.Terminal.TypedTerminal.StringTerminal
import compiler.parser.TreeNode
import java.io.IOException
import java.io.PrintWriter
import java.util.*

object TreeDotFileSerializer {
    @Throws(IOException::class)
    fun serialize(tree: TreeNode, filename: String) {
        val NullSentienal = NULL_NODE()
        NullSentienal.children.add(tree)
        tree.parent = NullSentienal
        val builder = StringBuilder()
        val visitedNodes = HashSet<String>()
        builder.append("digraph ")
                .append(filename.split("\\.".toRegex()).toTypedArray()[0])
                .append(" {\n")
        addNodes(builder, visitedNodes, NullSentienal)
        visitedNodes.clear()
        addEdges(builder, visitedNodes, NullSentienal, NullSentienal)
        builder.append("}\n")
        PrintWriter(filename).use { writer -> writer.write(builder.toString()) }
        tree.parent = null
    }

    private fun addEdges(builder: StringBuilder, visitedNodes: MutableSet<String>, nullNode: NULL_NODE, tree: TreeNode?) {
        if (tree == null || visitedNodes.contains(tree.UUID)) {
            return
        }
        if (tree.parent == null) {
            builder.append("ID")
                    .append(tree.UUID)
                    .append(" -> ID")
                    .append(nullNode.UUID)
                    .append(";\n")
        } else {
            builder.append("ID")
                    .append(tree.UUID)
                    .append(" -> ID")
                    .append(tree.parent!!.UUID)
                    .append(";\n")
        }
        visitedNodes.add(tree.UUID)
        for (child in tree.children) {
            builder.append("ID")
                    .append(tree.UUID)
                    .append(" -> ID")
                    .append(child.UUID)
                    .append(";\n")
        }
        for (child in tree.children) {
            addEdges(builder, visitedNodes, nullNode, child)
        }
    }

    private fun addNodes(builder: StringBuilder, visitedNodes: MutableSet<String>, tree: TreeNode?) {
        if (tree == null) {
            return
        }
        if (visitedNodes.contains(tree.UUID)) {
            System.err.println("Cycle in tree; Duplicated Node: " + formatWithValue(tree))
            return
        }
        if (tree.parent != null && !visitedNodes.contains(tree.parent!!.UUID)) {
            builder.append("ID")
                    .append(tree.parent!!.UUID)
                    .append(" [label=\"")
                    .append(formatWithValue(tree.parent!!))
                    .append("[Parent] (Error: Missing)")
                    .append("\"];\n")
            System.err.println("${formatWithValue(tree)} of type ${tree.javaClass.simpleName} with parent ${formatWithValue(tree.parent!!)} is not in graph")
            visitedNodes.add(tree.parent!!.UUID)
        }
        builder.append("ID")
                .append(tree.UUID)
                .append(" [label=\"")
                .append(formatWithValue(tree))
                .append("\"];\n")
        visitedNodes.add(tree.UUID)
        for (child in tree.children) {
            addNodes(builder, visitedNodes, child)
        }
    }

    fun formatWithValue(rule: TreeNode): String {
        return when (rule) {
            is EOFTerminal -> "EOF"
            is StringTerminal -> "\'\'${rule.str}\'\'"
            is Terminal -> rule.str
            else -> rule.toString()
        }
    }
}
