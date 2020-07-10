package com.intellij.xtextLanguage.xtext.emf

import org.eclipse.emf.ecore.EObject

interface EmfFileBase {

    fun getEmfRoot(): EObject
}