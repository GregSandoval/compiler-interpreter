package compiler.lexer.token

import compiler.parser.Symbol.Terminal
import compiler.parser.Symbol.Terminal.Keyword
import compiler.parser.Symbol.Terminal.Keyword.*
import java.util.*

object KeywordTokenRecognizer {
    private val keywords: MutableMap<String, () -> Keyword> = HashMap()

    @JvmStatic
    operator fun get(identifier: Terminal): Terminal {
        val tokenValue = identifier.str
        val supplier = keywords[tokenValue] ?: return identifier
        val keyword = supplier()
        keyword.lineInfo = identifier.lineInfo.copy()
        return keyword
    }

    init {
        keywords["prog"] = ::ProgramKeyword
        keywords["main"] = ::MainKeyword
        keywords["fcn"] = ::FunctionKeyword
        keywords["class"] = ::ClassKeyword
        keywords["float"] = Type::FloatKeyword
        keywords["int"] = Type::IntegerKeyword
        keywords["string"] = Type::StringKeyword
        keywords["if"] = ::IfKeyword
        keywords["elseif"] = ::ElseIfKeyword
        keywords["else"] = ::ElseKeyword
        keywords["while"] = ::WhileKeyword
        keywords["input"] = ::InputKeyword
        keywords["print"] = ::PrintKeyword
        keywords["new"] = ::NewKeyword
        keywords["return"] = ::ReturnKeyword
        keywords["var"] = ::VarKeyword
    }
}
