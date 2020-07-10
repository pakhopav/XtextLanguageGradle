package com.intellij.xtextLanguage.xtext.emf

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference

interface ScopeProvider {
    fun getScope(context: EObject, reference: EReference): Scope
}