package compiler.a5.lexicon;

import java.util.function.Predicate;

/**
 * A grouping of commonly used functions for detecting character classes.
 * Used to define the conditions for when the lexer should transition
 * to a different state.
 */
public final class A5EdgePredicates {
  // CHARACTER SETS
  public static Predicate<Character> A_LOWER_CASE_LETTER = symbol -> symbol >= 'a' && symbol <= 'z';
  public static Predicate<Character> A_UPPER_CASE_LETTER = symbol -> symbol >= 'A' && symbol <= 'Z';
  public static Predicate<Character> A_LETTER = A_LOWER_CASE_LETTER.or(A_UPPER_CASE_LETTER);
  public static Predicate<Character> A_DIGIT = symbol -> symbol >= '0' && symbol <= '9';

  // WHITE SPACE AND NEWLINES
  public static Predicate<Character> A_TAB = IS_CHARACTER('\t');
  public static Predicate<Character> A_NEWLINE = IS_CHARACTER('\n');
  public static Predicate<Character> A_FORM_FEED = IS_CHARACTER('\f');
  public static Predicate<Character> A_CARRIAGE_RETURN = IS_CHARACTER('\r');
  public static Predicate<Character> A_SPACE = IS_CHARACTER(' ');
  public static Predicate<Character> A_LINE_SEPARATOR = A_NEWLINE.or(A_FORM_FEED).or(A_CARRIAGE_RETURN);
  public static Predicate<Character> A_WHITESPACE = A_TAB.or(A_SPACE);

  // UNPAIRED DELIMITERS
  public static Predicate<Character> A_COMMA = IS_CHARACTER(',');
  public static Predicate<Character> A_SEMI_COLON = IS_CHARACTER(';');

  // PAIRED DELIMITERS
  public static Predicate<Character> A_LESS_THAN = IS_CHARACTER('<');
  public static Predicate<Character> A_GREATER_THAN = IS_CHARACTER('>');

  public static Predicate<Character> A_LEFT_BRACE = IS_CHARACTER('{');
  public static Predicate<Character> A_RIGHT_BRACE = IS_CHARACTER('}');

  public static Predicate<Character> A_LEFT_BRACKET = IS_CHARACTER('[');
  public static Predicate<Character> A_RIGHT_BRACKET = IS_CHARACTER(']');

  public static Predicate<Character> A_LEFT_PAREN = IS_CHARACTER('(');
  public static Predicate<Character> A_RIGHT_PAREN = IS_CHARACTER(')');

  // OTHER PUNCTUATION
  public static Predicate<Character> A_ASTERISK = IS_CHARACTER('*');
  public static Predicate<Character> A_CARET = IS_CHARACTER('^');
  public static Predicate<Character> A_COLON = IS_CHARACTER(':');
  public static Predicate<Character> A_PERIOD = IS_CHARACTER('.');
  public static Predicate<Character> A_EQUAL = IS_CHARACTER('=');
  public static Predicate<Character> A_MINUS = IS_CHARACTER('-');
  public static Predicate<Character> A_PLUS = IS_CHARACTER('+');
  public static Predicate<Character> A_FORWARD_SLASH = IS_CHARACTER('/');
  public static Predicate<Character> A_AND = IS_CHARACTER('&');
  public static Predicate<Character> A_QUOTE = IS_CHARACTER('"');
  public static Predicate<Character> A_UNDERSCORE = IS_CHARACTER('_');
  public static Predicate<Character> A_EXCLAMATION_MARK = IS_CHARACTER('!');

  // EVERYTHING EXCEPT LINE FEED
  public static Predicate<Character> NOT_A_NEWLINE = A_LINE_SEPARATOR.negate();

  private static Predicate<Character> IS_CHARACTER(Character character) {
    return symbol -> symbol == character;
  }
}
