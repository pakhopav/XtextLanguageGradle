// This is a generated file. Not intended for manual editing.
package com.intellij.xtextLanguage.xtext.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNameIdentifierOwner;
import org.jetbrains.annotations.Nullable;

public interface XtextAbstractRule extends PsiNameIdentifierOwner {

    @Nullable
    XtextEnumRule getEnumRule();

    @Nullable
    XtextParserRule getParserRule();

    @Nullable
    XtextTerminalRule getTerminalRule();

    String getName();

    PsiElement setName(String newName);

    PsiElement getNameIdentifier();

}
