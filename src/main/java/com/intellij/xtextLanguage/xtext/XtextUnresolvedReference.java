package com.intellij.xtextLanguage.xtext;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class XtextUnresolvedReference extends PsiReferenceBase<PsiElement> implements PsiPolyVariantReference {
    private String key;
    private List<Class<? extends PsiNameIdentifierOwner>> tClasses;

    public XtextUnresolvedReference(@NotNull PsiElement element, TextRange textRange, List<Class<? extends PsiNameIdentifierOwner>> tclasses) {
        super(element, textRange);
        key = element.getText().substring(textRange.getStartOffset(), textRange.getEndOffset());
        this.tClasses = tclasses;
    }

    @NotNull
    @Override
    public ResolveResult[] multiResolve(boolean incompleteCode) {
        return new ResolveResult[0];
    }

    @Nullable
    @Override
    public PsiElement resolve() {
        ResolveResult[] resolveResults = multiResolve(false);
        return resolveResults.length == 1 ? resolveResults[0].getElement() : null;
    }

    @NotNull
    @Override
    public Object[] getVariants() {
        return new Object[0];
    }

}