package com.intellij.entityLanguage.entity.emf

import com.intellij.entityLanguage.entity.emf.scope.EntityScope
import com.intellij.entityLanguage.entity.psi.EntityDomainmodel
import com.intellij.entityLanguage.entity.psi.EntityFile
import com.intellij.psi.PsiFile
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.xtextLanguage.xtext.emf.EmfBridge
import com.intellij.xtextLanguage.xtext.emf.Scope
import org.eclipse.emf.ecore.EObject
import org.xtext.example.entity.entity.Domainmodel
import org.xtext.example.entity.entity.Entity
import org.xtext.example.entity.entity.Feature
import org.xtext.example.entity.entity.Type

class EntityEmfBridge : EmfBridge {


    override fun createEmfModel(file: PsiFile): EObject? {
        if (file is EntityFile) {
            val filePsiRoot = PsiTreeUtil.findChildOfType(file, EntityDomainmodel::class.java)
            filePsiRoot?.let {
                val visitor = EntityEmfVisitor()
                visitor.createModel(it)
                return completeRawModel(visitor.emfRoot, visitor.referencedEntities.toMap(), visitor.referencedTypes.toMap(), EntityScope(visitor.modelDescriptions))
            }

        }
        return null

    }

    private fun completeRawModel(rawModel: EObject?, referensedEntities: Map<Entity, String>, referensedTypes: Map<Feature, String>, scope: Scope): EObject? {
        if (rawModel is Domainmodel) {

            referensedEntities.forEach {
                val container = it.key
                val resolvedEntity = scope.getSingleElement(it.value)
                resolvedEntity?.let { container.superType = it as Entity }
            }

            referensedTypes.forEach {
                val container = it.key
                val resolvedType = scope.getSingleElement(it.value)
                resolvedType?.let { container.type = it as Type }
            }


        }
        return rawModel
    }

}