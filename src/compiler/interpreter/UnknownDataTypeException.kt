package compiler.interpreter

import compiler.parser.Language.Token
import compiler.parser.UserException

class UnknownDataTypeException(token: Token) :
        UserException("Unknown data type: ${token.str}", token)
