package com.intellij.statLanguage.stat.emf.scope

import com.intellij.xtextLanguage.xtext.emf.ObjectDescription
import com.intellij.xtextLanguage.xtext.emf.Scope

class StatScope(val descriptions: List<ObjectDescription>) : Scope {

    override fun getSingleElement(name: String): ObjectDescription? {
        return descriptions.firstOrNull { it.objectName == name }
    }
}