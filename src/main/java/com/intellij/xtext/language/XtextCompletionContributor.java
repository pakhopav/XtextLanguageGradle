package com.intellij.xtext.language;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.xtext.language.psi.XtextFile;
import com.intellij.xtext.language.psi.XtextTokenType;
import com.intellij.xtext.util.completion.KeywordCompletionProvider;

import static com.intellij.patterns.PlatformPatterns.psiElement;


public class XtextCompletionContributor extends CompletionContributor {
    public XtextCompletionContributor() {
        extend(CompletionType.BASIC, psiElement().withLanguage(XtextLanguage.INSTANCE),
                new KeywordCompletionProvider<XtextFile, XtextTokenType>(XtextLanguage.INSTANCE, XtextFileType.INSTANCE, XtextTokenType.class));
    }
}

