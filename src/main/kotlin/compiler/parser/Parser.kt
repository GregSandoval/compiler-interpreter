package compiler.parser

import compiler.parser.Symbol.NonTerminal
import compiler.parser.Symbol.Terminal
import compiler.parser.lltable.LLTable
import java.util.*
import java.util.stream.Collectors

class Parser(
        private val startSymbol: NonTerminal,
        private val listener: ParserListener,
        private val llTable: LLTable
) {

    fun parse(tokensIn: List<Terminal>): ParseTree {
        val stack = LinkedList<Symbol>()
        val tokens = LinkedList(tokensIn)
        val tree = ParseTree(this.startSymbol)
        stack.push(startSymbol)

        while (!tokens.isEmpty() && !stack.isEmpty()) {
            this.listener.onBeforeRule(stack, tokens.peek())

            val rhs = when (val top = stack.pop()) {
                is Terminal -> matchTerminal(top, tokens, tree)
                is NonTerminal -> applyProduction(top, tokens, tree)
            }

            rhs.reversed().forEach(stack::push)
        }

        if (tokens.isEmpty() && stack.isEmpty()) {
            println("Parsing successful")
            return tree
        }

        if (!stack.isEmpty()) {
            throw Exception("Failed to parse input; Expected tokens but received EOF;")
        }

        if (!tokens.isEmpty()) {
            throw Exception(
                    "Failed to parse input; Expected grammar rule but found none.\n" +
                            "Remaining tokens: " + tokens.stream()
                            .map { it.javaClass.simpleName }
                            .collect(Collectors.toList())
            )
        }

        throw Exception("Unexpected parser error")
    }

    fun applyProduction(top: NonTerminal, tokens: LinkedList<Terminal>, tree: ParseTree): List<Symbol> {
        val token = tokens.peek()
        val rhs = this.llTable.getRHS(top, token::class)

        if (rhs === null) {
            this.listener.onPredictionNotFound(top, token)
            throw PredictiveParserException(top, token, this.llTable)
        }

        tree.link(top, token, rhs)
        this.listener.onNonTerminalReplaced(top, token, rhs)

        return rhs
    }

    fun matchTerminal(top: Terminal, tokens: LinkedList<Terminal>, tree: ParseTree): List<Symbol> {
        val token = tokens.pop()

        if (isEqual(token, top)) {
            tree.link(top, token, emptyList())
            this.listener.onNonTerminalReplaced(top, token, emptyList())
            return emptyList()
        }

        this.listener.onUnexpectedRule(top, token)
        throw UnexpectedToken(top, token)
    }

    fun isEqual(terminal: Terminal, top: TreeNode): Boolean {
        return terminal.javaClass == top.javaClass
    }
}
