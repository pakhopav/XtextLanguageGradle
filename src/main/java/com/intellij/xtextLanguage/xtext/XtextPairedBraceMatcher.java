package com.intellij.xtextLanguage.xtext;

import com.intellij.lang.BracePair;
import com.intellij.lang.PairedBraceMatcher;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IElementType;
import com.intellij.xtextLanguage.xtext.psi.XtextTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class XtextPairedBraceMatcher implements PairedBraceMatcher {
    private static final BracePair[] PAIRS = {
            new BracePair(XtextTypes.L_BRACKET, XtextTypes.R_BRACKET, true),
            new BracePair(XtextTypes.L_BRACE, XtextTypes.R_BRACE, true),
            new BracePair(XtextTypes.L_SQUARE_BRACKET, XtextTypes.R_SQUARE_BRACKET, true),
            new BracePair(XtextTypes.L_ANGLE_BRACKET, XtextTypes.R_ANGLE_BRACKET, true)
    };

    @NotNull
    @Override
    public BracePair[] getPairs() {
        return PAIRS;
    }

    @Override
    public boolean isPairedBracesAllowedBeforeType(@NotNull IElementType lbraceType, @Nullable IElementType contextType) {
        return false;
    }

    @Override
    public int getCodeConstructStart(PsiFile file, int openingBraceOffset) {
        return 0;
    }
}
