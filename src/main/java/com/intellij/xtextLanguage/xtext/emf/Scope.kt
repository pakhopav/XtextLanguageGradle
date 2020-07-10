package com.intellij.xtextLanguage.xtext.emf

import org.eclipse.emf.ecore.EObject

interface Scope {

    fun getSingleElement(name: String): EObject?
}