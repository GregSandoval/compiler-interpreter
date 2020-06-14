package compiler

import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path

data class UserInput(var pstFileName: String = "pst",
                     var astFileName: String = "ast",
                     var sourceFileName: String = "std.in",
                     var sourceText: String)

object CommandLine {

    fun processInputs(args: Array<String>): UserInput {
        var pstFileName = "pst"
        var astFileName = "ast"
        var sourceFileName = "std.in"
        var sourceText: String? = null

        for (arg in args) {
            val split = arg.split("=".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

            if (split.size != 2)
                continue

            val key = split[0]
            val value = split[1]
            when (key) {
                "--pst-name" -> pstFileName = value
                "--ast-name" -> astFileName = value
                "--file" -> {
                    sourceFileName = value
                    sourceText = Files.readString(Path.of(sourceFileName))
                }
            }
        }

        if (sourceText == null) {
            throw Exception("Source file name is required. Use --file flag to specify source.")
        }

        return UserInput(pstFileName, astFileName, sourceFileName, sourceText)
    }
}
