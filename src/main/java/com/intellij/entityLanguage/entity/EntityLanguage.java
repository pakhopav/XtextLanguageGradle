package com.intellij.entityLanguage.entity;

import com.intellij.lang.Language;

public class EntityLanguage extends Language {
    public static final EntityLanguage INSTANCE = new EntityLanguage();

    private EntityLanguage() {
        super("Entity");

    }
}