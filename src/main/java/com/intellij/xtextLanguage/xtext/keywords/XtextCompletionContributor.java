package com.intellij.xtextLanguage.xtext.keywords;


import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.xtextLanguage.xtext.XtextFileType;
import com.intellij.xtextLanguage.xtext.XtextLanguage;
import com.intellij.xtextLanguage.xtext.psi.XtextFile;
import com.intellij.xtextLanguage.xtext.psi.XtextTokenType;

import static com.intellij.patterns.PlatformPatterns.psiElement;


public class XtextCompletionContributor extends CompletionContributor {
    public XtextCompletionContributor() {

        extend(CompletionType.BASIC, psiElement().withLanguage(XtextLanguage.INSTANCE)
                ,
                new KeywordCompletionProvider<XtextFile, XtextTokenType>(XtextLanguage.INSTANCE, XtextFileType.INSTANCE, XtextTokenType.class));

    }
}



