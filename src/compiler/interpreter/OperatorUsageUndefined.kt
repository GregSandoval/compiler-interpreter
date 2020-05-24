package compiler.interpreter

import compiler.parser.Symbol.Terminal.Operator
import compiler.parser.UserException

class OperatorUsageUndefined(operator: Operator, lvalue: Any, rvalue: Any) :
        UserException("Operator ${operator.str} isn't defined between ${lvalue.javaClass.simpleName} and ${rvalue.javaClass.simpleName}", operator)
