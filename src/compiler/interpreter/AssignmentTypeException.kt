package compiler.interpreter

import compiler.parser.Symbols.Terminal
import compiler.parser.Symbols.Terminal.Keyword.Type
import compiler.parser.UserException

class AssignmentTypeException(lval: Terminal, rval: Terminal, lvalType: Type, rvalType: Type) :
        UserException("${lval.str} is of type ${lvalType.str} and cannot be assigned to a type of ${rvalType.str}", rval)
