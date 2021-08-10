package com.intellij.xtext.language;

import com.intellij.lexer.FlexAdapter;

import java.io.Reader;

public class XtextLexerAdapter extends FlexAdapter {
    public XtextLexerAdapter() {
        super(new _XtextLexer((Reader) null));
    }
}
