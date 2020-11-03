package com.intellij.smalljavaLanguage.smalljava;

import com.intellij.lexer.FlexAdapter;

import java.io.Reader;

public class SmalljavaLexerAdapter extends FlexAdapter {
    public SmalljavaLexerAdapter() {
        super(new _SmalljavaLexer((Reader) null));
    }
}
