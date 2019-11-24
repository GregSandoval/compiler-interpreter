package compiler.a5.grammar;

import compiler.lexer.token.FloatToken;
import compiler.lexer.token.IdentifierToken;
import compiler.lexer.token.IntegerToken;
import compiler.lexer.token.KeywordToken.*;
import compiler.lexer.token.StringToken;

import static compiler.a5.grammar.A5GrammarNonTerminals.*;
import static compiler.lexer.token.OperatorToken.*;
import static compiler.lexer.token.SymbolToken.*;

public class A5GrammarRules {

  public static void build() {
    new Pgm()
      .on(ProgramKeywordToken.class)
      .useRHS(ProgramKeywordToken::new, Vargroup::new, Fcndefs::new, Main::new);
    new Main()
      .on(MainKeywordToken.class)
      .useRHS(MainKeywordToken::new, BBlock::new);
    new BBlock()
      .on(LeftBrace.class)
      .useRHS(LeftBrace::new, Vargroup::new, Stmts::new, RightBrace::new);

    new Vargroup()
      .on(VarKeywordToken.class)
      .useRHS(VarKeywordToken::new, PPvarlist::new)
      .on(
        FunctionKeywordToken.class,
        MainKeywordToken.class,
        Asterisk.class,
        IdentifierToken.class,
        IfKeywordToken.class,
        WhileKeywordToken.class,
        PrintKeywordToken.class,
        ReturnKeywordToken.class,
        RightBrace.class,
        Colon.class
        // VarKeywordToken.class
      )
      .useRHS();
    new PPvarlist()
      .on(LeftParen.class)
      .useRHS(LeftParen::new, Varlist::new, RightParen::new);
    new Varlist()
      .on(
        IntegerKeywordToken.class,
        FloatKeywordToken.class,
        StringKeywordToken.class,
        IdentifierToken.class,
        ClassKeywordToken.class
      )
      .useRHS(Varitem::new, SemiColon::new, Varlist::new)
      .on(RightParen.class)
      .useRHS();
    new Varitem()
      .on(
        IntegerKeywordToken.class,
        FloatKeywordToken.class,
        StringKeywordToken.class,
        IdentifierToken.class
      )
      .useRHS(Vardecl::new, Varitem_Suffix::new)
      .on(ClassKeywordToken.class)
      .useRHS(Classdef::new);
    new Varitem_Suffix()
      .on(Equal.class)
      .useRHS(Equal::new, Varinit::new)
      .on(SemiColon.class)
      .useRHS();
    new Vardecl()
      .on(
        IntegerKeywordToken.class,
        FloatKeywordToken.class,
        StringKeywordToken.class,
        IdentifierToken.class
      )
      .useRHS(Simplekind::new, Varspec::new);
    new Simplekind()
      .on(
        IntegerKeywordToken.class,
        FloatKeywordToken.class,
        StringKeywordToken.class
      )
      .useRHS(BaseKind::new)
      .on(IdentifierToken.class)
      .useRHS(Classid::new);
    new BaseKind()
      .on(IntegerKeywordToken.class)
      .useRHS(IntegerKeywordToken::new)
      .on(FloatKeywordToken.class)
      .useRHS(FloatKeywordToken::new)
      .on(StringKeywordToken.class)
      .useRHS(StringKeywordToken::new);
    new Classid()
      .on(IdentifierToken.class)
      .useRHS(IdentifierToken::getSentinel);
    new Varspec()
      .on(IdentifierToken.class)
      .useRHS(Varid::new, Arrspec::new)
      .on(Asterisk.class)
      .useRHS(Deref_id::new);
    new Varid()
      .on(IdentifierToken.class)
      .useRHS(IdentifierToken::getSentinel);
    new Arrspec()
      .on(LeftBracket.class)
      .useRHS(KKint::new)
      .on(
        Equal.class,
        SemiColon.class,
        Comma.class,
        RightParen.class
      )
      .useRHS();
    new KKint()
      .on(LeftBracket.class)
      .useRHS(LeftBracket::new, IntegerToken::getSentinel, RightBracket::new);
    new Deref_id()
      .on(Asterisk.class)
      .useRHS(Deref::new, IdentifierToken::getSentinel);
    new Deref()
      .on(Asterisk.class)
      .useRHS(Asterisk::new);


    new Varinit()
      .on(
        IntegerToken.class,
        FloatToken.class,
        StringToken.class,
        Asterisk.class,
        IdentifierToken.class,
        Ampersand.class,
        LeftParen.class
      )
      .useRHS(Expr::new)
      .on(
        LeftBrace.class
      )
      .useRHS(BBexprs::new);
    new BBexprs()
      .on(LeftBrace.class)
      .useRHS(LeftBrace::new, Exprlist::new, RightBrace::new);
    new Exprlist()
      .on(
        IntegerToken.class,
        FloatToken.class,
        StringToken.class,
        Asterisk.class,
        IdentifierToken.class,
        Ampersand.class,
        LeftParen.class
      )
      .useRHS(Expr::new, Moreexprs::new)
      .on(
        RightBrace.class,
        RightParen.class
      )
      .useRHS();
    new Moreexprs()
      .on(Comma.class)
      .useRHS(Comma::new, Exprlist::new)
      .on(
        RightBrace.class,
        RightParen.class
      )
      .useRHS();


    new Classdef()
      .on(ClassKeywordToken.class)
      .useRHS(Classheader::new, Classdef_Suffix::new);
    new Classdef_Suffix()
      .on(LeftBrace.class)
      .useRHS(BBClassitems::new)
      .on(IfKeywordToken.class)
      .useRHS(IfKeywordToken::new, BBClassitems::new);
    new BBClassitems()
      .on(LeftBrace.class)
      .useRHS(LeftBrace::new, Classitems::new, RightBrace::new);
    new Classheader()
      .on(ClassKeywordToken.class)
      .useRHS(ClassKeywordToken::new, Classid::new, Classmom::new, Interfaces::new);
    new Classmom()
      .on(Colon.class)
      .useRHS(Colon::new, Classid::new)
      .on(
        Plus.class,
        LeftBrace.class,
        IfKeywordToken.class
      )
      .useRHS();
    new Classitems()
      .on(Colon.class, VarKeywordToken.class, FunctionKeywordToken.class)
      .useRHS(Classgroup::new, Classitems::new)
      .on(RightBrace.class)
      .useRHS();
    new Classgroup()
      .on(Colon.class)
      .useRHS(Class_ctrl::new)
      .on(VarKeywordToken.class)
      .useRHS(Vargroup::new)
      .on(FunctionKeywordToken.class)
      .useRHS(Mddecls::new);
    new Class_ctrl()
      .on(Colon.class)
      .useRHS(Colon::new, IdentifierToken::getSentinel);
    new Interfaces()
      .on(Plus.class)
      .useRHS(Plus::new, Classid::new, Interfaces::new)
      .on(LeftBrace.class, IfKeywordToken.class)
      .useRHS();


    new Mddecls()
      .on(FunctionKeywordToken.class)
      .useRHS(Mdheader::new, Mddecls::new)
      .on(
        SemiColon.class,
        VarKeywordToken.class,
        //FunctionKeywordToken.class, Mistake?
        RightBrace.class
      )
      .useRHS();
    new Mdheader()
      .on(FunctionKeywordToken.class)
      .useRHS(FunctionKeywordToken::new, Md_id::new, PParmlist::new, Retkind::new);
    new Md_id()
      .on(IdentifierToken.class)
      .useRHS(Classid::new, Colon::new, Fcnid::new);

    new Fcndefs()
      .on(FunctionKeywordToken.class)
      .useRHS(Fcndef::new, Fcndefs::new)
      .on(MainKeywordToken.class)
      .useRHS();
    new Fcndef()
      .on(FunctionKeywordToken.class)
      .useRHS(Fcnheader::new, BBlock::new);
    new Fcnheader()
      .on(FunctionKeywordToken.class)
      .useRHS(FunctionKeywordToken::new, Fcnid::new, PParmlist::new, Retkind::new);
    new Fcnid()
      .on(IdentifierToken.class)
      .useRHS(IdentifierToken::getSentinel);
    new Retkind()
      .on(
        IntegerKeywordToken.class,
        FloatKeywordToken.class,
        StringKeywordToken.class
      )
      .useRHS(BaseKind::new);
    new PParmlist()
      .on(LeftParen.class)
      .useRHS(LeftParen::new, Varspecs::new, RightParen::new);
    new Varspecs()
      .on(Asterisk.class, IdentifierToken.class)
      .useRHS(Varspec::new, More_varspecs::new)
      .on(RightParen.class)
      .useRHS();
    new More_varspecs()
      .on(Comma.class)
      .useRHS(Comma::new, Varspecs::new)
      .on(RightParen.class)
      .useRHS();


    new Stmts()
      .on(
        Asterisk.class,
        IdentifierToken.class,
        IfKeywordToken.class,
        WhileKeywordToken.class,
        PrintKeywordToken.class,
        ReturnKeywordToken.class
      )
      .useRHS(Stmt::new, SemiColon::new, Stmts::new)
      .on(RightBrace.class)
      .useRHS();
    new Stmt()
      .on(IdentifierToken.class, Asterisk.class)
      .useRHS(StasgnOrFcall::new)
      .on(IfKeywordToken.class)
      .useRHS(Stif::new)
      .on(WhileKeywordToken.class)
      .useRHS(Stwhile::new)
      .on(PrintKeywordToken.class)
      .useRHS(Stprint::new)
      .on(ReturnKeywordToken.class)
      .useRHS(Strtn::new);


    new StasgnOrFcall()
      .on(Asterisk.class)
      .useRHS(Deref_id::new, Stasgn_Suffix::new)
      .on(IdentifierToken.class)
      .useRHS(IdentifierToken::getSentinel, StasgnOrFcall_Suffix::new);
    new StasgnOrFcall_Suffix()
      .on(LeftBracket.class, Equal.class)
      .useRHS(Lval_Suffix::new, Stasgn_Suffix::new)
      .on(LeftParen.class)
      .useRHS(PPexprs::new);
    new Stasgn_Suffix()
      .on(Equal.class)
      .useRHS(Equal::new, Expr::new);
    new Lval_Suffix()
      .on(LeftBracket.class)
      .useRHS(KKexpr::new)
      .on(
        Equal.class,
        Asterisk.class,
        ForwardSlash.class,
        Caret.class,
        Plus.class,
        Minus.class,
        EqualEqual.class,
        NotEqual.class,
        LessThan.class,
        LessThanOrEqual.class,
        GreaterThanOrEqual.class,
        GreaterThan.class,
        RightBrace.class,
        RightBracket.class,
        SemiColon.class,
        RightParen.class,
        Comma.class
      ).useRHS();
    new KKexpr()
      .on(LeftBracket.class)
      .useRHS(LeftBracket::new, Expr::new, RightBracket::new);


    new PPexprs()
      .on(LeftParen.class)
      .useRHS(LeftParen::new, Exprlist::new, RightParen::new)
      .on()
      .useRHS(PPonly::new);


    new Stif()
      .on(IfKeywordToken.class)
      .useRHS(IfKeywordToken::new, PPexpr::new, BBlock::new, Elsepart::new);
    new Elsepart()
      .on(ElseIfKeywordToken.class)
      .useRHS(ElseIfKeywordToken::new, PPexpr::new, BBlock::new, Elsepart::new)
      .on(ElseKeywordToken.class)
      .useRHS(ElseKeywordToken::new, BBlock::new)
      .on(SemiColon.class)
      .useRHS();


    new Stwhile()
      .on(WhileKeywordToken.class)
      .useRHS(WhileKeywordToken::new, PPexpr::new, BBlock::new);
    new Stprint()
      .on(PrintKeywordToken.class)
      .useRHS(PrintKeywordToken::new, PPexprs::new);
    new Strtn()
      .on(ReturnKeywordToken.class)
      .useRHS(ReturnKeywordToken::new, Strtn_Suffix::new);
    new Strtn_Suffix()
      .on(IntegerToken.class, FloatToken.class, StringToken.class, IdentifierToken.class, Asterisk.class, Ampersand.class, LeftParen.class)
      .useRHS(Expr::new)
      .on(SemiColon.class)
      .useRHS();


    new PPexpr()
      .on(LeftParen.class)
      .useRHS(LeftParen::new, Expr::new, RightParen::new);
    new Expr()
      .on(IntegerToken.class, FloatToken.class, StringToken.class, IdentifierToken.class, Asterisk.class, Ampersand.class, LeftParen.class)
      .useRHS(Rterm::new, Expr_Tail::new);
    new Expr_Tail()
      .on(EqualEqual.class, NotEqual.class, LessThan.class, LessThanOrEqual.class, GreaterThanOrEqual.class, GreaterThan.class)
      .useRHS(Oprel::new, Rterm::new, Expr_Tail::new)
      .on(RightBrace.class, RightBracket.class, SemiColon.class, RightParen.class, Comma.class)
      .useRHS();
    new Rterm()
      .on(IntegerToken.class, FloatToken.class, StringToken.class, IdentifierToken.class, Asterisk.class, Ampersand.class, LeftParen.class)
      .useRHS(Term::new, Rterm_Tail::new);
    new Rterm_Tail()
      .on(Plus.class, Minus.class)
      .useRHS(Opadd::new, Term::new, Rterm_Tail::new)
      .on(
        EqualEqual.class,
        NotEqual.class,
        LessThan.class,
        LessThanOrEqual.class,
        GreaterThanOrEqual.class,
        GreaterThan.class,
        RightBrace.class,
        RightBracket.class,
        SemiColon.class,
        Comma.class,
        RightParen.class
      )
      .useRHS();
    new Term()
      .on(IntegerToken.class, FloatToken.class, StringToken.class, IdentifierToken.class, Asterisk.class, Ampersand.class, LeftParen.class)
      .useRHS(Fact::new, Term_Tail::new);
    new Term_Tail()
      .on(Asterisk.class, ForwardSlash.class, Caret.class)
      .useRHS(Opmul::new, Fact::new, Term_Tail::new)
      .on(EqualEqual.class, NotEqual.class, LessThan.class, LessThanOrEqual.class, GreaterThanOrEqual.class, GreaterThan.class, RightBrace.class, RightBracket.class, SemiColon.class, Comma.class, RightParen.class, Plus.class, Minus.class)
      .useRHS();
    new Fact()
      .on(IntegerToken.class, FloatToken.class, StringToken.class)
      .useRHS(BaseLiteral::new)
      .on(IdentifierToken.class, Asterisk.class)
      .useRHS(LvalOrFcall::new)
      .on(Ampersand.class)
      .useRHS(Addrof_id::new)
      .on(LeftParen.class)
      .useRHS(PPexpr::new);
    new LvalOrFcall()
      .on(Asterisk.class)
      .useRHS(Deref_id::new)
      .on(IdentifierToken.class)
      .useRHS(IdentifierToken::getSentinel, LvalOrFcall_Suffix::new);
    new LvalOrFcall_Suffix()
      .on(LeftBracket.class)
      .useRHS(Lval_Suffix::new)
      .on(LeftParen.class)
      .useRHS(PPexprs::new)
      .on(
        Asterisk.class,
        ForwardSlash.class,
        Caret.class,
        Plus.class,
        Minus.class,
        EqualEqual.class,
        NotEqual.class,
        LessThan.class,
        LessThanOrEqual.class,
        GreaterThan.class,
        GreaterThanOrEqual.class,
        RightBrace.class,
        RightBracket.class,
        SemiColon.class,
        RightParen.class,
        Comma.class
      )
      .useRHS();
    new BaseLiteral()
      .on(IntegerToken.class)
      .useRHS(IntegerToken::getSentinel)
      .on(FloatToken.class)
      .useRHS(FloatToken::getSentinel)
      .on(StringToken.class)
      .useRHS(StringToken::getSentinel);
    new Addrof_id()
      .on(Ampersand.class)
      .useRHS(Ampersand::new, IdentifierToken::getSentinel);
    new Oprel()
      .on(EqualEqual.class)
      .useRHS(EqualEqual::new)
      .on(NotEqual.class)
      .useRHS(NotEqual::new)
      .on(LessThan.class)
      .useRHS(Lthan::new)
      .on(LessThanOrEqual.class)
      .useRHS(LessThanOrEqual::new)
      .on(GreaterThanOrEqual.class)
      .useRHS(GreaterThanOrEqual::new)
      .on(GreaterThan.class)
      .useRHS(Gthan::new);
    new Lthan()
      .on(LessThan.class)
      .useRHS(LessThan::new);
    new Gthan()
      .on(GreaterThan.class)
      .useRHS(GreaterThan::new);
    new Opadd()
      .on(Plus.class)
      .useRHS(Plus::new)
      .on(Minus.class)
      .useRHS(Minus::new);
    new Opmul()
      .on(Asterisk.class)
      .useRHS(Asterisk::new)
      .on(ForwardSlash.class)
      .useRHS(ForwardSlash::new)
      .on(Caret.class)
      .useRHS(Caret::new);
  }
}
