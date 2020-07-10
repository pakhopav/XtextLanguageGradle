package com.intellij.entityLanguage.entity.emf.scope

import com.intellij.xtextLanguage.xtext.emf.ObjectDescription
import com.intellij.xtextLanguage.xtext.emf.Scope
import org.eclipse.emf.ecore.EObject

class EntityScope(val descriptions: List<ObjectDescription>) : Scope {

    override fun getSingleElement(name: String): EObject? {
        return descriptions.stream()
                .filter { it.getName() == name }
                .findFirst()
                .get().getObject()
    }
}