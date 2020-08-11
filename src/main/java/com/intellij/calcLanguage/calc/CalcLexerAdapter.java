package com.intellij.calcLanguage.calc;

import com.intellij.lexer.FlexAdapter;

import java.io.Reader;

public class CalcLexerAdapter extends FlexAdapter {
    public CalcLexerAdapter() {
        super(new _CalcLexer((Reader) null));
    }
}
