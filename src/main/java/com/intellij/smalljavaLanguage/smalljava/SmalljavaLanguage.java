package com.intellij.smalljavaLanguage.smalljava;

import com.intellij.lang.Language;

public class SmalljavaLanguage extends Language {
    public static final SmalljavaLanguage INSTANCE = new SmalljavaLanguage();

    private SmalljavaLanguage() {
        super("Smalljava");

    }
}