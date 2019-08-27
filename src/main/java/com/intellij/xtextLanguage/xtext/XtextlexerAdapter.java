package com.intellij.xtextLanguage.xtext;

import com.intellij.lexer.FlexAdapter;

import java.io.Reader;

public class XtextlexerAdapter extends FlexAdapter {
    public XtextlexerAdapter() {
        super(new _XtextLexer((Reader) null));
    }
}
