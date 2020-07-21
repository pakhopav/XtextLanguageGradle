package com.intellij.xtextLanguage.xtext.emf

import org.eclipse.emf.ecore.EObject

@FunctionalInterface
interface LiteralAssignment {
    fun assign(obj: EObject)
}