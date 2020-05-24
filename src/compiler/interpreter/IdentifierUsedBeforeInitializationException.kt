package compiler.interpreter

import compiler.parser.Language.Token.TypedToken.IdentifierToken
import compiler.parser.UserException

class IdentifierUsedBeforeInitializationException(token: IdentifierToken) :
        UserException("Identifier '${token.value}' used before it's been initialized", token)
