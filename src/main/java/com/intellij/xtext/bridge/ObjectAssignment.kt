package com.intellij.xtext.bridge

import org.eclipse.emf.ecore.EObject

interface ObjectAssignment {
    fun assign(obj: EObject, toAssign: EObject)
}