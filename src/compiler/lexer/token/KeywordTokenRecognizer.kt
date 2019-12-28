package compiler.lexer.token

import compiler.lexer.token.KeywordToken.*
import java.util.*

object KeywordTokenRecognizer {
    private val keywords: MutableMap<String, () -> KeywordToken> = HashMap()
    @JvmStatic
    operator fun get(identifier: Token): Token {
        val tokenValue = identifier.str
        val supplier = keywords[tokenValue] ?: return identifier
        val keyword = supplier()
        keyword.lineNumber = identifier.lineNumber
        keyword.linePosition = identifier.linePosition
        return keyword
    }

    init {
        keywords["prog"] = ::ProgramKeywordToken
        keywords["main"] = ::MainKeywordToken
        keywords["fcn"] = ::FunctionKeywordToken
        keywords["class"] = ::ClassKeywordToken
        keywords["float"] = { TypeToken.FloatKeywordToken() }
        keywords["int"] = { TypeToken.IntegerKeywordToken() }
        keywords["string"] = { TypeToken.StringKeywordToken() }
        keywords["if"] = ::IfKeywordToken
        keywords["elseif"] = ::ElseIfKeywordToken
        keywords["else"] = ::ElseKeywordToken
        keywords["while"] = ::WhileKeywordToken
        keywords["input"] = ::InputKeywordToken
        keywords["print"] = ::PrintKeywordToken
        keywords["new"] = ::NewKeywordToken
        keywords["return"] = ::ReturnKeywordToken
        keywords["var"] = ::VarKeywordToken
    }
}
