package compiler.a5.grammar

import compiler.parser.PstToAstHelpers
import compiler.parser.Symbol.NonTerminal.*
import compiler.parser.Symbol.Terminal.Keyword.VarKeyword
import compiler.parser.Symbol.Terminal.Operator.Equal
import compiler.parser.Symbol.Terminal.Operator.Plus
import compiler.parser.Symbol.Terminal.Punctuation.*
import compiler.parser.TreeNode
import java.util.*

class PstToAstGrammarVisitor : NonTerminalVisitor {
    override fun visit(node: Expr) {
        if (node.children.size == 2) {
            val left = node.children.pop()
            val right = node.children.peek()
            PstToAstHelpers.hoist(node)
            left.parent = right
            right.children.addFirst(left)
        }
    }

    override fun visit(node: Expr_Tail) {
        PstToAstHelpers.hoist(node)
    }

    override fun visit(node: Term) {
        if (node.children.size == 2) {
            val lvalue = node.children[0]
            val operator = node.children[1]
            node.children.remove(lvalue)
            lvalue.parent = operator
            operator.children.addFirst(lvalue)
        }
        PstToAstHelpers.hoist(node)
    }

    override fun visit(node: Term_Tail) {
        if (node.children.size == 3) {
            val tail = node.children[2]
            val tailLValue = node.children[1]
            tailLValue.parent = tail
            tail.children.addFirst(tailLValue)
            node.children.remove(tailLValue)
        }
        PstToAstHelpers.hoist(node)
    }

    override fun visit(node: Rterm) {
        if (node.children.size == 2) {
            val operator = node.children[1]
            val lvalue = node.children[0]
            lvalue.parent = operator
            node.children.remove(lvalue)
            operator.children.addFirst(lvalue)
        }
        PstToAstHelpers.hoist(node)
    }

    override fun visit(node: Rterm_Tail) {
        if (node.children.size == 3) {
            val tail = node.children[2]
            val tailLValue = node.children[1]
            tailLValue.parent = tail
            tail.children.addFirst(tailLValue)
            node.children.remove(tailLValue)
        }
        PstToAstHelpers.hoist(node)
    }

    override fun visit(node: PPexpr) {
        node.children.removeIf { it is RightParen }
        PstToAstHelpers.hoist(node)
    }

    override fun visit(node: Classmom) {
        PstToAstHelpers.hoist(node)
    }

    override fun visit(node: ParseTreeSentinel) {
    }

    override fun visit(node: NULL_NODE) {
    }

    override fun visit(node: Pgm) {
        var fcndefsIndex = -1
        var fcndefs: Fcndefs? = null
        for (i in node.children.indices) {
            val child = node.children[i]
            if (child is Fcndefs) {
                fcndefsIndex = i
                fcndefs = child
                break
            }
        }
        if (fcndefs != null) {
            for (child in fcndefs.children) {
                node.children.add(fcndefsIndex++, child)
                child.parent = node
            }
        }
        if (fcndefs != null)
            node.children.remove(fcndefs)
        PstToAstHelpers.hoist(node)
    }

    override fun visit(node: Main) {
        PstToAstHelpers.hoist(node)
    }

    override fun visit(node: BBlock) {
        node.children.removeIf { it is RightBrace }
        PstToAstHelpers.rightContraction(node)
        PstToAstHelpers.hoist(node)
    }

    override fun visit(node: Vargroup) {
        PstToAstHelpers.hoist(node)
    }

    override fun visit(node: PPvarlist) {
        node.children.removeIf { it is RightParen }
        if (node.children.size == 2) {
            val varlist = node.children[1]
            node.children.remove(varlist)
            varlist.parent = null
            varlist.children.forEach { node.children.addLast(it) }
            varlist.children.forEach { it.parent = node }
        }
        PstToAstHelpers.hoist(node)
    }

    override fun visit(node: Varlist) {
        node.children.removeIf { it is SemiColon }
        if (node.children.size == 2) {
            val otherVarList = node.children[1]
            node.children.remove(otherVarList)
            otherVarList.children.forEach { node.children.addLast(it) }
            otherVarList.children.forEach { it.parent = node }
            return
        }
    }

    override fun visit(node: Varitem) {
        PstToAstHelpers.reverseHoist(node)
    }

    override fun visit(node: Varitem_Suffix) {
        PstToAstHelpers.hoist(node)
    }

    override fun visit(node: Vardecl) {
        PstToAstHelpers.hoist(node)
    }

    override fun visit(node: Simplekind) {}
    override fun visit(node: BaseKind) {}
    override fun visit(node: Varspec) {
        PstToAstHelpers.hoist(node)
    }

    override fun visit(node: Varid) {}
    override fun visit(node: Arrspec) {}
    override fun visit(node: KKint) {
        node.children.removeIf { it is RightBracket }
        PstToAstHelpers.hoist(node)
    }

    override fun visit(node: Deref_id) {
        PstToAstHelpers.hoist(node)
    }

    override fun visit(node: Deref) {}
    override fun visit(node: Varinit) {}
    override fun visit(node: BBexprs) {}
    override fun visit(node: Exprlist) {
        if (!node.children.isEmpty()) {
            PstToAstHelpers.rightContraction(node)
        }
    }

    override fun visit(node: Moreexprs) {
        node.children.removeIf { it is Comma }
        if (!node.children.isEmpty()) {
            PstToAstHelpers.rightContraction(node)
        }
    }

