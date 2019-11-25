package compiler.parser.visitors;

public interface TokenVisitor<T> extends
  IgnorableTypedVisitor<T>,
  IdentifierTypedVisitor<T>,
  KeywordTypedVisitor<T>,
  OperatorTypedVisitor<T>,
  PrimitiveTypedVisitor<T, T, T>,
  SymbolTypedVisitor<T> {
}
