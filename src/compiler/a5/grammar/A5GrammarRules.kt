package compiler.a5.grammar

import compiler.lexer.token.Token.KeywordToken.*
import compiler.lexer.token.Token.KeywordToken.TypeToken.*
import compiler.lexer.token.Token.OperatorToken.*
import compiler.lexer.token.Token.SymbolToken.*
import compiler.lexer.token.Token.TypedToken.*
import compiler.parser.GrammarNode.*

object A5GrammarRules {
    fun build() {
        Pgm()
                .on(ProgramKeywordToken::class.java)
                .useRHS(::ProgramKeywordToken, ::Vargroup, ::Fcndefs, ::Main)
        Main()
                .on(MainKeywordToken::class.java)
                .useRHS(::MainKeywordToken, ::BBlock)
        BBlock()
                .on(LeftBrace::class.java)
                .useRHS(::LeftBrace, ::Vargroup, ::Stmts, ::RightBrace)
        Vargroup()
                .on(VarKeywordToken::class.java)
                .useRHS(::VarKeywordToken, ::PPvarlist)
                .on(
                        FunctionKeywordToken::class.java,
                        MainKeywordToken::class.java,
                        Asterisk::class.java,
                        IdentifierToken::class.java,
                        IfKeywordToken::class.java,
                        WhileKeywordToken::class.java,
                        PrintKeywordToken::class.java,
                        InputKeywordToken::class.java,
                        ReturnKeywordToken::class.java,
                        RightBrace::class.java,
                        Colon::class.java
                )
                .useRHS()
        PPvarlist()
                .on(LeftParen::class.java)
                .useRHS(::LeftParen, ::Varlist, ::RightParen)
        Varlist()
                .on(
                        IntegerKeywordToken::class.java,
                        FloatKeywordToken::class.java,
                        StringKeywordToken::class.java,
                        IdentifierToken::class.java,
                        ClassKeywordToken::class.java
                )
                .useRHS(::Varitem, ::SemiColon, ::Varlist)
                .on(RightParen::class.java)
                .useRHS()
        Varitem()
                .on(
                        IntegerKeywordToken::class.java,
                        FloatKeywordToken::class.java,
                        StringKeywordToken::class.java,
                        IdentifierToken::class.java
                )
                .useRHS(::Vardecl, ::Varitem_Suffix)
                .on(ClassKeywordToken::class.java)
                .useRHS(::Classdef)
        Varitem_Suffix()
                .on(Equal::class.java)
                .useRHS(::Equal, ::Varinit)
                .on(SemiColon::class.java)
                .useRHS()
        Vardecl()
                .on(
                        IntegerKeywordToken::class.java,
                        FloatKeywordToken::class.java,
                        StringKeywordToken::class.java,
                        IdentifierToken::class.java
                )
                .useRHS(::Simplekind, ::Varspec)
        Simplekind()
                .on(
                        IntegerKeywordToken::class.java,
                        FloatKeywordToken::class.java,
                        StringKeywordToken::class.java
                )
                .useRHS(::BaseKind)
                .on(IdentifierToken::class.java)
                .useRHS(::Classid)
        BaseKind()
                .on(IntegerKeywordToken::class.java)
                .useRHS(::IntegerKeywordToken)
                .on(FloatKeywordToken::class.java)
                .useRHS(::FloatKeywordToken)
                .on(StringKeywordToken::class.java)
                .useRHS(::StringKeywordToken)
        Classid()
                .on(IdentifierToken::class.java)
                .useRHS(::IdentifierToken)
        Varspec()
                .on(IdentifierToken::class.java)
                .useRHS(::Varid, ::Arrspec)
                .on(Asterisk::class.java)
                .useRHS(::Deref_id)
        Varid()
                .on(IdentifierToken::class.java)
                .useRHS(::IdentifierToken)
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
                .useRHS(::LeftBracket, ::IntegerToken, ::RightBracket)
        Deref_id()
                .on(Asterisk::class.java)
                .useRHS(::Deref, ::IdentifierToken)
        Deref()
                .on(Asterisk::class.java)
                .useRHS(::Asterisk)
        Varinit()
                .on(
                        IntegerToken::class.java,
                        FloatToken::class.java,
                        StringToken::class.java,
                        Asterisk::class.java,
                        IdentifierToken::class.java,
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
                        IntegerToken::class.java,
                        FloatToken::class.java,
                        StringToken::class.java,
                        Asterisk::class.java,
                        IdentifierToken::class.java,
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
                .on(ClassKeywordToken::class.java)
                .useRHS(::Classheader, ::Classdef_Suffix)
        Classdef_Suffix()
                .on(LeftBrace::class.java)
                .useRHS(::BBClassitems)
                .on(IfKeywordToken::class.java)
                .useRHS(::IfKeywordToken, ::BBClassitems)
        BBClassitems()
                .on(LeftBrace::class.java)
                .useRHS(::LeftBrace, ::Classitems, ::RightBrace)
        Classheader()
                .on(ClassKeywordToken::class.java)
                .useRHS(::ClassKeywordToken, ::Classid, ::Classmom, ::Interfaces)
        Classmom()
                .on(Colon::class.java)
                .useRHS(::Colon, ::Classid)
                .on(
                        Plus::class.java,
                        LeftBrace::class.java,
                        IfKeywordToken::class.java
                )
                .useRHS()
        Classitems()
                .on(Colon::class.java, VarKeywordToken::class.java, FunctionKeywordToken::class.java)
                .useRHS(::Classgroup, ::Classitems)
                .on(RightBrace::class.java)
                .useRHS()
        Classgroup()
                .on(Colon::class.java)
                .useRHS(::Class_ctrl)
                .on(VarKeywordToken::class.java)
                .useRHS(::Vargroup)
                .on(FunctionKeywordToken::class.java)
                .useRHS(::Mddecls)
        Class_ctrl()
                .on(Colon::class.java)
                .useRHS(::Colon, ::IdentifierToken)
        Interfaces()
                .on(Plus::class.java)
                .useRHS(::Plus, ::Classid, ::Interfaces)
                .on(LeftBrace::class.java, IfKeywordToken::class.java)
                .useRHS()
        Mddecls()
                .on(FunctionKeywordToken::class.java)
                .useRHS(::Mdheader, ::Mddecls)
                .on(
                        SemiColon::class.java,
                        VarKeywordToken::class.java,
                        RightBrace::class.java
                )
                .useRHS()
        Mdheader()
                .on(FunctionKeywordToken::class.java)
                .useRHS(::FunctionKeywordToken, ::Md_id, ::PParmlist, ::Retkind)
        Md_id()
                .on(IdentifierToken::class.java)
                .useRHS(::Classid, ::Colon, ::Fcnid)
        Fcndefs()
                .on(FunctionKeywordToken::class.java)
                .useRHS(::Fcndef, ::Fcndefs)
                .on(MainKeywordToken::class.java)
                .useRHS()
        Fcndef()
                .on(FunctionKeywordToken::class.java)
                .useRHS(::Fcnheader, ::BBlock)
        Fcnheader()
                .on(FunctionKeywordToken::class.java)
                .useRHS(::FunctionKeywordToken, ::Fcnid, ::PParmlist, ::Retkind)
        Fcnid()
                .on(IdentifierToken::class.java)
                .useRHS(::IdentifierToken)
        Retkind()
                .on(
                        IntegerKeywordToken::class.java,
                        FloatKeywordToken::class.java,
                        StringKeywordToken::class.java
                )
                .useRHS(::BaseKind)
        PParmlist()
                .on(LeftParen::class.java)
                .useRHS(::LeftParen, ::Varspecs, ::RightParen)
        Varspecs()
                .on(Asterisk::class.java, IdentifierToken::class.java)
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
                        IdentifierToken::class.java,
                        IfKeywordToken::class.java,
                        WhileKeywordToken::class.java,
                        PrintKeywordToken::class.java,
                        InputKeywordToken::class.java,
                        ReturnKeywordToken::class.java
                )
                .useRHS(::Stmt, ::SemiColon, ::Stmts)
                .on(RightBrace::class.java)
                .useRHS()
        Stmt()
                .on(IdentifierToken::class.java, Asterisk::class.java)
                .useRHS(::StasgnOrFcall)
                .on(IfKeywordToken::class.java)
                .useRHS(::Stif)
                .on(WhileKeywordToken::class.java)
                .useRHS(::Stwhile)
                .on(PrintKeywordToken::class.java)
                .useRHS(::Stprint)
                .on(InputKeywordToken::class.java)
                .useRHS(::Stinput)
                .on(ReturnKeywordToken::class.java)
                .useRHS(::Strtn)
        StasgnOrFcall()
                .on(Asterisk::class.java)
                .useRHS(::Deref_id, ::Stasgn_Suffix)
                .on(IdentifierToken::class.java)
                .useRHS(::IdentifierToken, ::StasgnOrFcall_Suffix)
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
                .on(IfKeywordToken::class.java)
                .useRHS(::IfKeywordToken, ::PPexpr, ::BBlock, ::Elsepart)
        Elsepart()
                .on(ElseIfKeywordToken::class.java)
                .useRHS(::ElseIfKeywordToken, ::PPexpr, ::BBlock, ::Elsepart)
                .on(ElseKeywordToken::class.java)
                .useRHS(::ElseKeywordToken, ::BBlock)
                .on(SemiColon::class.java)
                .useRHS()
        Stwhile()
                .on(WhileKeywordToken::class.java)
                .useRHS(::WhileKeywordToken, ::PPexpr, ::BBlock)
        Stprint()
                .on(PrintKeywordToken::class.java)
                .useRHS(::PrintKeywordToken, ::PPexprs)
        Stinput()
                .on(InputKeywordToken::class.java)
                .useRHS(::InputKeywordToken, ::PPexprs)
        Strtn()
                .on(ReturnKeywordToken::class.java)
                .useRHS(::ReturnKeywordToken, ::Strtn_Suffix)
        Strtn_Suffix()
                .on(
                        IntegerToken::class.java,
                        FloatToken::class.java,
                        StringToken::class.java,
                        IdentifierToken::class.java,
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
                        IntegerToken::class.java,
                        FloatToken::class.java,
                        StringToken::class.java,
                        IdentifierToken::class.java,
                        Asterisk::class.java,
                        Ampersand::class.java,
                        LeftParen::class.java,
                        InputKeywordToken::class.java
                )
                .useRHS(::Rterm, ::Expr_Tail)
        Expr_Tail()
                .on(EqualEqual::class.java, NotEqual::class.java, LessThan::class.java, LessThanOrEqual::class.java, GreaterThanOrEqual::class.java, GreaterThan::class.java)
                .useRHS(::Oprel, ::Rterm, ::Expr_Tail)
                .on(RightBrace::class.java, RightBracket::class.java, SemiColon::class.java, RightParen::class.java, Comma::class.java)
                .useRHS()
        Rterm()
                .on(
                        IntegerToken::class.java,
                        FloatToken::class.java,
                        StringToken::class.java,
                        IdentifierToken::class.java,
                        Asterisk::class.java,
                        Ampersand::class.java,
                        LeftParen::class.java,
                        InputKeywordToken::class.java
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
                        IntegerToken::class.java,
                        FloatToken::class.java,
                        StringToken::class.java,
                        IdentifierToken::class.java,
                        Asterisk::class.java,
                        Ampersand::class.java,
                        LeftParen::class.java,
                        InputKeywordToken::class.java
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
                .on(IntegerToken::class.java, FloatToken::class.java, StringToken::class.java)
                .useRHS(::BaseLiteral)
                .on(IdentifierToken::class.java, Asterisk::class.java)
                .useRHS(::LvalOrFcall)
                .on(Ampersand::class.java)
                .useRHS(::Addrof_id)
                .on(LeftParen::class.java)
                .useRHS(::PPexpr)
                .on(InputKeywordToken::class.java)
                .useRHS(::Stinput)
        LvalOrFcall()
                .on(Asterisk::class.java)
                .useRHS(::Deref_id)
                .on(IdentifierToken::class.java)
                .useRHS(::IdentifierToken, ::LvalOrFcall_Suffix)
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
                .on(IntegerToken::class.java)
                .useRHS(::IntegerToken)
                .on(FloatToken::class.java)
                .useRHS(::FloatToken)
                .on(StringToken::class.java)
                .useRHS(::StringToken)
        Addrof_id()
                .on(Ampersand::class.java)
                .useRHS(::Ampersand, ::IdentifierToken)
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
