package compiler.a5.pst

import compiler.parser.NonTerminalVisitor
import compiler.parser.PstToAstHelpers
import compiler.parser.Symbol.NonTerminal.*
import compiler.parser.Symbol.Terminal.Keyword.VarKeyword
import compiler.parser.Symbol.Terminal.Operator.Equal
import compiler.parser.Symbol.Terminal.Operator.Plus
import compiler.parser.Symbol.Terminal.Punctuation.*
import compiler.parser.TreeNode
import java.util.*

class PstToAstNonTerminalVisitor : NonTerminalVisitor {

    override fun visit(expression: Expression) {
        if (expression.children.size == 2) {
            val left = expression.children.pop()
            val right = expression.children.peek()
            PstToAstHelpers.hoist(expression)
            left.parent = right
            right.children.addFirst(left)
        }
    }

    override fun visit(expressionSuffix: ExpressionSuffix) {
        PstToAstHelpers.hoist(expressionSuffix)
    }

    override fun visit(term: Term) {
        if (term.children.size == 2) {
            val lvalue = term.children[0]
            val operator = term.children[1]
            term.children.remove(lvalue)
            lvalue.parent = operator
            operator.children.addFirst(lvalue)
        }
        PstToAstHelpers.hoist(term)
    }

    override fun visit(termSuffix: TermSuffix) {
        if (termSuffix.children.size == 3) {
            val tail = termSuffix.children[2]
            val tailLValue = termSuffix.children[1]
            tailLValue.parent = tail
            tail.children.addFirst(tailLValue)
            termSuffix.children.remove(tailLValue)
        }
        PstToAstHelpers.hoist(termSuffix)
    }

    override fun visit(rterm: Rterm) {
        if (rterm.children.size == 2) {
            val operator = rterm.children[1]
            val lvalue = rterm.children[0]
            lvalue.parent = operator
            rterm.children.remove(lvalue)
            operator.children.addFirst(lvalue)
        }
        PstToAstHelpers.hoist(rterm)
    }

    override fun visit(rtermSuffix: RtermSuffix) {
        if (rtermSuffix.children.size == 3) {
            val tail = rtermSuffix.children[2]
            val tailLValue = rtermSuffix.children[1]
            tailLValue.parent = tail
            tail.children.addFirst(tailLValue)
            rtermSuffix.children.remove(tailLValue)
        }
        PstToAstHelpers.hoist(rtermSuffix)
    }

    override fun visit(parenthesizedExpression: ParenthesizedExpression) {
        parenthesizedExpression.children.removeIf { it is RightParen }
        PstToAstHelpers.hoist(parenthesizedExpression)
    }

    override fun visit(classParent: ClassParent) {
        PstToAstHelpers.hoist(classParent)
    }

    override fun visit(parseTreeSentinel: ParseTreeSentinel) {
    }

    override fun visit(nullNode: NULL_NODE) {
    }

    override fun visit(program: Program) {
        var fcndefsIndex = -1
        var functionDefinitions: FunctionDefinitions? = null

        for (i in program.children.indices) {
            val child = program.children[i]
            if (child is FunctionDefinitions) {
                fcndefsIndex = i
                functionDefinitions = child
                break
            }
        }

        if (functionDefinitions != null) {
            for (child in functionDefinitions.children) {
                program.children.add(fcndefsIndex++, child)
                child.parent = program
            }
        }

        if (functionDefinitions != null)
            program.children.remove(functionDefinitions)
        PstToAstHelpers.hoist(program)
    }

    override fun visit(main: Main) {
        PstToAstHelpers.hoist(main)
    }

    override fun visit(basicBlock: BasicBlock) {
        basicBlock.children.removeIf { it is RightBrace }
        PstToAstHelpers.rightContraction(basicBlock)
        PstToAstHelpers.hoist(basicBlock)
    }

    override fun visit(variableGroup: VariableGroup) {
        PstToAstHelpers.hoist(variableGroup)
    }

    override fun visit(parenthesizedVariabledList: ParenthesizedVariabledList) {
        parenthesizedVariabledList.children.removeIf { it is RightParen }
        if (parenthesizedVariabledList.children.size == 2) {
            val varlist = parenthesizedVariabledList.children[1]
            parenthesizedVariabledList.children.remove(varlist)
            varlist.parent = null
            varlist.children.forEach { parenthesizedVariabledList.children.addLast(it) }
            varlist.children.forEach { it.parent = parenthesizedVariabledList }
        }
        PstToAstHelpers.hoist(parenthesizedVariabledList)
    }

