package com.intellij.entityLanguage.entity.emf.scope

import com.intellij.xtextLanguage.xtext.emf.ObjectDescription
import com.intellij.xtextLanguage.xtext.emf.Scope

class EntityScope(val descriptions: List<ObjectDescription>) : Scope {

    override fun getSingleElement(name: String): ObjectDescription? {
        return descriptions.stream()
                .filter { it.objectName == name }
                .findFirst().get()
    }
}