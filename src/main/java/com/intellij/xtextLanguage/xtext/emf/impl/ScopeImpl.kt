package com.intellij.xtextLanguage.xtext.emf.impl

import com.intellij.xtextLanguage.xtext.emf.ObjectDescription
import com.intellij.xtextLanguage.xtext.emf.Scope
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EObject

open class ScopeImpl(protected val descriptions: List<ObjectDescription>) : Scope {

    override fun getSingleElementOfType(name: String, type: EClass): EObject? {
        return descriptions.firstOrNull { it.getName() == name && type.isSuperTypeOf(it.getClass()) }?.getObject()
    }

    override fun getElementsOfType(name: String, type: EClass): List<EObject> {
        return descriptions.filter { it.getName() == name && type.isSuperTypeOf(it.getClass()) }.map { it.getObject() }
    }

}