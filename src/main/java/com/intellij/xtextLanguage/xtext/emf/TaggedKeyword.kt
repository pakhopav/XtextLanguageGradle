package com.intellij.xtextLanguage.xtext.emf

import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EStructuralFeature

data class TaggedKeyword(val keyword: String,
                         val newClass: EClass,
                         val objectFeatureToAssignCurrent: EStructuralFeature) {
}