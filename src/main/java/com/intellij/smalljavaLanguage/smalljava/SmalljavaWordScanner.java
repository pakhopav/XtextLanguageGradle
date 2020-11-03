package com.intellij.smalljavaLanguage.smalljava;

import com.intellij.lang.cacheBuilder.DefaultWordsScanner;
import com.intellij.psi.tree.TokenSet;
import com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaTypes;


public class SmalljavaWordScanner extends DefaultWordsScanner {
    public SmalljavaWordScanner() {
        super(new SmalljavaLexerAdapter(), TokenSet.create(SmalljavaTypes.ID), SmalljavaParserDefinition.COMMENTS, TokenSet.EMPTY);
        setMayHaveFileRefsInLiterals(true);
    }
}