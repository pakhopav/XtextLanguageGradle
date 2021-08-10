package com.intellij.xtext.module.wizard

import com.intellij.xtext.language.psi.XtextFile

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