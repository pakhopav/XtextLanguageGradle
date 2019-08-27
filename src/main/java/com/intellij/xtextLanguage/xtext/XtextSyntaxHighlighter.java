package com.intellij.xtextLanguage.xtext;


import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.*;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import com.intellij.xtextLanguage.xtext.XtextParserDefinition;
import com.intellij.xtextLanguage.xtext.psi.XtextTypes;
import org.jetbrains.annotations.NotNull;

import static com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey;

public class XtextSyntaxHighlighter extends SyntaxHighlighterBase {

    public static final TextAttributesKey KEYWORD =
            createTextAttributesKey("XTEXT_KEYWORD", DefaultLanguageHighlighterColors.KEYWORD);
    public static final TextAttributesKey VALUE =
            createTextAttributesKey("XTEXT_VALUE", DefaultLanguageHighlighterColors.STRING);
    public static final TextAttributesKey COMMENT =
            createTextAttributesKey("XTEXT_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT);
    public static final TextAttributesKey BAD_CHARACTER =
            createTextAttributesKey("XTEXT_BAD_CHARACTER", HighlighterColors.BAD_CHARACTER);

    private static final TextAttributesKey[] BAD_CHAR_KEYS = new TextAttributesKey[]{BAD_CHARACTER};
    private static final TextAttributesKey[] KEY_KEYS = new TextAttributesKey[]{KEYWORD};
    private static final TextAttributesKey[] VALUE_KEYS = new TextAttributesKey[]{VALUE};
    private static final TextAttributesKey[] COMMENT_KEYS = new TextAttributesKey[]{COMMENT};
    private static final TextAttributesKey[] EMPTY_KEYS = new TextAttributesKey[0];

    @NotNull
    @Override
    public Lexer getHighlightingLexer() {
        return new XtextlexerAdapter();
    }

    @NotNull
    @Override
    public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
        if (XtextParserDefinition.KEYWORDS.contains(tokenType)) {
            return KEY_KEYS;
        } else if (tokenType.equals(XtextTypes.STRING)) {
            return VALUE_KEYS;
        } else if (XtextParserDefinition.COMMENTS.contains(tokenType)) {
            return COMMENT_KEYS;
        }  else {
            return EMPTY_KEYS;
        }
    }
}