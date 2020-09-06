package com.intellij.statLanguage.stat;

import com.intellij.lexer.FlexAdapter;

import java.io.Reader;

public class StatLexerAdapter extends FlexAdapter {
    public StatLexerAdapter() {
        super(new _StatLexer((Reader) null));
    }
}
