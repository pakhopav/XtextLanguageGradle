package com.intellij.statLanguage.stat;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.languageUtil.completion.KeywordCompletionProvider;
import com.intellij.statLanguage.stat.psi.StatFile;
import com.intellij.statLanguage.stat.psi.StatTokenType;

import static com.intellij.patterns.PlatformPatterns.psiElement;


public class StatCompletionContributor extends CompletionContributor {
    public StatCompletionContributor() {

        extend(CompletionType.BASIC, psiElement().withLanguage(StatLanguage.INSTANCE)
                ,
                new KeywordCompletionProvider<StatFile, StatTokenType>(StatLanguage.INSTANCE, StatFileType.INSTANCE, StatTokenType.class));

    }
}

