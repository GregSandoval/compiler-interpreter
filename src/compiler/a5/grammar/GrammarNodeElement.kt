package compiler.a5.grammar

interface GrammarNodeElement {
    fun accept(visitor: GrammarNodeVisitor)
}
