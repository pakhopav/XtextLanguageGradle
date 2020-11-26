package com.intellij.smalljavaLanguage.smalljava;

import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SmalljavaReference extends PsiReferenceBase<PsiElement> implements PsiPolyVariantReference {
    private String key;
    private List<Class<? extends PsiNameIdentifierOwner>> tClasses;

    public SmalljavaReference(@NotNull PsiElement element, TextRange textRange, List<Class<? extends PsiNameIdentifierOwner>> tclasses) {
        super(element, textRange);
        key = element.getText().substring(textRange.getStartOffset(), textRange.getEndOffset());
        this.tClasses = tclasses;
    }

    @NotNull
    @Override
    public ResolveResult[] multiResolve(boolean incompleteCode) {
        return multiResolve(incompleteCode, tClasses);
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
        return getVariants(tClasses);
    }

    public ResolveResult[] multiResolve(boolean incompleteCode, final List<Class<? extends PsiNameIdentifierOwner>> classes) {
        PsiFile file = myElement.getContainingFile();
        ResolveResult[] results = classes.stream()
                .flatMap(it -> SmalljavaUtil.findElementsInCurrentFile(file, it, key).stream())
                .map(PsiElementResolveResult::new).toArray(ResolveResult[]::new);
        return results;
    }


    public Object[] getVariants(List<Class<? extends PsiNameIdentifierOwner>> classes) {
        PsiFile file = myElement.getContainingFile();
        Object[] variants = classes.stream()
                .flatMap(it -> SmalljavaUtil.findElementsInCurrentFile(file, it).stream())
                .filter(it -> (it.getName() != null && it.getName().length() > 0))
                .map(it -> LookupElementBuilder.create(it).
                        withIcon(SmalljavaIcons.FILE).
                        withTypeText(it.getContainingFile().getName())).toArray(Object[]::new);

        return variants;
    }
}