    override fun visit(variableList: VariableList) {
        variableList.children.removeIf { it is SemiColon }
        if (variableList.children.size == 2) {
            val otherVarList = variableList.children[1]
            variableList.children.remove(otherVarList)
            otherVarList.children.forEach { variableList.children.addLast(it) }
            otherVarList.children.forEach { it.parent = variableList }
            return
        }
    }

    override fun visit(variableItem: VariableItem) {
        PstToAstHelpers.reverseHoist(variableItem)
    }

    override fun visit(variableItem_Suffix: VariableItemSuffix) {
        PstToAstHelpers.hoist(variableItem_Suffix)
    }

    override fun visit(variableDecleration: VariableDecleration) {
        PstToAstHelpers.hoist(variableDecleration)
    }

    override fun visit(simplekind: Simplekind) {}

    override fun visit(baseKind: BaseKind) {}

    override fun visit(variableSpec: VariableSpec) {
        PstToAstHelpers.hoist(variableSpec)
    }

    override fun visit(variableIdentifier: VariableIdentifier) {}

    override fun visit(arraySpec: ArraySpec) {}

    override fun visit(kKint: KKint) {
        kKint.children.removeIf { it is RightBracket }
        PstToAstHelpers.hoist(kKint)
    }

    override fun visit(dereferencedIdentifier: DereferencedIdentifier) {
        PstToAstHelpers.hoist(dereferencedIdentifier)
    }

    override fun visit(dereference: Dereference) {}

    override fun visit(variableInitializer: VariableInitializer) {}

    override fun visit(bracedExprersions: BracedExprersions) {}

    override fun visit(expressionList: ExpressionList) {
        if (!expressionList.children.isEmpty()) {
            PstToAstHelpers.rightContraction(expressionList)
        }
    }

    override fun visit(moreExpressions: MoreExpressions) {
        moreExpressions.children.removeIf { it is Comma }
        if (!moreExpressions.children.isEmpty()) {
            PstToAstHelpers.rightContraction(moreExpressions)
        }
    }

    override fun visit(classdecl: Classdecl) {}

    override fun visit(classDefinition: ClassDefinition) {
        PstToAstHelpers.hoist(classDefinition)
    }

    override fun visit(classDefSuffix: ClassDefinitionSuffix) {}

    override fun visit(bBClassitems: BracedClassItems) {
        bBClassitems.children.removeIf { it is RightBrace }
        PstToAstHelpers.rightContraction(bBClassitems)
        val removables = ArrayList<TreeNode>()
        for (i in 0 until bBClassitems.children.size - 1) {
            val left = bBClassitems.children[i]
            val right = bBClassitems.children[i + 1]
            if (left is Colon && right is VarKeyword) {
                removables.add(right)
                left.children.addLast(right)
                right.parent = left
            }
        }
        if (bBClassitems.children.last is MethodDeclerations) {
            PstToAstHelpers.rightContraction(bBClassitems)
        }
        bBClassitems.children.removeAll(removables)
        PstToAstHelpers.hoist(bBClassitems)
    }

    override fun visit(classItems: ClassItems) {
        if (!classItems.children.isEmpty() && classItems.children.last is ClassItems)
            PstToAstHelpers.rightContraction(classItems)
    }

    override fun visit(classGroup: ClassGroup) {}

    override fun visit(classVisibility: ClassVisibility) {
        PstToAstHelpers.hoist(classVisibility)
    }

    override fun visit(interfaces: Interfaces) {
        if (!interfaces.children.isEmpty()) {
            PstToAstHelpers.rightContraction(interfaces)
        }
        if (interfaces.parent is ClassHeader) {
            PstToAstHelpers.hoist(interfaces)
        }
        interfaces.children.removeIf { it is Plus }
    }

    override fun visit(methodDeclerations: MethodDeclerations) {
        if (!methodDeclerations.children.isEmpty())
            PstToAstHelpers.rightContraction(methodDeclerations)
    }

    override fun visit(methodHeader: MethodHeader) {
        PstToAstHelpers.hoist(methodHeader)
    }

    override fun visit(methodIdentifier: MethodIdentifier) {
        methodIdentifier.children.add(1, methodIdentifier.children.removeFirst())
        PstToAstHelpers.hoist(methodIdentifier)
    }

    override fun visit(functionDefinitions: FunctionDefinitions) {}

    override fun visit(functionDefinition: FunctionDefinition) {
        PstToAstHelpers.hoist(functionDefinition)
    }

    override fun visit(functionHeader: FunctionHeader) {
        PstToAstHelpers.hoist(functionHeader)
    }

    override fun visit(functionIdentifier: FunctionIdentifier) {}

