package com.intellij.calcLanguage.calc;

import com.intellij.calcLanguage.calc.psi.calcFile;
import com.intellij.calcLanguage.calc.psi.calcTokenType;
import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.languageUtil.completion.KeywordCompletionProvider;

import static com.intellij.patterns.PlatformPatterns.psiElement;


public class calcCompletionContributor extends CompletionContributor {
    public calcCompletionContributor() {

        extend(CompletionType.BASIC, psiElement().withLanguage(calcLanguage.INSTANCE)
                ,
                new KeywordCompletionProvider<calcFile, calcTokenType>(calcLanguage.INSTANCE, calcFileType.INSTANCE, calcTokenType.class));

    }
}

