package com.intellij.xtextLanguage.xtext.emf.impl

import com.intellij.xtextLanguage.xtext.emf.ObjectDescription
import org.eclipse.emf.ecore.EObject

class ObjectDescriptionImpl(val obj: EObject, val objectName: String) : ObjectDescription {

    override fun getName(): String {
        return objectName
    }

    override fun getObject(): EObject {
        return obj
    }
}