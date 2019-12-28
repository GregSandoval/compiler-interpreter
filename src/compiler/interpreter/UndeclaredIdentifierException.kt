package compiler.interpreter

import compiler.lexer.token.Token
import compiler.parser.UserException

class UndeclaredIdentifierException(token: Token) :
        UserException("Identifier '${token.str}' is undeclared. Please declare identifier in variable block", token)
