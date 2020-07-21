package com.intellij.xtextLanguage.xtext.emf

import org.eclipse.emf.ecore.EObject

interface Rewrite {
    fun rewrite(obj: EObject?): EObject
}