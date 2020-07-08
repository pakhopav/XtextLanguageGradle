package com.intellij.entityLanguage.entity;

import com.intellij.lexer.FlexAdapter;

import java.io.Reader;

public class EntityLexerAdapter extends FlexAdapter {
    public EntityLexerAdapter() {
        super(new _EntityLexer((Reader) null));
    }
}
