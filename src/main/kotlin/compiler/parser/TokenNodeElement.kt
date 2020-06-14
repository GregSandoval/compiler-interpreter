package compiler.parser

import compiler.parser.visitors.TokenVisitor

interface TokenNodeElement {
    fun <T> accept(visitor: TokenVisitor<T>): T

    fun accept(visitor: TokenEvaluator): Any
}
