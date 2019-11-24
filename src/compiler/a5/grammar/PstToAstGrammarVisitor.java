package compiler.a5.grammar;

import compiler.lexer.token.KeywordToken;
import compiler.lexer.token.OperatorToken;
import compiler.lexer.token.SymbolToken;
import compiler.parser.AbstractGrammarNode;
import compiler.parser.GrammarNode;

import java.util.ArrayList;

import static compiler.a5.grammar.A5GrammarNonTerminals.*;
import static compiler.parser.PstToAstHelpers.*;

public class PstToAstGrammarVisitor implements GrammarNodeVisitor {
  @Override
  public void visit(Expr tree) {
    if (tree.children.size() == 2) {
      final var left = tree.children.pop();
      final var right = tree.children.peek();
      hoist(tree);
      left.parent = right;
      right.children.addFirst(left);
    }
  }

  @Override
  public void visit(Expr_Tail tree) {
    hoist(tree);
  }

  @Override
  public void visit(Term term) {
    if (term.children.size() == 2) {
      final var lvalue = term.children.get(0);
      final var operator = term.children.get(1);
      term.children.remove(lvalue);
      lvalue.parent = operator;
      operator.children.addFirst(lvalue);
    }
    hoist(term);
  }

  @Override
  public void visit(Term_Tail tree) {
    if (tree.children.size() == 3) {
      final var tail = tree.children.get(2);
      final var tailLValue = tree.children.get(1);
      tailLValue.parent = tail;
      tail.children.addFirst(tailLValue);
      tree.children.remove(tailLValue);
    }
    hoist(tree);
  }

  @Override
  public void visit(Rterm tree) {
    if (tree.children.size() == 2) {
      final var operator = tree.children.get(1);
      final var lvalue = tree.children.get(0);
      lvalue.parent = operator;
      tree.children.remove(lvalue);
      operator.children.addFirst(lvalue);
    }
    hoist(tree);
  }

  @Override
  public void visit(Rterm_Tail tree) {
    if (tree.children.size() == 3) {
      final var tail = tree.children.get(2);
      final var tailLValue = tree.children.get(1);
      tailLValue.parent = tail;
      tail.children.addFirst(tailLValue);
      tree.children.remove(tailLValue);
    }
    hoist(tree);
  }

  @Override
  public void visit(PPexpr ppexpr) {
    ppexpr.children.removeIf(child -> child instanceof SymbolToken.RightParen);
    hoist(ppexpr);
  }

  @Override
  public void visit(Classmom node) {
    hoist(node);
  }

  @Override
  public void visit(GrammarNode node) {

  }

  @Override
  public void visit(Pgm pgm) {
    var fcndefsIndex = -1;
    Fcndefs fcndefs = null;
    for (int i = 0; i < pgm.children.size(); i++) {
      AbstractGrammarNode child = pgm.children.get(i);
      if (child instanceof Fcndefs) {
        fcndefsIndex = i;
        fcndefs = (Fcndefs) child;
        break;
      }
    }

    if (fcndefs != null) {
      for (final var child : fcndefs.children) {
        pgm.children.add(fcndefsIndex++, child);
        child.parent = pgm;
      }
    }
    pgm.children.remove(fcndefs);

    hoist(pgm);
  }

  @Override
  public void visit(Main node) {
    hoist(node);
  }

  @Override
  public void visit(BBlock bblock) {
    bblock.children.removeIf(child -> child instanceof SymbolToken.RightBrace);
    rightContraction(bblock);
    hoist(bblock);
  }

  @Override
  public void visit(Vargroup node) {
    hoist(node);
  }

  @Override
  public void visit(PPvarlist node) {
    node.children.removeIf(child -> child instanceof SymbolToken.RightParen);
    if (node.children.size() == 2) {
      final var varlist = node.children.get(1);
      node.children.remove(varlist);
      varlist.parent = null;

      varlist.children.forEach(node.children::addLast);
      varlist.children.forEach(child -> child.parent = node);
    }
    hoist(node);
  }

  @Override
  public void visit(Varlist node) {
    node.children.removeIf(child -> child instanceof SymbolToken.SemiColon);
    if (node.children.size() == 2) {
      final var otherVarList = node.children.get(1);
      node.children.remove(otherVarList);
      otherVarList.children.forEach(node.children::addLast);
      otherVarList.children.forEach(child -> child.parent = node);
      return;
    }
  }

  @Override
  public void visit(Varitem node) {
    reverseHoist(node);
  }

  @Override
  public void visit(Varitem_Suffix node) {
    hoist(node);
  }

  @Override
  public void visit(Vardecl node) {
    hoist(node);
  }

  @Override
  public void visit(Simplekind node) {

  }

  @Override
  public void visit(BaseKind node) {

  }

  @Override
  public void visit(Varspec node) {
    hoist(node);
  }

  @Override
  public void visit(Varid node) {

  }

  @Override
  public void visit(Arrspec node) {

  }

  @Override
  public void visit(KKint node) {
    node.children.removeIf(child -> child instanceof SymbolToken.RightBracket);
    hoist(node);
  }

  @Override
  public void visit(Deref_id node) {
    hoist(node);
  }

  @Override
  public void visit(Deref node) {

  }

  @Override
  public void visit(Varinit node) {

  }

  @Override
  public void visit(BBexprs node) {

  }

  @Override
  public void visit(Exprlist node) {
    if (!node.children.isEmpty()) {
      rightContraction(node);
    }
  }

  @Override
  public void visit(Moreexprs node) {
    node.children.removeIf(child -> child instanceof SymbolToken.Comma);
    if (!node.children.isEmpty()) {
      rightContraction(node);
    }
  }

  @Override
  public void visit(Classdecl node) {

  }

