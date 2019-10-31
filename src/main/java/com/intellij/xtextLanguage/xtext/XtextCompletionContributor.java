package com.intellij.xtextLanguage.xtext;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.languageUtil.completion.KeywordCompletionProvider;
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

