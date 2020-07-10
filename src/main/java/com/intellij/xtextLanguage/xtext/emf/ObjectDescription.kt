package com.intellij.xtextLanguage.xtext.emf

import org.eclipse.emf.ecore.EObject

interface ObjectDescription {
    val obj: EObject
    val objectName: String

}