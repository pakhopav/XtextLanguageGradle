package com.intellij.xtext.language;

import com.intellij.codeInsight.highlighting.BraceMatcher;
import com.intellij.lang.BracePair;
import com.intellij.lang.Language;
import com.intellij.lang.LanguageBraceMatching;
import com.intellij.lang.PairedBraceMatcher;
import com.intellij.openapi.editor.highlighter.HighlighterIterator;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class XtextBraceMatcher implements BraceMatcher {
    @Override
    public int getBraceTokenGroupId(IElementType tokenType) {
        final Language l = tokenType.getLanguage();
        PairedBraceMatcher matcher = LanguageBraceMatching.INSTANCE.forLanguage(l);
        if (matcher != null) {
            BracePair[] pairs = matcher.getPairs();
            for (BracePair pair : pairs) {
                if (pair.getLeftBraceType() == tokenType || pair.getRightBraceType() == tokenType) return l.hashCode();
            }
        }
        return -1;
    }

    @Override
    public boolean isLBraceToken(HighlighterIterator iterator, CharSequence fileText, FileType fileType) {
        IElementType type = iterator.getTokenType();
        PairedBraceMatcher matcher = LanguageBraceMatching.INSTANCE.forLanguage(type.getLanguage());
        if (matcher != null) {
            BracePair[] pairs = matcher.getPairs();
            for (BracePair pair : pairs) {
                if (pair.getLeftBraceType() == type) return true;
            }
        }
        return false;
    }

    @Override
    public boolean isRBraceToken(HighlighterIterator iterator, CharSequence fileText, FileType fileType) {
        return false;
    }

    @Override
    public boolean isPairBraces(IElementType tokenType, IElementType tokenType2) {
        return false;
    }

    @Override
    public boolean isStructuralBrace(HighlighterIterator iterator, CharSequence text, FileType fileType) {
        return false;
    }

    @Nullable
    @Override
    public IElementType getOppositeBraceTokenType(@NotNull IElementType type) {
        return null;
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
