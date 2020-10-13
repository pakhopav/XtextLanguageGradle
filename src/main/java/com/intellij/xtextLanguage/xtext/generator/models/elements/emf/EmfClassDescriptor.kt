package com.intellij.xtextLanguage.xtext.generator.models.elements.emf

data class EmfClassDescriptor(val path: String, val prefix: String) {
    val name = path.split(".").last()


    companion object {
        val STRING = EmfClassDescriptor("", "")
    }
}