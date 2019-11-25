package compiler.parser.visitors;

public interface TokenVisitor<T> extends
  IgnorableTypedVisitor<T>,
  IdentifierTypedVisitor<T>,
  KeywordTypedVisitor<T>,
  OperatorTypedVisitor<T>,
  PrimitiveTypedVisitor<T>,
  SymbolTypedVisitor<T> {
}
