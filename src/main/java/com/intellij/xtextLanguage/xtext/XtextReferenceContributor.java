package com.intellij.xtextLanguage.xtext;

import com.intellij.psi.PsiReferenceContributor;
import com.intellij.psi.PsiReferenceRegistrar;
import org.jetbrains.annotations.NotNull;

public class XtextReferenceContributor extends PsiReferenceContributor {
    @Override
    public void registerReferenceProviders(@NotNull PsiReferenceRegistrar registrar) {
//        registrar.registerReferenceProvider(PlatformPatterns.psiElement(XtextREFERENCEAbstractRuleRuleID.class).withLanguage(XtextLanguage.INSTANCE),
//                new PsiReferenceProvider() {
//                    @NotNull
//                    @Override
//                    public PsiReference[] getReferencesByElement(@NotNull PsiElement element,
//                                                                 @NotNull ProcessingContext
//                                                                         context) {
//                        XtextREFERENCEAbstractRuleRuleID simpleRef = (XtextREFERENCEAbstractRuleRuleID) element;
//                        String value = simpleRef.getText() instanceof String ? simpleRef.getText() : null;
//
//                        return new PsiReference[]{
//                                new XtextReference(element, new TextRange(0, value.length()), XtextAbstractRule.class)};
//
//
//                    }
//                });
//
//
    }
}