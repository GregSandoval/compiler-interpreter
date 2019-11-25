package compiler.interpreter;

import compiler.lexer.token.Token;
import compiler.lexer.token.TypeToken;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class SymbolTable {
  private Map<String, Object> symtab = new HashMap<>();
  private Map<String, TypeToken> types = new HashMap<>();

  public TypeToken getSymbolType(Token token) {
    final var type = this.types.get(this.hashcode(token));

    if (type == null) {
      throw new UndeclaredIdentifierException(token);
    }

    return type;
  }

  public void setSymbolType(Token token, TypeToken type) {
    final var oldType = this.types.put(this.hashcode(token), type);

    if (oldType != null) {
      throw new RuntimeException(token.getValue() + "'s datatype cannot be redefined");
    }
  }

  public void setSymbolValue(Token token, Object value) {
    final var previous = this.symtab.put(hashcode(token), value);
  }

  public boolean hasSymbol(Token token) {
    return this.symtab.containsKey(hashcode(token));
  }

  public Object getValue(Token token) {
    return this.symtab.get(hashcode(token));
  }

  public String getAddress(Token token) {
    return this.hashcode(token);
  }

  public Object getValueAtAddress(String address) {
    return this.symtab.get(address);
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
      .map(entry -> entry.getKey() + ":" + this.types.get(entry.getKey()).getValue() + " = " + entry.getValue().toString())
      .collect(Collectors.joining("\n"));
  }
}
