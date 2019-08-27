package com.intellij.xtextLanguage.xtext.grammar;

import com.intellij.lexer.FlexAdapter;
import com.intellij.xtextLanguage.xtext._XtextLexer;


public class XtextLexer extends FlexAdapter {


    public XtextLexer() {
        super(new _XtextLexer());
    }
}