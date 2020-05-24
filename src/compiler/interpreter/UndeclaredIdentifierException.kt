package compiler.interpreter

import compiler.parser.Symbols.Terminal
import compiler.parser.UserException

class UndeclaredIdentifierException(terminal: Terminal) :
        UserException("Identifier '${terminal.str}' is undeclared. Please declare identifier in variable block", terminal)
