package com.intellij.xtextLanguage.xtext;

import com.intellij.openapi.util.TextRange;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.*;
import com.intellij.util.ProcessingContext;
import com.intellij.xtextLanguage.xtext.psi.XtextAbstractRule;
import com.intellij.xtextLanguage.xtext.psi.XtextREFERENCEAbstractRuleRuleID;
import org.jetbrains.annotations.NotNull;

public class XtextReferenceContributor extends PsiReferenceContributor {
    @Override
    public void registerReferenceProviders(@NotNull PsiReferenceRegistrar registrar) {
        registrar.registerReferenceProvider(PlatformPatterns.psiElement(XtextREFERENCEAbstractRuleRuleID.class).withLanguage(XtextLanguage.INSTANCE),
                new PsiReferenceProvider() {
                    @NotNull
                    @Override
                    public PsiReference[] getReferencesByElement(@NotNull PsiElement element,
                                                                 @NotNull ProcessingContext
                                                                         context) {
                        XtextREFERENCEAbstractRuleRuleID simpleRef = (XtextREFERENCEAbstractRuleRuleID) element;
                        String value = simpleRef.getText() instanceof String ? simpleRef.getText() : null;

                        return new PsiReference[]{
                                new XtextReference(element, new TextRange(0, value.length()), XtextAbstractRule.class)};


                    }
                });


    }
}