package com.intellij.module

import com.intellij.xtextLanguage.xtext.psi.XtextFile

class XtextGrammarFileInfo(var grammarName: String, var file: XtextFile?) : Cloneable {

    public override fun clone(): Any {
        return super.clone()
    }


}