package compiler.a5.grammar

import compiler.parser.Language.Grammar
import compiler.parser.Language.Grammar.*


interface GrammarNodeVisitor {
    fun accept(node: Grammar) = when (node) {
        is Pgm -> visit(node)
        is Main -> visit(node)
        is BBlock -> visit(node)
        is Vargroup -> visit(node)
        is PPvarlist -> visit(node)
        is Varlist -> visit(node)
        is Varitem -> visit(node)
        is Varitem_Suffix -> visit(node)
        is Vardecl -> visit(node)
        is Simplekind -> visit(node)
        is BaseKind -> visit(node)
        is Classid -> visit(node)
        is Varspec -> visit(node)
        is Varid -> visit(node)
        is Arrspec -> visit(node)
        is KKint -> visit(node)
        is Deref_id -> visit(node)
        is Deref -> visit(node)
        is Varinit -> visit(node)
        is BBexprs -> visit(node)
        is Exprlist -> visit(node)
        is Moreexprs -> visit(node)
        is Classdecl -> visit(node)
        is Classdef -> visit(node)
        is Classdef_Suffix -> visit(node)
        is BBClassitems -> visit(node)
        is Classheader -> visit(node)
        is Classmom -> visit(node)
        is Classitems -> visit(node)
        is Classgroup -> visit(node)
        is Class_ctrl -> visit(node)
        is Interfaces -> visit(node)
        is Mddecls -> visit(node)
        is Mdheader -> visit(node)
        is Md_id -> visit(node)
        is Fcndefs -> visit(node)
        is Fcndef -> visit(node)
        is Fcnheader -> visit(node)
        is Fcnid -> visit(node)
        is Retkind -> visit(node)
        is PParmlist -> visit(node)
        is Varspecs -> visit(node)
        is More_varspecs -> visit(node)
        is PPonly -> visit(node)
        is Stmts -> visit(node)
        is Stmt -> visit(node)
        is StasgnOrFcall -> visit(node)
        is StasgnOrFcall_Suffix -> visit(node)
        is Stasgn_Suffix -> visit(node)
        is Lval_Suffix -> visit(node)
        is Lval -> visit(node)
        is LvalOrFcall -> visit(node)
        is LvalOrFcall_Suffix -> visit(node)
        is Lval_Tail -> visit(node)
        is KKexpr -> visit(node)
        is Fcall -> visit(node)
        is PPexprs -> visit(node)
        is Stif -> visit(node)
        is Elsepart -> visit(node)
        is Stwhile -> visit(node)
        is Stprint -> visit(node)
        is Stinput -> visit(node)
        is Strtn -> visit(node)
        is Strtn_Suffix -> visit(node)
        is PPexpr -> visit(node)
        is Expr -> visit(node)
        is Expr_Tail -> visit(node)
        is Rterm -> visit(node)
        is Rterm_Tail -> visit(node)
        is Term -> visit(node)
        is Term_Tail -> visit(node)
        is Fact -> visit(node)
        is BaseLiteral -> visit(node)
        is Addrof_id -> visit(node)
        is Oprel -> visit(node)
        is Lthan -> visit(node)
        is Gthan -> visit(node)
        is Opadd -> visit(node)
        is Opmul -> visit(node)
        is Epsilon -> visit(node)
        is NULL_NODE -> visit(node)
        is ParseTreeSentinel -> visit(node)
    }

    fun visit(node: ParseTreeSentinel)
    fun visit(node: NULL_NODE)
    fun visit(node: Pgm)
    fun visit(node: Main)
    fun visit(node: BBlock)
    fun visit(node: Vargroup)
    fun visit(node: PPvarlist)
    fun visit(node: Varlist)
    fun visit(node: Varitem)
    fun visit(node: Varitem_Suffix)
    fun visit(node: Vardecl)
    fun visit(node: Simplekind)
    fun visit(node: BaseKind)
    fun visit(node: Classid)
    fun visit(node: Varspec)
    fun visit(node: Varid)
    fun visit(node: Arrspec)
    fun visit(node: KKint)
    fun visit(node: Deref_id)
    fun visit(node: Deref)
    fun visit(node: Varinit)
    fun visit(node: BBexprs)
    fun visit(node: Exprlist)
    fun visit(node: Moreexprs)
    fun visit(node: Classdecl)
    fun visit(node: Classdef)
    fun visit(node: Classdef_Suffix)
    fun visit(node: BBClassitems)
    fun visit(node: Classheader)
    fun visit(node: Classmom)
    fun visit(node: Classitems)
    fun visit(node: Classgroup)
    fun visit(node: Class_ctrl)
    fun visit(node: Interfaces)
    fun visit(node: Mddecls)
    fun visit(node: Mdheader)
    fun visit(node: Md_id)
    fun visit(node: Fcndefs)
    fun visit(node: Fcndef)
    fun visit(node: Fcnheader)
    fun visit(node: Fcnid)
    fun visit(node: Retkind)
    fun visit(node: PParmlist)
    fun visit(node: Varspecs)
    fun visit(node: More_varspecs)
    fun visit(node: PPonly)
    fun visit(node: Stmts)
    fun visit(node: Stmt)
    fun visit(node: StasgnOrFcall)
    fun visit(node: StasgnOrFcall_Suffix)
    fun visit(node: Stasgn_Suffix)
    fun visit(node: Lval_Suffix)
    fun visit(node: Lval)
    fun visit(node: LvalOrFcall)
    fun visit(node: LvalOrFcall_Suffix)
    fun visit(node: Lval_Tail)
    fun visit(node: KKexpr)
    fun visit(node: Fcall)
    fun visit(node: PPexprs)
    fun visit(node: Stif)
    fun visit(node: Elsepart)
    fun visit(node: Stwhile)
    fun visit(node: Stprint)
    fun visit(node: Stinput)
    fun visit(node: Strtn)
    fun visit(node: Strtn_Suffix)
    fun visit(node: PPexpr)
    fun visit(node: Expr)
    fun visit(node: Expr_Tail)
    fun visit(node: Rterm)
    fun visit(node: Rterm_Tail)
    fun visit(node: Term)
    fun visit(node: Term_Tail)
    fun visit(node: Fact)
    fun visit(node: BaseLiteral)
    fun visit(node: Addrof_id)
    fun visit(node: Oprel)
    fun visit(node: Lthan)
    fun visit(node: Gthan)
    fun visit(node: Opadd)
    fun visit(node: Opmul)
    fun visit(node: Epsilon)
}
