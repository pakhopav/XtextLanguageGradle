package com.intellij.xtextLanguage.xtext.emf

import org.eclipse.emf.ecore.EObject

interface ObjectDescription {


    fun getName(): String

    fun getObjectOrProxy(): EObject
}