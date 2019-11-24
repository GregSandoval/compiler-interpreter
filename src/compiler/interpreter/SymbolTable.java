package compiler.interpreter;

import compiler.lexer.token.Token;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class SymbolTable {
  private Map<String, Object> symtab = new HashMap<>();

  public void setSymbolValue(Token token, Object value) {
    final var previous = this.symtab.put(hashcode(token), value);
  }

  public boolean hasSymbol(Token token) {
    return this.symtab.containsKey(hashcode(token));
  }

  public Object getValue(Token token) {
    return this.symtab.get(hashcode(token));
  }

  public Object removeSymbol(Token token) {
    return this.symtab.remove(hashcode(token));
  }

  private String hashcode(Token token) {
    return token.getValue() + ":" + token.getClass().getSimpleName();
  }

  @Override
  public String toString() {
    return symtab.entrySet()
      .stream()
      .map(entry -> entry.getKey() + " = " + entry.getValue().toString())
      .collect(Collectors.joining("\n"));
  }
}
