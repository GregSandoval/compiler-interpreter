package compiler.a5.grammar

import compiler.parser.PstToAstHelpers
import compiler.parser.Symbol.NonTerminal.*
import compiler.parser.Symbol.Terminal.Keyword.VarKeyword
import compiler.parser.Symbol.Terminal.Operator.Equal
import compiler.parser.Symbol.Terminal.Operator.Plus
import compiler.parser.Symbol.Terminal.Punctuation.*
import compiler.parser.TreeNode
import java.util.*

class PstToAstNonTerminalVisitor : NonTerminalVisitor {

    override fun visit(expr: Expr) {
        if (expr.children.size == 2) {
            val left = expr.children.pop()
            val right = expr.children.peek()
            PstToAstHelpers.hoist(expr)
            left.parent = right
            right.children.addFirst(left)
        }
    }

    override fun visit(exprTail: Expr_Tail) {
        PstToAstHelpers.hoist(exprTail)
    }

    override fun visit(term: Term) {
        if (term.children.size == 2) {
            val lvalue = term.children[0]
            val operator = term.children[1]
            term.children.remove(lvalue)
            lvalue.parent = operator
            operator.children.addFirst(lvalue)
        }
        PstToAstHelpers.hoist(term)
    }

    override fun visit(termTail: Term_Tail) {
        if (termTail.children.size == 3) {
            val tail = termTail.children[2]
            val tailLValue = termTail.children[1]
            tailLValue.parent = tail
            tail.children.addFirst(tailLValue)
            termTail.children.remove(tailLValue)
        }
        PstToAstHelpers.hoist(termTail)
    }

    override fun visit(rterm: Rterm) {
        if (rterm.children.size == 2) {
            val operator = rterm.children[1]
            val lvalue = rterm.children[0]
            lvalue.parent = operator
            rterm.children.remove(lvalue)
            operator.children.addFirst(lvalue)
        }
        PstToAstHelpers.hoist(rterm)
    }

    override fun visit(rtermTail: Rterm_Tail) {
        if (rtermTail.children.size == 3) {
            val tail = rtermTail.children[2]
            val tailLValue = rtermTail.children[1]
            tailLValue.parent = tail
            tail.children.addFirst(tailLValue)
            rtermTail.children.remove(tailLValue)
        }
        PstToAstHelpers.hoist(rtermTail)
    }

    override fun visit(pPexpr: PPexpr) {
        pPexpr.children.removeIf { it is RightParen }
        PstToAstHelpers.hoist(pPexpr)
    }

    override fun visit(classmom: Classmom) {
        PstToAstHelpers.hoist(classmom)
    }

    override fun visit(parseTreeSentinel: ParseTreeSentinel) {
    }

    override fun visit(nullNode: NULL_NODE) {
    }

    override fun visit(pgm: Pgm) {
        var fcndefsIndex = -1
        var fcndefs: Fcndefs? = null

        for (i in pgm.children.indices) {
            val child = pgm.children[i]
            if (child is Fcndefs) {
                fcndefsIndex = i
                fcndefs = child
                break
            }
        }

        if (fcndefs != null) {
            for (child in fcndefs.children) {
                pgm.children.add(fcndefsIndex++, child)
                child.parent = pgm
            }
        }

        if (fcndefs != null)
            pgm.children.remove(fcndefs)
        PstToAstHelpers.hoist(pgm)
    }

    override fun visit(main: Main) {
        PstToAstHelpers.hoist(main)
    }

    override fun visit(bBlock: BBlock) {
        bBlock.children.removeIf { it is RightBrace }
        PstToAstHelpers.rightContraction(bBlock)
        PstToAstHelpers.hoist(bBlock)
    }

    override fun visit(vargroup: Vargroup) {
        PstToAstHelpers.hoist(vargroup)
    }

    override fun visit(pPvarlist: PPvarlist) {
        pPvarlist.children.removeIf { it is RightParen }
        if (pPvarlist.children.size == 2) {
            val varlist = pPvarlist.children[1]
            pPvarlist.children.remove(varlist)
            varlist.parent = null
            varlist.children.forEach { pPvarlist.children.addLast(it) }
            varlist.children.forEach { it.parent = pPvarlist }
        }
        PstToAstHelpers.hoist(pPvarlist)
    }