    override fun visit(returnKind: ReturnKind) {}

    override fun visit(parenthesizedParameterList: ParenthesizedParameterList) {
        parenthesizedParameterList.children.removeIf { it is RightParen }
        if (!parenthesizedParameterList.children.isEmpty())
            PstToAstHelpers.rightContraction(parenthesizedParameterList)
        PstToAstHelpers.hoist(parenthesizedParameterList)
    }

    override fun visit(variableSpecs: VariableSpecs) {
        if (!variableSpecs.children.isEmpty())
            PstToAstHelpers.rightContraction(variableSpecs)
    }

    override fun visit(moreVariableSpecs: MoreVariableSpecs) {
        moreVariableSpecs.children.removeIf { it is Comma }
        if (!moreVariableSpecs.children.isEmpty())
            PstToAstHelpers.rightContraction(moreVariableSpecs)
    }

    override fun visit(pPonly: PPonly) {}

    override fun visit(statements: Statements) {
        statements.children.removeIf { it is SemiColon }
        if (!statements.children.isEmpty())
            PstToAstHelpers.rightContraction(statements)
    }

    override fun visit(statement: Statement) {}

    override fun visit(assignmentOrFunction: AssignmentOrFunction) {
        when (assignmentOrFunction.children.last) {
            is Equal -> PstToAstHelpers.reverseHoist(assignmentOrFunction)
            is LeftParen -> PstToAstHelpers.hoist(assignmentOrFunction)
        }
    }

    override fun visit(assignmentOrFunctionSuffix: AssignmentOrFunctionSuffix) {}

    override fun visit(assignmentSuffix: AssignmentSuffix) {
        PstToAstHelpers.hoist(assignmentSuffix)
    }

    override fun visit(leftValueSuffix: LeftValueSuffix) {}

    override fun visit(lval: Lval) {}

    override fun visit(leftValueOrFunction: LeftValueOrFunction) {
        PstToAstHelpers.hoist(leftValueOrFunction)
    }

    override fun visit(leftValueOrFunctionSuffix: LeftValueOrFunctionSuffix) {}

    override fun visit(lvalTail: Lval_Tail) {}

    override fun visit(bracketedExpression: BracketedExpression) {
        bracketedExpression.children.removeIf { it is RightBracket }
        PstToAstHelpers.hoist(bracketedExpression)
    }

    override fun visit(fcall: Fcall) {}
    override fun visit(parenthesizedExpressions: ParenthesizedExpressions) {
        parenthesizedExpressions.children.removeIf { it is RightParen }
        if (!parenthesizedExpressions.children.isEmpty()) {
            PstToAstHelpers.rightContraction(parenthesizedExpressions)
        }
        PstToAstHelpers.hoist(parenthesizedExpressions)
    }

    override fun visit(ifStatement: IfStatement) {
        PstToAstHelpers.hoist(ifStatement)
    }

    override fun visit(elsePartialStatement: ElsePartialStatement) {
        PstToAstHelpers.hoist(elsePartialStatement)
    }

    override fun visit(whileStatement: WhileStatement) {
        PstToAstHelpers.hoist(whileStatement)
    }

    override fun visit(printStatement: PrintStatement) {
        PstToAstHelpers.hoist(printStatement)
    }

    override fun visit(inputStatement: InputStatement) {
        PstToAstHelpers.hoist(inputStatement)
    }

    override fun visit(returnStatement: ReturnStatement) {
        if (returnStatement.children.size <= 1) {
            return
        }
        val returnVal = returnStatement.children.removeLast()
        val returnNode = returnStatement.children.first
        returnNode.children.addLast(returnVal)
        returnVal.parent = returnNode
        PstToAstHelpers.hoist(returnStatement)
    }

    override fun visit(returnStatementSuffix: ReturnStatementSuffix) {}

    override fun visit(fact: Fact) {}

    override fun visit(baseLiteral: BaseLiteral) {}

    override fun visit(addressOfIdentifier: AddressOfIdentifier) {
        PstToAstHelpers.hoist(addressOfIdentifier)
    }

    override fun visit(relationalOperator: RelationalOperator) {}

    override fun visit(lthan: Lthan) {}

    override fun visit(gthan: Gthan) {}

    override fun visit(plusOrMinus: PlusOrMinus) {}

    override fun visit(multiplyOrDivideOrExponentiate: MultiplyOrDivideOrExponentiate) {}

    override fun visit(epsilon: Epsilon) {}

    override fun visit(classHeader: ClassHeader) {
        PstToAstHelpers.hoist(classHeader)
    }

    override fun visit(classIdentifier: ClassIdentifier) {}
}
