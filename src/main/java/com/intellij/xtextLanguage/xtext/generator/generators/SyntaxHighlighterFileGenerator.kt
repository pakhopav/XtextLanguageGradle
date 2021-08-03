package com.intellij.xtextLanguage.xtext.generator.generators

import com.intellij.xtextLanguage.xtext.generator.models.MetaContext
import java.io.FileOutputStream
import java.io.PrintWriter

class SyntaxHighlighterFileGenerator(extension: String, val context: MetaContext, rootPath: String) :
    AbstractGenerator(extension, rootPath) {
    fun generateSyntaxHighlighterFile() {
        val file = createOrFindFile(extension.capitalize() + "SyntaxHighlighter.java", myGenDir)
        val out = PrintWriter(FileOutputStream(file))
        out.println(
            """
            |package $packageDir;
            |
            |import com.intellij.lexer.Lexer;
            |import com.intellij.openapi.editor.*;
            |import com.intellij.openapi.editor.colors.TextAttributesKey;
            |import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
            |import com.intellij.psi.tree.IElementType;
            |import $packageDir.psi.${extension.capitalize()}Types;
            |import org.jetbrains.annotations.NotNull;
            |import static com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey;
            |
            |public class ${extension.capitalize()}SyntaxHighlighter extends SyntaxHighlighterBase {
            |    public static final TextAttributesKey KEYWORD =
            |        createTextAttributesKey("${extension.toUpperCase()}_KEYWORD", DefaultLanguageHighlighterColors.KEYWORD);
            |    public static final TextAttributesKey VALUE =
            |        createTextAttributesKey("${extension.toUpperCase()}_VALUE", DefaultLanguageHighlighterColors.STRING);
            |    public static final TextAttributesKey COMMENT =
            |        createTextAttributesKey("${extension.toUpperCase()}_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT);
            |    public static final TextAttributesKey BAD_CHARACTER =
            |        createTextAttributesKey("${extension.toUpperCase()}_BAD_CHARACTER", HighlighterColors.BAD_CHARACTER);
            
            |    private static final TextAttributesKey[] BAD_CHAR_KEYS = new TextAttributesKey[]{BAD_CHARACTER};
            |    private static final TextAttributesKey[] KEY_KEYS = new TextAttributesKey[]{KEYWORD};
            |    private static final TextAttributesKey[] VALUE_KEYS = new TextAttributesKey[]{VALUE};
            |    private static final TextAttributesKey[] COMMENT_KEYS = new TextAttributesKey[]{COMMENT};
            |    private static final TextAttributesKey[] EMPTY_KEYS = new TextAttributesKey[0];
            
            |    @NotNull
            |    @Override
            |    public Lexer getHighlightingLexer() {
            |        return new ${extension.capitalize()}LexerAdapter();
            |    }
            
            |    @NotNull
            |    @Override
            |    public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
            |        if (${extension.capitalize()}ParserDefinition.KEYWORDS.contains(tokenType)) {
            |            return KEY_KEYS;""".trimMargin("|"))
        if (context.terminalRules.any { it.name == "STRING" }) {
            out.println("""
            |        } else if (tokenType.equals(${extension.capitalize()}Types.STRING)) {
            |            return VALUE_KEYS;
        """.trimMargin("|"))
        }
        out.print("""
            |        } else if (${extension.capitalize()}ParserDefinition.COMMENTS.contains(tokenType)) {
            |            return COMMENT_KEYS;
            |        }  else {
            |            return EMPTY_KEYS;
            |        }
            |    }
            |}

        """.trimMargin("|"))
        out.close()

    }
}