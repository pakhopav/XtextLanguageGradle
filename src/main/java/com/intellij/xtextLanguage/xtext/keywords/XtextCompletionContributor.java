package com.intellij.xtextLanguage.xtext.keywords;


import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.xtextLanguage.xtext.XtextLanguage;
import com.intellij.xtextLanguage.xtext.psi.XtextTypes;

import static com.intellij.patterns.PlatformPatterns.psiElement;


public class XtextCompletionContributor extends CompletionContributor {
    public XtextCompletionContributor() {

        extend(CompletionType.BASIC, psiElement(XtextTypes.ID).withLanguage(XtextLanguage.INSTANCE)
                ,
                new XtextKeywordCompletionProvider());

    }
}



