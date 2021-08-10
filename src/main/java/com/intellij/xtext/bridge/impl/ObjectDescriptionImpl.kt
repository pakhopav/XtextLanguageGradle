package com.intellij.xtext.bridge.impl

import com.intellij.xtext.bridge.ObjectDescription
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EObject

class ObjectDescriptionImpl(private val obj: EObject, private val objectName: String) : ObjectDescription {
    override fun getName(): String {
        return objectName
    }

    override fun getObject(): EObject {
        return obj
    }

    override fun getClass(): EClass {
        return obj.eClass()
    }

}