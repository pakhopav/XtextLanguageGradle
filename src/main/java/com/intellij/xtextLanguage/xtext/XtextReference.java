package com.intellij.xtextLanguage.xtext;


import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import com.intellij.xtextLanguage.xtext.psi.XtextNamedElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class XtextReference<T extends XtextNamedElement> extends PsiReferenceBase<PsiElement> implements PsiPolyVariantReference {
    private String key;
    private Class<T> tClass;

    public XtextReference(@NotNull PsiElement element, TextRange textRange, Class<T> tClass) {
        super(element, textRange);
        key = element.getText().substring(textRange.getStartOffset(), textRange.getEndOffset());
        this.tClass = tClass;
    }

    @NotNull
    @Override
    public ResolveResult[] multiResolve(boolean incompleteCode) {
        return XtextMultiResolve(incompleteCode, tClass);
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
        return XtextGetVariants(tClass);
    }

    public <T extends XtextNamedElement> ResolveResult[] XtextMultiResolve(boolean incompleteCode, final Class<T> tClass) {
        PsiFile file = myElement.getContainingFile();
        List<T> elements = XtextUtil.findElementsInCurrentFile(file, tClass, key);
        List<ResolveResult> results = new ArrayList<ResolveResult>();
        for (T element : elements) {
            results.add(new PsiElementResolveResult(element));
        }
        return results.toArray(new ResolveResult[results.size()]);
    }

    public <T extends XtextNamedElement> Object[] XtextGetVariants(Class<T> tClass) {
        PsiFile file = myElement.getContainingFile();
        List<T> elements = XtextUtil.findElementsInCurrentFile(file, tClass);
        List<LookupElement> variants = new ArrayList<LookupElement>();
        for (final T element : elements) {
            if (element.getName() != null && element.getName().length() > 0) {
                variants.add(LookupElementBuilder.create(element).
                        withIcon(XtextIcons.FILE).
                        withTypeText(element.getContainingFile().getName())
                );
            }
        }
        return variants.toArray();
    }
}