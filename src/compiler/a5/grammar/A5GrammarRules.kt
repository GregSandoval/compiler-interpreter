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
                .on(RightParen::class)
                .useRHS()
        llTable
                .on(
                        Varitem(), IntegerKeyword::class,
                        FloatKeyword::class,
                        StringKeyword::class,
                        IdentifierTerminal::class
                )
                .useRHS(::Vardecl, ::Varitem_Suffix)
                .on(ClassKeyword::class)
                .useRHS(::Classdef)
        llTable
                .on(Varitem_Suffix(), Equal::class)
                .useRHS(::Equal, ::Varinit)
                .on(SemiColon::class)
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
                .on(IdentifierTerminal::class)
                .useRHS(::Classid)
        llTable
                .on(BaseKind(), IntegerKeyword::class)
                .useRHS(::IntegerKeyword)
                .on(FloatKeyword::class)
                .useRHS(::FloatKeyword)
                .on(StringKeyword::class)
                .useRHS(::StringKeyword)
        llTable
                .on(Classid(), IdentifierTerminal::class)
                .useRHS(::IdentifierTerminal)
        llTable
                .on(Varspec(), IdentifierTerminal::class)
                .useRHS(::Varid, ::Arrspec)
                .on(Asterisk::class)
                .useRHS(::Deref_id)
        llTable
                .on(Varid(), IdentifierTerminal::class)
                .useRHS(::IdentifierTerminal)
        llTable
                .on(Arrspec(), LeftBracket::class)
                .useRHS(::KKint)
                .on(
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
                .on(LeftBrace::class)
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
                        RightBrace::class,
                        RightParen::class
                )
                .useRHS()
        llTable
                .on(Moreexprs(), Comma::class)
                .useRHS(::Comma, ::Exprlist)
                .on(
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
                .on(IfKeyword::class)
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
                        Plus::class,
                        LeftBrace::class,
                        IfKeyword::class
                )
                .useRHS()
        llTable
                .on(Classitems(), Colon::class, VarKeyword::class, FunctionKeyword::class)
                .useRHS(::Classgroup, ::Classitems)
                .on(RightBrace::class)
                .useRHS()
        llTable
                .on(Classgroup(), Colon::class)
                .useRHS(::Class_ctrl)
                .on(VarKeyword::class)
                .useRHS(::Vargroup)
                .on(FunctionKeyword::class)
                .useRHS(::Mddecls)
        llTable
                .on(Class_ctrl(), Colon::class)
                .useRHS(::Colon, ::IdentifierTerminal)
        llTable
                .on(Interfaces(), Plus::class)
                .useRHS(::Plus, ::Classid, ::Interfaces)
                .on(LeftBrace::class, IfKeyword::class)
                .useRHS()
        llTable
                .on(Mddecls(), FunctionKeyword::class)
                .useRHS(::Mdheader, ::Mddecls)
                .on(
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
                .on(MainKeyword::class)
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
                .on(RightParen::class)
                .useRHS()
        llTable
                .on(More_varspecs(), Comma::class)
                .useRHS(::Comma, ::Varspecs)
                .on(RightParen::class)
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
                .on(RightBrace::class)
                .useRHS()
        llTable
                .on(Stmt(), IdentifierTerminal::class, Asterisk::class)
                .useRHS(::StasgnOrFcall)
                .on(IfKeyword::class)
                .useRHS(::Stif)
                .on(WhileKeyword::class)
                .useRHS(::Stwhile)
                .on(PrintKeyword::class)
                .useRHS(::Stprint)
                .on(InputKeyword::class)
                .useRHS(::Stinput)
                .on(ReturnKeyword::class)
                .useRHS(::Strtn)
        llTable
                .on(StasgnOrFcall(), Asterisk::class)
                .useRHS(::Deref_id, ::Stasgn_Suffix)
                .on(IdentifierTerminal::class)
                .useRHS(::IdentifierTerminal, ::StasgnOrFcall_Suffix)
        llTable
                .on(StasgnOrFcall_Suffix(), LeftBracket::class, Equal::class)
                .useRHS(::Lval_Suffix, ::Stasgn_Suffix)
                .on(LeftParen::class)
                .useRHS(::PPexprs)
        llTable
                .on(Stasgn_Suffix(), Equal::class)
                .useRHS(::Equal, ::Expr)
        llTable
                .on(Lval_Suffix(), LeftBracket::class)
                .useRHS(::KKexpr)
                .on(
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
                .on()
                .useRHS(::PPonly)
        llTable
                .on(Stif(), IfKeyword::class)
                .useRHS(::IfKeyword, ::PPexpr, ::BBlock, ::Elsepart)
        llTable
                .on(Elsepart(), ElseIfKeyword::class)
                .useRHS(::ElseIfKeyword, ::PPexpr, ::BBlock, ::Elsepart)
                .on(ElseKeyword::class)
                .useRHS(::ElseKeyword, ::BBlock)
                .on(SemiColon::class)
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
                .on(SemiColon::class)
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
                .on(RightBrace::class, RightBracket::class, SemiColon::class, RightParen::class, Comma::class)
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
                .on(IdentifierTerminal::class, Asterisk::class)
                .useRHS(::LvalOrFcall)
                .on(Ampersand::class)
                .useRHS(::Addrof_id)
                .on(LeftParen::class)
                .useRHS(::PPexpr)
                .on(InputKeyword::class)
                .useRHS(::Stinput)
        llTable
                .on(LvalOrFcall(), Asterisk::class)
                .useRHS(::Deref_id)
                .on(IdentifierTerminal::class)
                .useRHS(::IdentifierTerminal, ::LvalOrFcall_Suffix)
        llTable
                .on(LvalOrFcall_Suffix(), LeftBracket::class)
                .useRHS(::Lval_Suffix)
                .on(LeftParen::class)
                .useRHS(::PPexprs)
                .on(
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
                .on(FloatTerminal::class)
                .useRHS(::FloatTerminal)
                .on(StringTerminal::class)
                .useRHS(::StringTerminal)
        llTable
                .on(Addrof_id(), Ampersand::class)
                .useRHS(::Ampersand, ::IdentifierTerminal)
        llTable
                .on(Oprel(), EqualEqual::class)
                .useRHS(::EqualEqual)
                .on(NotEqual::class)
                .useRHS(::NotEqual)
                .on(LessThan::class)
                .useRHS(::Lthan)
                .on(LessThanOrEqual::class)
                .useRHS(::LessThanOrEqual)
                .on(GreaterThanOrEqual::class)
                .useRHS(::GreaterThanOrEqual)
                .on(GreaterThan::class)
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
                .on(Minus::class)
                .useRHS(::Minus)
        llTable
                .on(Opmul(), Asterisk::class)
                .useRHS(::Asterisk)
                .on(ForwardSlash::class)
                .useRHS(::ForwardSlash)
                .on(Caret::class)
                .useRHS(::Caret)

        return llTable
    }
}
