package compiler.interpreter

import compiler.parser.Language.Token
import compiler.parser.Language.Token.KeywordToken.TypeToken
import compiler.parser.UserException

class AssignmentTypeException(lval: Token, rval: Token, lvalType: TypeToken, rvalType: TypeToken) :
        UserException("${lval.str} is of type ${lvalType.str} and cannot be assigned to a type of ${rvalType.str}", rval)
