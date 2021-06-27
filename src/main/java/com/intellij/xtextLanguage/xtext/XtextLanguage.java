package com.intellij.xtextLanguage.xtext;

import com.intellij.lang.Language;

public class XtextLanguage extends Language {
    public static final XtextLanguage INSTANCE = new XtextLanguage();

    private XtextLanguage() {
        super("Xtext");
    }
}