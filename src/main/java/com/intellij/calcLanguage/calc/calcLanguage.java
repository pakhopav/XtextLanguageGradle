package com.intellij.calcLanguage.calc;

import com.intellij.lang.Language;

public class calcLanguage extends Language {
    public static final calcLanguage INSTANCE = new calcLanguage();

    private calcLanguage() {
        super("calc");

    }
}