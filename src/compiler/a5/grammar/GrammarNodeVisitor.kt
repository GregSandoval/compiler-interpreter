package compiler.a5.grammar

import compiler.a5.grammar.A5GrammarNonTerminals.*
import compiler.parser.GrammarNode

interface GrammarNodeVisitor {
    fun visit(node: GrammarNode) {
        throw RuntimeException("Not implemented: visit(Pgm node); this: $this")
    }

    fun visit(node: Pgm) {
        throw RuntimeException("Not implemented: visit(Pgm node); this: $this")
    }

    fun visit(node: Main) {
        throw RuntimeException("Not implemented: visit(Main node); this: $this")
    }

    fun visit(node: BBlock) {
        throw RuntimeException("Not implemented: visit(BBlock node); this: $this")
    }

    fun visit(node: Vargroup) {
        throw RuntimeException("Not implemented: visit(Vargroup node); this: $this")
    }

    fun visit(node: PPvarlist) {
        throw RuntimeException("Not implemented: visit(PPvarlist node); this: $this")
    }

    fun visit(node: Varlist) {
        throw RuntimeException("Not implemented: visit(Varlist node); this: $this")
    }

    fun visit(node: Varitem) {
        throw RuntimeException("Not implemented: visit(Varitem node); this: $this")
    }

    fun visit(node: Varitem_Suffix) {
        throw RuntimeException("Not implemented: visit(Varitem node); this: $this")
    }

    fun visit(node: Vardecl) {
        throw RuntimeException("Not implemented: visit(Vardecl node); this: $this")
    }

    fun visit(node: Simplekind) {
        throw RuntimeException("Not implemented: visit(Simplekind node); this: $this")
    }

    fun visit(node: BaseKind) {
        throw RuntimeException("Not implemented: visit(BaseKind node); this: $this")
    }

    fun visit(node: Classid) {
        throw RuntimeException("Not implemented: visit(Classid node); this: $this")
    }

    fun visit(node: Varspec) {
        throw RuntimeException("Not implemented: visit(Varspec node); this: $this")
    }

    fun visit(node: Varid) {
        throw RuntimeException("Not implemented: visit(Varid node); this: $this")
    }

    fun visit(node: Arrspec) {
        throw RuntimeException("Not implemented: visit(Arrspec node); this: $this")
    }

    fun visit(node: KKint) {
        throw RuntimeException("Not implemented: visit(KKint node); this: $this")
    }

    fun visit(node: Deref_id) {
        throw RuntimeException("Not implemented: visit(Deref_id node); this: $this")
    }

    fun visit(node: Deref) {
        throw RuntimeException("Not implemented: visit(Deref node); this: $this")
    }

    fun visit(node: Varinit) {
        throw RuntimeException("Not implemented: visit(Varinit node); this: $this")
    }

    fun visit(node: BBexprs) {
        throw RuntimeException("Not implemented: visit(BBexprs node); this: $this")
    }

    fun visit(node: Exprlist) {
        throw RuntimeException("Not implemented: visit(Exprlist node); this: $this")
    }

    fun visit(node: Moreexprs) {
        throw RuntimeException("Not implemented: visit(Moreexprs node); this: $this")
    }

    fun visit(node: Classdecl) {
        throw RuntimeException("Not implemented: visit(Classdecl node); this: $this")
    }

    fun visit(node: Classdef) {
        throw RuntimeException("Not implemented: visit(Classdef node); this: $this")
    }

    fun visit(node: Classdef_Suffix) {
        throw RuntimeException("Not implemented: visit(Classdef node); this: $this")
    }

    fun visit(node: BBClassitems) {
        throw RuntimeException("Not implemented: visit(BBClassitems node); this: $this")
    }

    fun visit(node: Classheader) {
        throw RuntimeException("Not implemented: visit(Classheader node); this: $this")
    }

    fun visit(node: Classmom) {
        throw RuntimeException("Not implemented: visit(Classmom node); this: $this")
    }

    fun visit(node: Classitems) {
        throw RuntimeException("Not implemented: visit(Classitems node); this: $this")
    }

    fun visit(node: Classgroup) {
        throw RuntimeException("Not implemented: visit(Classgroup node); this: $this")
    }

    fun visit(node: Class_ctrl) {
        throw RuntimeException("Not implemented: visit(Class_ctrl node); this: $this")
    }

    fun visit(node: Interfaces) {
        throw RuntimeException("Not implemented: visit(Interfaces node); this: $this")
    }

    fun visit(node: Mddecls) {
        throw RuntimeException("Not implemented: visit(Mddecls node); this: $this")
    }

    fun visit(node: Mdheader) {
        throw RuntimeException("Not implemented: visit(Mdheader node); this: $this")
    }

    fun visit(node: Md_id) {
        throw RuntimeException("Not implemented: visit(Md_id node); this: $this")
    }

    fun visit(node: Fcndefs) {
        throw RuntimeException("Not implemented: visit(Fcndefs node); this: $this")
    }

    fun visit(node: Fcndef) {
        throw RuntimeException("Not implemented: visit(Fcndef node); this: $this")
    }

    fun visit(node: Fcnheader) {
        throw RuntimeException("Not implemented: visit(Fcnheader node); this: $this")
    }

    fun visit(node: Fcnid) {
        throw RuntimeException("Not implemented: visit(Fcnid node); this: $this")
    }

