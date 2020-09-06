package com.intellij.statLanguage.stat;

import com.intellij.lang.Language;

public class StatLanguage extends Language {
    public static final StatLanguage INSTANCE = new StatLanguage();

    private StatLanguage() {
        super("Stat");

    }
}