    override fun visit(varlist: Varlist) {
        varlist.children.removeIf { it is SemiColon }
        if (varlist.children.size == 2) {
            val otherVarList = varlist.children[1]
            varlist.children.remove(otherVarList)
            otherVarList.children.forEach { varlist.children.addLast(it) }
            otherVarList.children.forEach { it.parent = varlist }
            return
        }
    }

    override fun visit(varitem: Varitem) {
        PstToAstHelpers.reverseHoist(varitem)
    }

    override fun visit(varitem_Suffix: Varitem_Suffix) {
        PstToAstHelpers.hoist(varitem_Suffix)
    }

    override fun visit(vardecl: Vardecl) {
        PstToAstHelpers.hoist(vardecl)
    }

    override fun visit(simplekind: Simplekind) {}

    override fun visit(baseKind: BaseKind) {}

    override fun visit(varspec: Varspec) {
        PstToAstHelpers.hoist(varspec)
    }

    override fun visit(varid: Varid) {}

    override fun visit(arrspec: Arrspec) {}

    override fun visit(kKint: KKint) {
        kKint.children.removeIf { it is RightBracket }
        PstToAstHelpers.hoist(kKint)
    }

    override fun visit(derefID: Deref_id) {
        PstToAstHelpers.hoist(derefID)
    }

    override fun visit(deref: Deref) {}

    override fun visit(varinit: Varinit) {}

    override fun visit(bBexprs: BBexprs) {}

    override fun visit(exprlist: Exprlist) {
        if (!exprlist.children.isEmpty()) {
            PstToAstHelpers.rightContraction(exprlist)
        }
    }

    override fun visit(moreexprs: Moreexprs) {
        moreexprs.children.removeIf { it is Comma }
        if (!moreexprs.children.isEmpty()) {
            PstToAstHelpers.rightContraction(moreexprs)
        }
    }

    override fun visit(classdecl: Classdecl) {}

    override fun visit(classdef: Classdef) {
        PstToAstHelpers.hoist(classdef)
    }

    override fun visit(classDefSuffix: Classdef_Suffix) {}

    override fun visit(bBClassitems: BBClassitems) {
        bBClassitems.children.removeIf { it is RightBrace }
        PstToAstHelpers.rightContraction(bBClassitems)
        val removables = ArrayList<TreeNode>()
        for (i in 0 until bBClassitems.children.size - 1) {
            val left = bBClassitems.children[i]
            val right = bBClassitems.children[i + 1]
            if (left is Colon && right is VarKeyword) {
                removables.add(right)
                left.children.addLast(right)
                right.parent = left
            }
        }
        if (bBClassitems.children.last is Mddecls) {
            PstToAstHelpers.rightContraction(bBClassitems)
        }
        bBClassitems.children.removeAll(removables)
        PstToAstHelpers.hoist(bBClassitems)
    }

    override fun visit(classitems: Classitems) {
        if (!classitems.children.isEmpty() && classitems.children.last is Classitems)
            PstToAstHelpers.rightContraction(classitems)
    }

    override fun visit(classgroup: Classgroup) {}

    override fun visit(classCtrl: Class_ctrl) {
        PstToAstHelpers.hoist(classCtrl)
    }

    override fun visit(interfaces: Interfaces) {
        if (!interfaces.children.isEmpty()) {
            PstToAstHelpers.rightContraction(interfaces)
        }
        if (interfaces.parent is Classheader) {
            PstToAstHelpers.hoist(interfaces)
        }
        interfaces.children.removeIf { it is Plus }
    }

    override fun visit(mddecls: Mddecls) {
        if (!mddecls.children.isEmpty())
            PstToAstHelpers.rightContraction(mddecls)
    }

    override fun visit(mdheader: Mdheader) {
        PstToAstHelpers.hoist(mdheader)
    }

    override fun visit(mdId: Md_id) {
        mdId.children.add(1, mdId.children.removeFirst())
        PstToAstHelpers.hoist(mdId)
    }

    override fun visit(fcndefs: Fcndefs) {}

    override fun visit(fcndef: Fcndef) {
        PstToAstHelpers.hoist(fcndef)
    }

