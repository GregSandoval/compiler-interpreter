package compiler.a5.grammar

import compiler.parser.LLTable
import compiler.parser.NodeSupplier
import compiler.parser.Symbol.NonTerminal
import compiler.parser.Symbol.NonTerminal.*
import compiler.parser.Symbol.Terminal
import compiler.parser.Symbol.Terminal.Keyword.*
import compiler.parser.Symbol.Terminal.Keyword.Type.*
import compiler.parser.Symbol.Terminal.Operator.*
import compiler.parser.Symbol.Terminal.Punctuation.*
import compiler.parser.Symbol.Terminal.TypedTerminal.*
import kotlin.reflect.KClass

object A5GrammarRules {
    private lateinit var llTable: LLTable

    fun build(): LLTable {
        this.llTable = LLTable()

        Pgm::class[ProgramKeyword::class] = listOf(::ProgramKeyword, ::Vargroup, ::Fcndefs, ::Main)


        Main::class[MainKeyword::class] = listOf(::MainKeyword, ::BBlock)


        BBlock::class[LeftBrace::class] = listOf(::LeftBrace, ::Vargroup, ::Stmts, ::RightBrace)


        Vargroup::class[VarKeyword::class] = listOf(::VarKeyword, ::PPvarlist)
        Vargroup::class[FunctionKeyword::class] = listOf()
        Vargroup::class[MainKeyword::class] = listOf()
        Vargroup::class[Asterisk::class] = listOf()
        Vargroup::class[IdentifierTerminal::class] = listOf()
        Vargroup::class[IfKeyword::class] = listOf()
        Vargroup::class[WhileKeyword::class] = listOf()
        Vargroup::class[PrintKeyword::class] = listOf()
        Vargroup::class[InputKeyword::class] = listOf()
        Vargroup::class[ReturnKeyword::class] = listOf()
        Vargroup::class[RightBrace::class] = listOf()
        Vargroup::class[Colon::class] = listOf()


        PPvarlist::class[LeftParen::class] = listOf(::LeftParen, ::Varlist, ::RightParen)


        Varlist::class[IntegerKeyword::class] = listOf(::Varitem, ::SemiColon, ::Varlist)
        Varlist::class[FloatKeyword::class] = listOf(::Varitem, ::SemiColon, ::Varlist)
        Varlist::class[StringKeyword::class] = listOf(::Varitem, ::SemiColon, ::Varlist)
        Varlist::class[IdentifierTerminal::class] = listOf(::Varitem, ::SemiColon, ::Varlist)
        Varlist::class[ClassKeyword::class] = listOf(::Varitem, ::SemiColon, ::Varlist)
        Varlist::class[RightParen::class] = listOf()
        Varitem::class[IntegerKeyword::class] = listOf(::Vardecl, ::Varitem_Suffix)
        Varitem::class[FloatKeyword::class] = listOf(::Vardecl, ::Varitem_Suffix)
        Varitem::class[StringKeyword::class] = listOf(::Vardecl, ::Varitem_Suffix)
        Varitem::class[IdentifierTerminal::class] = listOf(::Vardecl, ::Varitem_Suffix)
        Varitem::class[ClassKeyword::class] = listOf(::Classdef)


        Varitem_Suffix::class[Equal::class] = listOf(::Equal, ::Varinit)
        Varitem_Suffix::class[SemiColon::class] = listOf()


        Vardecl::class[IntegerKeyword::class] = listOf(::Simplekind, ::Varspec)
        Vardecl::class[FloatKeyword::class] = listOf(::Simplekind, ::Varspec)
        Vardecl::class[StringKeyword::class] = listOf(::Simplekind, ::Varspec)
        Vardecl::class[IdentifierTerminal::class] = listOf(::Simplekind, ::Varspec)


        Simplekind::class[IntegerKeyword::class] = listOf(::BaseKind)
        Simplekind::class[FloatKeyword::class] = listOf(::BaseKind)
        Simplekind::class[StringKeyword::class] = listOf(::BaseKind)
        Simplekind::class[IdentifierTerminal::class] = listOf(::Classid)


        BaseKind::class[IntegerKeyword::class] = listOf(::IntegerKeyword)
        BaseKind::class[FloatKeyword::class] = listOf(::FloatKeyword)
        BaseKind::class[StringKeyword::class] = listOf(::StringKeyword)


        Classid::class[IdentifierTerminal::class] = listOf(::IdentifierTerminal)


        Varspec::class[IdentifierTerminal::class] = listOf(::Varid, ::Arrspec)
        Varspec::class[Asterisk::class] = listOf(::Deref_id)


        Varid::class[IdentifierTerminal::class] = listOf(::IdentifierTerminal)


        Arrspec::class[LeftBracket::class] = listOf(::KKint)
        Arrspec::class[Equal::class] = listOf()
        Arrspec::class[SemiColon::class] = listOf()
        Arrspec::class[Comma::class] = listOf()
        Arrspec::class[RightParen::class] = listOf()


        KKint::class[LeftBracket::class] = listOf(::LeftBracket, ::IntegerTerminal, ::RightBracket)


        Deref_id::class[Asterisk::class] = listOf(::Deref, ::IdentifierTerminal)


        Deref::class[Asterisk::class] = listOf(::Asterisk)


        Varinit::class[IntegerTerminal::class] = listOf(::Expr)
        Varinit::class[FloatTerminal::class] = listOf(::Expr)
        Varinit::class[StringTerminal::class] = listOf(::Expr)
        Varinit::class[Asterisk::class] = listOf(::Expr)
        Varinit::class[IdentifierTerminal::class] = listOf(::Expr)
        Varinit::class[Ampersand::class] = listOf(::Expr)
        Varinit::class[LeftParen::class] = listOf(::Expr)
        Varinit::class[LeftBrace::class] = listOf(::BBexprs)


        BBexprs::class[LeftBrace::class] = listOf(::LeftBrace, ::Exprlist, ::RightBrace)


        Exprlist::class[IntegerTerminal::class] = listOf(::Expr, ::Moreexprs)
        Exprlist::class[FloatTerminal::class] = listOf(::Expr, ::Moreexprs)
        Exprlist::class[StringTerminal::class] = listOf(::Expr, ::Moreexprs)
        Exprlist::class[Asterisk::class] = listOf(::Expr, ::Moreexprs)
        Exprlist::class[IdentifierTerminal::class] = listOf(::Expr, ::Moreexprs)
        Exprlist::class[Ampersand::class] = listOf(::Expr, ::Moreexprs)
        Exprlist::class[LeftParen::class] = listOf(::Expr, ::Moreexprs)
        Exprlist::class[RightBrace::class] = listOf()
        Exprlist::class[RightParen::class] = listOf()


        Moreexprs::class[Comma::class] = listOf(::Comma, ::Exprlist)
        Moreexprs::class[RightBrace::class] = listOf()
        Moreexprs::class[RightParen::class] = listOf()


        Classdef::class[ClassKeyword::class] = listOf(::Classheader, ::Classdef_Suffix)


        Classdef_Suffix::class[LeftBrace::class] = listOf(::BBClassitems)
        Classdef_Suffix::class[IfKeyword::class] = listOf(::IfKeyword, ::BBClassitems)


        BBClassitems::class[LeftBrace::class] = listOf(::LeftBrace, ::Classitems, ::RightBrace)


        Classheader::class[ClassKeyword::class] = listOf(::ClassKeyword, ::Classid, ::Classmom, ::Interfaces)


        Classmom::class[Colon::class] = listOf(::Colon, ::Classid)
        Classmom::class[Plus::class] = listOf()
        Classmom::class[LeftBrace::class] = listOf()
        Classmom::class[IfKeyword::class] = listOf()


        Classitems::class[Colon::class] = listOf(::Classgroup, ::Classitems)
        Classitems::class[VarKeyword::class] = listOf(::Classgroup, ::Classitems)
        Classitems::class[FunctionKeyword::class] = listOf(::Classgroup, ::Classitems)
        Classitems::class[RightBrace::class] = listOf()


        Classgroup::class[Colon::class] = listOf(::Class_ctrl)
        Classgroup::class[VarKeyword::class] = listOf(::Vargroup)
        Classgroup::class[FunctionKeyword::class] = listOf(::Mddecls)


        Class_ctrl::class[Colon::class] = listOf(::Colon, ::IdentifierTerminal)


        Interfaces::class[Plus::class] = listOf(::Plus, ::Classid, ::Interfaces)
        Interfaces::class[LeftBrace::class] = listOf()
        Interfaces::class[IfKeyword::class] = listOf()


        Mddecls::class[FunctionKeyword::class] = listOf(::Mdheader, ::Mddecls)
        Mddecls::class[SemiColon::class] = listOf()
        Mddecls::class[VarKeyword::class] = listOf()
        Mddecls::class[RightBrace::class] = listOf()


        Mdheader::class[FunctionKeyword::class] = listOf(::FunctionKeyword, ::Md_id, ::PParmlist, ::Retkind)


        Md_id::class[IdentifierTerminal::class] = listOf(::Classid, ::Colon, ::Fcnid)


        Fcndefs::class[FunctionKeyword::class] = listOf(::Fcndef, ::Fcndefs)
        Fcndefs::class[MainKeyword::class] = listOf()


        Fcndef::class[FunctionKeyword::class] = listOf(::Fcnheader, ::BBlock)


        Fcnheader::class[FunctionKeyword::class] = listOf(::FunctionKeyword, ::Fcnid, ::PParmlist, ::Retkind)


        Fcnid::class[IdentifierTerminal::class] = listOf(::IdentifierTerminal)


        Retkind::class[IntegerKeyword::class] = listOf(::BaseKind)
        Retkind::class[FloatKeyword::class] = listOf(::BaseKind)
        Retkind::class[StringKeyword::class] = listOf(::BaseKind)


        PParmlist::class[LeftParen::class] = listOf(::LeftParen, ::Varspecs, ::RightParen)


        Varspecs::class[Asterisk::class] = listOf(::Varspec, ::More_varspecs)
        Varspecs::class[IdentifierTerminal::class] = listOf(::Varspec, ::More_varspecs)
        Varspecs::class[RightParen::class] = listOf()


        More_varspecs::class[Comma::class] = listOf(::Comma, ::Varspecs)
        More_varspecs::class[RightParen::class] = listOf()


        Stmts::class[Asterisk::class] = listOf(::Stmt, ::SemiColon, ::Stmts)
        Stmts::class[IdentifierTerminal::class] = listOf(::Stmt, ::SemiColon, ::Stmts)
        Stmts::class[IfKeyword::class] = listOf(::Stmt, ::SemiColon, ::Stmts)
        Stmts::class[WhileKeyword::class] = listOf(::Stmt, ::SemiColon, ::Stmts)
        Stmts::class[PrintKeyword::class] = listOf(::Stmt, ::SemiColon, ::Stmts)
        Stmts::class[InputKeyword::class] = listOf(::Stmt, ::SemiColon, ::Stmts)
        Stmts::class[ReturnKeyword::class] = listOf(::Stmt, ::SemiColon, ::Stmts)
        Stmts::class[RightBrace::class] = listOf()


        Stmt::class[IdentifierTerminal::class] = listOf(::StasgnOrFcall)
        Stmt::class[Asterisk::class] = listOf(::StasgnOrFcall)
        Stmt::class[IfKeyword::class] = listOf(::Stif)
        Stmt::class[WhileKeyword::class] = listOf(::Stwhile)
        Stmt::class[PrintKeyword::class] = listOf(::Stprint)
        Stmt::class[InputKeyword::class] = listOf(::Stinput)
        Stmt::class[ReturnKeyword::class] = listOf(::Strtn)


        StasgnOrFcall::class[Asterisk::class] = listOf(::Deref_id, ::Stasgn_Suffix)
        StasgnOrFcall::class[IdentifierTerminal::class] = listOf(::IdentifierTerminal, ::StasgnOrFcall_Suffix)


        StasgnOrFcall_Suffix::class[LeftBracket::class] = listOf(::Lval_Suffix, ::Stasgn_Suffix)
        StasgnOrFcall_Suffix::class[Equal::class] = listOf(::Lval_Suffix, ::Stasgn_Suffix)
        StasgnOrFcall_Suffix::class[LeftParen::class] = listOf(::PPexprs)


        Stasgn_Suffix::class[Equal::class] = listOf(::Equal, ::Expr)


        Lval_Suffix::class[LeftBracket::class] = listOf(::KKexpr)
        Lval_Suffix::class[Equal::class] = listOf()
        Lval_Suffix::class[Asterisk::class] = listOf()
        Lval_Suffix::class[ForwardSlash::class] = listOf()
        Lval_Suffix::class[Caret::class] = listOf()
        Lval_Suffix::class[Plus::class] = listOf()
        Lval_Suffix::class[Minus::class] = listOf()
        Lval_Suffix::class[EqualEqual::class] = listOf()
        Lval_Suffix::class[NotEqual::class] = listOf()
        Lval_Suffix::class[LessThan::class] = listOf()
        Lval_Suffix::class[LessThanOrEqual::class] = listOf()
        Lval_Suffix::class[GreaterThanOrEqual::class] = listOf()
        Lval_Suffix::class[GreaterThan::class] = listOf()
        Lval_Suffix::class[RightBrace::class] = listOf()
        Lval_Suffix::class[RightBracket::class] = listOf()
        Lval_Suffix::class[SemiColon::class] = listOf()
        Lval_Suffix::class[RightParen::class] = listOf()
        Lval_Suffix::class[Comma::class] = listOf()


        KKexpr::class[LeftBracket::class] = listOf(::LeftBracket, ::Expr, ::RightBracket)


        PPexprs::class[LeftParen::class] = listOf(::LeftParen, ::Exprlist, ::RightParen)


        Stif::class[IfKeyword::class] = listOf(::IfKeyword, ::PPexpr, ::BBlock, ::Elsepart)


        Elsepart::class[ElseIfKeyword::class] = listOf(::ElseIfKeyword, ::PPexpr, ::BBlock, ::Elsepart)
        Elsepart::class[ElseKeyword::class] = listOf(::ElseKeyword, ::BBlock)
        Elsepart::class[SemiColon::class] = listOf()


        Stwhile::class[WhileKeyword::class] = listOf(::WhileKeyword, ::PPexpr, ::BBlock)


        Stprint::class[PrintKeyword::class] = listOf(::PrintKeyword, ::PPexprs)


        Stinput::class[InputKeyword::class] = listOf(::InputKeyword, ::PPexprs)


        Strtn::class[ReturnKeyword::class] = listOf(::ReturnKeyword, ::Strtn_Suffix)


        Strtn_Suffix::class[IntegerTerminal::class] = listOf(::Expr)
        Strtn_Suffix::class[FloatTerminal::class] = listOf(::Expr)
        Strtn_Suffix::class[StringTerminal::class] = listOf(::Expr)
        Strtn_Suffix::class[IdentifierTerminal::class] = listOf(::Expr)
        Strtn_Suffix::class[Asterisk::class] = listOf(::Expr)
        Strtn_Suffix::class[Ampersand::class] = listOf(::Expr)
        Strtn_Suffix::class[LeftParen::class] = listOf(::Expr)
        Strtn_Suffix::class[SemiColon::class] = listOf()


        PPexpr::class[LeftParen::class] = listOf(::LeftParen, ::Expr, ::RightParen)


        Expr::class[IntegerTerminal::class] = listOf(::Rterm, ::Expr_Tail)
        Expr::class[FloatTerminal::class] = listOf(::Rterm, ::Expr_Tail)
        Expr::class[StringTerminal::class] = listOf(::Rterm, ::Expr_Tail)
        Expr::class[IdentifierTerminal::class] = listOf(::Rterm, ::Expr_Tail)
        Expr::class[Asterisk::class] = listOf(::Rterm, ::Expr_Tail)
        Expr::class[Ampersand::class] = listOf(::Rterm, ::Expr_Tail)
        Expr::class[LeftParen::class] = listOf(::Rterm, ::Expr_Tail)
        Expr::class[InputKeyword::class] = listOf(::Rterm, ::Expr_Tail)


        Expr_Tail::class[EqualEqual::class] = listOf(::Oprel, ::Rterm, ::Expr_Tail)
        Expr_Tail::class[NotEqual::class] = listOf(::Oprel, ::Rterm, ::Expr_Tail)
        Expr_Tail::class[LessThan::class] = listOf(::Oprel, ::Rterm, ::Expr_Tail)
        Expr_Tail::class[LessThanOrEqual::class] = listOf(::Oprel, ::Rterm, ::Expr_Tail)
        Expr_Tail::class[GreaterThanOrEqual::class] = listOf(::Oprel, ::Rterm, ::Expr_Tail)
        Expr_Tail::class[GreaterThan::class] = listOf(::Oprel, ::Rterm, ::Expr_Tail)
        Expr_Tail::class[RightBrace::class] = listOf()
        Expr_Tail::class[RightBracket::class] = listOf()
        Expr_Tail::class[SemiColon::class] = listOf()
        Expr_Tail::class[RightParen::class] = listOf()
        Expr_Tail::class[Comma::class] = listOf()


        Rterm::class[IntegerTerminal::class] = listOf(::Term, ::Rterm_Tail)
        Rterm::class[FloatTerminal::class] = listOf(::Term, ::Rterm_Tail)
        Rterm::class[StringTerminal::class] = listOf(::Term, ::Rterm_Tail)
        Rterm::class[IdentifierTerminal::class] = listOf(::Term, ::Rterm_Tail)
        Rterm::class[Asterisk::class] = listOf(::Term, ::Rterm_Tail)
        Rterm::class[Ampersand::class] = listOf(::Term, ::Rterm_Tail)
        Rterm::class[LeftParen::class] = listOf(::Term, ::Rterm_Tail)
        Rterm::class[InputKeyword::class] = listOf(::Term, ::Rterm_Tail)


        Rterm_Tail::class[Plus::class] = listOf(::Opadd, ::Term, ::Rterm_Tail)
        Rterm_Tail::class[Minus::class] = listOf(::Opadd, ::Term, ::Rterm_Tail)
        Rterm_Tail::class[EqualEqual::class] = listOf()
        Rterm_Tail::class[NotEqual::class] = listOf()
        Rterm_Tail::class[LessThan::class] = listOf()
        Rterm_Tail::class[LessThanOrEqual::class] = listOf()
        Rterm_Tail::class[GreaterThanOrEqual::class] = listOf()
        Rterm_Tail::class[GreaterThan::class] = listOf()
        Rterm_Tail::class[RightBrace::class] = listOf()
        Rterm_Tail::class[RightBracket::class] = listOf()
        Rterm_Tail::class[SemiColon::class] = listOf()
        Rterm_Tail::class[Comma::class] = listOf()
        Rterm_Tail::class[RightParen::class] = listOf()


        Term::class[IntegerTerminal::class] = listOf(::Fact, ::Term_Tail)
        Term::class[FloatTerminal::class] = listOf(::Fact, ::Term_Tail)
        Term::class[StringTerminal::class] = listOf(::Fact, ::Term_Tail)
        Term::class[IdentifierTerminal::class] = listOf(::Fact, ::Term_Tail)
        Term::class[Asterisk::class] = listOf(::Fact, ::Term_Tail)
        Term::class[Ampersand::class] = listOf(::Fact, ::Term_Tail)
        Term::class[LeftParen::class] = listOf(::Fact, ::Term_Tail)
        Term::class[InputKeyword::class] = listOf(::Fact, ::Term_Tail)


        Term_Tail::class[Asterisk::class] = listOf(::Opmul, ::Fact, ::Term_Tail)
        Term_Tail::class[ForwardSlash::class] = listOf(::Opmul, ::Fact, ::Term_Tail)
        Term_Tail::class[Caret::class] = listOf(::Opmul, ::Fact, ::Term_Tail)
        Term_Tail::class[EqualEqual::class] = listOf()
        Term_Tail::class[NotEqual::class] = listOf()
        Term_Tail::class[LessThan::class] = listOf()
        Term_Tail::class[LessThanOrEqual::class] = listOf()
        Term_Tail::class[GreaterThanOrEqual::class] = listOf()
        Term_Tail::class[GreaterThan::class] = listOf()
        Term_Tail::class[RightBrace::class] = listOf()
        Term_Tail::class[RightBracket::class] = listOf()
        Term_Tail::class[SemiColon::class] = listOf()
        Term_Tail::class[Comma::class] = listOf()
        Term_Tail::class[RightParen::class] = listOf()
        Term_Tail::class[Plus::class] = listOf()
        Term_Tail::class[Minus::class] = listOf()


        Fact::class[IntegerTerminal::class] = listOf(::BaseLiteral)
        Fact::class[FloatTerminal::class] = listOf(::BaseLiteral)
        Fact::class[StringTerminal::class] = listOf(::BaseLiteral)
        Fact::class[IdentifierTerminal::class] = listOf(::LvalOrFcall)
        Fact::class[Asterisk::class] = listOf(::LvalOrFcall)
        Fact::class[Ampersand::class] = listOf(::Addrof_id)
        Fact::class[LeftParen::class] = listOf(::PPexpr)
        Fact::class[InputKeyword::class] = listOf(::Stinput)


        LvalOrFcall::class[Asterisk::class] = listOf(::Deref_id)
        LvalOrFcall::class[IdentifierTerminal::class] = listOf(::IdentifierTerminal, ::LvalOrFcall_Suffix)


        LvalOrFcall_Suffix::class[LeftBracket::class] = listOf(::Lval_Suffix)
        LvalOrFcall_Suffix::class[LeftParen::class] = listOf(::PPexprs)
        LvalOrFcall_Suffix::class[Asterisk::class] = listOf()
        LvalOrFcall_Suffix::class[ForwardSlash::class] = listOf()
        LvalOrFcall_Suffix::class[Caret::class] = listOf()
        LvalOrFcall_Suffix::class[Plus::class] = listOf()
        LvalOrFcall_Suffix::class[Minus::class] = listOf()
        LvalOrFcall_Suffix::class[EqualEqual::class] = listOf()
        LvalOrFcall_Suffix::class[NotEqual::class] = listOf()
        LvalOrFcall_Suffix::class[LessThan::class] = listOf()
        LvalOrFcall_Suffix::class[LessThanOrEqual::class] = listOf()
        LvalOrFcall_Suffix::class[GreaterThan::class] = listOf()
        LvalOrFcall_Suffix::class[GreaterThanOrEqual::class] = listOf()
        LvalOrFcall_Suffix::class[RightBrace::class] = listOf()
        LvalOrFcall_Suffix::class[RightBracket::class] = listOf()
        LvalOrFcall_Suffix::class[SemiColon::class] = listOf()
        LvalOrFcall_Suffix::class[RightParen::class] = listOf()
        LvalOrFcall_Suffix::class[Comma::class] = listOf()


        BaseLiteral::class[IntegerTerminal::class] = listOf(::IntegerTerminal)
        BaseLiteral::class[FloatTerminal::class] = listOf(::FloatTerminal)
        BaseLiteral::class[StringTerminal::class] = listOf(::StringTerminal)


        Addrof_id::class[Ampersand::class] = listOf(::Ampersand, ::IdentifierTerminal)


        Oprel::class[EqualEqual::class] = listOf(::EqualEqual)
        Oprel::class[NotEqual::class] = listOf(::NotEqual)
        Oprel::class[LessThan::class] = listOf(::Lthan)
        Oprel::class[LessThanOrEqual::class] = listOf(::LessThanOrEqual)
        Oprel::class[GreaterThanOrEqual::class] = listOf(::GreaterThanOrEqual)
        Oprel::class[GreaterThan::class] = listOf(::Gthan)


        Lthan::class[LessThan::class] = listOf(::LessThan)


        Gthan::class[GreaterThan::class] = listOf(::GreaterThan)


        Opadd::class[Plus::class] = listOf(::Plus)
        Opadd::class[Minus::class] = listOf(::Minus)


        Opmul::class[Asterisk::class] = listOf(::Asterisk)
        Opmul::class[ForwardSlash::class] = listOf(::ForwardSlash)
        Opmul::class[Caret::class] = listOf(::Caret)

        return llTable
    }

    private operator fun <NT : NonTerminal, T : Terminal> KClass<NT>.set(first: KClass<T>, rhs: List<NodeSupplier>) {
        llTable.on(this, first).useRHS(rhs)
    }

}
