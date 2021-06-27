package com.intellij.xtextLanguage.xtext.bridge

import org.eclipse.emf.ecore.EObject

interface ObjectAssignment {
    fun assign(obj: EObject, toAssign: EObject)
}