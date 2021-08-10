package com.intellij.xtext.bridge

import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EObject

interface ObjectDescription {
    fun getName(): String
    fun getObject(): EObject
    fun getClass(): EClass

}