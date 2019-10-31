package com.intellij.xtextLanguage.xtext;


import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Reference extends PsiReferenceBase<PsiElement> implements PsiPolyVariantReference {
    private String key;
    private List<Class<? extends PsiNameIdentifierOwner>> tClasses;

    public Reference(@NotNull PsiElement element, TextRange textRange, List<Class<? extends PsiNameIdentifierOwner>> tclasses) {
        super(element, textRange);
        key = element.getText().substring(textRange.getStartOffset(), textRange.getEndOffset());
        this.tClasses = tclasses;
    }

    @NotNull
    @Override
    public ResolveResult[] multiResolve(boolean incompleteCode) {
        return MultiResolve(incompleteCode, tClasses);
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
        return XtextGetVariants(tClasses);
    }

    public ResolveResult[] MultiResolve(boolean incompleteCode, final List<Class<? extends PsiNameIdentifierOwner>> classes) {
        PsiFile file = myElement.getContainingFile();
        List<? extends PsiNameIdentifierOwner> elements = new ArrayList<>();
        classes.forEach(it -> {
            elements.addAll((ArrayList) XtextUtil.findElementsInCurrentFile(file, it, key));
        });
        List<ResolveResult> results = new ArrayList<>();
        elements.forEach(it -> {
            results.add(new PsiElementResolveResult(it));
        });

        return results.toArray(new ResolveResult[results.size()]);
    }

    public Object[] XtextGetVariants(List<Class<? extends PsiNameIdentifierOwner>> classes) {
        PsiFile file = myElement.getContainingFile();
        List<? extends PsiNameIdentifierOwner> elements = new ArrayList<>();
        classes.forEach(it -> {
            elements.addAll((ArrayList) XtextUtil.findElementsInCurrentFile(file, it));
        });
        List<LookupElement> variants = new ArrayList<LookupElement>();
        elements.forEach(it -> {
            if (it.getName() != null && it.getName().length() > 0) {
                variants.add(LookupElementBuilder.create(it).
                        withIcon(XtextIcons.FILE).
                        withTypeText(it.getContainingFile().getName())
                );
            }
        });

        return variants.toArray();
    }

}