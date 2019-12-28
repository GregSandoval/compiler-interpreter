package compiler.parser.visitors

interface TokenVisitor<T> :
        IgnorableTypedVisitor<T>,
        IdentifierTypedVisitor<T>,
        KeywordTypedVisitor<T>,
        OperatorTypedVisitor<T>,
        PrimitiveTypedVisitor<T>,
        SymbolTypedVisitor<T>
