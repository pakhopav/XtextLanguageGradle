package com.intellij.xtextLanguage.xtext.emf

import org.eclipse.emf.ecore.EObject

interface ObjectAssignment {
    fun assign(obj: EObject, toAssign: EObject)
}