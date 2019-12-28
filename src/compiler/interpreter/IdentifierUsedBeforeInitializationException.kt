package compiler.interpreter

import compiler.lexer.token.IdentifierToken
import compiler.parser.UserException

class IdentifierUsedBeforeInitializationException(token: IdentifierToken) :
        UserException("Identifier '${token.value}' used before it's been initialized", token)
