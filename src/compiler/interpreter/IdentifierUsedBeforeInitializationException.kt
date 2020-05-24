package compiler.interpreter

import compiler.parser.Symbol.Terminal.TypedTerminal.IdentifierTerminal
import compiler.parser.UserException

class IdentifierUsedBeforeInitializationException(token: IdentifierTerminal) :
        UserException("Identifier '${token.value}' used before it's been initialized", token)
