package compiler.interpreter

import compiler.lexer.token.Token.OperatorToken
import compiler.parser.UserException

class OperatorUsageUndefined(operator: OperatorToken, lvalue: Any, rvalue: Any) :
        UserException("Operator ${operator.str} isn't defined between ${lvalue.javaClass.simpleName} and ${rvalue.javaClass.simpleName}", operator)
