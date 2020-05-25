package compiler.a5.grammar

import compiler.parser.LLTable
import compiler.parser.Symbol.NonTerminal.*
import compiler.parser.Symbol.Terminal.Keyword.*
import compiler.parser.Symbol.Terminal.Keyword.Type.*
import compiler.parser.Symbol.Terminal.Operator.*
import compiler.parser.Symbol.Terminal.Punctuation.*
import compiler.parser.Symbol.Terminal.TypedTerminal.*

object A5GrammarRules {
    fun build(): LLTable {
        val llTable = LLTable()

        llTable
                .on(Pgm(), ProgramKeyword::class)
                .useRHS(::ProgramKeyword, ::Vargroup, ::Fcndefs, ::Main)
        llTable
                .on(Main(), MainKeyword::class)
                .useRHS(::MainKeyword, ::BBlock)
        llTable
                .on(BBlock(), LeftBrace::class)
                .useRHS(::LeftBrace, ::Vargroup, ::Stmts, ::RightBrace)
        llTable
                .on(Vargroup(), VarKeyword::class)
                .useRHS(::VarKeyword, ::PPvarlist)
                .on(
                        Vargroup(),
                        FunctionKeyword::class,
                        MainKeyword::class,
                        Asterisk::class,
                        IdentifierTerminal::class,
                        IfKeyword::class,
                        WhileKeyword::class,
                        PrintKeyword::class,
                        InputKeyword::class,
                        ReturnKeyword::class,
                        RightBrace::class,
                        Colon::class
                )
                .useRHS()
        llTable
                .on(PPvarlist(), LeftParen::class)
                .useRHS(::LeftParen, ::Varlist, ::RightParen)
        llTable
                .on(
                        Varlist(), IntegerKeyword::class,
                        FloatKeyword::class,
                        StringKeyword::class,
                        IdentifierTerminal::class,
                        ClassKeyword::class
                )
                .useRHS(::Varitem, ::SemiColon, ::Varlist)
                .on(Varlist(), RightParen::class)
                .useRHS()
        llTable
                .on(
                        Varitem(), IntegerKeyword::class,
                        FloatKeyword::class,
                        StringKeyword::class,
                        IdentifierTerminal::class
                )
                .useRHS(::Vardecl, ::Varitem_Suffix)
                .on(Varitem(), ClassKeyword::class)
                .useRHS(::Classdef)
        llTable
                .on(Varitem_Suffix(), Equal::class)
                .useRHS(::Equal, ::Varinit)
                .on(Varitem_Suffix(), SemiColon::class)
                .useRHS()
        llTable
                .on(
                        Vardecl(), IntegerKeyword::class,
                        FloatKeyword::class,
                        StringKeyword::class,
                        IdentifierTerminal::class
                )
                .useRHS(::Simplekind, ::Varspec)
        llTable
                .on(
                        Simplekind(), IntegerKeyword::class,
                        FloatKeyword::class,
                        StringKeyword::class
                )
                .useRHS(::BaseKind)
                .on(Simplekind(), IdentifierTerminal::class)
                .useRHS(::Classid)
        llTable
                .on(BaseKind(), IntegerKeyword::class)
                .useRHS(::IntegerKeyword)
                .on(BaseKind(), FloatKeyword::class)
                .useRHS(::FloatKeyword)
                .on(BaseKind(), StringKeyword::class)
                .useRHS(::StringKeyword)
        llTable
                .on(Classid(), IdentifierTerminal::class)
                .useRHS(::IdentifierTerminal)
        llTable
                .on(Varspec(), IdentifierTerminal::class)
                .useRHS(::Varid, ::Arrspec)
                .on(Varspec(), Asterisk::class)
                .useRHS(::Deref_id)
        llTable
                .on(Varid(), IdentifierTerminal::class)
                .useRHS(::IdentifierTerminal)
        llTable
                .on(Arrspec(), LeftBracket::class)
                .useRHS(::KKint)
                .on(
                        Arrspec(),
                        Equal::class,
                        SemiColon::class,
                        Comma::class,
                        RightParen::class
                )
                .useRHS()
        llTable
                .on(KKint(), LeftBracket::class)
                .useRHS(::LeftBracket, ::IntegerTerminal, ::RightBracket)
        llTable
                .on(Deref_id(), Asterisk::class)
                .useRHS(::Deref, ::IdentifierTerminal)
        llTable
                .on(Deref(), Asterisk::class)
                .useRHS(::Asterisk)
        llTable
                .on(
                        Varinit(), IntegerTerminal::class,
                        FloatTerminal::class,
                        StringTerminal::class,
                        Asterisk::class,
                        IdentifierTerminal::class,
                        Ampersand::class,
                        LeftParen::class
                )
                .useRHS(::Expr)
                .on(Varinit(), LeftBrace::class)
                .useRHS(::BBexprs)
        llTable
                .on(BBexprs(), LeftBrace::class)
                .useRHS(::LeftBrace, ::Exprlist, ::RightBrace)
        llTable
                .on(
                        Exprlist(), IntegerTerminal::class,
                        FloatTerminal::class,
                        StringTerminal::class,
                        Asterisk::class,
                        IdentifierTerminal::class,
                        Ampersand::class,
                        LeftParen::class
                )
                .useRHS(::Expr, ::Moreexprs)
                .on(
                        Exprlist(),
                        RightBrace::class,
                        RightParen::class
                )
                .useRHS()
        llTable
                .on(Moreexprs(), Comma::class)
                .useRHS(::Comma, ::Exprlist)
                .on(
                        Moreexprs(),
                        RightBrace::class,
                        RightParen::class
                )
                .useRHS()
        llTable
                .on(Classdef(), ClassKeyword::class)
                .useRHS(::Classheader, ::Classdef_Suffix)
        llTable
                .on(Classdef_Suffix(), LeftBrace::class)
                .useRHS(::BBClassitems)
                .on(Classdef_Suffix(), IfKeyword::class)
                .useRHS(::IfKeyword, ::BBClassitems)
        llTable
                .on(BBClassitems(), LeftBrace::class)
                .useRHS(::LeftBrace, ::Classitems, ::RightBrace)
        llTable
                .on(Classheader(), ClassKeyword::class)
                .useRHS(::ClassKeyword, ::Classid, ::Classmom, ::Interfaces)
        llTable
                .on(Classmom(), Colon::class)
                .useRHS(::Colon, ::Classid)
                .on(
                        Classmom(),
                        Plus::class,
                        LeftBrace::class,
                        IfKeyword::class
                )
                .useRHS()
        llTable
                .on(Classitems(), Colon::class, VarKeyword::class, FunctionKeyword::class)
                .useRHS(::Classgroup, ::Classitems)
                .on(Classitems(), RightBrace::class)
                .useRHS()
        llTable
                .on(Classgroup(), Colon::class)
                .useRHS(::Class_ctrl)
                .on(Classgroup(), VarKeyword::class)
                .useRHS(::Vargroup)
                .on(Classgroup(), FunctionKeyword::class)
                .useRHS(::Mddecls)
        llTable
                .on(Class_ctrl(), Colon::class)
                .useRHS(::Colon, ::IdentifierTerminal)
        llTable
                .on(Interfaces(), Plus::class)
                .useRHS(::Plus, ::Classid, ::Interfaces)
                .on(Interfaces(), LeftBrace::class, IfKeyword::class)
                .useRHS()
        llTable
                .on(Mddecls(), FunctionKeyword::class)
                .useRHS(::Mdheader, ::Mddecls)
                .on(
                        Mddecls(),
                        SemiColon::class,
                        VarKeyword::class,
                        RightBrace::class
                )
                .useRHS()
        llTable
                .on(Mdheader(), FunctionKeyword::class)
                .useRHS(::FunctionKeyword, ::Md_id, ::PParmlist, ::Retkind)
        llTable
                .on(Md_id(), IdentifierTerminal::class)
                .useRHS(::Classid, ::Colon, ::Fcnid)
        llTable
                .on(Fcndefs(), FunctionKeyword::class)
                .useRHS(::Fcndef, ::Fcndefs)
                .on(Fcndefs(), MainKeyword::class)
                .useRHS()
        llTable
                .on(Fcndef(), FunctionKeyword::class)
                .useRHS(::Fcnheader, ::BBlock)
        llTable
                .on(Fcnheader(), FunctionKeyword::class)
                .useRHS(::FunctionKeyword, ::Fcnid, ::PParmlist, ::Retkind)
        llTable
                .on(Fcnid(), IdentifierTerminal::class)
                .useRHS(::IdentifierTerminal)
        llTable
                .on(
                        Retkind(), IntegerKeyword::class,
                        FloatKeyword::class,
                        StringKeyword::class
                )
                .useRHS(::BaseKind)
        llTable
                .on(PParmlist(), LeftParen::class)
                .useRHS(::LeftParen, ::Varspecs, ::RightParen)
        llTable
                .on(Varspecs(), Asterisk::class, IdentifierTerminal::class)
                .useRHS(::Varspec, ::More_varspecs)
                .on(Varspecs(), RightParen::class)
                .useRHS()
        llTable
                .on(More_varspecs(), Comma::class)
                .useRHS(::Comma, ::Varspecs)
                .on(More_varspecs(), RightParen::class)
                .useRHS()
        llTable
                .on(
                        Stmts(), Asterisk::class,
                        IdentifierTerminal::class,
                        IfKeyword::class,
                        WhileKeyword::class,
                        PrintKeyword::class,
                        InputKeyword::class,
                        ReturnKeyword::class
                )
                .useRHS(::Stmt, ::SemiColon, ::Stmts)
                .on(Stmts(), RightBrace::class)
                .useRHS()
        llTable
                .on(Stmt(), IdentifierTerminal::class, Asterisk::class)
                .useRHS(::StasgnOrFcall)
                .on(Stmt(), IfKeyword::class)
                .useRHS(::Stif)
                .on(Stmt(), WhileKeyword::class)
                .useRHS(::Stwhile)
                .on(Stmt(), PrintKeyword::class)
                .useRHS(::Stprint)
                .on(Stmt(), InputKeyword::class)
                .useRHS(::Stinput)
                .on(Stmt(), ReturnKeyword::class)
                .useRHS(::Strtn)
        llTable
                .on(StasgnOrFcall(), Asterisk::class)
                .useRHS(::Deref_id, ::Stasgn_Suffix)
                .on(StasgnOrFcall(), IdentifierTerminal::class)
                .useRHS(::IdentifierTerminal, ::StasgnOrFcall_Suffix)
        llTable
                .on(StasgnOrFcall_Suffix(), LeftBracket::class, Equal::class)
                .useRHS(::Lval_Suffix, ::Stasgn_Suffix)
                .on(StasgnOrFcall_Suffix(), LeftParen::class)
                .useRHS(::PPexprs)
        llTable
                .on(Stasgn_Suffix(), Equal::class)
                .useRHS(::Equal, ::Expr)
        llTable
                .on(Lval_Suffix(), LeftBracket::class)
                .useRHS(::KKexpr)
                .on(
                        Lval_Suffix(),
                        Equal::class,
                        Asterisk::class,
                        ForwardSlash::class,
                        Caret::class,
                        Plus::class,
                        Minus::class,
                        EqualEqual::class,
                        NotEqual::class,
                        LessThan::class,
                        LessThanOrEqual::class,
                        GreaterThanOrEqual::class,
                        GreaterThan::class,
                        RightBrace::class,
                        RightBracket::class,
                        SemiColon::class,
                        RightParen::class,
                        Comma::class
                ).useRHS()
        llTable
                .on(KKexpr(), LeftBracket::class)
                .useRHS(::LeftBracket, ::Expr, ::RightBracket)
        llTable
                .on(PPexprs(), LeftParen::class)
                .useRHS(::LeftParen, ::Exprlist, ::RightParen)
                .on(PPexprs())
                .useRHS(::PPonly)
        llTable
                .on(Stif(), IfKeyword::class)
                .useRHS(::IfKeyword, ::PPexpr, ::BBlock, ::Elsepart)
        llTable
                .on(Elsepart(), ElseIfKeyword::class)
                .useRHS(::ElseIfKeyword, ::PPexpr, ::BBlock, ::Elsepart)
                .on(Elsepart(), ElseKeyword::class)
                .useRHS(::ElseKeyword, ::BBlock)
                .on(Elsepart(), SemiColon::class)
                .useRHS()
        llTable
                .on(Stwhile(), WhileKeyword::class)
                .useRHS(::WhileKeyword, ::PPexpr, ::BBlock)
        llTable
                .on(Stprint(), PrintKeyword::class)
                .useRHS(::PrintKeyword, ::PPexprs)
        llTable
                .on(Stinput(), InputKeyword::class)
                .useRHS(::InputKeyword, ::PPexprs)
        llTable
                .on(Strtn(), ReturnKeyword::class)
                .useRHS(::ReturnKeyword, ::Strtn_Suffix)
        llTable
                .on(
                        Strtn_Suffix(), IntegerTerminal::class,
                        FloatTerminal::class,
                        StringTerminal::class,
                        IdentifierTerminal::class,
                        Asterisk::class,
                        Ampersand::class,
                        LeftParen::class
                )
                .useRHS(::Expr)
                .on(Strtn_Suffix(), SemiColon::class)
                .useRHS()
        llTable
                .on(PPexpr(), LeftParen::class)
                .useRHS(::LeftParen, ::Expr, ::RightParen)
        llTable
                .on(
                        Expr(), IntegerTerminal::class,
                        FloatTerminal::class,
                        StringTerminal::class,
                        IdentifierTerminal::class,
                        Asterisk::class,
                        Ampersand::class,
                        LeftParen::class,
                        InputKeyword::class
                )
                .useRHS(::Rterm, ::Expr_Tail)
        llTable
                .on(Expr_Tail(), EqualEqual::class, NotEqual::class, LessThan::class, LessThanOrEqual::class, GreaterThanOrEqual::class, GreaterThan::class)
                .useRHS(::Oprel, ::Rterm, ::Expr_Tail)
                .on(Expr_Tail(), RightBrace::class, RightBracket::class, SemiColon::class, RightParen::class, Comma::class)
                .useRHS()
        llTable
                .on(
                        Rterm(), IntegerTerminal::class,
                        FloatTerminal::class,
                        StringTerminal::class,
                        IdentifierTerminal::class,
                        Asterisk::class,
                        Ampersand::class,
                        LeftParen::class,
                        InputKeyword::class
                )
                .useRHS(::Term, ::Rterm_Tail)
        llTable
                .on(Rterm_Tail(), Plus::class, Minus::class)
                .useRHS(::Opadd, ::Term, ::Rterm_Tail)
                .on(
                        Rterm_Tail(),
                        EqualEqual::class,
                        NotEqual::class,
                        LessThan::class,
                        LessThanOrEqual::class,
                        GreaterThanOrEqual::class,
                        GreaterThan::class,
                        RightBrace::class,
                        RightBracket::class,
                        SemiColon::class,
                        Comma::class,
                        RightParen::class
                )
                .useRHS()
        llTable
                .on(
                        Term(), IntegerTerminal::class,
                        FloatTerminal::class,
                        StringTerminal::class,
                        IdentifierTerminal::class,
                        Asterisk::class,
                        Ampersand::class,
                        LeftParen::class,
                        InputKeyword::class
                )
                .useRHS(::Fact, ::Term_Tail)
        llTable
                .on(Term_Tail(), Asterisk::class, ForwardSlash::class, Caret::class)
                .useRHS(::Opmul, ::Fact, ::Term_Tail)
                .on(
                        Term_Tail(),
                        EqualEqual::class,
                        NotEqual::class,
                        LessThan::class,
                        LessThanOrEqual::class,
                        GreaterThanOrEqual::class,
                        GreaterThan::class,
                        RightBrace::class,
                        RightBracket::class,
                        SemiColon::class,
                        Comma::class,
                        RightParen::class,
                        Plus::class,
                        Minus::class
                )
                .useRHS()
        llTable
                .on(Fact(), IntegerTerminal::class, FloatTerminal::class, StringTerminal::class)
                .useRHS(::BaseLiteral)
                .on(Fact(), IdentifierTerminal::class, Asterisk::class)
                .useRHS(::LvalOrFcall)
                .on(Fact(), Ampersand::class)
                .useRHS(::Addrof_id)
                .on(Fact(), LeftParen::class)
                .useRHS(::PPexpr)
                .on(Fact(), InputKeyword::class)
                .useRHS(::Stinput)
        llTable
                .on(LvalOrFcall(), Asterisk::class)
                .useRHS(::Deref_id)
                .on(LvalOrFcall(), IdentifierTerminal::class)
                .useRHS(::IdentifierTerminal, ::LvalOrFcall_Suffix)
        llTable
                .on(LvalOrFcall_Suffix(), LeftBracket::class)
                .useRHS(::Lval_Suffix)
                .on(LvalOrFcall_Suffix(), LeftParen::class)
                .useRHS(::PPexprs)
                .on(
                        LvalOrFcall_Suffix(),
                        Asterisk::class,
                        ForwardSlash::class,
                        Caret::class,
                        Plus::class,
                        Minus::class,
                        EqualEqual::class,
                        NotEqual::class,
                        LessThan::class,
                        LessThanOrEqual::class,
                        GreaterThan::class,
                        GreaterThanOrEqual::class,
                        RightBrace::class,
                        RightBracket::class,
                        SemiColon::class,
                        RightParen::class,
                        Comma::class
                )
                .useRHS()
        llTable
                .on(BaseLiteral(), IntegerTerminal::class)
                .useRHS(::IntegerTerminal)
                .on(BaseLiteral(), FloatTerminal::class)
                .useRHS(::FloatTerminal)
                .on(BaseLiteral(), StringTerminal::class)
                .useRHS(::StringTerminal)
        llTable
                .on(Addrof_id(), Ampersand::class)
                .useRHS(::Ampersand, ::IdentifierTerminal)
        llTable
                .on(Oprel(), EqualEqual::class)
                .useRHS(::EqualEqual)
                .on(Oprel(), NotEqual::class)
                .useRHS(::NotEqual)
                .on(Oprel(), LessThan::class)
                .useRHS(::Lthan)
                .on(Oprel(), LessThanOrEqual::class)
                .useRHS(::LessThanOrEqual)
                .on(Oprel(), GreaterThanOrEqual::class)
                .useRHS(::GreaterThanOrEqual)
                .on(Oprel(), GreaterThan::class)
                .useRHS(::Gthan)
        llTable
                .on(Lthan(), LessThan::class)
                .useRHS(::LessThan)
        llTable
                .on(Gthan(), GreaterThan::class)
                .useRHS(::GreaterThan)
        llTable
                .on(Opadd(), Plus::class)
                .useRHS(::Plus)
                .on(Opadd(), Minus::class)
                .useRHS(::Minus)
        llTable
                .on(Opmul(), Asterisk::class)
                .useRHS(::Asterisk)
                .on(Opmul(), ForwardSlash::class)
                .useRHS(::ForwardSlash)
                .on(Opmul(), Caret::class)
                .useRHS(::Caret)

        return llTable
    }
}
