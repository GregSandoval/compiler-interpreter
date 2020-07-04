package compiler.interpreter.types

import compiler.parser.Symbol.Terminal
import compiler.parser.Symbol.Terminal.Keyword.Type
import compiler.parser.UserException

class AssignmentTypeException(lval: Terminal, rval: Terminal, lvalType: Type, rvalType: Type) :
        UserException("${lval.str} is of type ${lvalType.str} and cannot be assigned to a type of ${rvalType.str}", rval)
