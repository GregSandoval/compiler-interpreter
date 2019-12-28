package compiler.interpreter

import compiler.lexer.token.Token
import compiler.parser.UserException

class UnknownDataTypeException(token: Token) :
        UserException("Unknown data type: ${token.str}", token)
