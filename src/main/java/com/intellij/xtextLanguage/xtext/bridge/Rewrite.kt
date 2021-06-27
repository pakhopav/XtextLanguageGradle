package com.intellij.xtextLanguage.xtext.bridge

import org.eclipse.emf.ecore.EObject

interface Rewrite {
    fun rewrite(obj: EObject): EObject
}