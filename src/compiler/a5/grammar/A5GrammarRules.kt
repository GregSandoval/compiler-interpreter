package compiler.a5.grammar

import compiler.parser.Symbol.NonTerminal.*
import compiler.parser.Symbol.Terminal.Keyword.*
import compiler.parser.Symbol.Terminal.Keyword.Type.*
import compiler.parser.Symbol.Terminal.Operator.*
import compiler.parser.Symbol.Terminal.Punctuation.*
import compiler.parser.Symbol.Terminal.TypedTerminal.*

object A5GrammarRules {
    fun build() {
        Pgm()
                .on(ProgramKeyword::class.java)
                .useRHS(::ProgramKeyword, ::Vargroup, ::Fcndefs, ::Main)
        Main()
                .on(MainKeyword::class.java)
                .useRHS(::MainKeyword, ::BBlock)
        BBlock()
                .on(LeftBrace::class.java)
                .useRHS(::LeftBrace, ::Vargroup, ::Stmts, ::RightBrace)
        Vargroup()
                .on(VarKeyword::class.java)
                .useRHS(::VarKeyword, ::PPvarlist)
                .on(
                        FunctionKeyword::class.java,
                        MainKeyword::class.java,
                        Asterisk::class.java,
                        IdentifierTerminal::class.java,
                        IfKeyword::class.java,
                        WhileKeyword::class.java,
                        PrintKeyword::class.java,
                        InputKeyword::class.java,
                        ReturnKeyword::class.java,
                        RightBrace::class.java,
                        Colon::class.java
                )
                .useRHS()
        PPvarlist()
                .on(LeftParen::class.java)
                .useRHS(::LeftParen, ::Varlist, ::RightParen)
        Varlist()
                .on(
                        IntegerKeyword::class.java,
                        FloatKeyword::class.java,
                        StringKeyword::class.java,
                        IdentifierTerminal::class.java,
                        ClassKeyword::class.java
                )
                .useRHS(::Varitem, ::SemiColon, ::Varlist)
                .on(RightParen::class.java)
                .useRHS()
        Varitem()
                .on(
                        IntegerKeyword::class.java,
                        FloatKeyword::class.java,
                        StringKeyword::class.java,
                        IdentifierTerminal::class.java
                )
                .useRHS(::Vardecl, ::Varitem_Suffix)
                .on(ClassKeyword::class.java)
                .useRHS(::Classdef)
        Varitem_Suffix()
                .on(Equal::class.java)
                .useRHS(::Equal, ::Varinit)
                .on(SemiColon::class.java)
                .useRHS()
        Vardecl()
                .on(
                        IntegerKeyword::class.java,
                        FloatKeyword::class.java,
                        StringKeyword::class.java,
                        IdentifierTerminal::class.java
                )
                .useRHS(::Simplekind, ::Varspec)
        Simplekind()
                .on(
                        IntegerKeyword::class.java,
                        FloatKeyword::class.java,
                        StringKeyword::class.java
                )
                .useRHS(::BaseKind)
                .on(IdentifierTerminal::class.java)
                .useRHS(::Classid)
        BaseKind()
                .on(IntegerKeyword::class.java)
                .useRHS(::IntegerKeyword)
                .on(FloatKeyword::class.java)
                .useRHS(::FloatKeyword)
                .on(StringKeyword::class.java)
                .useRHS(::StringKeyword)
        Classid()
                .on(IdentifierTerminal::class.java)
                .useRHS(::IdentifierTerminal)
        Varspec()
                .on(IdentifierTerminal::class.java)
                .useRHS(::Varid, ::Arrspec)
                .on(Asterisk::class.java)
                .useRHS(::Deref_id)
        Varid()
                .on(IdentifierTerminal::class.java)
                .useRHS(::IdentifierTerminal)
        Arrspec()
                .on(LeftBracket::class.java)
                .useRHS(::KKint)
                .on(
                        Equal::class.java,
                        SemiColon::class.java,
                        Comma::class.java,
                        RightParen::class.java
                )
                .useRHS()
        KKint()
                .on(LeftBracket::class.java)
                .useRHS(::LeftBracket, ::IntegerTerminal, ::RightBracket)
        Deref_id()
                .on(Asterisk::class.java)
                .useRHS(::Deref, ::IdentifierTerminal)
        Deref()
                .on(Asterisk::class.java)
                .useRHS(::Asterisk)
        Varinit()
                .on(
                        IntegerTerminal::class.java,
                        FloatTerminal::class.java,
                        StringTerminal::class.java,
                        Asterisk::class.java,
                        IdentifierTerminal::class.java,
                        Ampersand::class.java,
                        LeftParen::class.java
                )
                .useRHS(::Expr)
                .on(LeftBrace::class.java)
                .useRHS(::BBexprs)
        BBexprs()
                .on(LeftBrace::class.java)
                .useRHS(::LeftBrace, ::Exprlist, ::RightBrace)
        Exprlist()
                .on(
                        IntegerTerminal::class.java,
                        FloatTerminal::class.java,
                        StringTerminal::class.java,
                        Asterisk::class.java,
                        IdentifierTerminal::class.java,
                        Ampersand::class.java,
                        LeftParen::class.java
                )
                .useRHS(::Expr, ::Moreexprs)
                .on(
                        RightBrace::class.java,
                        RightParen::class.java
                )
                .useRHS()
        Moreexprs()
                .on(Comma::class.java)
                .useRHS(::Comma, ::Exprlist)
                .on(
                        RightBrace::class.java,
                        RightParen::class.java
                )
                .useRHS()
        Classdef()
                .on(ClassKeyword::class.java)
                .useRHS(::Classheader, ::Classdef_Suffix)
        Classdef_Suffix()
                .on(LeftBrace::class.java)
                .useRHS(::BBClassitems)
                .on(IfKeyword::class.java)
                .useRHS(::IfKeyword, ::BBClassitems)
        BBClassitems()
                .on(LeftBrace::class.java)
                .useRHS(::LeftBrace, ::Classitems, ::RightBrace)
        Classheader()
                .on(ClassKeyword::class.java)
                .useRHS(::ClassKeyword, ::Classid, ::Classmom, ::Interfaces)
        Classmom()
                .on(Colon::class.java)
                .useRHS(::Colon, ::Classid)
                .on(
                        Plus::class.java,
                        LeftBrace::class.java,
                        IfKeyword::class.java
                )
                .useRHS()
        Classitems()
                .on(Colon::class.java, VarKeyword::class.java, FunctionKeyword::class.java)
                .useRHS(::Classgroup, ::Classitems)
                .on(RightBrace::class.java)
                .useRHS()
        Classgroup()
                .on(Colon::class.java)
                .useRHS(::Class_ctrl)
                .on(VarKeyword::class.java)
                .useRHS(::Vargroup)
                .on(FunctionKeyword::class.java)
                .useRHS(::Mddecls)
        Class_ctrl()
                .on(Colon::class.java)
                .useRHS(::Colon, ::IdentifierTerminal)
        Interfaces()
                .on(Plus::class.java)
                .useRHS(::Plus, ::Classid, ::Interfaces)
                .on(LeftBrace::class.java, IfKeyword::class.java)
                .useRHS()
        Mddecls()
                .on(FunctionKeyword::class.java)
                .useRHS(::Mdheader, ::Mddecls)
                .on(
                        SemiColon::class.java,
                        VarKeyword::class.java,
                        RightBrace::class.java
                )
                .useRHS()
        Mdheader()
                .on(FunctionKeyword::class.java)
                .useRHS(::FunctionKeyword, ::Md_id, ::PParmlist, ::Retkind)
        Md_id()
                .on(IdentifierTerminal::class.java)
                .useRHS(::Classid, ::Colon, ::Fcnid)
        Fcndefs()
                .on(FunctionKeyword::class.java)
                .useRHS(::Fcndef, ::Fcndefs)
                .on(MainKeyword::class.java)
                .useRHS()
        Fcndef()
                .on(FunctionKeyword::class.java)
                .useRHS(::Fcnheader, ::BBlock)
        Fcnheader()
                .on(FunctionKeyword::class.java)
                .useRHS(::FunctionKeyword, ::Fcnid, ::PParmlist, ::Retkind)
        Fcnid()
                .on(IdentifierTerminal::class.java)
                .useRHS(::IdentifierTerminal)
        Retkind()
                .on(
                        IntegerKeyword::class.java,
                        FloatKeyword::class.java,
                        StringKeyword::class.java
                )
                .useRHS(::BaseKind)
        PParmlist()
                .on(LeftParen::class.java)
                .useRHS(::LeftParen, ::Varspecs, ::RightParen)
        Varspecs()
                .on(Asterisk::class.java, IdentifierTerminal::class.java)
                .useRHS(::Varspec, ::More_varspecs)
                .on(RightParen::class.java)
                .useRHS()
        More_varspecs()
                .on(Comma::class.java)
                .useRHS(::Comma, ::Varspecs)
                .on(RightParen::class.java)
                .useRHS()
        Stmts()
                .on(
                        Asterisk::class.java,
                        IdentifierTerminal::class.java,
                        IfKeyword::class.java,
                        WhileKeyword::class.java,
                        PrintKeyword::class.java,
                        InputKeyword::class.java,
                        ReturnKeyword::class.java
                )
                .useRHS(::Stmt, ::SemiColon, ::Stmts)
                .on(RightBrace::class.java)
                .useRHS()
        Stmt()
                .on(IdentifierTerminal::class.java, Asterisk::class.java)
                .useRHS(::StasgnOrFcall)
                .on(IfKeyword::class.java)
                .useRHS(::Stif)
                .on(WhileKeyword::class.java)
                .useRHS(::Stwhile)
                .on(PrintKeyword::class.java)
                .useRHS(::Stprint)
                .on(InputKeyword::class.java)
                .useRHS(::Stinput)
                .on(ReturnKeyword::class.java)
                .useRHS(::Strtn)
        StasgnOrFcall()
                .on(Asterisk::class.java)
                .useRHS(::Deref_id, ::Stasgn_Suffix)
                .on(IdentifierTerminal::class.java)
                .useRHS(::IdentifierTerminal, ::StasgnOrFcall_Suffix)
        StasgnOrFcall_Suffix()
                .on(LeftBracket::class.java, Equal::class.java)
                .useRHS(::Lval_Suffix, ::Stasgn_Suffix)
                .on(LeftParen::class.java)
                .useRHS(::PPexprs)
        Stasgn_Suffix()
                .on(Equal::class.java)
                .useRHS(::Equal, ::Expr)
        Lval_Suffix()
                .on(LeftBracket::class.java)
                .useRHS(::KKexpr)
                .on(
                        Equal::class.java,
                        Asterisk::class.java,
                        ForwardSlash::class.java,
                        Caret::class.java,
                        Plus::class.java,
                        Minus::class.java,
                        EqualEqual::class.java,
                        NotEqual::class.java,
                        LessThan::class.java,
                        LessThanOrEqual::class.java,
                        GreaterThanOrEqual::class.java,
                        GreaterThan::class.java,
                        RightBrace::class.java,
                        RightBracket::class.java,
                        SemiColon::class.java,
                        RightParen::class.java,
                        Comma::class.java
                ).useRHS()
        KKexpr()
                .on(LeftBracket::class.java)
                .useRHS(::LeftBracket, ::Expr, ::RightBracket)
        PPexprs()
                .on(LeftParen::class.java)
                .useRHS(::LeftParen, ::Exprlist, ::RightParen)
                .on()
                .useRHS(::PPonly)
        Stif()
                .on(IfKeyword::class.java)
                .useRHS(::IfKeyword, ::PPexpr, ::BBlock, ::Elsepart)
        Elsepart()
                .on(ElseIfKeyword::class.java)
                .useRHS(::ElseIfKeyword, ::PPexpr, ::BBlock, ::Elsepart)
                .on(ElseKeyword::class.java)
                .useRHS(::ElseKeyword, ::BBlock)
                .on(SemiColon::class.java)
                .useRHS()
        Stwhile()
                .on(WhileKeyword::class.java)
                .useRHS(::WhileKeyword, ::PPexpr, ::BBlock)
        Stprint()
                .on(PrintKeyword::class.java)
                .useRHS(::PrintKeyword, ::PPexprs)
        Stinput()
                .on(InputKeyword::class.java)
                .useRHS(::InputKeyword, ::PPexprs)
        Strtn()
                .on(ReturnKeyword::class.java)
                .useRHS(::ReturnKeyword, ::Strtn_Suffix)
        Strtn_Suffix()
                .on(
                        IntegerTerminal::class.java,
                        FloatTerminal::class.java,
                        StringTerminal::class.java,
                        IdentifierTerminal::class.java,
                        Asterisk::class.java,
                        Ampersand::class.java,
                        LeftParen::class.java
                )
                .useRHS(::Expr)
                .on(SemiColon::class.java)
                .useRHS()
        PPexpr()
                .on(LeftParen::class.java)
                .useRHS(::LeftParen, ::Expr, ::RightParen)
        Expr()
                .on(
                        IntegerTerminal::class.java,
                        FloatTerminal::class.java,
                        StringTerminal::class.java,
                        IdentifierTerminal::class.java,
                        Asterisk::class.java,
                        Ampersand::class.java,
                        LeftParen::class.java,
                        InputKeyword::class.java
                )
                .useRHS(::Rterm, ::Expr_Tail)
        Expr_Tail()
                .on(EqualEqual::class.java, NotEqual::class.java, LessThan::class.java, LessThanOrEqual::class.java, GreaterThanOrEqual::class.java, GreaterThan::class.java)
                .useRHS(::Oprel, ::Rterm, ::Expr_Tail)
                .on(RightBrace::class.java, RightBracket::class.java, SemiColon::class.java, RightParen::class.java, Comma::class.java)
                .useRHS()
        Rterm()
                .on(
                        IntegerTerminal::class.java,
                        FloatTerminal::class.java,
                        StringTerminal::class.java,
                        IdentifierTerminal::class.java,
                        Asterisk::class.java,
                        Ampersand::class.java,
                        LeftParen::class.java,
                        InputKeyword::class.java
                )
                .useRHS(::Term, ::Rterm_Tail)
        Rterm_Tail()
                .on(Plus::class.java, Minus::class.java)
                .useRHS(::Opadd, ::Term, ::Rterm_Tail)
                .on(
                        EqualEqual::class.java,
                        NotEqual::class.java,
                        LessThan::class.java,
                        LessThanOrEqual::class.java,
                        GreaterThanOrEqual::class.java,
                        GreaterThan::class.java,
                        RightBrace::class.java,
                        RightBracket::class.java,
                        SemiColon::class.java,
                        Comma::class.java,
                        RightParen::class.java
                )
                .useRHS()
        Term()
                .on(
                        IntegerTerminal::class.java,
                        FloatTerminal::class.java,
                        StringTerminal::class.java,
                        IdentifierTerminal::class.java,
                        Asterisk::class.java,
                        Ampersand::class.java,
                        LeftParen::class.java,
                        InputKeyword::class.java
                )
                .useRHS(::Fact, ::Term_Tail)
        Term_Tail()
                .on(Asterisk::class.java, ForwardSlash::class.java, Caret::class.java)
                .useRHS(::Opmul, ::Fact, ::Term_Tail)
                .on(
                        EqualEqual::class.java,
                        NotEqual::class.java,
                        LessThan::class.java,
                        LessThanOrEqual::class.java,
                        GreaterThanOrEqual::class.java,
                        GreaterThan::class.java,
                        RightBrace::class.java,
                        RightBracket::class.java,
                        SemiColon::class.java,
                        Comma::class.java,
                        RightParen::class.java,
                        Plus::class.java,
                        Minus::class.java
                )
                .useRHS()
        Fact()
                .on(IntegerTerminal::class.java, FloatTerminal::class.java, StringTerminal::class.java)
                .useRHS(::BaseLiteral)
                .on(IdentifierTerminal::class.java, Asterisk::class.java)
                .useRHS(::LvalOrFcall)
                .on(Ampersand::class.java)
                .useRHS(::Addrof_id)
                .on(LeftParen::class.java)
                .useRHS(::PPexpr)
                .on(InputKeyword::class.java)
                .useRHS(::Stinput)
        LvalOrFcall()
                .on(Asterisk::class.java)
                .useRHS(::Deref_id)
                .on(IdentifierTerminal::class.java)
                .useRHS(::IdentifierTerminal, ::LvalOrFcall_Suffix)
        LvalOrFcall_Suffix()
                .on(LeftBracket::class.java)
                .useRHS(::Lval_Suffix)
                .on(LeftParen::class.java)
                .useRHS(::PPexprs)
                .on(
                        Asterisk::class.java,
                        ForwardSlash::class.java,
                        Caret::class.java,
                        Plus::class.java,
                        Minus::class.java,
                        EqualEqual::class.java,
                        NotEqual::class.java,
                        LessThan::class.java,
                        LessThanOrEqual::class.java,
                        GreaterThan::class.java,
                        GreaterThanOrEqual::class.java,
                        RightBrace::class.java,
                        RightBracket::class.java,
                        SemiColon::class.java,
                        RightParen::class.java,
                        Comma::class.java
                )
                .useRHS()
        BaseLiteral()
                .on(IntegerTerminal::class.java)
                .useRHS(::IntegerTerminal)
                .on(FloatTerminal::class.java)
                .useRHS(::FloatTerminal)
                .on(StringTerminal::class.java)
                .useRHS(::StringTerminal)
        Addrof_id()
                .on(Ampersand::class.java)
                .useRHS(::Ampersand, ::IdentifierTerminal)
        Oprel()
                .on(EqualEqual::class.java)
                .useRHS(::EqualEqual)
                .on(NotEqual::class.java)
                .useRHS(::NotEqual)
                .on(LessThan::class.java)
                .useRHS(::Lthan)
                .on(LessThanOrEqual::class.java)
                .useRHS(::LessThanOrEqual)
                .on(GreaterThanOrEqual::class.java)
                .useRHS(::GreaterThanOrEqual)
                .on(GreaterThan::class.java)
                .useRHS(::Gthan)
        Lthan()
                .on(LessThan::class.java)
                .useRHS(::LessThan)
        Gthan()
                .on(GreaterThan::class.java)
                .useRHS(::GreaterThan)
        Opadd()
                .on(Plus::class.java)
                .useRHS(::Plus)
                .on(Minus::class.java)
                .useRHS(::Minus)
        Opmul()
                .on(Asterisk::class.java)
                .useRHS(::Asterisk)
                .on(ForwardSlash::class.java)
                .useRHS(::ForwardSlash)
                .on(Caret::class.java)
                .useRHS(::Caret)
    }
}
