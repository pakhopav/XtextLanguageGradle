package com.intellij.xtextLanguage.xtext;

import com.intellij.lang.cacheBuilder.DefaultWordsScanner;
import com.intellij.psi.tree.TokenSet;
import com.intellij.xtextLanguage.xtext.psi.XtextTypes;


public class XtextWordScanner extends DefaultWordsScanner {
    public XtextWordScanner() {
        super(new XtextLexerAdapter(), TokenSet.create(XtextTypes.ID), XtextParserDefinition.COMMENTS, TokenSet.EMPTY);
        setMayHaveFileRefsInLiterals(true);
    }
}