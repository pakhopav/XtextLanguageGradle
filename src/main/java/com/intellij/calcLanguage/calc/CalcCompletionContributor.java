package com.intellij.calcLanguage.calc;

import com.intellij.calcLanguage.calc.psi.CalcFile;
import com.intellij.calcLanguage.calc.psi.CalcTokenType;
import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.languageUtil.completion.KeywordCompletionProvider;

import static com.intellij.patterns.PlatformPatterns.psiElement;


public class CalcCompletionContributor extends CompletionContributor {
    public CalcCompletionContributor() {

        extend(CompletionType.BASIC, psiElement().withLanguage(CalcLanguage.INSTANCE)
                ,
                new KeywordCompletionProvider<CalcFile, CalcTokenType>(CalcLanguage.INSTANCE, CalcFileType.INSTANCE, CalcTokenType.class));

    }
}

