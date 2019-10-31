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
            new BracePair(XtextTypes.L_BRACKET_KEYWORD, XtextTypes.R_BRACKET_KEYWORD, true),
            new BracePair(XtextTypes.L_BRACE_KEYWORD, XtextTypes.R_BRACE_KEYWORD, true),
            new BracePair(XtextTypes.L_SQUARE_BRACKET_KEYWORD, XtextTypes.R_SQUARE_BRACKET_KEYWORD, true),
            new BracePair(XtextTypes.L_ANGLE_BRACKET_KEYWORD, XtextTypes.R_ANGLE_BRACKET_KEYWORD, true)
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
