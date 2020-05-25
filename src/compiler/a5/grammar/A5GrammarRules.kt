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

        llTable.on(Pgm(), ProgramKeyword::class).useRHS(::ProgramKeyword, ::Vargroup, ::Fcndefs, ::Main)


        llTable.on(Main(), MainKeyword::class).useRHS(::MainKeyword, ::BBlock)


        llTable.on(BBlock(), LeftBrace::class).useRHS(::LeftBrace, ::Vargroup, ::Stmts, ::RightBrace)


        llTable.on(Vargroup(), VarKeyword::class).useRHS(::VarKeyword, ::PPvarlist)
        llTable.on(Vargroup(), FunctionKeyword::class).useRHS()
        llTable.on(Vargroup(), MainKeyword::class).useRHS()
        llTable.on(Vargroup(), Asterisk::class).useRHS()
        llTable.on(Vargroup(), IdentifierTerminal::class).useRHS()
        llTable.on(Vargroup(), IfKeyword::class).useRHS()
        llTable.on(Vargroup(), WhileKeyword::class).useRHS()
        llTable.on(Vargroup(), PrintKeyword::class).useRHS()
        llTable.on(Vargroup(), InputKeyword::class).useRHS()
        llTable.on(Vargroup(), ReturnKeyword::class).useRHS()
        llTable.on(Vargroup(), RightBrace::class).useRHS()
        llTable.on(Vargroup(), Colon::class).useRHS()


        llTable.on(PPvarlist(), LeftParen::class).useRHS(::LeftParen, ::Varlist, ::RightParen)


        llTable.on(Varlist(), IntegerKeyword::class).useRHS(::Varitem, ::SemiColon, ::Varlist)
        llTable.on(Varlist(), FloatKeyword::class).useRHS(::Varitem, ::SemiColon, ::Varlist)
        llTable.on(Varlist(), StringKeyword::class).useRHS(::Varitem, ::SemiColon, ::Varlist)
        llTable.on(Varlist(), IdentifierTerminal::class).useRHS(::Varitem, ::SemiColon, ::Varlist)
        llTable.on(Varlist(), ClassKeyword::class).useRHS(::Varitem, ::SemiColon, ::Varlist)
        llTable.on(Varlist(), RightParen::class).useRHS()
        llTable.on(Varitem(), IntegerKeyword::class).useRHS(::Vardecl, ::Varitem_Suffix)
        llTable.on(Varitem(), FloatKeyword::class).useRHS(::Vardecl, ::Varitem_Suffix)
        llTable.on(Varitem(), StringKeyword::class).useRHS(::Vardecl, ::Varitem_Suffix)
        llTable.on(Varitem(), IdentifierTerminal::class).useRHS(::Vardecl, ::Varitem_Suffix)
        llTable.on(Varitem(), ClassKeyword::class).useRHS(::Classdef)


        llTable.on(Varitem_Suffix(), Equal::class).useRHS(::Equal, ::Varinit)
        llTable.on(Varitem_Suffix(), SemiColon::class).useRHS()


        llTable.on(Vardecl(), IntegerKeyword::class).useRHS(::Simplekind, ::Varspec)
        llTable.on(Vardecl(), FloatKeyword::class).useRHS(::Simplekind, ::Varspec)
        llTable.on(Vardecl(), StringKeyword::class).useRHS(::Simplekind, ::Varspec)
        llTable.on(Vardecl(), IdentifierTerminal::class).useRHS(::Simplekind, ::Varspec)


        llTable.on(Simplekind(), IntegerKeyword::class).useRHS(::BaseKind)
        llTable.on(Simplekind(), FloatKeyword::class).useRHS(::BaseKind)
        llTable.on(Simplekind(), StringKeyword::class).useRHS(::BaseKind)
        llTable.on(Simplekind(), IdentifierTerminal::class).useRHS(::Classid)


        llTable.on(BaseKind(), IntegerKeyword::class).useRHS(::IntegerKeyword)
        llTable.on(BaseKind(), FloatKeyword::class).useRHS(::FloatKeyword)
        llTable.on(BaseKind(), StringKeyword::class).useRHS(::StringKeyword)


        llTable.on(Classid(), IdentifierTerminal::class).useRHS(::IdentifierTerminal)


        llTable.on(Varspec(), IdentifierTerminal::class).useRHS(::Varid, ::Arrspec)
        llTable.on(Varspec(), Asterisk::class).useRHS(::Deref_id)


        llTable.on(Varid(), IdentifierTerminal::class).useRHS(::IdentifierTerminal)


        llTable.on(Arrspec(), LeftBracket::class).useRHS(::KKint)
        llTable.on(Arrspec(), Equal::class).useRHS()
        llTable.on(Arrspec(), SemiColon::class).useRHS()
        llTable.on(Arrspec(), Comma::class).useRHS()
        llTable.on(Arrspec(), RightParen::class).useRHS()


        llTable.on(KKint(), LeftBracket::class).useRHS(::LeftBracket, ::IntegerTerminal, ::RightBracket)


        llTable.on(Deref_id(), Asterisk::class).useRHS(::Deref, ::IdentifierTerminal)


        llTable.on(Deref(), Asterisk::class).useRHS(::Asterisk)


        llTable.on(Varinit(), IntegerTerminal::class).useRHS(::Expr)
        llTable.on(Varinit(), FloatTerminal::class).useRHS(::Expr)
        llTable.on(Varinit(), StringTerminal::class).useRHS(::Expr)
        llTable.on(Varinit(), Asterisk::class).useRHS(::Expr)
        llTable.on(Varinit(), IdentifierTerminal::class).useRHS(::Expr)
        llTable.on(Varinit(), Ampersand::class).useRHS(::Expr)
        llTable.on(Varinit(), LeftParen::class).useRHS(::Expr)
        llTable.on(Varinit(), LeftBrace::class).useRHS(::BBexprs)


        llTable.on(BBexprs(), LeftBrace::class).useRHS(::LeftBrace, ::Exprlist, ::RightBrace)


        llTable.on(Exprlist(), IntegerTerminal::class).useRHS(::Expr, ::Moreexprs)
        llTable.on(Exprlist(), FloatTerminal::class).useRHS(::Expr, ::Moreexprs)
        llTable.on(Exprlist(), StringTerminal::class).useRHS(::Expr, ::Moreexprs)
        llTable.on(Exprlist(), Asterisk::class).useRHS(::Expr, ::Moreexprs)
        llTable.on(Exprlist(), IdentifierTerminal::class).useRHS(::Expr, ::Moreexprs)
        llTable.on(Exprlist(), Ampersand::class).useRHS(::Expr, ::Moreexprs)
        llTable.on(Exprlist(), LeftParen::class).useRHS(::Expr, ::Moreexprs)
        llTable.on(Exprlist(), RightBrace::class).useRHS()
        llTable.on(Exprlist(), RightParen::class).useRHS()


        llTable.on(Moreexprs(), Comma::class).useRHS(::Comma, ::Exprlist)
        llTable.on(Moreexprs(), RightBrace::class).useRHS()
        llTable.on(Moreexprs(), RightParen::class).useRHS()


        llTable.on(Classdef(), ClassKeyword::class).useRHS(::Classheader, ::Classdef_Suffix)


        llTable.on(Classdef_Suffix(), LeftBrace::class).useRHS(::BBClassitems)
        llTable.on(Classdef_Suffix(), IfKeyword::class).useRHS(::IfKeyword, ::BBClassitems)


        llTable.on(BBClassitems(), LeftBrace::class).useRHS(::LeftBrace, ::Classitems, ::RightBrace)


        llTable.on(Classheader(), ClassKeyword::class).useRHS(::ClassKeyword, ::Classid, ::Classmom, ::Interfaces)


        llTable.on(Classmom(), Colon::class).useRHS(::Colon, ::Classid)
        llTable.on(Classmom(), Plus::class).useRHS()
        llTable.on(Classmom(), LeftBrace::class).useRHS()
        llTable.on(Classmom(), IfKeyword::class).useRHS()


        llTable.on(Classitems(), Colon::class).useRHS(::Classgroup, ::Classitems)
        llTable.on(Classitems(), VarKeyword::class).useRHS(::Classgroup, ::Classitems)
        llTable.on(Classitems(), FunctionKeyword::class).useRHS(::Classgroup, ::Classitems)
        llTable.on(Classitems(), RightBrace::class).useRHS()


        llTable.on(Classgroup(), Colon::class).useRHS(::Class_ctrl)
        llTable.on(Classgroup(), VarKeyword::class).useRHS(::Vargroup)
        llTable.on(Classgroup(), FunctionKeyword::class).useRHS(::Mddecls)


        llTable.on(Class_ctrl(), Colon::class).useRHS(::Colon, ::IdentifierTerminal)


        llTable.on(Interfaces(), Plus::class).useRHS(::Plus, ::Classid, ::Interfaces)
        llTable.on(Interfaces(), LeftBrace::class).useRHS()
        llTable.on(Interfaces(), IfKeyword::class).useRHS()


        llTable.on(Mddecls(), FunctionKeyword::class).useRHS(::Mdheader, ::Mddecls)
        llTable.on(Mddecls(), SemiColon::class).useRHS()
        llTable.on(Mddecls(), VarKeyword::class).useRHS()
        llTable.on(Mddecls(), RightBrace::class).useRHS()


        llTable.on(Mdheader(), FunctionKeyword::class).useRHS(::FunctionKeyword, ::Md_id, ::PParmlist, ::Retkind)


        llTable.on(Md_id(), IdentifierTerminal::class).useRHS(::Classid, ::Colon, ::Fcnid)


        llTable.on(Fcndefs(), FunctionKeyword::class).useRHS(::Fcndef, ::Fcndefs)
        llTable.on(Fcndefs(), MainKeyword::class).useRHS()


        llTable.on(Fcndef(), FunctionKeyword::class).useRHS(::Fcnheader, ::BBlock)


        llTable.on(Fcnheader(), FunctionKeyword::class).useRHS(::FunctionKeyword, ::Fcnid, ::PParmlist, ::Retkind)


        llTable.on(Fcnid(), IdentifierTerminal::class).useRHS(::IdentifierTerminal)


        llTable.on(Retkind(), IntegerKeyword::class, FloatKeyword::class, StringKeyword::class).useRHS(::BaseKind)


        llTable.on(PParmlist(), LeftParen::class).useRHS(::LeftParen, ::Varspecs, ::RightParen)


        llTable.on(Varspecs(), Asterisk::class).useRHS(::Varspec, ::More_varspecs)
        llTable.on(Varspecs(), IdentifierTerminal::class).useRHS(::Varspec, ::More_varspecs)
        llTable.on(Varspecs(), RightParen::class).useRHS()


        llTable.on(More_varspecs(), Comma::class).useRHS(::Comma, ::Varspecs)
        llTable.on(More_varspecs(), RightParen::class).useRHS()


        llTable.on(Stmts(), Asterisk::class).useRHS(::Stmt, ::SemiColon, ::Stmts)
        llTable.on(Stmts(), IdentifierTerminal::class).useRHS(::Stmt, ::SemiColon, ::Stmts)
        llTable.on(Stmts(), IfKeyword::class).useRHS(::Stmt, ::SemiColon, ::Stmts)
        llTable.on(Stmts(), WhileKeyword::class).useRHS(::Stmt, ::SemiColon, ::Stmts)
        llTable.on(Stmts(), PrintKeyword::class).useRHS(::Stmt, ::SemiColon, ::Stmts)
        llTable.on(Stmts(), InputKeyword::class).useRHS(::Stmt, ::SemiColon, ::Stmts)
        llTable.on(Stmts(), ReturnKeyword::class).useRHS(::Stmt, ::SemiColon, ::Stmts)
        llTable.on(Stmts(), RightBrace::class).useRHS()


        llTable.on(Stmt(), IdentifierTerminal::class).useRHS(::StasgnOrFcall)
        llTable.on(Stmt(), Asterisk::class).useRHS(::StasgnOrFcall)
        llTable.on(Stmt(), IfKeyword::class).useRHS(::Stif)
        llTable.on(Stmt(), WhileKeyword::class).useRHS(::Stwhile)
        llTable.on(Stmt(), PrintKeyword::class).useRHS(::Stprint)
        llTable.on(Stmt(), InputKeyword::class).useRHS(::Stinput)
        llTable.on(Stmt(), ReturnKeyword::class).useRHS(::Strtn)


        llTable.on(StasgnOrFcall(), Asterisk::class).useRHS(::Deref_id, ::Stasgn_Suffix)
        llTable.on(StasgnOrFcall(), IdentifierTerminal::class).useRHS(::IdentifierTerminal, ::StasgnOrFcall_Suffix)


        llTable.on(StasgnOrFcall_Suffix(), LeftBracket::class).useRHS(::Lval_Suffix, ::Stasgn_Suffix)
        llTable.on(StasgnOrFcall_Suffix(), Equal::class).useRHS(::Lval_Suffix, ::Stasgn_Suffix)
        llTable.on(StasgnOrFcall_Suffix(), LeftParen::class).useRHS(::PPexprs)


        llTable.on(Stasgn_Suffix(), Equal::class).useRHS(::Equal, ::Expr)


        llTable.on(Lval_Suffix(), LeftBracket::class).useRHS(::KKexpr)
        llTable.on(Lval_Suffix(), Equal::class).useRHS()
        llTable.on(Lval_Suffix(), Asterisk::class).useRHS()
        llTable.on(Lval_Suffix(), ForwardSlash::class).useRHS()
        llTable.on(Lval_Suffix(), Caret::class).useRHS()
        llTable.on(Lval_Suffix(), Plus::class).useRHS()
        llTable.on(Lval_Suffix(), Minus::class).useRHS()
        llTable.on(Lval_Suffix(), EqualEqual::class).useRHS()
        llTable.on(Lval_Suffix(), NotEqual::class).useRHS()
        llTable.on(Lval_Suffix(), LessThan::class).useRHS()
        llTable.on(Lval_Suffix(), LessThanOrEqual::class).useRHS()
        llTable.on(Lval_Suffix(), GreaterThanOrEqual::class).useRHS()
        llTable.on(Lval_Suffix(), GreaterThan::class).useRHS()
        llTable.on(Lval_Suffix(), RightBrace::class).useRHS()
        llTable.on(Lval_Suffix(), RightBracket::class).useRHS()
        llTable.on(Lval_Suffix(), SemiColon::class).useRHS()
        llTable.on(Lval_Suffix(), RightParen::class).useRHS()
        llTable.on(Lval_Suffix(), Comma::class).useRHS()


        llTable.on(KKexpr(), LeftBracket::class).useRHS(::LeftBracket, ::Expr, ::RightBracket)


        llTable.on(PPexprs(), LeftParen::class).useRHS(::LeftParen, ::Exprlist, ::RightParen)
        llTable.on(PPexprs()).useRHS(::PPonly)


        llTable.on(Stif(), IfKeyword::class).useRHS(::IfKeyword, ::PPexpr, ::BBlock, ::Elsepart)


        llTable.on(Elsepart(), ElseIfKeyword::class).useRHS(::ElseIfKeyword, ::PPexpr, ::BBlock, ::Elsepart)
        llTable.on(Elsepart(), ElseKeyword::class).useRHS(::ElseKeyword, ::BBlock)
        llTable.on(Elsepart(), SemiColon::class).useRHS()


        llTable.on(Stwhile(), WhileKeyword::class).useRHS(::WhileKeyword, ::PPexpr, ::BBlock)


        llTable.on(Stprint(), PrintKeyword::class).useRHS(::PrintKeyword, ::PPexprs)


        llTable.on(Stinput(), InputKeyword::class).useRHS(::InputKeyword, ::PPexprs)


        llTable.on(Strtn(), ReturnKeyword::class).useRHS(::ReturnKeyword, ::Strtn_Suffix)


        llTable.on(Strtn_Suffix(), IntegerTerminal::class).useRHS(::Expr)
        llTable.on(Strtn_Suffix(), FloatTerminal::class).useRHS(::Expr)
        llTable.on(Strtn_Suffix(), StringTerminal::class).useRHS(::Expr)
        llTable.on(Strtn_Suffix(), IdentifierTerminal::class).useRHS(::Expr)
        llTable.on(Strtn_Suffix(), Asterisk::class).useRHS(::Expr)
        llTable.on(Strtn_Suffix(), Ampersand::class).useRHS(::Expr)
        llTable.on(Strtn_Suffix(), LeftParen::class).useRHS(::Expr)
        llTable.on(Strtn_Suffix(), SemiColon::class).useRHS()


        llTable.on(PPexpr(), LeftParen::class).useRHS(::LeftParen, ::Expr, ::RightParen)


        llTable.on(Expr(), IntegerTerminal::class).useRHS(::Rterm, ::Expr_Tail)
        llTable.on(Expr(), FloatTerminal::class).useRHS(::Rterm, ::Expr_Tail)
        llTable.on(Expr(), StringTerminal::class).useRHS(::Rterm, ::Expr_Tail)
        llTable.on(Expr(), IdentifierTerminal::class).useRHS(::Rterm, ::Expr_Tail)
        llTable.on(Expr(), Asterisk::class).useRHS(::Rterm, ::Expr_Tail)
        llTable.on(Expr(), Ampersand::class).useRHS(::Rterm, ::Expr_Tail)
        llTable.on(Expr(), LeftParen::class).useRHS(::Rterm, ::Expr_Tail)
        llTable.on(Expr(), InputKeyword::class).useRHS(::Rterm, ::Expr_Tail)


        llTable.on(Expr_Tail(), EqualEqual::class).useRHS(::Oprel, ::Rterm, ::Expr_Tail)
        llTable.on(Expr_Tail(), NotEqual::class).useRHS(::Oprel, ::Rterm, ::Expr_Tail)
        llTable.on(Expr_Tail(), LessThan::class).useRHS(::Oprel, ::Rterm, ::Expr_Tail)
        llTable.on(Expr_Tail(), LessThanOrEqual::class).useRHS(::Oprel, ::Rterm, ::Expr_Tail)
        llTable.on(Expr_Tail(), GreaterThanOrEqual::class).useRHS(::Oprel, ::Rterm, ::Expr_Tail)
        llTable.on(Expr_Tail(), GreaterThan::class).useRHS(::Oprel, ::Rterm, ::Expr_Tail)
        llTable.on(Expr_Tail(), RightBrace::class).useRHS()
        llTable.on(Expr_Tail(), RightBracket::class).useRHS()
        llTable.on(Expr_Tail(), SemiColon::class).useRHS()
        llTable.on(Expr_Tail(), RightParen::class).useRHS()
        llTable.on(Expr_Tail(), Comma::class).useRHS()


        llTable.on(Rterm(), IntegerTerminal::class).useRHS(::Term, ::Rterm_Tail)
        llTable.on(Rterm(), FloatTerminal::class).useRHS(::Term, ::Rterm_Tail)
        llTable.on(Rterm(), StringTerminal::class).useRHS(::Term, ::Rterm_Tail)
        llTable.on(Rterm(), IdentifierTerminal::class).useRHS(::Term, ::Rterm_Tail)
        llTable.on(Rterm(), Asterisk::class).useRHS(::Term, ::Rterm_Tail)
        llTable.on(Rterm(), Ampersand::class).useRHS(::Term, ::Rterm_Tail)
        llTable.on(Rterm(), LeftParen::class).useRHS(::Term, ::Rterm_Tail)
        llTable.on(Rterm(), InputKeyword::class).useRHS(::Term, ::Rterm_Tail)


        llTable.on(Rterm_Tail(), Plus::class).useRHS(::Opadd, ::Term, ::Rterm_Tail)
        llTable.on(Rterm_Tail(), Minus::class).useRHS(::Opadd, ::Term, ::Rterm_Tail)
        llTable.on(Rterm_Tail(), EqualEqual::class).useRHS()
        llTable.on(Rterm_Tail(), NotEqual::class).useRHS()
        llTable.on(Rterm_Tail(), LessThan::class).useRHS()
        llTable.on(Rterm_Tail(), LessThanOrEqual::class).useRHS()
        llTable.on(Rterm_Tail(), GreaterThanOrEqual::class).useRHS()
        llTable.on(Rterm_Tail(), GreaterThan::class).useRHS()
        llTable.on(Rterm_Tail(), RightBrace::class).useRHS()
        llTable.on(Rterm_Tail(), RightBracket::class).useRHS()
        llTable.on(Rterm_Tail(), SemiColon::class).useRHS()
        llTable.on(Rterm_Tail(), Comma::class).useRHS()
        llTable.on(Rterm_Tail(), RightParen::class).useRHS()


        llTable.on(Term(), IntegerTerminal::class).useRHS(::Fact, ::Term_Tail)
        llTable.on(Term(), FloatTerminal::class).useRHS(::Fact, ::Term_Tail)
        llTable.on(Term(), StringTerminal::class).useRHS(::Fact, ::Term_Tail)
        llTable.on(Term(), IdentifierTerminal::class).useRHS(::Fact, ::Term_Tail)
        llTable.on(Term(), Asterisk::class).useRHS(::Fact, ::Term_Tail)
        llTable.on(Term(), Ampersand::class).useRHS(::Fact, ::Term_Tail)
        llTable.on(Term(), LeftParen::class).useRHS(::Fact, ::Term_Tail)
        llTable.on(Term(), InputKeyword::class).useRHS(::Fact, ::Term_Tail)


        llTable.on(Term_Tail(), Asterisk::class).useRHS(::Opmul, ::Fact, ::Term_Tail)
        llTable.on(Term_Tail(), ForwardSlash::class).useRHS(::Opmul, ::Fact, ::Term_Tail)
        llTable.on(Term_Tail(), Caret::class).useRHS(::Opmul, ::Fact, ::Term_Tail)
        llTable.on(Term_Tail(), EqualEqual::class).useRHS()
        llTable.on(Term_Tail(), NotEqual::class).useRHS()
        llTable.on(Term_Tail(), LessThan::class).useRHS()
        llTable.on(Term_Tail(), LessThanOrEqual::class).useRHS()
        llTable.on(Term_Tail(), GreaterThanOrEqual::class).useRHS()
        llTable.on(Term_Tail(), GreaterThan::class).useRHS()
        llTable.on(Term_Tail(), RightBrace::class).useRHS()
        llTable.on(Term_Tail(), RightBracket::class).useRHS()
        llTable.on(Term_Tail(), SemiColon::class).useRHS()
        llTable.on(Term_Tail(), Comma::class).useRHS()
        llTable.on(Term_Tail(), RightParen::class).useRHS()
        llTable.on(Term_Tail(), Plus::class).useRHS()
        llTable.on(Term_Tail(), Minus::class).useRHS()


        llTable.on(Fact(), IntegerTerminal::class).useRHS(::BaseLiteral)
        llTable.on(Fact(), FloatTerminal::class).useRHS(::BaseLiteral)
        llTable.on(Fact(), StringTerminal::class).useRHS(::BaseLiteral)
        llTable.on(Fact(), IdentifierTerminal::class).useRHS(::LvalOrFcall)
        llTable.on(Fact(), Asterisk::class).useRHS(::LvalOrFcall)
        llTable.on(Fact(), Ampersand::class).useRHS(::Addrof_id)
        llTable.on(Fact(), LeftParen::class).useRHS(::PPexpr)
        llTable.on(Fact(), InputKeyword::class).useRHS(::Stinput)


        llTable.on(LvalOrFcall(), Asterisk::class).useRHS(::Deref_id)
        llTable.on(LvalOrFcall(), IdentifierTerminal::class).useRHS(::IdentifierTerminal, ::LvalOrFcall_Suffix)


        llTable.on(LvalOrFcall_Suffix(), LeftBracket::class).useRHS(::Lval_Suffix)
        llTable.on(LvalOrFcall_Suffix(), LeftParen::class).useRHS(::PPexprs)
        llTable.on(LvalOrFcall_Suffix(), Asterisk::class).useRHS()
        llTable.on(LvalOrFcall_Suffix(), ForwardSlash::class).useRHS()
        llTable.on(LvalOrFcall_Suffix(), Caret::class).useRHS()
        llTable.on(LvalOrFcall_Suffix(), Plus::class).useRHS()
        llTable.on(LvalOrFcall_Suffix(), Minus::class).useRHS()
        llTable.on(LvalOrFcall_Suffix(), EqualEqual::class).useRHS()
        llTable.on(LvalOrFcall_Suffix(), NotEqual::class).useRHS()
        llTable.on(LvalOrFcall_Suffix(), LessThan::class).useRHS()
        llTable.on(LvalOrFcall_Suffix(), LessThanOrEqual::class).useRHS()
        llTable.on(LvalOrFcall_Suffix(), GreaterThan::class).useRHS()
        llTable.on(LvalOrFcall_Suffix(), GreaterThanOrEqual::class).useRHS()
        llTable.on(LvalOrFcall_Suffix(), RightBrace::class).useRHS()
        llTable.on(LvalOrFcall_Suffix(), RightBracket::class).useRHS()
        llTable.on(LvalOrFcall_Suffix(), SemiColon::class).useRHS()
        llTable.on(LvalOrFcall_Suffix(), RightParen::class).useRHS()
        llTable.on(LvalOrFcall_Suffix(), Comma::class).useRHS()


        llTable.on(BaseLiteral(), IntegerTerminal::class).useRHS(::IntegerTerminal)
        llTable.on(BaseLiteral(), FloatTerminal::class).useRHS(::FloatTerminal)
        llTable.on(BaseLiteral(), StringTerminal::class).useRHS(::StringTerminal)


        llTable.on(Addrof_id(), Ampersand::class).useRHS(::Ampersand, ::IdentifierTerminal)


        llTable.on(Oprel(), EqualEqual::class).useRHS(::EqualEqual)
        llTable.on(Oprel(), NotEqual::class).useRHS(::NotEqual)
        llTable.on(Oprel(), LessThan::class).useRHS(::Lthan)
        llTable.on(Oprel(), LessThanOrEqual::class).useRHS(::LessThanOrEqual)
        llTable.on(Oprel(), GreaterThanOrEqual::class).useRHS(::GreaterThanOrEqual)
        llTable.on(Oprel(), GreaterThan::class).useRHS(::Gthan)


        llTable.on(Lthan(), LessThan::class).useRHS(::LessThan)


        llTable.on(Gthan(), GreaterThan::class).useRHS(::GreaterThan)


        llTable.on(Opadd(), Plus::class).useRHS(::Plus)
        llTable.on(Opadd(), Minus::class).useRHS(::Minus)


        llTable.on(Opmul(), Asterisk::class).useRHS(::Asterisk)
        llTable.on(Opmul(), ForwardSlash::class).useRHS(::ForwardSlash)
        llTable.on(Opmul(), Caret::class).useRHS(::Caret)

        return llTable
    }
}
