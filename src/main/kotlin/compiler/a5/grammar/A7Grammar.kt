package compiler.a5.grammar

import compiler.parser.ProductionRules
import compiler.parser.Symbol.NonTerminal.*
import compiler.parser.Symbol.Terminal.Keyword.*
import compiler.parser.Symbol.Terminal.Keyword.Type.*
import compiler.parser.Symbol.Terminal.Operator.*
import compiler.parser.Symbol.Terminal.Punctuation.*
import compiler.parser.Symbol.Terminal.TypedTerminal.*

class A7Grammar: ProductionRules() {

    init {
        this[Program::class] = listOf(::ProgramKeyword, ::VariableGroup, ::FunctionDefinitions, ::Main)

        this[Main::class] = listOf(::MainKeyword, ::BasicBlock)

        this[BasicBlock::class] = listOf(::LeftBrace, ::VariableGroup, ::Statements, ::RightBrace)

        this[VariableGroup::class] = listOf(::VarKeyword, ::ParenthesizedVariabledList)
        this[VariableGroup::class] = listOf()

        this[ParenthesizedVariabledList::class] = listOf(::LeftParen, ::VariableList, ::RightParen)

        this[VariableList::class] = listOf(::VariableItem, ::SemiColon, ::VariableList)
        this[VariableList::class] = listOf()

        this[VariableItem::class] = listOf(::VariableDecleration, ::VariableItemSuffix)
        // this[VariableItem::class] = listOf(::ClassDefinition)

        this[VariableItemSuffix::class] = listOf(::Equal, ::VariableInitializer)
        this[VariableItemSuffix::class] = listOf()

        this[VariableDecleration::class] = listOf(::Simplekind, ::VariableSpec)

        this[Simplekind::class] = listOf(::BaseKind)
        //this[Simplekind::class] = listOf(::ClassIdentifier)

        this[BaseKind::class] = listOf(::IntegerKeyword)
        this[BaseKind::class] = listOf(::FloatKeyword)
        this[BaseKind::class] = listOf(::StringKeyword)

        this[VariableSpec::class] = listOf(::VariableIdentifier, ::ArraySpec)
        this[VariableSpec::class] = listOf(::DereferencedIdentifier)

        this[VariableIdentifier::class] = listOf(::IdentifierTerminal)

        this[ArraySpec::class] = listOf(::KKint)
        this[ArraySpec::class] = listOf()

        this[KKint::class] = listOf(::LeftBracket, ::IntegerTerminal, ::RightBracket)

        this[DereferencedIdentifier::class] = listOf(::Dereference, ::IdentifierTerminal)

        this[Dereference::class] = listOf(::Asterisk)

        this[VariableInitializer::class] = listOf(::Expression)
        this[VariableInitializer::class] = listOf(::BracedExprersions)

        this[BracedExprersions::class] = listOf(::LeftBrace, ::ExpressionList, ::RightBrace)

        this[ExpressionList::class] = listOf(::Expression, ::MoreExpressions)
        this[ExpressionList::class] = listOf()

        this[MoreExpressions::class] = listOf(::Comma, ::ExpressionList)
        this[MoreExpressions::class] = listOf()


//        this[ClassIdentifier::class] = listOf(::IdentifierTerminal)
//        this[ClassDefinition::class] = listOf(::ClassHeader, ::ClassDefinitionSuffix)
//
//        this[ClassDefinitionSuffix::class] = listOf(::BracedClassItems)
//        this[ClassDefinitionSuffix::class] = listOf(::IfKeyword, ::BracedClassItems)
//
//        this[BracedClassItems::class] = listOf(::LeftBrace, ::ClassItems, ::RightBrace)
//
//        this[ClassHeader::class] = listOf(::ClassKeyword, ::ClassIdentifier, ::ClassParent, ::Interfaces)
//
//        this[ClassParent::class] = listOf(::Colon, ::ClassIdentifier)
//        this[ClassParent::class] = listOf()
//
//        this[ClassItems::class] = listOf(::ClassGroup, ::ClassItems)
//        this[ClassItems::class] = listOf()
//
//        this[ClassGroup::class] = listOf(::ClassVisibility)
//        this[ClassGroup::class] = listOf(::VariableGroup)
//        this[ClassGroup::class] = listOf(::MethodDeclerations)
//
//        this[ClassVisibility::class] = listOf(::Colon, ::IdentifierTerminal)
//
//        this[Interfaces::class] = listOf(::Plus, ::ClassIdentifier, ::Interfaces)
//        this[Interfaces::class] = listOf()
//
//        this[MethodDeclerations::class] = listOf(::MethodHeader)
//
//        this[MethodHeader::class] = listOf(::FunctionKeyword, ::MethodIdentifier, ::ParenthesizedParameterList, ::ReturnKind)
//
//        this[MethodIdentifier::class] = listOf(::ClassIdentifier, ::Colon, ::FunctionIdentifier)

        this[FunctionDefinitions::class] = listOf(::FunctionDefinition, ::FunctionDefinitions)
        this[FunctionDefinitions::class] = listOf()

        this[FunctionDefinition::class] = listOf(::FunctionHeader, ::BasicBlock)

        this[FunctionHeader::class] = listOf(::FunctionKeyword, ::FunctionIdentifier, ::ParenthesizedParameterList, ::ReturnKind)

        this[FunctionIdentifier::class] = listOf(::IdentifierTerminal)

        this[ReturnKind::class] = listOf(::BaseKind)

        this[ParenthesizedParameterList::class] = listOf(::LeftParen, ::VariableSpecs, ::RightParen)

        this[VariableSpecs::class] = listOf(::VariableSpec, ::MoreVariableSpecs)
        this[VariableSpecs::class] = listOf()

        this[MoreVariableSpecs::class] = listOf(::Comma, ::VariableSpecs)
        this[MoreVariableSpecs::class] = listOf()

        this[Statements::class] = listOf(::Statement, ::SemiColon, ::Statements)
        this[Statements::class] = listOf()

        this[Statement::class] = listOf(::AssignmentOrFunction)
        this[Statement::class] = listOf(::IfStatement)
        this[Statement::class] = listOf(::WhileStatement)
        this[Statement::class] = listOf(::PrintStatement)
        this[Statement::class] = listOf(::InputStatement)
        this[Statement::class] = listOf(::ReturnStatement)

        this[AssignmentOrFunction::class] = listOf(::DereferencedIdentifier, ::AssignmentSuffix)
        this[AssignmentOrFunction::class] = listOf(::IdentifierTerminal, ::AssignmentOrFunctionSuffix)

        this[AssignmentOrFunctionSuffix::class] = listOf(::LeftValueSuffix, ::AssignmentSuffix)
        this[AssignmentOrFunctionSuffix::class] = listOf(::ParenthesizedExpressions)

        this[AssignmentSuffix::class] = listOf(::Equal, ::Expression)

        this[LeftValueSuffix::class] = listOf(::BracketedExpression)
        this[LeftValueSuffix::class] = listOf()

        this[BracketedExpression::class] = listOf(::LeftBracket, ::Expression, ::RightBracket)

        this[ParenthesizedExpressions::class] = listOf(::LeftParen, ::ExpressionList, ::RightParen)

        this[IfStatement::class] = listOf(::IfKeyword, ::ParenthesizedExpression, ::BasicBlock, ::ElsePartialStatement)

        this[ElsePartialStatement::class] = listOf(::ElseIfKeyword, ::ParenthesizedExpression, ::BasicBlock, ::ElsePartialStatement)
        this[ElsePartialStatement::class] = listOf(::ElseKeyword, ::BasicBlock)
        this[ElsePartialStatement::class] = listOf()

        this[WhileStatement::class] = listOf(::WhileKeyword, ::ParenthesizedExpression, ::BasicBlock)

        this[PrintStatement::class] = listOf(::PrintKeyword, ::ParenthesizedExpressions)

        this[InputStatement::class] = listOf(::InputKeyword, ::ParenthesizedExpressions)

        this[ReturnStatement::class] = listOf(::ReturnKeyword, ::ReturnStatementSuffix)

        this[ReturnStatementSuffix::class] = listOf(::Expression)
        this[ReturnStatementSuffix::class] = listOf()

        this[ParenthesizedExpression::class] = listOf(::LeftParen, ::Expression, ::RightParen)

        this[Expression::class] = listOf(::Rterm, ::ExpressionSuffix)

        this[ExpressionSuffix::class] = listOf(::RelationalOperator, ::Rterm, ::ExpressionSuffix)
        this[ExpressionSuffix::class] = listOf()

        this[Rterm::class] = listOf(::Term, ::RtermSuffix)

        this[RtermSuffix::class] = listOf(::PlusOrMinus, ::Term, ::RtermSuffix)
        this[RtermSuffix::class] = listOf()

        this[Term::class] = listOf(::Fact, ::TermSuffix)

        this[TermSuffix::class] = listOf(::MultiplyOrDivideOrExponentiateOrModulus, ::Fact, ::TermSuffix)
        this[TermSuffix::class] = listOf()

        this[Fact::class] = listOf(::BaseLiteral)
        this[Fact::class] = listOf(::LeftValueOrFunction)
        this[Fact::class] = listOf(::LeftValueOrFunction)
        this[Fact::class] = listOf(::AddressOfIdentifier)
        this[Fact::class] = listOf(::ParenthesizedExpression)
        this[Fact::class] = listOf(::InputStatement)

        this[LeftValueOrFunction::class] = listOf(::DereferencedIdentifier)
        this[LeftValueOrFunction::class] = listOf(::IdentifierTerminal, ::LeftValueOrFunctionSuffix)

        this[LeftValueOrFunctionSuffix::class] = listOf(::LeftValueSuffix)
        this[LeftValueOrFunctionSuffix::class] = listOf(::ParenthesizedExpressions)
        this[LeftValueOrFunctionSuffix::class] = listOf()

        this[BaseLiteral::class] = listOf(::IntegerTerminal)
        this[BaseLiteral::class] = listOf(::FloatTerminal)
        this[BaseLiteral::class] = listOf(::StringTerminal)

        this[AddressOfIdentifier::class] = listOf(::Ampersand, ::IdentifierTerminal)

        this[RelationalOperator::class] = listOf(::EqualEqual)
        this[RelationalOperator::class] = listOf(::NotEqual)
        this[RelationalOperator::class] = listOf(::Lthan)
        this[RelationalOperator::class] = listOf(::LessThanOrEqual)
        this[RelationalOperator::class] = listOf(::GreaterThanOrEqual)
        this[RelationalOperator::class] = listOf(::Gthan)

        this[Lthan::class] = listOf(::LessThan)

        this[Gthan::class] = listOf(::GreaterThan)

        this[PlusOrMinus::class] = listOf(::Plus)
        this[PlusOrMinus::class] = listOf(::Minus)

        this[MultiplyOrDivideOrExponentiateOrModulus::class] = listOf(::Asterisk)
        this[MultiplyOrDivideOrExponentiateOrModulus::class] = listOf(::ForwardSlash)
        this[MultiplyOrDivideOrExponentiateOrModulus::class] = listOf(::Caret)
        this[MultiplyOrDivideOrExponentiateOrModulus::class] = listOf(::Modulus)
    }
}
