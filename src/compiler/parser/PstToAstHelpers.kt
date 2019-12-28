package compiler.parser

import compiler.lexer.token.Token
import java.util.function.Consumer

object PstToAstHelpers {
    fun hoist(tree: AbstractGrammarNode) {
        for (token in tree.children) {
            if (token !is Token) {
                continue
            }

            val parent = tree.parent
            var tokenIndex = parent!!.children.indexOf(tree)

            // Replace token's parent
            token.parent = parent

            // Replace rule with token
            parent.children[tokenIndex] = token

            // Remove token from rule's children
            tree.children.remove(token)

            // Set tree's children to point to token as new parent
            tree.children.forEach(Consumer { child: AbstractGrammarNode -> child.parent = token })

            // Add rule's children to the token;
            while (--tokenIndex != 0 && !tree.children.isEmpty())
                token.children.addLast(tree.children.pop())

            while (!tree.children.isEmpty())
                token.children.addLast(tree.children.pop())
        }
    }

    fun reverseHoist(tree: AbstractGrammarNode) {
        val children = tree.children
        for (i in children.indices.reversed()) {
            if (children.isEmpty()) {
                break
            }
            val token = children[i]

            if (token !is Token) {
                continue
            }

            val parent = tree.parent
            val tokenIndex = parent!!.children.indexOf(tree)

            // Replace token's parent
            token.parent = parent

            // Replace rule with token
            parent.children[tokenIndex] = token

            // Remove token from rule's children
            tree.children.remove(token)

            // Set tree's children to point to token as new parent
            tree.children.forEach { it.parent = token }

            // Add rule's children to the token;
            while (!tree.children.isEmpty())
                token.children.addFirst(tree.children.pop())
        }
    }

    fun rightContraction(tree: AbstractGrammarNode) {
        val rightMostNode = tree.children.last
        if (rightMostNode is GrammarNode) {
            tree.children.remove(rightMostNode)
            rightMostNode.parent = null
            rightMostNode.children.forEach { tree.children.addLast(it) }
            rightMostNode.children.forEach { it.parent = tree }
        }
    }
}
