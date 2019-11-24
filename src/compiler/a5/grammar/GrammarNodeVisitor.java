package compiler.a5.grammar;

import compiler.parser.GrammarNode;

import static compiler.a5.grammar.A5GrammarNonTerminals.*;

public interface GrammarNodeVisitor {
  default void visit(GrammarNode node) {
    throw new RuntimeException("Not implemented: visit(Pgm node); this: " + this);
  }

  default void visit(Pgm node) {
    throw new RuntimeException("Not implemented: visit(Pgm node); this: " + this);
  }

  default void visit(Main node) {
    throw new RuntimeException("Not implemented: visit(Main node); this: " + this);
  }

  default void visit(BBlock node) {
    throw new RuntimeException("Not implemented: visit(BBlock node); this: " + this);
  }

  default void visit(Vargroup node) {
    throw new RuntimeException("Not implemented: visit(Vargroup node); this: " + this);
  }

  default void visit(PPvarlist node) {
    throw new RuntimeException("Not implemented: visit(PPvarlist node); this: " + this);
  }

  default void visit(Varlist node) {
    throw new RuntimeException("Not implemented: visit(Varlist node); this: " + this);
  }

  default void visit(Varitem node) {
    throw new RuntimeException("Not implemented: visit(Varitem node); this: " + this);
  }

  default void visit(Varitem_Suffix node) {
    throw new RuntimeException("Not implemented: visit(Varitem node); this: " + this);
  }

  default void visit(Vardecl node) {
    throw new RuntimeException("Not implemented: visit(Vardecl node); this: " + this);
  }

  default void visit(Simplekind node) {
    throw new RuntimeException("Not implemented: visit(Simplekind node); this: " + this);
  }

  default void visit(BaseKind node) {
    throw new RuntimeException("Not implemented: visit(BaseKind node); this: " + this);
  }

  default void visit(Classid node) {
    throw new RuntimeException("Not implemented: visit(Classid node); this: " + this);
  }

  default void visit(Varspec node) {
    throw new RuntimeException("Not implemented: visit(Varspec node); this: " + this);
  }

  default void visit(Varid node) {
    throw new RuntimeException("Not implemented: visit(Varid node); this: " + this);
  }

  default void visit(Arrspec node) {
    throw new RuntimeException("Not implemented: visit(Arrspec node); this: " + this);
  }

  default void visit(KKint node) {
    throw new RuntimeException("Not implemented: visit(KKint node); this: " + this);
  }

  default void visit(Deref_id node) {
    throw new RuntimeException("Not implemented: visit(Deref_id node); this: " + this);
  }

  default void visit(Deref node) {
    throw new RuntimeException("Not implemented: visit(Deref node); this: " + this);
  }

  default void visit(Varinit node) {
    throw new RuntimeException("Not implemented: visit(Varinit node); this: " + this);
  }

  default void visit(BBexprs node) {
    throw new RuntimeException("Not implemented: visit(BBexprs node); this: " + this);
  }

  default void visit(Exprlist node) {
    throw new RuntimeException("Not implemented: visit(Exprlist node); this: " + this);
  }

  default void visit(Moreexprs node) {
    throw new RuntimeException("Not implemented: visit(Moreexprs node); this: " + this);
  }

  default void visit(Classdecl node) {
    throw new RuntimeException("Not implemented: visit(Classdecl node); this: " + this);
  }

  default void visit(Classdef node) {
    throw new RuntimeException("Not implemented: visit(Classdef node); this: " + this);
  }

  default void visit(Classdef_Suffix node) {
    throw new RuntimeException("Not implemented: visit(Classdef node); this: " + this);
  }

  default void visit(BBClassitems node) {
    throw new RuntimeException("Not implemented: visit(BBClassitems node); this: " + this);
  }

  default void visit(Classheader node) {
    throw new RuntimeException("Not implemented: visit(Classheader node); this: " + this);
  }

  default void visit(Classmom node) {
    throw new RuntimeException("Not implemented: visit(Classmom node); this: " + this);
  }

  default void visit(Classitems node) {
    throw new RuntimeException("Not implemented: visit(Classitems node); this: " + this);
  }

  default void visit(Classgroup node) {
    throw new RuntimeException("Not implemented: visit(Classgroup node); this: " + this);
  }

  default void visit(Class_ctrl node) {
    throw new RuntimeException("Not implemented: visit(Class_ctrl node); this: " + this);
  }

  default void visit(Interfaces node) {
    throw new RuntimeException("Not implemented: visit(Interfaces node); this: " + this);
  }

  default void visit(Mddecls node) {
    throw new RuntimeException("Not implemented: visit(Mddecls node); this: " + this);
  }

  default void visit(Mdheader node) {
    throw new RuntimeException("Not implemented: visit(Mdheader node); this: " + this);
  }

  default void visit(Md_id node) {
    throw new RuntimeException("Not implemented: visit(Md_id node); this: " + this);
  }

  default void visit(Fcndefs node) {
    throw new RuntimeException("Not implemented: visit(Fcndefs node); this: " + this);
  }

  default void visit(Fcndef node) {
    throw new RuntimeException("Not implemented: visit(Fcndef node); this: " + this);
  }

  default void visit(Fcnheader node) {
    throw new RuntimeException("Not implemented: visit(Fcnheader node); this: " + this);
  }

  default void visit(Fcnid node) {
    throw new RuntimeException("Not implemented: visit(Fcnid node); this: " + this);
  }