    override fun visit(node: Classdecl) {}
    override fun visit(node: Classdef) {
        PstToAstHelpers.hoist(node)
    }

    override fun visit(node: Classdef_Suffix) {}
    override fun visit(node: BBClassitems) {
        node.children.removeIf { it is RightBrace }
        PstToAstHelpers.rightContraction(node)
        val removables = ArrayList<TreeNode>()
        for (i in 0 until node.children.size - 1) {
            val left = node.children[i]
            val right = node.children[i + 1]
            if (left is Colon && right is VarKeyword) {
                removables.add(right)
                left.children.addLast(right)
                right.parent = left
            }
        }
        if (node.children.last is Mddecls) {
            PstToAstHelpers.rightContraction(node)
        }
        node.children.removeAll(removables)
        PstToAstHelpers.hoist(node)
    }

    override fun visit(node: Classitems) {
        if (!node.children.isEmpty() && node.children.last is Classitems)
            PstToAstHelpers.rightContraction(node)
    }

    override fun visit(node: Classgroup) {}
    override fun visit(node: Class_ctrl) {
        PstToAstHelpers.hoist(node)
    }

    override fun visit(node: Interfaces) {
        if (!node.children.isEmpty()) {
            PstToAstHelpers.rightContraction(node)
        }
        if (node.parent is Classheader) {
            PstToAstHelpers.hoist(node)
        }
        node.children.removeIf { it is Plus }
    }

    override fun visit(node: Mddecls) {
        if (!node.children.isEmpty())
            PstToAstHelpers.rightContraction(node)
    }

    override fun visit(node: Mdheader) {
        PstToAstHelpers.hoist(node)
    }

    override fun visit(node: Md_id) {
        node.children.add(1, node.children.removeFirst())
        PstToAstHelpers.hoist(node)
    }

    override fun visit(node: Fcndefs) {}
    override fun visit(node: Fcndef) {
        PstToAstHelpers.hoist(node)
    }

    override fun visit(node: Fcnheader) {
        PstToAstHelpers.hoist(node)
    }

    override fun visit(node: Fcnid) {}
    override fun visit(node: Retkind) {}
    override fun visit(node: PParmlist) {
        node.children.removeIf { it is RightParen }
        if (!node.children.isEmpty())
            PstToAstHelpers.rightContraction(node)
        PstToAstHelpers.hoist(node)
    }

    override fun visit(node: Varspecs) {
        if (!node.children.isEmpty())
            PstToAstHelpers.rightContraction(node)
    }

    override fun visit(node: More_varspecs) {
        node.children.removeIf { it is Comma }
        if (!node.children.isEmpty())
            PstToAstHelpers.rightContraction(node)
    }

    override fun visit(node: PPonly) {}
    override fun visit(node: Stmts) {
        node.children.removeIf { it is SemiColon }
        if (!node.children.isEmpty())
            PstToAstHelpers.rightContraction(node)
    }

    override fun visit(node: Stmt) {}
    override fun visit(node: StasgnOrFcall) {
        when (node.children.last) {
            is Equal -> PstToAstHelpers.reverseHoist(node)
            is LeftParen -> PstToAstHelpers.hoist(node)
        }
    }

    override fun visit(node: StasgnOrFcall_Suffix) {}
    override fun visit(node: Stasgn_Suffix) {
        PstToAstHelpers.hoist(node)
    }

    override fun visit(node: Lval_Suffix) {}
    override fun visit(node: Lval) {}
    override fun visit(node: LvalOrFcall) {
        PstToAstHelpers.hoist(node)
    }

    override fun visit(node: LvalOrFcall_Suffix) {}
    override fun visit(node: Lval_Tail) {}
    override fun visit(node: KKexpr) {
        node.children.removeIf { it is RightBracket }
        PstToAstHelpers.hoist(node)
    }

    override fun visit(node: Fcall) {}
    override fun visit(node: PPexprs) {
        node.children.removeIf { it is RightParen }
        if (!node.children.isEmpty()) {
            PstToAstHelpers.rightContraction(node)
        }
        PstToAstHelpers.hoist(node)
    }

    override fun visit(node: Stif) {
        PstToAstHelpers.hoist(node)
    }

    override fun visit(node: Elsepart) {
        PstToAstHelpers.hoist(node)
    }

    override fun visit(node: Stwhile) {
        PstToAstHelpers.hoist(node)
    }

    override fun visit(node: Stprint) {
        PstToAstHelpers.hoist(node)
    }

    override fun visit(node: Stinput) {
        PstToAstHelpers.hoist(node)
    }

    override fun visit(node: Strtn) {
        if (node.children.size <= 1) {
            return
        }
        val returnVal = node.children.removeLast()
        val returnNode = node.children.first
        returnNode.children.addLast(returnVal)
        returnVal.parent = returnNode
        PstToAstHelpers.hoist(node)
    }

    override fun visit(node: Strtn_Suffix) {}
    override fun visit(node: Fact) {}
    override fun visit(node: BaseLiteral) {}
    override fun visit(node: Addrof_id) {
        PstToAstHelpers.hoist(node)
    }

    override fun visit(node: Oprel) {}
    override fun visit(node: Lthan) {}
    override fun visit(node: Gthan) {}
    override fun visit(node: Opadd) {}
    override fun visit(node: Opmul) {}
    override fun visit(node: Epsilon) {}
    override fun visit(node: Classheader) {
        PstToAstHelpers.hoist(node)
    }

    override fun visit(node: Classid) {}
}
