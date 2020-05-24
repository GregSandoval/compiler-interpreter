package compiler.interpreter

import compiler.parser.Language.Token
import compiler.parser.Language.Token.OperatorToken
import compiler.parser.UserException

class OperatorTypeException(op: OperatorToken, left: Token, right: Token) :
        UserException("Operator '${op.str}' is not defined between types ${left.str} and ${right.str}", right)
