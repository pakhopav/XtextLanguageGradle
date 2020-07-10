package com.intellij.xtextLanguage.xtext.emf.impl

import com.intellij.xtextLanguage.xtext.emf.ObjectDescription
import org.eclipse.emf.ecore.EObject

class ObjectDescriptionImpl(override val obj: EObject, override val objectName: String) : ObjectDescription {

}