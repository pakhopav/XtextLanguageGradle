package com.intellij.entityLanguage.entity;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.entityLanguage.entity.psi.EntityFile;
import com.intellij.entityLanguage.entity.psi.EntityTokenType;
import com.intellij.languageUtil.completion.KeywordCompletionProvider;

import static com.intellij.patterns.PlatformPatterns.psiElement;


public class EntityCompletionContributor extends CompletionContributor {
    public EntityCompletionContributor() {

        extend(CompletionType.BASIC, psiElement().withLanguage(EntityLanguage.INSTANCE)
                ,
                new KeywordCompletionProvider<EntityFile, EntityTokenType>(EntityLanguage.INSTANCE, EntityFileType.INSTANCE, EntityTokenType.class));

    }
}