    fun visit(node: Retkind) {
        throw RuntimeException("Not implemented: visit(Retkind node); this: $this")
    }

    fun visit(node: PParmlist) {
        throw RuntimeException("Not implemented: visit(PParmlist node); this: $this")
    }

    fun visit(node: Varspecs) {
        throw RuntimeException("Not implemented: visit(Varspecs node); this: $this")
    }

    fun visit(node: More_varspecs) {
        throw RuntimeException("Not implemented: visit(More_varspecs node); this: $this")
    }

    fun visit(node: PPonly) {
        throw RuntimeException("Not implemented: visit(PPonly node); this: $this")
    }

    fun visit(node: Stmts) {
        throw RuntimeException("Not implemented: visit(Stmts node); this: $this")
    }

    fun visit(node: Stmt) {
        throw RuntimeException("Not implemented: visit(Stmt node); this: $this")
    }

    fun visit(node: StasgnOrFcall) {
        throw RuntimeException("Not implemented: visit(Stasgn node); this: $this")
    }

    fun visit(node: StasgnOrFcall_Suffix) {
        throw RuntimeException("Not implemented: visit(Stasgn node); this: $this")
    }

    fun visit(node: Stasgn_Suffix) {
        throw RuntimeException("Not implemented: visit(Stasgn node); this: $this")
    }

    fun visit(node: Lval_Suffix) {
        throw RuntimeException("Not implemented: visit(Stasgn node); this: $this")
    }

    fun visit(node: Lval) {
        throw RuntimeException("Not implemented: visit(Lval node); this: $this")
    }

    fun visit(node: LvalOrFcall) {
        throw RuntimeException("Not implemented: visit(Lval node); this: $this")
    }

    fun visit(node: LvalOrFcall_Suffix) {
        throw RuntimeException("Not implemented: visit(Lval node); this: $this")
    }

    fun visit(node: Lval_Tail) {
        throw RuntimeException("Not implemented: visit(Aref node); this: $this")
    }

    fun visit(node: KKexpr) {
        throw RuntimeException("Not implemented: visit(KKexpr node); this: $this")
    }

    fun visit(node: Fcall) {
        throw RuntimeException("Not implemented: visit(Fcall node); this: $this")
    }

    fun visit(node: PPexprs) {
        throw RuntimeException("Not implemented: visit(PPexprs node); this: $this")
    }

    fun visit(node: Stif) {
        throw RuntimeException("Not implemented: visit(Stif node); this: $this")
    }

    fun visit(node: Elsepart) {
        throw RuntimeException("Not implemented: visit(Elsepart node); this: $this")
    }

    fun visit(node: Stwhile) {
        throw RuntimeException("Not implemented: visit(Stwhile node); this: $this")
    }

    fun visit(node: Stprint) {
        throw RuntimeException("Not implemented: visit(Stprint node); this: $this")
    }

    fun visit(node: Stinput) {
        throw RuntimeException("Not implemented: visit(Stinput node); this: $this")
    }

    fun visit(node: Strtn) {
        throw RuntimeException("Not implemented: visit(Strtn node); this: $this")
    }

    fun visit(node: Strtn_Suffix) {
        throw RuntimeException("Not implemented: visit(Strtn node); this: $this")
    }

    fun visit(node: PPexpr) {
        throw RuntimeException("Not implemented: visit(PPexpr node); this: $this")
    }

    fun visit(node: Expr) {
        throw RuntimeException("Not implemented: visit(Expr node); this: $this")
    }

    fun visit(node: Expr_Tail) {
        throw RuntimeException("Not implemented: visit(Expr_Tail node); this: $this")
    }

    fun visit(node: Rterm) {
        throw RuntimeException("Not implemented: visit(Rterm node); this: $this")
    }

    fun visit(node: Rterm_Tail) {
        throw RuntimeException("Not implemented: visit(Rterm_Tail node); this: $this")
    }

    fun visit(node: Term) {
        throw RuntimeException("Not implemented: visit(Term node); this: $this")
    }

    fun visit(node: Term_Tail) {
        throw RuntimeException("Not implemented: visit(Term_Tail node); this: $this")
    }

    fun visit(node: Fact) {
        throw RuntimeException("Not implemented: visit(Fact node); this: $this")
    }

    fun visit(node: BaseLiteral) {
        throw RuntimeException("Not implemented: visit(BaseLiteral node); this: $this")
    }

    fun visit(node: Addrof_id) {
        throw RuntimeException("Not implemented: visit(Addrof_id node); this: $this")
    }

    fun visit(node: Oprel) {
        throw RuntimeException("Not implemented: visit(Oprel node); this: $this")
    }

    fun visit(node: Lthan) {
        throw RuntimeException("Not implemented: visit(Lthan node); this: $this")
    }

    fun visit(node: Gthan) {
        throw RuntimeException("Not implemented: visit(Gthan node); this: $this")
    }

    fun visit(node: Opadd) {
        throw RuntimeException("Not implemented: visit(Opadd node); this: $this")
    }

    fun visit(node: Opmul) {
        throw RuntimeException("Not implemented: visit(Opmul node); this: $this")
    }

    fun visit(node: Epsilon) {
        throw RuntimeException("Not implemented: visit(Epsilon node); this: $this")
    }
}
