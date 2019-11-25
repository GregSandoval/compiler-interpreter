package compiler.interpreter;

import compiler.lexer.token.Token;
import compiler.lexer.token.TypeToken;
import compiler.parser.UserException;

public class AssignmentTypeException extends UserException {
  public AssignmentTypeException(Token lval, Token rval, TypeToken lvalType, TypeToken rvalType) {
    super(lval.getValue() + " is of type " + lvalType.getValue() + " and cannot be assigned to a type of " + rvalType.getValue(), rval);
  }
}