    override fun visit(fcnheader: Fcnheader) {
        PstToAstHelpers.hoist(fcnheader)
    }

    override fun visit(fcnid: Fcnid) {}

    override fun visit(retkind: Retkind) {}

    override fun visit(pParmlist: PParmlist) {
        pParmlist.children.removeIf { it is RightParen }
        if (!pParmlist.children.isEmpty())
            PstToAstHelpers.rightContraction(pParmlist)
        PstToAstHelpers.hoist(pParmlist)
    }

    override fun visit(varspecs: Varspecs) {
        if (!varspecs.children.isEmpty())
            PstToAstHelpers.rightContraction(varspecs)
    }

    override fun visit(moreVarspecs: More_varspecs) {
        moreVarspecs.children.removeIf { it is Comma }
        if (!moreVarspecs.children.isEmpty())
            PstToAstHelpers.rightContraction(moreVarspecs)
    }

    override fun visit(pPonly: PPonly) {}

    override fun visit(stmts: Stmts) {
        stmts.children.removeIf { it is SemiColon }
        if (!stmts.children.isEmpty())
            PstToAstHelpers.rightContraction(stmts)
    }

    override fun visit(stmt: Stmt) {}

    override fun visit(stasgnOrFcall: StasgnOrFcall) {
        when (stasgnOrFcall.children.last) {
            is Equal -> PstToAstHelpers.reverseHoist(stasgnOrFcall)
            is LeftParen -> PstToAstHelpers.hoist(stasgnOrFcall)
        }
    }

    override fun visit(stasgnOrFcallSuffix: StasgnOrFcall_Suffix) {}

    override fun visit(stasgnSuffix: Stasgn_Suffix) {
        PstToAstHelpers.hoist(stasgnSuffix)
    }

    override fun visit(lvalSuffix: Lval_Suffix) {}

    override fun visit(lval: Lval) {}

    override fun visit(lvalOrFcall: LvalOrFcall) {
        PstToAstHelpers.hoist(lvalOrFcall)
    }

    override fun visit(lvalOrFcallSuffix: LvalOrFcall_Suffix) {}

    override fun visit(lvalTail: Lval_Tail) {}

    override fun visit(kKexpr: KKexpr) {
        kKexpr.children.removeIf { it is RightBracket }
        PstToAstHelpers.hoist(kKexpr)
    }

    override fun visit(fcall: Fcall) {}
    override fun visit(pPexprs: PPexprs) {
        pPexprs.children.removeIf { it is RightParen }
        if (!pPexprs.children.isEmpty()) {
            PstToAstHelpers.rightContraction(pPexprs)
        }
        PstToAstHelpers.hoist(pPexprs)
    }

    override fun visit(stif: Stif) {
        PstToAstHelpers.hoist(stif)
    }

    override fun visit(elsepart: Elsepart) {
        PstToAstHelpers.hoist(elsepart)
    }

    override fun visit(stwhile: Stwhile) {
        PstToAstHelpers.hoist(stwhile)
    }

    override fun visit(stprint: Stprint) {
        PstToAstHelpers.hoist(stprint)
    }

    override fun visit(stinput: Stinput) {
        PstToAstHelpers.hoist(stinput)
    }

    override fun visit(strtn: Strtn) {
        if (strtn.children.size <= 1) {
            return
        }
        val returnVal = strtn.children.removeLast()
        val returnNode = strtn.children.first
        returnNode.children.addLast(returnVal)
        returnVal.parent = returnNode
        PstToAstHelpers.hoist(strtn)
    }

    override fun visit(strtnSuffix: Strtn_Suffix) {}

    override fun visit(fact: Fact) {}

    override fun visit(baseLiteral: BaseLiteral) {}

    override fun visit(addrofId: Addrof_id) {
        PstToAstHelpers.hoist(addrofId)
    }

    override fun visit(oprel: Oprel) {}

    override fun visit(lthan: Lthan) {}

    override fun visit(gthan: Gthan) {}

    override fun visit(opadd: Opadd) {}

    override fun visit(opmul: Opmul) {}

    override fun visit(epsilon: Epsilon) {}

    override fun visit(classheader: Classheader) {
        PstToAstHelpers.hoist(classheader)
    }

    override fun visit(classid: Classid) {}
}
