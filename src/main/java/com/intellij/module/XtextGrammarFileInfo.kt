package com.intellij.module

import com.intellij.xtextLanguage.xtext.psi.XtextFile

class XtextGrammarFileInfo(var grammarName: String, pFile: XtextFile?) : Cloneable {
    var file: XtextFile? = pFile
        set(value) {
            field = value
            value?.let {
                filePath = it.virtualFile.path
            }
        }

    var filePath = file?.virtualFile?.path ?: "unresolved"


    public override fun clone(): Any {
        return super.clone()
    }

}