  @Override
  public void visit(Classdef node) {
    hoist(node);
  }

  @Override
  public void visit(Classdef_Suffix node) {

  }

  @Override
  public void visit(BBClassitems bbClassitems) {
    bbClassitems.children.removeIf(child -> child instanceof SymbolToken.RightBrace);
    rightContraction(bbClassitems);

    final var removables = new ArrayList<AbstractGrammarNode>();
    for (int i = 0; i < bbClassitems.children.size() - 1; i++) {
      final var left = bbClassitems.children.get(i);
      final var right = bbClassitems.children.get(i + 1);
      if (left instanceof SymbolToken.Colon && right instanceof KeywordToken.VarKeywordToken) {
        removables.add(right);
        left.children.addLast(right);
        right.parent = left;
      }
    }

    if (bbClassitems.children.getLast() instanceof Mddecls) {
      rightContraction(bbClassitems);
    }

    bbClassitems.children.removeAll(removables);
    hoist(bbClassitems);
  }

  @Override
  public void visit(Classitems node) {
    if (!node.children.isEmpty() && node.children.getLast() instanceof Classitems)
      rightContraction(node);
  }

  @Override
  public void visit(Classgroup node) {

  }

  @Override
  public void visit(Class_ctrl node) {
    hoist(node);
  }

  @Override
  public void visit(Interfaces node) {
    if (!node.children.isEmpty()) {
      rightContraction(node);
    }

    if (node.parent instanceof Classheader) {
      hoist(node);
    }

    node.children.removeIf(child -> child instanceof OperatorToken.Plus);
  }

  @Override
  public void visit(Mddecls node) {
    if (!node.children.isEmpty())
      rightContraction(node);
  }

  @Override
  public void visit(Mdheader node) {
    hoist(node);
  }

  @Override
  public void visit(Md_id node) {
    node.children.add(1, node.children.removeFirst());
    hoist(node);
  }

  @Override
  public void visit(Fcndefs node) {
  }

  @Override
  public void visit(Fcndef node) {
    hoist(node);
  }

  @Override
  public void visit(Fcnheader node) {
    hoist(node);
  }

  @Override
  public void visit(Fcnid node) {

  }

  @Override
  public void visit(Retkind node) {

  }

  @Override
  public void visit(PParmlist node) {
    node.children.removeIf(child -> child instanceof SymbolToken.RightParen);
    if (!node.children.isEmpty())
      rightContraction(node);
    hoist(node);
  }

  @Override
  public void visit(Varspecs node) {
    if (!node.children.isEmpty())
      rightContraction(node);
  }

  @Override
  public void visit(More_varspecs node) {
    node.children.removeIf(child -> child instanceof SymbolToken.Comma);
    if (!node.children.isEmpty())
      rightContraction(node);
  }

  @Override
  public void visit(PPonly node) {

  }

  @Override
  public void visit(Stmts stmts) {
    stmts.children.removeIf(child -> child instanceof SymbolToken.SemiColon);
    if (!stmts.children.isEmpty())
      rightContraction(stmts);
  }

  @Override
  public void visit(Stmt node) {

  }

  @Override
  public void visit(StasgnOrFcall node) {
    final var discriminant = node.children.getLast();
    if (discriminant instanceof OperatorToken.Equal) {
      reverseHoist(node);
    }

    if (discriminant instanceof SymbolToken.LeftParen) {
      hoist(node);
    }
  }

  @Override
  public void visit(StasgnOrFcall_Suffix node) {

  }

  @Override
  public void visit(Stasgn_Suffix node) {
    hoist(node);
  }

  @Override
  public void visit(Lval_Suffix node) {

  }

  @Override
  public void visit(Lval node) {

  }

  @Override
  public void visit(LvalOrFcall node) {
    hoist(node);
  }

  @Override
  public void visit(LvalOrFcall_Suffix node) {

  }

  @Override
  public void visit(Lval_Tail node) {

  }

  @Override
  public void visit(KKexpr node) {
    node.children.removeIf(child -> child instanceof SymbolToken.RightBracket);
    hoist(node);
  }

  @Override
  public void visit(Fcall node) {

  }

  @Override
  public void visit(PPexprs ppexprs) {
    ppexprs.children.removeIf(child -> child instanceof SymbolToken.RightParen);
    if (!ppexprs.children.isEmpty()) {
      rightContraction(ppexprs);
    }
    hoist(ppexprs);
  }

  @Override
  public void visit(Stif stif) {
    hoist(stif);
  }

  @Override
  public void visit(Elsepart node) {
    hoist(node);
  }

  @Override
  public void visit(Stwhile node) {
    hoist(node);
  }

  @Override
  public void visit(Stprint node) {
    hoist(node);
  }

  @Override
  public void visit(Strtn node) {
    if (node.children.size() <= 1) {
      return;
    }

    final var returnVal = node.children.removeLast();
    final var returnNode = node.children.getFirst();
    returnNode.children.addLast(returnVal);
    returnVal.parent = returnNode;
    hoist(node);
  }

  @Override
  public void visit(Strtn_Suffix node) {

  }

  @Override
  public void visit(Fact node) {

  }

  @Override
  public void visit(BaseLiteral node) {

  }

  @Override
  public void visit(Addrof_id node) {
    hoist(node);
  }

  @Override
  public void visit(Oprel node) {

  }

  @Override
  public void visit(Lthan node) {

  }

  @Override
  public void visit(Gthan node) {

  }

  @Override
  public void visit(Opadd node) {

  }

  @Override
  public void visit(Opmul node) {

  }

  @Override
  public void visit(Epsilon node) {

  }

  @Override
  public void visit(Classheader node) {
    hoist(node);
  }

  @Override
  public void visit(Classid node) {

  }

}