  default void visit(Retkind node) {
    throw new RuntimeException("Not implemented: visit(Retkind node); this: " + this);
  }

  default void visit(PParmlist node) {
    throw new RuntimeException("Not implemented: visit(PParmlist node); this: " + this);
  }

  default void visit(Varspecs node) {
    throw new RuntimeException("Not implemented: visit(Varspecs node); this: " + this);
  }

  default void visit(More_varspecs node) {
    throw new RuntimeException("Not implemented: visit(More_varspecs node); this: " + this);
  }

  default void visit(PPonly node) {
    throw new RuntimeException("Not implemented: visit(PPonly node); this: " + this);
  }

  default void visit(Stmts node) {
    throw new RuntimeException("Not implemented: visit(Stmts node); this: " + this);
  }

  default void visit(Stmt node) {
    throw new RuntimeException("Not implemented: visit(Stmt node); this: " + this);
  }

  default void visit(StasgnOrFcall node) {
    throw new RuntimeException("Not implemented: visit(Stasgn node); this: " + this);
  }

  default void visit(StasgnOrFcall_Suffix node) {
    throw new RuntimeException("Not implemented: visit(Stasgn node); this: " + this);
  }

  default void visit(Stasgn_Suffix node) {
    throw new RuntimeException("Not implemented: visit(Stasgn node); this: " + this);
  }

  default void visit(Lval_Suffix node) {
    throw new RuntimeException("Not implemented: visit(Stasgn node); this: " + this);
  }

  default void visit(Lval node) {
    throw new RuntimeException("Not implemented: visit(Lval node); this: " + this);
  }

  default void visit(LvalOrFcall node) {
    throw new RuntimeException("Not implemented: visit(Lval node); this: " + this);
  }

  default void visit(LvalOrFcall_Suffix node) {
    throw new RuntimeException("Not implemented: visit(Lval node); this: " + this);
  }

  default void visit(Lval_Tail node) {
    throw new RuntimeException("Not implemented: visit(Aref node); this: " + this);
  }

  default void visit(KKexpr node) {
    throw new RuntimeException("Not implemented: visit(KKexpr node); this: " + this);
  }

  default void visit(Fcall node) {
    throw new RuntimeException("Not implemented: visit(Fcall node); this: " + this);
  }

  default void visit(PPexprs node) {
    throw new RuntimeException("Not implemented: visit(PPexprs node); this: " + this);
  }

  default void visit(Stif node) {
    throw new RuntimeException("Not implemented: visit(Stif node); this: " + this);
  }

  default void visit(Elsepart node) {
    throw new RuntimeException("Not implemented: visit(Elsepart node); this: " + this);
  }

  default void visit(Stwhile node) {
    throw new RuntimeException("Not implemented: visit(Stwhile node); this: " + this);
  }

  default void visit(Stprint node) {
    throw new RuntimeException("Not implemented: visit(Stprint node); this: " + this);
  }

  default void visit(Stinput node) {
    throw new RuntimeException("Not implemented: visit(Stinput node); this: " + this);
  }

  default void visit(Strtn node) {
    throw new RuntimeException("Not implemented: visit(Strtn node); this: " + this);
  }

  default void visit(Strtn_Suffix node) {
    throw new RuntimeException("Not implemented: visit(Strtn node); this: " + this);
  }

  default void visit(PPexpr node) {
    throw new RuntimeException("Not implemented: visit(PPexpr node); this: " + this);
  }

  default void visit(Expr node) {
    throw new RuntimeException("Not implemented: visit(Expr node); this: " + this);
  }

  default void visit(Expr_Tail node) {
    throw new RuntimeException("Not implemented: visit(Expr_Tail node); this: " + this);
  }

  default void visit(Rterm node) {
    throw new RuntimeException("Not implemented: visit(Rterm node); this: " + this);
  }

  default void visit(Rterm_Tail node) {
    throw new RuntimeException("Not implemented: visit(Rterm_Tail node); this: " + this);
  }

  default void visit(Term node) {
    throw new RuntimeException("Not implemented: visit(Term node); this: " + this);
  }

  default void visit(Term_Tail node) {
    throw new RuntimeException("Not implemented: visit(Term_Tail node); this: " + this);
  }

  default void visit(Fact node) {
    throw new RuntimeException("Not implemented: visit(Fact node); this: " + this);
  }

  default void visit(BaseLiteral node) {
    throw new RuntimeException("Not implemented: visit(BaseLiteral node); this: " + this);
  }

  default void visit(Addrof_id node) {
    throw new RuntimeException("Not implemented: visit(Addrof_id node); this: " + this);
  }

  default void visit(Oprel node) {
    throw new RuntimeException("Not implemented: visit(Oprel node); this: " + this);
  }

  default void visit(Lthan node) {
    throw new RuntimeException("Not implemented: visit(Lthan node); this: " + this);
  }

  default void visit(Gthan node) {
    throw new RuntimeException("Not implemented: visit(Gthan node); this: " + this);
  }

  default void visit(Opadd node) {
    throw new RuntimeException("Not implemented: visit(Opadd node); this: " + this);
  }

  default void visit(Opmul node) {
    throw new RuntimeException("Not implemented: visit(Opmul node); this: " + this);
  }

  default void visit(Epsilon node) {
    throw new RuntimeException("Not implemented: visit(Epsilon node); this: " + this);
  }
}
