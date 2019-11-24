package compiler.parser;

import compiler.lexer.token.EOFToken;
import compiler.lexer.token.Token;

import static compiler.parser.ParserListeners.*;

public class ParserBuilder {

  public ParserBuilderLastStep setStartSymbol(GrammarNode startSymbol) {
    return new ParserBuilderLastStep(startSymbol);
  }

  public static class ParserBuilderLastStep {
    private BeforeRuleApplicationListener beforeRuleApplication = BeforeRuleApplicationListenerIdentity();
    private GrammarRuleApplicationListener onGrammarRuleApplication = GrammarRuleApplicationIdentity();
    private GeneralListener onPredictionNotFoundError = GeneralListenerIdentity();
    private GeneralListener onUnknownGrammarRule = GeneralListenerIdentity();
    private GeneralListener onUnexpectedToken = GeneralListenerIdentity();
    private Token eof = new EOFToken();
    private GrammarNode startSymbol;

    private ParserBuilderLastStep(GrammarNode startSymbol) {
      this.startSymbol = startSymbol;
    }

    public ParserBuilderLastStep beforeRuleApplication(BeforeRuleApplicationListener beforeRuleApplication) {
      this.beforeRuleApplication = this.beforeRuleApplication.andThen(beforeRuleApplication);
      return this;
    }

    public ParserBuilderLastStep onUnexpectedToken(GeneralListener onUnexpectedToken) {
      this.onUnexpectedToken = this.onUnexpectedToken.andThen(onUnexpectedToken);
      return this;
    }

    public ParserBuilderLastStep onUnknownGrammarRule(GeneralListener onUnknownGrammarRule) {
      this.onUnknownGrammarRule = this.onUnknownGrammarRule.andThen(onUnknownGrammarRule);
      return this;
    }

    public ParserBuilderLastStep onPredictionNotFoundError(GeneralListener onPredictionNotFoundError) {
      this.onPredictionNotFoundError = this.onPredictionNotFoundError.andThen(onPredictionNotFoundError);
      return this;
    }

    public ParserBuilderLastStep onGrammarRuleApplication(GrammarRuleApplicationListener onGrammarRuleApplication) {
      this.onGrammarRuleApplication = this.onGrammarRuleApplication.andThen(onGrammarRuleApplication);
      return this;
    }

    public ParserBuilderLastStep setEOF(Token eof) {
      this.eof = eof;
      return this;
    }


    public Parser createParser() {
      return new Parser(
        eof,
        startSymbol,
        beforeRuleApplication,
        onUnexpectedToken,
        onUnknownGrammarRule,
        onPredictionNotFoundError,
        onGrammarRuleApplication
      );
    }

  }
}
