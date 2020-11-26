package com.intellij.smalljavaLanguage.smalljava;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.languageUtil.completion.KeywordCompletionProvider;
import com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaFile;
import com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaTokenType;

import static com.intellij.patterns.PlatformPatterns.psiElement;


public class SmalljavaCompletionContributor extends CompletionContributor {
    public SmalljavaCompletionContributor() {
        extend(CompletionType.BASIC, psiElement().withLanguage(SmalljavaLanguage.INSTANCE),
                new KeywordCompletionProvider<SmalljavaFile, SmalljavaTokenType>(SmalljavaLanguage.INSTANCE, SmalljavaFileType.INSTANCE, SmalljavaTokenType.class));
    }
}

