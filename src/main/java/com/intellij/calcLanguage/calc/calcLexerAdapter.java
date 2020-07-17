package com.intellij.calcLanguage.calc;

import com.intellij.lexer.FlexAdapter;

import java.io.Reader;

public class calcLexerAdapter extends FlexAdapter {
    public calcLexerAdapter() {
        super(new _calcLexer((Reader) null));
    }
}
