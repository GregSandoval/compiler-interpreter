package compiler.lexer;

import compiler.lexer.token.*;
import compiler.utils.TextCursor;
import compiler.utils.TriConsumer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import static compiler.lexer.NonFinalState.END_OF_TERMINAL;
import static compiler.lexer.NonFinalState.FATAL_ERROR;

public class Lexer {
  private final TriConsumer<LexicalNode, Character, LexicalNode> transitionListeners;
  private final TriConsumer<LexicalNode, LexicalNode, Token> tokenCreatedListeners;
  private final BiConsumer<String, TextCursor> unknownTokenListener;
  private final LexicalNode START_STATE;

  protected Lexer(
    LexicalNode startState,
    TriConsumer<LexicalNode, Character, LexicalNode> transitionListeners,
    TriConsumer<LexicalNode, LexicalNode, Token> tokenCreatedListeners,
    BiConsumer<String, TextCursor> unknownTokenListener
  ) {
    this.transitionListeners = transitionListeners;
    this.tokenCreatedListeners = tokenCreatedListeners;
    this.START_STATE = startState;
    this.unknownTokenListener = unknownTokenListener;
  }

  public List<Token> analyze(String text) {
    var CURRENT_STATE = START_STATE;
    var tokens = new ArrayList<Token>();
    var currentToken = new StringBuilder();
    var cursor = new TextCursor(text);
    for (var letter : cursor) {
      var GOTO = CURRENT_STATE.ON(letter);

      transitionListeners.accept(CURRENT_STATE, letter, GOTO);

      if (GOTO == END_OF_TERMINAL) {
        var token = ((FinalState) CURRENT_STATE).getToken(currentToken.toString());
        token.setLineNumber(cursor.getCursorLineNumber());
        token.setLinePosition(cursor.getCursorLinePosition() - currentToken.length());
        tokens.add(token);
        tokenCreatedListeners.accept(CURRENT_STATE, GOTO, token);
        currentToken.setLength(0);
        cursor.rewind();
        CURRENT_STATE = START_STATE;
        continue;
      }

      if (GOTO == FATAL_ERROR) {
        unknownTokenListener.accept(currentToken.toString() + letter, cursor);
        break;
      }

      currentToken.append(letter);
      CURRENT_STATE = GOTO;
    }

    // Add eof :)
    var eof = new EOFToken();
    eof.setLinePosition(cursor.getCursorLinePosition());
    eof.setLineNumber(cursor.getCursorLineNumber());
    tokens.add(eof);

    return tokens
      .stream()
      .filter(terminal -> !(terminal instanceof WhitespaceToken))
      .filter(terminal -> !(terminal instanceof CommentToken))
      .map(KeywordTokenRecognizer::get)
      .collect(Collectors.toList());
  }
}
