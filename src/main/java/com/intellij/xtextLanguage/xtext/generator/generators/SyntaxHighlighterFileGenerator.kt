package com.intellij.xtextLanguage.xtext.generator.generators

import com.intellij.xtextLanguage.xtext.generator.models.XtextMainModel
import java.io.FileOutputStream
import java.io.PrintWriter

class SyntaxHighlighterFileGenerator(extention: String, fileModel: XtextMainModel) : Generator(extention, fileModel) {
    fun generateSyntaxHighlighterFile() {
        val file = createFile(extention + "SyntaxHighlighter.java", myGenDir)
        val out = PrintWriter(FileOutputStream(file))
        out.print("""
            |package $packageDir;
            |import com.intellij.lexer.Lexer;
            |import com.intellij.openapi.editor.*;
            |import com.intellij.openapi.editor.colors.TextAttributesKey;
            |import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
            |import com.intellij.psi.tree.IElementType;
            |import $packageDir.psi.${extention}Types;
            |import org.jetbrains.annotations.NotNull;
            
            |import static com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey;
            
            |public class ${extention}SyntaxHighlighter extends SyntaxHighlighterBase {
            
            |    public static final TextAttributesKey KEYWORD =
            |        createTextAttributesKey("${extention.toUpperCase()}_KEYWORD", DefaultLanguageHighlighterColors.KEYWORD);
            |    public static final TextAttributesKey VALUE =
            |        createTextAttributesKey("${extention.toUpperCase()}_VALUE", DefaultLanguageHighlighterColors.STRING);
            |    public static final TextAttributesKey COMMENT =
            |        createTextAttributesKey("${extention.toUpperCase()}_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT);
            |    public static final TextAttributesKey BAD_CHARACTER =
            |        createTextAttributesKey("${extention.toUpperCase()}_BAD_CHARACTER", HighlighterColors.BAD_CHARACTER);
            
            |    private static final TextAttributesKey[] BAD_CHAR_KEYS = new TextAttributesKey[]{BAD_CHARACTER};
            |    private static final TextAttributesKey[] KEY_KEYS = new TextAttributesKey[]{KEYWORD};
            |    private static final TextAttributesKey[] VALUE_KEYS = new TextAttributesKey[]{VALUE};
            |    private static final TextAttributesKey[] COMMENT_KEYS = new TextAttributesKey[]{COMMENT};
            |    private static final TextAttributesKey[] EMPTY_KEYS = new TextAttributesKey[0];
            
            |    @NotNull
            |    @Override
            |    public Lexer getHighlightingLexer() {
            |        return new ${extention}LexerAdapter();
            |    }
            
            |    @NotNull
            |    @Override
            |    public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
            |        if (${extention}ParserDefinition.KEYWORDS.contains(tokenType)) {
            |            return KEY_KEYS;""".trimMargin("|"))
        if (fileModel.ruleResolver.getTerminalRuleByName("STRING") != null) {
            out.print("""
            |        } else if (tokenType.equals(${extention}Types.STRING)) {
            |            return VALUE_KEYS;
        """.trimMargin("|"))
        }
        out.print("""
            |        } else if (${extention}ParserDefinition.COMMENTS.contains(tokenType)) {
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