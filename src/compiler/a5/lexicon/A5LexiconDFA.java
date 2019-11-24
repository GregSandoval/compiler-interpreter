package compiler.a5.lexicon;

import compiler.lexer.FinalState;
import compiler.lexer.LexicalNode;
import compiler.lexer.NonFinalState;
import compiler.lexer.token.*;

import static compiler.a5.lexicon.A5EdgePredicates.*;

/**
 * This file creates the DFA nodes and edges.
 */
public class A5LexiconDFA {
  // DFA Grammar
  public static final LexicalNode START = new NonFinalState("START");
  public static final LexicalNode IDENTIFIER = new FinalState("IDENTIFIER", IdentifierToken::new);

  public static final LexicalNode INTEGER = new FinalState("INTEGER", IntegerToken::new);
  public static final LexicalNode MAYBE_FLOAT = new NonFinalState("MAYBE_FLOAT");
  public static final LexicalNode FLOAT = new FinalState("FLOAT", FloatToken::new);

  public static final LexicalNode OPENING_STRING = new NonFinalState("OPENING_STRING");
  public static final LexicalNode STRING_CONTENTS = new NonFinalState("STRING_CONTENTS");
  public static final LexicalNode CLOSING_STRING = new FinalState("CLOSING_STRING", StringToken::new);

  public static final LexicalNode WHITESPACE = new FinalState("WHITESPACE", WhitespaceToken::new);
  public static final LexicalNode COMMENT = new FinalState("COMMENT", CommentToken::new);

  // Unpaired delimiters
  public static final LexicalNode COMMA = new FinalState("COMMA", SymbolToken.Comma::new);
  public static final LexicalNode SEMI_COLON = new FinalState("SEMI_COLON", SymbolToken.SemiColon::new);

  // Paired delimiters
  public static final LexicalNode LESS_THAN = new FinalState("LESS_THAN", OperatorToken.LessThan::new);
  public static final LexicalNode GREATER_THAN = new FinalState("GREATER_THAN", OperatorToken.GreaterThan::new);

  public static final LexicalNode LEFT_BRACE = new FinalState("LEFT_BRACE", SymbolToken.LeftBrace::new);
  public static final LexicalNode RIGHT_BRACE = new FinalState("RIGHT_BRACE", SymbolToken.RightBrace::new);

  public static final LexicalNode LEFT_BRACKET = new FinalState("LEFT_BRACKET", SymbolToken.LeftBracket::new);
  public static final LexicalNode RIGHT_BRACKET = new FinalState("RIGHT_BRACKET", SymbolToken.RightBracket::new);

  public static final LexicalNode LEFT_PAREN = new FinalState("LEFT_PAREN", SymbolToken.LeftParen::new);
  public static final LexicalNode RIGHT_PAREN = new FinalState("RIGHT_PAREN", SymbolToken.RightParen::new);

  // Other punctuation tokens
  public static final LexicalNode ASTERISK = new FinalState("ASTERISK", OperatorToken.Asterisk::new);
  public static final LexicalNode CARET = new FinalState("CARET", SymbolToken.RightParen.Caret::new);
  public static final LexicalNode COLON = new FinalState("COLON", SymbolToken.RightParen.Colon::new);
  public static final LexicalNode PERIOD = new FinalState("PERIOD", SymbolToken.RightParen.Period::new);
  public static final LexicalNode EQUAL = new FinalState("EQUAL", OperatorToken.Equal::new);
  public static final LexicalNode MINUS = new FinalState("MINUS", OperatorToken.Minus::new);
  public static final LexicalNode PLUS = new FinalState("PLUS", OperatorToken.Plus::new);
  public static final LexicalNode FORWARD_SLASH = new FinalState("FORWARD_SLASH", SymbolToken.ForwardSlash::new);
  public static final LexicalNode AND = new FinalState("AND", OperatorToken.Ampersand::new);
  public static final LexicalNode EXCLAMATION_MARK = new NonFinalState("EXCLAMATION_MARK");

  // Multi character operators
  public static final LexicalNode OP_ARROW = new FinalState("OP_ARROW", OperatorToken.Arrow::new);
  public static final LexicalNode OP_EQUAL = new FinalState("OP_EQUAL", OperatorToken.EqualEqual::new);
  public static final LexicalNode OP_NEGATE = new FinalState("OP_NEGATE", OperatorToken.NotEqual::new);
  public static final LexicalNode OP_LESS_THAN = new FinalState("OP_LESS_THAN", OperatorToken.LessThanOrEqual::new);
  public static final LexicalNode OP_GREATER_THAN = new FinalState("OP_GREATER_THAN", OperatorToken.GreaterThanOrEqual::new);
  public static final LexicalNode OP_SHIFT_LEFT = new FinalState("OP_SHIFT_LEFT", OperatorToken.BitShiftLeft::new);
  public static final LexicalNode OP_SHIFT_RIGHT = new FinalState("OP_SHIFT_RIGHT", OperatorToken.BitShiftRight::new);

