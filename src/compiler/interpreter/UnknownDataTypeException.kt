package compiler.interpreter

import compiler.parser.Symbols.Terminal
import compiler.parser.UserException

class UnknownDataTypeException(terminal: Terminal) :
        UserException("Unknown data type: ${terminal.str}", terminal)
