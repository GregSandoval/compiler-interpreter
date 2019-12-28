package compiler.interpreter

import compiler.lexer.token.OperatorToken
import compiler.lexer.token.Token
import compiler.parser.UserException

class OperatorTypeException(op: OperatorToken, left: Token, right: Token) :
        UserException("Operator '${op.str}' is not defined between types ${left.str} and ${right.str}", right)