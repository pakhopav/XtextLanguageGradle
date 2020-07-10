package com.intellij.xtextLanguage.xtext.emf


interface Scope {

    fun getSingleElement(name: String): ObjectDescription?
}