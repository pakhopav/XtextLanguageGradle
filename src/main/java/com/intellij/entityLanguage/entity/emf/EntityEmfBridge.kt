package com.intellij.entityLanguage.entity.emf

import com.intellij.entityLanguage.entity.psi.EntityDomainmodel
import com.intellij.entityLanguage.entity.psi.EntityFile
import com.intellij.psi.PsiFile
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.xtextLanguage.xtext.emf.EmfBridge
import org.eclipse.emf.ecore.EObject
import org.xtext.example.entity.entity.Domainmodel
import org.xtext.example.entity.entity.Entity
import org.xtext.example.entity.entity.Type

class EntityEmfBridge : EmfBridge {
    override fun createEmfModel(file: PsiFile): EObject? {
        if (file is EntityFile) {
            val filePsiRoot = PsiTreeUtil.findChildOfType(file, EntityDomainmodel::class.java)
            filePsiRoot?.let {
                val rawModel = EntityEmfVisitor.getRawEmfModel(it)
                rawModel?.let { return completeRawModel(it) }
            }

        }
        return null

    }

    override fun completeRawModel(rawModel: EObject): EObject {
        if (rawModel is Domainmodel) {
            val proxyListEntities = EntityEmfEntityProxyFinder.getMapOfEntityAndItsSupertype(rawModel)
            val proxyListTypes = EntityEmfTypeProxyFinder.getMapOfFeaturesAndItsTypes(rawModel)
            proxyListEntities.forEach {
                val resolvedProxy = findEntityInModelByName(rawModel, it.value.name)
                it.key.superType = resolvedProxy
            }
            proxyListTypes.forEach {
                val resolvedType = findTypeInModelByName(rawModel, it.value.name)
                it.key.type = resolvedType
            }
        }
        return rawModel
    }

    fun findEntityInModelByName(model: Domainmodel, name: String): Entity? {
        return model.elements.stream()
                .filter { it is Entity }
                .filter { it.name == name }
                .findFirst().get() as Entity
    }

    fun findTypeInModelByName(model: Domainmodel, name: String): Type? {
        return model.elements.stream()
                .filter { it.name == name }
                .findFirst().get()
    }

}