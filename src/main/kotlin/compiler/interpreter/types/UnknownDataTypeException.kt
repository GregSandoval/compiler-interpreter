package compiler.interpreter.types

import compiler.parser.Symbol.Terminal
import compiler.parser.UserException

class UnknownDataTypeException(terminal: Terminal) :
        UserException("Unknown data type: ${terminal.str}", terminal)
