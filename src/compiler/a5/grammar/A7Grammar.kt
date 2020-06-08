package compiler.a5.grammar

import compiler.parser.ProductionRules
import compiler.parser.Symbol.NonTerminal.*
import compiler.parser.Symbol.Terminal.Keyword.*
import compiler.parser.Symbol.Terminal.Keyword.Type.*
import compiler.parser.Symbol.Terminal.Operator.*
import compiler.parser.Symbol.Terminal.Punctuation.*
import compiler.parser.Symbol.Terminal.TypedTerminal.*

object A7Grammar {
    private lateinit var productions: ProductionRules

    fun build(): ProductionRules {
        this.productions = ProductionRules()

        productions[Program::class] = listOf(::ProgramKeyword, ::VariableGroup, ::FunctionDefinitions, ::Main)

        productions[Main::class] = listOf(::MainKeyword, ::BasicBlock)

        productions[BasicBlock::class] = listOf(::LeftBrace, ::VariableGroup, ::Statements, ::RightBrace)

        productions[VariableGroup::class] = listOf(::VarKeyword, ::ParenthesizedVariabledList)
        productions[VariableGroup::class] = listOf()

        productions[ParenthesizedVariabledList::class] = listOf(::LeftParen, ::VariableList, ::RightParen)

        productions[VariableList::class] = listOf(::VariableItem, ::SemiColon, ::VariableList)
        productions[VariableList::class] = listOf()

        productions[VariableItem::class] = listOf(::VariableDecleration, ::VariableItemSuffix)
        // productions[VariableItem::class] = listOf(::ClassDefinition)

        productions[VariableItemSuffix::class] = listOf(::Equal, ::VariableInitializer)
        productions[VariableItemSuffix::class] = listOf()

        productions[VariableDecleration::class] = listOf(::Simplekind, ::VariableSpec)

        productions[Simplekind::class] = listOf(::BaseKind)
        //productions[Simplekind::class] = listOf(::ClassIdentifier)

        productions[BaseKind::class] = listOf(::IntegerKeyword)
        productions[BaseKind::class] = listOf(::FloatKeyword)
        productions[BaseKind::class] = listOf(::StringKeyword)

        productions[VariableSpec::class] = listOf(::VariableIdentifier, ::ArraySpec)
        productions[VariableSpec::class] = listOf(::DereferencedIdentifier)

        productions[VariableIdentifier::class] = listOf(::IdentifierTerminal)

        productions[ArraySpec::class] = listOf(::KKint)
        productions[ArraySpec::class] = listOf()

        productions[KKint::class] = listOf(::LeftBracket, ::IntegerTerminal, ::RightBracket)

        productions[DereferencedIdentifier::class] = listOf(::Dereference, ::IdentifierTerminal)

        productions[Dereference::class] = listOf(::Asterisk)

        productions[VariableInitializer::class] = listOf(::Expression)
        productions[VariableInitializer::class] = listOf(::BracedExprersions)

        productions[BracedExprersions::class] = listOf(::LeftBrace, ::ExpressionList, ::RightBrace)

        productions[ExpressionList::class] = listOf(::Expression, ::MoreExpressions)
        productions[ExpressionList::class] = listOf()

        productions[MoreExpressions::class] = listOf(::Comma, ::ExpressionList)
        productions[MoreExpressions::class] = listOf()


      //productions[ClassIdentifier::class] = listOf(::IdentifierTerminal)
      //productions[ClassDefinition::class] = listOf(::ClassHeader, ::ClassDefinitionSuffix)

      //productions[ClassDefinitionSuffix::class] = listOf(::BracedClassItems)
      //productions[ClassDefinitionSuffix::class] = listOf(::IfKeyword, ::BracedClassItems)

      //productions[BracedClassItems::class] = listOf(::LeftBrace, ::ClassItems, ::RightBrace)

      //productions[ClassHeader::class] = listOf(::ClassKeyword, ::ClassIdentifier, ::ClassParent, ::Interfaces)

      //productions[ClassParent::class] = listOf(::Colon, ::ClassIdentifier)
      //productions[ClassParent::class] = listOf()

      //productions[ClassItems::class] = listOf(::ClassGroup, ::ClassItems)
      //productions[ClassItems::class] = listOf()

      //productions[ClassGroup::class] = listOf(::ClassVisibility)
      //productions[ClassGroup::class] = listOf(::VariableGroup)
      //productions[ClassGroup::class] = listOf(::MethodDeclerations)

      //productions[ClassVisibility::class] = listOf(::Colon, ::IdentifierTerminal)

      //productions[Interfaces::class] = listOf(::Plus, ::ClassIdentifier, ::Interfaces)
      //productions[Interfaces::class] = listOf()

      //productions[MethodDeclerations::class] = listOf(::MethodHeader)

      //productions[MethodHeader::class] = listOf(::FunctionKeyword, ::MethodIdentifier, ::ParenthesizedParameterList, ::ReturnKind)

      //productions[MethodIdentifier::class] = listOf(::ClassIdentifier, ::Colon, ::FunctionIdentifier)

        productions[FunctionDefinitions::class] = listOf(::FunctionDefinition, ::FunctionDefinitions)
        productions[FunctionDefinitions::class] = listOf()

        productions[FunctionDefinition::class] = listOf(::FunctionHeader, ::BasicBlock)

        productions[FunctionHeader::class] = listOf(::FunctionKeyword, ::FunctionIdentifier, ::ParenthesizedParameterList, ::ReturnKind)

        productions[FunctionIdentifier::class] = listOf(::IdentifierTerminal)

        productions[ReturnKind::class] = listOf(::BaseKind)

        productions[ParenthesizedParameterList::class] = listOf(::LeftParen, ::VariableSpecs, ::RightParen)

        productions[VariableSpecs::class] = listOf(::VariableSpec, ::MoreVariableSpecs)
        productions[VariableSpecs::class] = listOf()

        productions[MoreVariableSpecs::class] = listOf(::Comma, ::VariableSpecs)
        productions[MoreVariableSpecs::class] = listOf()

        productions[Statements::class] = listOf(::Statement, ::SemiColon, ::Statements)
        productions[Statements::class] = listOf()

        productions[Statement::class] = listOf(::AssignmentOrFunction)
        productions[Statement::class] = listOf(::IfStatement)
        productions[Statement::class] = listOf(::WhileStatement)
        productions[Statement::class] = listOf(::PrintStatement)
        productions[Statement::class] = listOf(::InputStatement)
        productions[Statement::class] = listOf(::ReturnStatement)

        productions[AssignmentOrFunction::class] = listOf(::DereferencedIdentifier, ::AssignmentSuffix)
        productions[AssignmentOrFunction::class] = listOf(::IdentifierTerminal, ::AssignmentOrFunctionSuffix)

        productions[AssignmentOrFunctionSuffix::class] = listOf(::LeftValueSuffix, ::AssignmentSuffix)
        productions[AssignmentOrFunctionSuffix::class] = listOf(::ParenthesizedExpressions)

        productions[AssignmentSuffix::class] = listOf(::Equal, ::Expression)

        productions[LeftValueSuffix::class] = listOf(::BracketedExpression)
        productions[LeftValueSuffix::class] = listOf()

        productions[BracketedExpression::class] = listOf(::LeftBracket, ::Expression, ::RightBracket)

        productions[ParenthesizedExpressions::class] = listOf(::LeftParen, ::ExpressionList, ::RightParen)

        productions[IfStatement::class] = listOf(::IfKeyword, ::ParenthesizedExpression, ::BasicBlock, ::ElsePartialStatement)

        productions[ElsePartialStatement::class] = listOf(::ElseIfKeyword, ::ParenthesizedExpression, ::BasicBlock, ::ElsePartialStatement)
        productions[ElsePartialStatement::class] = listOf(::ElseKeyword, ::BasicBlock)
        productions[ElsePartialStatement::class] = listOf()

        productions[WhileStatement::class] = listOf(::WhileKeyword, ::ParenthesizedExpression, ::BasicBlock)

        productions[PrintStatement::class] = listOf(::PrintKeyword, ::ParenthesizedExpressions)

        productions[InputStatement::class] = listOf(::InputKeyword, ::ParenthesizedExpressions)

        productions[ReturnStatement::class] = listOf(::ReturnKeyword, ::ReturnStatementSuffix)

        productions[ReturnStatementSuffix::class] = listOf(::Expression)
        productions[ReturnStatementSuffix::class] = listOf()

        productions[ParenthesizedExpression::class] = listOf(::LeftParen, ::Expression, ::RightParen)

        productions[Expression::class] = listOf(::Rterm, ::ExpressionSuffix)

        productions[ExpressionSuffix::class] = listOf(::RelationalOperator, ::Rterm, ::ExpressionSuffix)
        productions[ExpressionSuffix::class] = listOf()

        productions[Rterm::class] = listOf(::Term, ::RtermSuffix)

        productions[RtermSuffix::class] = listOf(::PlusOrMinus, ::Term, ::RtermSuffix)
        productions[RtermSuffix::class] = listOf()

        productions[Term::class] = listOf(::Fact, ::TermSuffix)

        productions[TermSuffix::class] = listOf(::MultiplyOrDivideOrExponentiate, ::Fact, ::TermSuffix)
        productions[TermSuffix::class] = listOf()

        productions[Fact::class] = listOf(::BaseLiteral)
        productions[Fact::class] = listOf(::LeftValueOrFunction)
        productions[Fact::class] = listOf(::LeftValueOrFunction)
        productions[Fact::class] = listOf(::AddressOfIdentifier)
        productions[Fact::class] = listOf(::ParenthesizedExpression)
        productions[Fact::class] = listOf(::InputStatement)

        productions[LeftValueOrFunction::class] = listOf(::DereferencedIdentifier)
        productions[LeftValueOrFunction::class] = listOf(::IdentifierTerminal, ::LeftValueOrFunctionSuffix)

        productions[LeftValueOrFunctionSuffix::class] = listOf(::LeftValueSuffix)
        productions[LeftValueOrFunctionSuffix::class] = listOf(::ParenthesizedExpressions)
        productions[LeftValueOrFunctionSuffix::class] = listOf()

        productions[BaseLiteral::class] = listOf(::IntegerTerminal)
        productions[BaseLiteral::class] = listOf(::FloatTerminal)
        productions[BaseLiteral::class] = listOf(::StringTerminal)

        productions[AddressOfIdentifier::class] = listOf(::Ampersand, ::IdentifierTerminal)

        productions[RelationalOperator::class] = listOf(::EqualEqual)
        productions[RelationalOperator::class] = listOf(::NotEqual)
        productions[RelationalOperator::class] = listOf(::Lthan)
        productions[RelationalOperator::class] = listOf(::LessThanOrEqual)
        productions[RelationalOperator::class] = listOf(::GreaterThanOrEqual)
        productions[RelationalOperator::class] = listOf(::Gthan)

        productions[Lthan::class] = listOf(::LessThan)

        productions[Gthan::class] = listOf(::GreaterThan)

        productions[PlusOrMinus::class] = listOf(::Plus)
        productions[PlusOrMinus::class] = listOf(::Minus)

        productions[MultiplyOrDivideOrExponentiate::class] = listOf(::Asterisk)
        productions[MultiplyOrDivideOrExponentiate::class] = listOf(::ForwardSlash)
        productions[MultiplyOrDivideOrExponentiate::class] = listOf(::Caret)

        return productions
    }
}