  static {
    // WHITESPACE STATE
    START.ON(A_WHITESPACE).OR(A_LINE_SEPARATOR).GOTO(WHITESPACE);
    WHITESPACE.ON(A_WHITESPACE).OR(A_NEWLINE).GOTO(WHITESPACE);


    // IDENTIFIER
    START.ON(A_LETTER).OR(A_UNDERSCORE).GOTO(IDENTIFIER);
    IDENTIFIER.ON(A_UNDERSCORE).OR(A_DIGIT.or(A_LETTER)).GOTO(IDENTIFIER);


    // INTEGER
    START.ON(A_DIGIT).GOTO(INTEGER);
    INTEGER.ON(A_DIGIT).GOTO(INTEGER);
    PLUS.ON(A_DIGIT).GOTO(INTEGER);
    MINUS.ON(A_DIGIT).GOTO(INTEGER);


    // FLOAT
    INTEGER.ON(A_PERIOD).GOTO(MAYBE_FLOAT);
    MAYBE_FLOAT.ON(A_DIGIT).GOTO(FLOAT);
    FLOAT.ON(A_DIGIT).GOTO(FLOAT);


    // STRING
    START.ON(A_QUOTE).GOTO(OPENING_STRING);
    OPENING_STRING.ON(NOT_A_NEWLINE).AND(A_QUOTE.negate()).GOTO(STRING_CONTENTS);
    OPENING_STRING.ON(A_QUOTE).GOTO(CLOSING_STRING);
    STRING_CONTENTS.ON(NOT_A_NEWLINE).AND(A_QUOTE.negate()).GOTO(STRING_CONTENTS);
    STRING_CONTENTS.ON(A_QUOTE).GOTO(CLOSING_STRING);

    // COMMENT
    FORWARD_SLASH.ON(A_FORWARD_SLASH).GOTO(COMMENT);
    COMMENT.ON(NOT_A_NEWLINE).GOTO(COMMENT);


    // UNPAIRED DELIMITERS
    START.ON(A_COMMA).GOTO(COMMA);
    START.ON(A_SEMI_COLON).GOTO(SEMI_COLON);


    // PAIRED DELIMITERS
    START.ON(A_LESS_THAN).GOTO(LESS_THAN);
    START.ON(A_GREATER_THAN).GOTO(GREATER_THAN);

    START.ON(A_LEFT_BRACE).GOTO(LEFT_BRACE);
    START.ON(A_RIGHT_BRACE).GOTO(RIGHT_BRACE);

    START.ON(A_LEFT_BRACKET).GOTO(LEFT_BRACKET);
    START.ON(A_RIGHT_BRACKET).GOTO(RIGHT_BRACKET);

    START.ON(A_LEFT_PAREN).GOTO(LEFT_PAREN);
    START.ON(A_RIGHT_PAREN).GOTO(RIGHT_PAREN);

    // OTHER PUNCTUATION
    START.ON(A_ASTERISK).GOTO(ASTERISK);
    START.ON(A_CARET).GOTO(CARET);
    START.ON(A_COLON).GOTO(COLON);
    START.ON(A_PERIOD).GOTO(PERIOD);
    START.ON(A_EQUAL).GOTO(EQUAL);
    START.ON(A_MINUS).GOTO(MINUS);
    START.ON(A_PLUS).GOTO(PLUS);
    START.ON(A_FORWARD_SLASH).GOTO(FORWARD_SLASH);
    START.ON(A_AND).GOTO(AND);
    START.ON(A_EXCLAMATION_MARK).GOTO(EXCLAMATION_MARK);


    // MULTI CHARACTER OPERATORS
    MINUS.ON(A_GREATER_THAN).GOTO(OP_ARROW);
    EQUAL.ON(A_EQUAL).GOTO(OP_EQUAL);
    EXCLAMATION_MARK.ON(A_EQUAL).GOTO(OP_NEGATE);
    LESS_THAN.ON(A_EQUAL).GOTO(OP_LESS_THAN);
    GREATER_THAN.ON(A_EQUAL).GOTO(OP_GREATER_THAN);
    LESS_THAN.ON(A_LESS_THAN).GOTO(OP_SHIFT_LEFT);
    GREATER_THAN.ON(A_GREATER_THAN).GOTO(OP_SHIFT_RIGHT);
  }

  private A5LexiconDFA() {
  }
}
