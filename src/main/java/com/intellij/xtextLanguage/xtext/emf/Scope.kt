package com.intellij.xtextLanguage.xtext.emf

import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EObject


interface Scope {

    fun getSingleElementOfType(name: String, type: EClass): EObject?

    fun getElementsOfType(name: String, type: EClass): List<EObject>
}