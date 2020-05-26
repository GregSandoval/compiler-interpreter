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

        Program::class[ProgramKeyword::class] = listOf(::ProgramKeyword, ::VariableGroup, ::FunctionDefinitions, ::Main)


        Main::class[MainKeyword::class] = listOf(::MainKeyword, ::BasicBlock)


        BasicBlock::class[LeftBrace::class] = listOf(::LeftBrace, ::VariableGroup, ::Statements, ::RightBrace)


        VariableGroup::class[VarKeyword::class] = listOf(::VarKeyword, ::ParenthesizedVariabledList)
        VariableGroup::class[FunctionKeyword::class] = listOf()
        VariableGroup::class[MainKeyword::class] = listOf()
        VariableGroup::class[Asterisk::class] = listOf()
        VariableGroup::class[IdentifierTerminal::class] = listOf()
        VariableGroup::class[IfKeyword::class] = listOf()
        VariableGroup::class[WhileKeyword::class] = listOf()
        VariableGroup::class[PrintKeyword::class] = listOf()
        VariableGroup::class[InputKeyword::class] = listOf()
        VariableGroup::class[ReturnKeyword::class] = listOf()
        VariableGroup::class[RightBrace::class] = listOf()
        VariableGroup::class[Colon::class] = listOf()


        ParenthesizedVariabledList::class[LeftParen::class] = listOf(::LeftParen, ::VariableList, ::RightParen)


        VariableList::class[IntegerKeyword::class] = listOf(::VariableItem, ::SemiColon, ::VariableList)
        VariableList::class[FloatKeyword::class] = listOf(::VariableItem, ::SemiColon, ::VariableList)
        VariableList::class[StringKeyword::class] = listOf(::VariableItem, ::SemiColon, ::VariableList)
        VariableList::class[IdentifierTerminal::class] = listOf(::VariableItem, ::SemiColon, ::VariableList)
        VariableList::class[ClassKeyword::class] = listOf(::VariableItem, ::SemiColon, ::VariableList)
        VariableList::class[RightParen::class] = listOf()


        VariableItem::class[IntegerKeyword::class] = listOf(::VariableDecleration, ::VariableItemSuffix)
        VariableItem::class[FloatKeyword::class] = listOf(::VariableDecleration, ::VariableItemSuffix)
        VariableItem::class[StringKeyword::class] = listOf(::VariableDecleration, ::VariableItemSuffix)
        VariableItem::class[IdentifierTerminal::class] = listOf(::VariableDecleration, ::VariableItemSuffix)
        VariableItem::class[ClassKeyword::class] = listOf(::ClassDefinition)


        VariableItemSuffix::class[Equal::class] = listOf(::Equal, ::VariableInitializer)
        VariableItemSuffix::class[SemiColon::class] = listOf()


        VariableDecleration::class[IntegerKeyword::class] = listOf(::Simplekind, ::VariableSpec)
        VariableDecleration::class[FloatKeyword::class] = listOf(::Simplekind, ::VariableSpec)
        VariableDecleration::class[StringKeyword::class] = listOf(::Simplekind, ::VariableSpec)
        VariableDecleration::class[IdentifierTerminal::class] = listOf(::Simplekind, ::VariableSpec)


        Simplekind::class[IntegerKeyword::class] = listOf(::BaseKind)
        Simplekind::class[FloatKeyword::class] = listOf(::BaseKind)
        Simplekind::class[StringKeyword::class] = listOf(::BaseKind)
        Simplekind::class[IdentifierTerminal::class] = listOf(::ClassIdentifier)


        BaseKind::class[IntegerKeyword::class] = listOf(::IntegerKeyword)
        BaseKind::class[FloatKeyword::class] = listOf(::FloatKeyword)
        BaseKind::class[StringKeyword::class] = listOf(::StringKeyword)


        ClassIdentifier::class[IdentifierTerminal::class] = listOf(::IdentifierTerminal)


        VariableSpec::class[IdentifierTerminal::class] = listOf(::VariableIdentifier, ::ArraySpec)
        VariableSpec::class[Asterisk::class] = listOf(::DereferencedIdentifier)


        VariableIdentifier::class[IdentifierTerminal::class] = listOf(::IdentifierTerminal)


        ArraySpec::class[LeftBracket::class] = listOf(::KKint)
        ArraySpec::class[Equal::class] = listOf()
        ArraySpec::class[SemiColon::class] = listOf()
        ArraySpec::class[Comma::class] = listOf()
        ArraySpec::class[RightParen::class] = listOf()


        KKint::class[LeftBracket::class] = listOf(::LeftBracket, ::IntegerTerminal, ::RightBracket)


        DereferencedIdentifier::class[Asterisk::class] = listOf(::Dereference, ::IdentifierTerminal)


        Dereference::class[Asterisk::class] = listOf(::Asterisk)


        VariableInitializer::class[IntegerTerminal::class] = listOf(::Expression)
        VariableInitializer::class[FloatTerminal::class] = listOf(::Expression)
        VariableInitializer::class[StringTerminal::class] = listOf(::Expression)
        VariableInitializer::class[Asterisk::class] = listOf(::Expression)
        VariableInitializer::class[IdentifierTerminal::class] = listOf(::Expression)
        VariableInitializer::class[Ampersand::class] = listOf(::Expression)
        VariableInitializer::class[LeftParen::class] = listOf(::Expression)
        VariableInitializer::class[LeftBrace::class] = listOf(::BracedExprersions)


        BracedExprersions::class[LeftBrace::class] = listOf(::LeftBrace, ::ExpressionList, ::RightBrace)


        ExpressionList::class[IntegerTerminal::class] = listOf(::Expression, ::MoreExpressions)
        ExpressionList::class[FloatTerminal::class] = listOf(::Expression, ::MoreExpressions)
        ExpressionList::class[StringTerminal::class] = listOf(::Expression, ::MoreExpressions)
        ExpressionList::class[Asterisk::class] = listOf(::Expression, ::MoreExpressions)
        ExpressionList::class[IdentifierTerminal::class] = listOf(::Expression, ::MoreExpressions)
        ExpressionList::class[Ampersand::class] = listOf(::Expression, ::MoreExpressions)
        ExpressionList::class[LeftParen::class] = listOf(::Expression, ::MoreExpressions)
        ExpressionList::class[RightBrace::class] = listOf()
        ExpressionList::class[RightParen::class] = listOf()


        MoreExpressions::class[Comma::class] = listOf(::Comma, ::ExpressionList)
        MoreExpressions::class[RightBrace::class] = listOf()
        MoreExpressions::class[RightParen::class] = listOf()


        ClassDefinition::class[ClassKeyword::class] = listOf(::ClassHeader, ::ClassDefinitionSuffix)


        ClassDefinitionSuffix::class[LeftBrace::class] = listOf(::BracedClassItems)
        ClassDefinitionSuffix::class[IfKeyword::class] = listOf(::IfKeyword, ::BracedClassItems)


        BracedClassItems::class[LeftBrace::class] = listOf(::LeftBrace, ::ClassItems, ::RightBrace)


        ClassHeader::class[ClassKeyword::class] = listOf(::ClassKeyword, ::ClassIdentifier, ::ClassParent, ::Interfaces)


        ClassParent::class[Colon::class] = listOf(::Colon, ::ClassIdentifier)
        ClassParent::class[Plus::class] = listOf()
        ClassParent::class[LeftBrace::class] = listOf()
        ClassParent::class[IfKeyword::class] = listOf()


        ClassItems::class[Colon::class] = listOf(::ClassGroup, ::ClassItems)
        ClassItems::class[VarKeyword::class] = listOf(::ClassGroup, ::ClassItems)
        ClassItems::class[FunctionKeyword::class] = listOf(::ClassGroup, ::ClassItems)
        ClassItems::class[RightBrace::class] = listOf()


        ClassGroup::class[Colon::class] = listOf(::ClassVisibility)
        ClassGroup::class[VarKeyword::class] = listOf(::VariableGroup)
        ClassGroup::class[FunctionKeyword::class] = listOf(::MethodDeclerations)


        ClassVisibility::class[Colon::class] = listOf(::Colon, ::IdentifierTerminal)


        Interfaces::class[Plus::class] = listOf(::Plus, ::ClassIdentifier, ::Interfaces)
        Interfaces::class[LeftBrace::class] = listOf()
        Interfaces::class[IfKeyword::class] = listOf()


        MethodDeclerations::class[FunctionKeyword::class] = listOf(::MethodHeader, ::MethodDeclerations)
        MethodDeclerations::class[SemiColon::class] = listOf()
        MethodDeclerations::class[VarKeyword::class] = listOf()
        MethodDeclerations::class[RightBrace::class] = listOf()


        MethodHeader::class[FunctionKeyword::class] = listOf(::FunctionKeyword, ::MethodIdentifier, ::ParenthesizedParameterList, ::ReturnKind)


        MethodIdentifier::class[IdentifierTerminal::class] = listOf(::ClassIdentifier, ::Colon, ::FunctionIdentifier)


        FunctionDefinitions::class[FunctionKeyword::class] = listOf(::FunctionDefinition, ::FunctionDefinitions)
        FunctionDefinitions::class[MainKeyword::class] = listOf()


        FunctionDefinition::class[FunctionKeyword::class] = listOf(::FunctionHeader, ::BasicBlock)


        FunctionHeader::class[FunctionKeyword::class] = listOf(::FunctionKeyword, ::FunctionIdentifier, ::ParenthesizedParameterList, ::ReturnKind)


        FunctionIdentifier::class[IdentifierTerminal::class] = listOf(::IdentifierTerminal)


        ReturnKind::class[IntegerKeyword::class] = listOf(::BaseKind)
        ReturnKind::class[FloatKeyword::class] = listOf(::BaseKind)
        ReturnKind::class[StringKeyword::class] = listOf(::BaseKind)


        ParenthesizedParameterList::class[LeftParen::class] = listOf(::LeftParen, ::VariableSpecs, ::RightParen)


        VariableSpecs::class[Asterisk::class] = listOf(::VariableSpec, ::MoreVariableSpecs)
        VariableSpecs::class[IdentifierTerminal::class] = listOf(::VariableSpec, ::MoreVariableSpecs)
        VariableSpecs::class[RightParen::class] = listOf()


        MoreVariableSpecs::class[Comma::class] = listOf(::Comma, ::VariableSpecs)
        MoreVariableSpecs::class[RightParen::class] = listOf()


        Statements::class[Asterisk::class] = listOf(::Statement, ::SemiColon, ::Statements)
        Statements::class[IdentifierTerminal::class] = listOf(::Statement, ::SemiColon, ::Statements)
        Statements::class[IfKeyword::class] = listOf(::Statement, ::SemiColon, ::Statements)
        Statements::class[WhileKeyword::class] = listOf(::Statement, ::SemiColon, ::Statements)
        Statements::class[PrintKeyword::class] = listOf(::Statement, ::SemiColon, ::Statements)
        Statements::class[InputKeyword::class] = listOf(::Statement, ::SemiColon, ::Statements)
        Statements::class[ReturnKeyword::class] = listOf(::Statement, ::SemiColon, ::Statements)
        Statements::class[RightBrace::class] = listOf()


        Statement::class[IdentifierTerminal::class] = listOf(::AssignmentOrFunction)
        Statement::class[Asterisk::class] = listOf(::AssignmentOrFunction)
        Statement::class[IfKeyword::class] = listOf(::IfStatement)
        Statement::class[WhileKeyword::class] = listOf(::WhileStatement)
        Statement::class[PrintKeyword::class] = listOf(::PrintStatement)
        Statement::class[InputKeyword::class] = listOf(::InputStatement)
        Statement::class[ReturnKeyword::class] = listOf(::ReturnStatement)


        AssignmentOrFunction::class[Asterisk::class] = listOf(::DereferencedIdentifier, ::AssignmentSuffix)
        AssignmentOrFunction::class[IdentifierTerminal::class] = listOf(::IdentifierTerminal, ::AssignmentOrFunctionSuffix)


        AssignmentOrFunctionSuffix::class[LeftBracket::class] = listOf(::LeftValueSuffix, ::AssignmentSuffix)
        AssignmentOrFunctionSuffix::class[Equal::class] = listOf(::LeftValueSuffix, ::AssignmentSuffix)
        AssignmentOrFunctionSuffix::class[LeftParen::class] = listOf(::ParenthesizedExpressions)


        AssignmentSuffix::class[Equal::class] = listOf(::Equal, ::Expression)


        LeftValueSuffix::class[LeftBracket::class] = listOf(::BracketedExpression)
        LeftValueSuffix::class[Equal::class] = listOf()
        LeftValueSuffix::class[Asterisk::class] = listOf()
        LeftValueSuffix::class[ForwardSlash::class] = listOf()
        LeftValueSuffix::class[Caret::class] = listOf()
        LeftValueSuffix::class[Plus::class] = listOf()
        LeftValueSuffix::class[Minus::class] = listOf()
        LeftValueSuffix::class[EqualEqual::class] = listOf()
        LeftValueSuffix::class[NotEqual::class] = listOf()
        LeftValueSuffix::class[LessThan::class] = listOf()
        LeftValueSuffix::class[LessThanOrEqual::class] = listOf()
        LeftValueSuffix::class[GreaterThanOrEqual::class] = listOf()
        LeftValueSuffix::class[GreaterThan::class] = listOf()
        LeftValueSuffix::class[RightBrace::class] = listOf()
        LeftValueSuffix::class[RightBracket::class] = listOf()
        LeftValueSuffix::class[SemiColon::class] = listOf()
        LeftValueSuffix::class[RightParen::class] = listOf()
        LeftValueSuffix::class[Comma::class] = listOf()


        BracketedExpression::class[LeftBracket::class] = listOf(::LeftBracket, ::Expression, ::RightBracket)


        ParenthesizedExpressions::class[LeftParen::class] = listOf(::LeftParen, ::ExpressionList, ::RightParen)


        IfStatement::class[IfKeyword::class] = listOf(::IfKeyword, ::ParenthesizedExpression, ::BasicBlock, ::ElsePartialStatement)


        ElsePartialStatement::class[ElseIfKeyword::class] = listOf(::ElseIfKeyword, ::ParenthesizedExpression, ::BasicBlock, ::ElsePartialStatement)
        ElsePartialStatement::class[ElseKeyword::class] = listOf(::ElseKeyword, ::BasicBlock)
        ElsePartialStatement::class[SemiColon::class] = listOf()


        WhileStatement::class[WhileKeyword::class] = listOf(::WhileKeyword, ::ParenthesizedExpression, ::BasicBlock)


        PrintStatement::class[PrintKeyword::class] = listOf(::PrintKeyword, ::ParenthesizedExpressions)


        InputStatement::class[InputKeyword::class] = listOf(::InputKeyword, ::ParenthesizedExpressions)


        ReturnStatement::class[ReturnKeyword::class] = listOf(::ReturnKeyword, ::ReturnStatementSuffix)


        ReturnStatementSuffix::class[IntegerTerminal::class] = listOf(::Expression)
        ReturnStatementSuffix::class[FloatTerminal::class] = listOf(::Expression)
        ReturnStatementSuffix::class[StringTerminal::class] = listOf(::Expression)
        ReturnStatementSuffix::class[IdentifierTerminal::class] = listOf(::Expression)
        ReturnStatementSuffix::class[Asterisk::class] = listOf(::Expression)
        ReturnStatementSuffix::class[Ampersand::class] = listOf(::Expression)
        ReturnStatementSuffix::class[LeftParen::class] = listOf(::Expression)
        ReturnStatementSuffix::class[SemiColon::class] = listOf()


        ParenthesizedExpression::class[LeftParen::class] = listOf(::LeftParen, ::Expression, ::RightParen)


        Expression::class[IntegerTerminal::class] = listOf(::Rterm, ::ExpressionSuffix)
        Expression::class[FloatTerminal::class] = listOf(::Rterm, ::ExpressionSuffix)
        Expression::class[StringTerminal::class] = listOf(::Rterm, ::ExpressionSuffix)
        Expression::class[IdentifierTerminal::class] = listOf(::Rterm, ::ExpressionSuffix)
        Expression::class[Asterisk::class] = listOf(::Rterm, ::ExpressionSuffix)
        Expression::class[Ampersand::class] = listOf(::Rterm, ::ExpressionSuffix)
        Expression::class[LeftParen::class] = listOf(::Rterm, ::ExpressionSuffix)
        Expression::class[InputKeyword::class] = listOf(::Rterm, ::ExpressionSuffix)


        ExpressionSuffix::class[EqualEqual::class] = listOf(::RelationalOperator, ::Rterm, ::ExpressionSuffix)
        ExpressionSuffix::class[NotEqual::class] = listOf(::RelationalOperator, ::Rterm, ::ExpressionSuffix)
        ExpressionSuffix::class[LessThan::class] = listOf(::RelationalOperator, ::Rterm, ::ExpressionSuffix)
        ExpressionSuffix::class[LessThanOrEqual::class] = listOf(::RelationalOperator, ::Rterm, ::ExpressionSuffix)
        ExpressionSuffix::class[GreaterThanOrEqual::class] = listOf(::RelationalOperator, ::Rterm, ::ExpressionSuffix)
        ExpressionSuffix::class[GreaterThan::class] = listOf(::RelationalOperator, ::Rterm, ::ExpressionSuffix)
        ExpressionSuffix::class[RightBrace::class] = listOf()
        ExpressionSuffix::class[RightBracket::class] = listOf()
        ExpressionSuffix::class[SemiColon::class] = listOf()
        ExpressionSuffix::class[RightParen::class] = listOf()
        ExpressionSuffix::class[Comma::class] = listOf()


        Rterm::class[IntegerTerminal::class] = listOf(::Term, ::RtermSuffix)
        Rterm::class[FloatTerminal::class] = listOf(::Term, ::RtermSuffix)
        Rterm::class[StringTerminal::class] = listOf(::Term, ::RtermSuffix)
        Rterm::class[IdentifierTerminal::class] = listOf(::Term, ::RtermSuffix)
        Rterm::class[Asterisk::class] = listOf(::Term, ::RtermSuffix)
        Rterm::class[Ampersand::class] = listOf(::Term, ::RtermSuffix)
        Rterm::class[LeftParen::class] = listOf(::Term, ::RtermSuffix)
        Rterm::class[InputKeyword::class] = listOf(::Term, ::RtermSuffix)


        RtermSuffix::class[Plus::class] = listOf(::PlusOrMinus, ::Term, ::RtermSuffix)
        RtermSuffix::class[Minus::class] = listOf(::PlusOrMinus, ::Term, ::RtermSuffix)
        RtermSuffix::class[EqualEqual::class] = listOf()
        RtermSuffix::class[NotEqual::class] = listOf()
        RtermSuffix::class[LessThan::class] = listOf()
        RtermSuffix::class[LessThanOrEqual::class] = listOf()
        RtermSuffix::class[GreaterThanOrEqual::class] = listOf()
        RtermSuffix::class[GreaterThan::class] = listOf()
        RtermSuffix::class[RightBrace::class] = listOf()
        RtermSuffix::class[RightBracket::class] = listOf()
        RtermSuffix::class[SemiColon::class] = listOf()
        RtermSuffix::class[Comma::class] = listOf()
        RtermSuffix::class[RightParen::class] = listOf()


        Term::class[IntegerTerminal::class] = listOf(::Fact, ::TermSuffix)
        Term::class[FloatTerminal::class] = listOf(::Fact, ::TermSuffix)
        Term::class[StringTerminal::class] = listOf(::Fact, ::TermSuffix)
        Term::class[IdentifierTerminal::class] = listOf(::Fact, ::TermSuffix)
        Term::class[Asterisk::class] = listOf(::Fact, ::TermSuffix)
        Term::class[Ampersand::class] = listOf(::Fact, ::TermSuffix)
        Term::class[LeftParen::class] = listOf(::Fact, ::TermSuffix)
        Term::class[InputKeyword::class] = listOf(::Fact, ::TermSuffix)


        TermSuffix::class[Asterisk::class] = listOf(::MultiplyOrDivideOrExponentiate, ::Fact, ::TermSuffix)
        TermSuffix::class[ForwardSlash::class] = listOf(::MultiplyOrDivideOrExponentiate, ::Fact, ::TermSuffix)
        TermSuffix::class[Caret::class] = listOf(::MultiplyOrDivideOrExponentiate, ::Fact, ::TermSuffix)
        TermSuffix::class[EqualEqual::class] = listOf()
        TermSuffix::class[NotEqual::class] = listOf()
        TermSuffix::class[LessThan::class] = listOf()
        TermSuffix::class[LessThanOrEqual::class] = listOf()
        TermSuffix::class[GreaterThanOrEqual::class] = listOf()
        TermSuffix::class[GreaterThan::class] = listOf()
        TermSuffix::class[RightBrace::class] = listOf()
        TermSuffix::class[RightBracket::class] = listOf()
        TermSuffix::class[SemiColon::class] = listOf()
        TermSuffix::class[Comma::class] = listOf()
        TermSuffix::class[RightParen::class] = listOf()
        TermSuffix::class[Plus::class] = listOf()
        TermSuffix::class[Minus::class] = listOf()


        Fact::class[IntegerTerminal::class] = listOf(::BaseLiteral)
        Fact::class[FloatTerminal::class] = listOf(::BaseLiteral)
        Fact::class[StringTerminal::class] = listOf(::BaseLiteral)
        Fact::class[IdentifierTerminal::class] = listOf(::LeftValueOrFunction)
        Fact::class[Asterisk::class] = listOf(::LeftValueOrFunction)
        Fact::class[Ampersand::class] = listOf(::AddressOfIdentifier)
        Fact::class[LeftParen::class] = listOf(::ParenthesizedExpression)
        Fact::class[InputKeyword::class] = listOf(::InputStatement)


        LeftValueOrFunction::class[Asterisk::class] = listOf(::DereferencedIdentifier)
        LeftValueOrFunction::class[IdentifierTerminal::class] = listOf(::IdentifierTerminal, ::LeftValueOrFunctionSuffix)


        LeftValueOrFunctionSuffix::class[LeftBracket::class] = listOf(::LeftValueSuffix)
        LeftValueOrFunctionSuffix::class[LeftParen::class] = listOf(::ParenthesizedExpressions)
        LeftValueOrFunctionSuffix::class[Asterisk::class] = listOf()
        LeftValueOrFunctionSuffix::class[ForwardSlash::class] = listOf()
        LeftValueOrFunctionSuffix::class[Caret::class] = listOf()
        LeftValueOrFunctionSuffix::class[Plus::class] = listOf()
        LeftValueOrFunctionSuffix::class[Minus::class] = listOf()
        LeftValueOrFunctionSuffix::class[EqualEqual::class] = listOf()
        LeftValueOrFunctionSuffix::class[NotEqual::class] = listOf()
        LeftValueOrFunctionSuffix::class[LessThan::class] = listOf()
        LeftValueOrFunctionSuffix::class[LessThanOrEqual::class] = listOf()
        LeftValueOrFunctionSuffix::class[GreaterThan::class] = listOf()
        LeftValueOrFunctionSuffix::class[GreaterThanOrEqual::class] = listOf()
        LeftValueOrFunctionSuffix::class[RightBrace::class] = listOf()
        LeftValueOrFunctionSuffix::class[RightBracket::class] = listOf()
        LeftValueOrFunctionSuffix::class[SemiColon::class] = listOf()
        LeftValueOrFunctionSuffix::class[RightParen::class] = listOf()
        LeftValueOrFunctionSuffix::class[Comma::class] = listOf()


        BaseLiteral::class[IntegerTerminal::class] = listOf(::IntegerTerminal)
        BaseLiteral::class[FloatTerminal::class] = listOf(::FloatTerminal)
        BaseLiteral::class[StringTerminal::class] = listOf(::StringTerminal)


        AddressOfIdentifier::class[Ampersand::class] = listOf(::Ampersand, ::IdentifierTerminal)


        RelationalOperator::class[EqualEqual::class] = listOf(::EqualEqual)
        RelationalOperator::class[NotEqual::class] = listOf(::NotEqual)
        RelationalOperator::class[LessThan::class] = listOf(::Lthan)
        RelationalOperator::class[LessThanOrEqual::class] = listOf(::LessThanOrEqual)
        RelationalOperator::class[GreaterThanOrEqual::class] = listOf(::GreaterThanOrEqual)
        RelationalOperator::class[GreaterThan::class] = listOf(::Gthan)


        Lthan::class[LessThan::class] = listOf(::LessThan)


        Gthan::class[GreaterThan::class] = listOf(::GreaterThan)


        PlusOrMinus::class[Plus::class] = listOf(::Plus)
        PlusOrMinus::class[Minus::class] = listOf(::Minus)


        MultiplyOrDivideOrExponentiate::class[Asterisk::class] = listOf(::Asterisk)
        MultiplyOrDivideOrExponentiate::class[ForwardSlash::class] = listOf(::ForwardSlash)
        MultiplyOrDivideOrExponentiate::class[Caret::class] = listOf(::Caret)

        return llTable
    }

    private operator fun <NT : NonTerminal, T : Terminal> KClass<NT>.set(first: KClass<T>, rhs: List<NodeSupplier>) {
        llTable.on(this, first).useRHS(rhs)
    }

}
