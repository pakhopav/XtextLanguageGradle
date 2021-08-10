package com.intellij.xtext.language.reference;

import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import com.intellij.xtext.language.XtextIcon;
import com.intellij.xtext.language.psi.XtextFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class XtextRuleReference extends PsiReferenceBase<PsiElement> implements PsiPolyVariantReference {
    private String key;

    public XtextRuleReference(@NotNull PsiElement element, TextRange textRange) {
        super(element, textRange);
        key = element.getText().substring(textRange.getStartOffset(), textRange.getEndOffset());
    }

    @NotNull
    @Override
    public ResolveResult[] multiResolve(boolean incompleteCode) {
        return _multiResolve();
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
        return _getVariants();
    }

    private ResolveResult[] _multiResolve() {
        PsiFile file = myElement.getContainingFile();
        List<? extends PsiNameIdentifierOwner> elements = new ArrayList<>();

        elements.addAll(XtextReferenceUtil.findRulesInCurrentFile(file, key));

        elements.addAll(XtextReferenceUtil.findRulesByNameInUsedGrammars((XtextFile) file, key));

        List<ResolveResult> results = new ArrayList<>();
        elements.forEach(it -> {
            results.add(new PsiElementResolveResult(it));
        });

        return results.toArray(new ResolveResult[results.size()]);
    }

    public Object[] _getVariants() {
        PsiFile file = myElement.getContainingFile();
        List<? extends PsiNameIdentifierOwner> elements = new ArrayList<>();
        elements.addAll(XtextReferenceUtil.findAllRulesInCurrentFile(file));
        List<LookupElement> variants = new ArrayList<LookupElement>();
        elements.forEach(it -> {
            if (it.getName() != null && it.getName().length() > 0) {
                variants.add(LookupElementBuilder.create(it).
                        withIcon(XtextIcon.FILE).
                        withTypeText(it.getContainingFile().getName())
                );
            }
        });

        return variants.toArray();
    }
}