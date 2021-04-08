package com.intellij.xtextLanguage.xtext.generator.generators

import java.io.FileOutputStream
import java.io.PrintWriter

class WordScannerGenerator(extension: String, rootPath: String) : AbstractGenerator(extension, rootPath) {
    fun generateWordScanner() {
        val file = createFile(extensionCapitalized + "WordScanner.java", myGenDir)
        val out = PrintWriter(FileOutputStream(file))
        out.print(
            """
            |package $packageDir;
            |
            |import com.intellij.lang.cacheBuilder.DefaultWordsScanner;
            |import com.intellij.psi.tree.TokenSet;
            |import com.intellij.${extension}Language.${extension}.psi.${extensionCapitalized}Types;
            |

            |public class ${extensionCapitalized}WordScanner extends DefaultWordsScanner {
            |    public ${extensionCapitalized}WordScanner() {
            |        super(new ${extensionCapitalized}LexerAdapter(), TokenSet.create(${extensionCapitalized}Types.ID), ${extensionCapitalized}ParserDefinition.COMMENTS, TokenSet.EMPTY);
            |        setMayHaveFileRefsInLiterals(true);
            |    }
            |}
        """.trimMargin("|"))
        out.close()
    }
}