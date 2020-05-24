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
                .on(Pgm(), ProgramKeyword::class.java)
                .useRHS(::ProgramKeyword, ::Vargroup, ::Fcndefs, ::Main)
        llTable
                .on(Main(), MainKeyword::class.java)
                .useRHS(::MainKeyword, ::BBlock)
        llTable
                .on(BBlock(), LeftBrace::class.java)
                .useRHS(::LeftBrace, ::Vargroup, ::Stmts, ::RightBrace)
        llTable
                .on(Vargroup(), VarKeyword::class.java)
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
        llTable
                .on(PPvarlist(), LeftParen::class.java)
                .useRHS(::LeftParen, ::Varlist, ::RightParen)
        llTable
                .on(
                        Varlist(), IntegerKeyword::class.java,
                        FloatKeyword::class.java,
                        StringKeyword::class.java,
                        IdentifierTerminal::class.java,
                        ClassKeyword::class.java
                )
                .useRHS(::Varitem, ::SemiColon, ::Varlist)
                .on(RightParen::class.java)
                .useRHS()
        llTable
                .on(
                        Varitem(), IntegerKeyword::class.java,
                        FloatKeyword::class.java,
                        StringKeyword::class.java,
                        IdentifierTerminal::class.java
                )
                .useRHS(::Vardecl, ::Varitem_Suffix)
                .on(ClassKeyword::class.java)
                .useRHS(::Classdef)
        llTable
                .on(Varitem_Suffix(), Equal::class.java)
                .useRHS(::Equal, ::Varinit)
                .on(SemiColon::class.java)
                .useRHS()
        llTable
                .on(
                        Vardecl(), IntegerKeyword::class.java,
                        FloatKeyword::class.java,
                        StringKeyword::class.java,
                        IdentifierTerminal::class.java
                )
                .useRHS(::Simplekind, ::Varspec)
        llTable
                .on(
                        Simplekind(), IntegerKeyword::class.java,
                        FloatKeyword::class.java,
                        StringKeyword::class.java
                )
                .useRHS(::BaseKind)
                .on(IdentifierTerminal::class.java)
                .useRHS(::Classid)
        llTable
                .on(BaseKind(), IntegerKeyword::class.java)
                .useRHS(::IntegerKeyword)
                .on(FloatKeyword::class.java)
                .useRHS(::FloatKeyword)
                .on(StringKeyword::class.java)
                .useRHS(::StringKeyword)
        llTable
                .on(Classid(), IdentifierTerminal::class.java)
                .useRHS(::IdentifierTerminal)
        llTable
                .on(Varspec(), IdentifierTerminal::class.java)
                .useRHS(::Varid, ::Arrspec)
                .on(Asterisk::class.java)
                .useRHS(::Deref_id)
        llTable
                .on(Varid(), IdentifierTerminal::class.java)
                .useRHS(::IdentifierTerminal)
        llTable
                .on(Arrspec(), LeftBracket::class.java)
                .useRHS(::KKint)
                .on(
                        Equal::class.java,
                        SemiColon::class.java,
                        Comma::class.java,
                        RightParen::class.java
                )
                .useRHS()
        llTable
                .on(KKint(), LeftBracket::class.java)
                .useRHS(::LeftBracket, ::IntegerTerminal, ::RightBracket)
        llTable
                .on(Deref_id(), Asterisk::class.java)
                .useRHS(::Deref, ::IdentifierTerminal)
        llTable
                .on(Deref(), Asterisk::class.java)
                .useRHS(::Asterisk)
        llTable
                .on(
                        Varinit(), IntegerTerminal::class.java,
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
        llTable
                .on(BBexprs(), LeftBrace::class.java)
                .useRHS(::LeftBrace, ::Exprlist, ::RightBrace)
        llTable
                .on(
                        Exprlist(), IntegerTerminal::class.java,
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
        llTable
                .on(Moreexprs(), Comma::class.java)
                .useRHS(::Comma, ::Exprlist)
                .on(
                        RightBrace::class.java,
                        RightParen::class.java
                )
                .useRHS()
        llTable
                .on(Classdef(), ClassKeyword::class.java)
                .useRHS(::Classheader, ::Classdef_Suffix)
        llTable
                .on(Classdef_Suffix(), LeftBrace::class.java)
                .useRHS(::BBClassitems)
                .on(IfKeyword::class.java)
                .useRHS(::IfKeyword, ::BBClassitems)
        llTable
                .on(BBClassitems(), LeftBrace::class.java)
                .useRHS(::LeftBrace, ::Classitems, ::RightBrace)
        llTable
                .on(Classheader(), ClassKeyword::class.java)
                .useRHS(::ClassKeyword, ::Classid, ::Classmom, ::Interfaces)
        llTable
                .on(Classmom(), Colon::class.java)
                .useRHS(::Colon, ::Classid)
                .on(
                        Plus::class.java,
                        LeftBrace::class.java,
                        IfKeyword::class.java
                )
                .useRHS()
        llTable
                .on(Classitems(), Colon::class.java, VarKeyword::class.java, FunctionKeyword::class.java)
                .useRHS(::Classgroup, ::Classitems)
                .on(RightBrace::class.java)
                .useRHS()
        llTable
                .on(Classgroup(), Colon::class.java)
                .useRHS(::Class_ctrl)
                .on(VarKeyword::class.java)
                .useRHS(::Vargroup)
                .on(FunctionKeyword::class.java)
                .useRHS(::Mddecls)
        llTable
                .on(Class_ctrl(), Colon::class.java)
                .useRHS(::Colon, ::IdentifierTerminal)
        llTable
                .on(Interfaces(), Plus::class.java)
                .useRHS(::Plus, ::Classid, ::Interfaces)
                .on(LeftBrace::class.java, IfKeyword::class.java)
                .useRHS()
        llTable
                .on(Mddecls(), FunctionKeyword::class.java)
                .useRHS(::Mdheader, ::Mddecls)
                .on(
                        SemiColon::class.java,
                        VarKeyword::class.java,
                        RightBrace::class.java
                )
                .useRHS()
        llTable
                .on(Mdheader(), FunctionKeyword::class.java)
                .useRHS(::FunctionKeyword, ::Md_id, ::PParmlist, ::Retkind)
        llTable
                .on(Md_id(), IdentifierTerminal::class.java)
                .useRHS(::Classid, ::Colon, ::Fcnid)
        llTable
                .on(Fcndefs(), FunctionKeyword::class.java)
                .useRHS(::Fcndef, ::Fcndefs)
                .on(MainKeyword::class.java)
                .useRHS()
        llTable
                .on(Fcndef(), FunctionKeyword::class.java)
                .useRHS(::Fcnheader, ::BBlock)
        llTable
                .on(Fcnheader(), FunctionKeyword::class.java)
                .useRHS(::FunctionKeyword, ::Fcnid, ::PParmlist, ::Retkind)
        llTable
                .on(Fcnid(), IdentifierTerminal::class.java)
                .useRHS(::IdentifierTerminal)
        llTable
                .on(
                        Retkind(), IntegerKeyword::class.java,
                        FloatKeyword::class.java,
                        StringKeyword::class.java
                )
                .useRHS(::BaseKind)
        llTable
                .on(PParmlist(), LeftParen::class.java)
                .useRHS(::LeftParen, ::Varspecs, ::RightParen)
        llTable
                .on(Varspecs(), Asterisk::class.java, IdentifierTerminal::class.java)
                .useRHS(::Varspec, ::More_varspecs)
                .on(RightParen::class.java)
                .useRHS()
        llTable
                .on(More_varspecs(), Comma::class.java)
                .useRHS(::Comma, ::Varspecs)
                .on(RightParen::class.java)
                .useRHS()
        llTable
                .on(
                        Stmts(), Asterisk::class.java,
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
        llTable
                .on(Stmt(), IdentifierTerminal::class.java, Asterisk::class.java)
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
        llTable
                .on(StasgnOrFcall(), Asterisk::class.java)
                .useRHS(::Deref_id, ::Stasgn_Suffix)
                .on(IdentifierTerminal::class.java)
                .useRHS(::IdentifierTerminal, ::StasgnOrFcall_Suffix)
        llTable
                .on(StasgnOrFcall_Suffix(), LeftBracket::class.java, Equal::class.java)
                .useRHS(::Lval_Suffix, ::Stasgn_Suffix)
                .on(LeftParen::class.java)
                .useRHS(::PPexprs)
        llTable
                .on(Stasgn_Suffix(), Equal::class.java)
                .useRHS(::Equal, ::Expr)
        llTable
                .on(Lval_Suffix(), LeftBracket::class.java)
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
        llTable
                .on(KKexpr(), LeftBracket::class.java)
                .useRHS(::LeftBracket, ::Expr, ::RightBracket)
        llTable
                .on(PPexprs(), LeftParen::class.java)
                .useRHS(::LeftParen, ::Exprlist, ::RightParen)
                .on()
                .useRHS(::PPonly)
        llTable
                .on(Stif(), IfKeyword::class.java)
                .useRHS(::IfKeyword, ::PPexpr, ::BBlock, ::Elsepart)
        llTable
                .on(Elsepart(), ElseIfKeyword::class.java)
                .useRHS(::ElseIfKeyword, ::PPexpr, ::BBlock, ::Elsepart)
                .on(ElseKeyword::class.java)
                .useRHS(::ElseKeyword, ::BBlock)
                .on(SemiColon::class.java)
                .useRHS()
        llTable
                .on(Stwhile(), WhileKeyword::class.java)
                .useRHS(::WhileKeyword, ::PPexpr, ::BBlock)
        llTable
                .on(Stprint(), PrintKeyword::class.java)
                .useRHS(::PrintKeyword, ::PPexprs)
        llTable
                .on(Stinput(), InputKeyword::class.java)
                .useRHS(::InputKeyword, ::PPexprs)
        llTable
                .on(Strtn(), ReturnKeyword::class.java)
                .useRHS(::ReturnKeyword, ::Strtn_Suffix)
        llTable
                .on(
                        Strtn_Suffix(), IntegerTerminal::class.java,
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
        llTable
                .on(PPexpr(), LeftParen::class.java)
                .useRHS(::LeftParen, ::Expr, ::RightParen)
        llTable
                .on(
                        Expr(), IntegerTerminal::class.java,
                        FloatTerminal::class.java,
                        StringTerminal::class.java,
                        IdentifierTerminal::class.java,
                        Asterisk::class.java,
                        Ampersand::class.java,
                        LeftParen::class.java,
                        InputKeyword::class.java
                )
                .useRHS(::Rterm, ::Expr_Tail)
        llTable
                .on(Expr_Tail(), EqualEqual::class.java, NotEqual::class.java, LessThan::class.java, LessThanOrEqual::class.java, GreaterThanOrEqual::class.java, GreaterThan::class.java)
                .useRHS(::Oprel, ::Rterm, ::Expr_Tail)
                .on(RightBrace::class.java, RightBracket::class.java, SemiColon::class.java, RightParen::class.java, Comma::class.java)
                .useRHS()
        llTable
                .on(
                        Rterm(), IntegerTerminal::class.java,
                        FloatTerminal::class.java,
                        StringTerminal::class.java,
                        IdentifierTerminal::class.java,
                        Asterisk::class.java,
                        Ampersand::class.java,
                        LeftParen::class.java,
                        InputKeyword::class.java
                )
                .useRHS(::Term, ::Rterm_Tail)
        llTable
                .on(Rterm_Tail(), Plus::class.java, Minus::class.java)
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
        llTable
                .on(
                        Term(), IntegerTerminal::class.java,
                        FloatTerminal::class.java,
                        StringTerminal::class.java,
                        IdentifierTerminal::class.java,
                        Asterisk::class.java,
                        Ampersand::class.java,
                        LeftParen::class.java,
                        InputKeyword::class.java
                )
                .useRHS(::Fact, ::Term_Tail)
        llTable
                .on(Term_Tail(), Asterisk::class.java, ForwardSlash::class.java, Caret::class.java)
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
        llTable
                .on(Fact(), IntegerTerminal::class.java, FloatTerminal::class.java, StringTerminal::class.java)
                .useRHS(::BaseLiteral)
                .on(IdentifierTerminal::class.java, Asterisk::class.java)
                .useRHS(::LvalOrFcall)
                .on(Ampersand::class.java)
                .useRHS(::Addrof_id)
                .on(LeftParen::class.java)
                .useRHS(::PPexpr)
                .on(InputKeyword::class.java)
                .useRHS(::Stinput)
        llTable
                .on(LvalOrFcall(), Asterisk::class.java)
                .useRHS(::Deref_id)
                .on(IdentifierTerminal::class.java)
                .useRHS(::IdentifierTerminal, ::LvalOrFcall_Suffix)
        llTable
                .on(LvalOrFcall_Suffix(), LeftBracket::class.java)
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
        llTable
                .on(BaseLiteral(), IntegerTerminal::class.java)
                .useRHS(::IntegerTerminal)
                .on(FloatTerminal::class.java)
                .useRHS(::FloatTerminal)
                .on(StringTerminal::class.java)
                .useRHS(::StringTerminal)
        llTable
                .on(Addrof_id(), Ampersand::class.java)
                .useRHS(::Ampersand, ::IdentifierTerminal)
        llTable
                .on(Oprel(), EqualEqual::class.java)
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
        llTable
                .on(Lthan(), LessThan::class.java)
                .useRHS(::LessThan)
        llTable
                .on(Gthan(), GreaterThan::class.java)
                .useRHS(::GreaterThan)
        llTable
                .on(Opadd(), Plus::class.java)
                .useRHS(::Plus)
                .on(Minus::class.java)
                .useRHS(::Minus)
        llTable
                .on(Opmul(), Asterisk::class.java)
                .useRHS(::Asterisk)
                .on(ForwardSlash::class.java)
                .useRHS(::ForwardSlash)
                .on(Caret::class.java)
                .useRHS(::Caret)

        return llTable
    }
}
