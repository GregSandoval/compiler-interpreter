package compiler.interpreter

import compiler.lexer.token.Token
import compiler.lexer.token.Token.KeywordToken.TypeToken
import compiler.parser.UserException

class AssignmentTypeException(lval: Token, rval: Token, lvalType: TypeToken, rvalType: TypeToken) :
        UserException("${lval.str} is of type ${lvalType.str} and cannot be assigned to a type of ${rvalType.str}", rval)
