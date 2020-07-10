package com.intellij.entityLanguage.entity.emf

import com.intellij.entityLanguage.entity.emf.scope.EntityScope
import com.intellij.entityLanguage.entity.psi.*
import com.intellij.xtextLanguage.xtext.emf.ObjectDescription
import com.intellij.xtextLanguage.xtext.emf.impl.ObjectDescriptionImpl
import org.xtext.example.entity.entity.*

class EntityEmfVisitor {
    private var emfRoot: Domainmodel? = null
    private var referencedEntities = mutableMapOf<Entity, String>()
    private var referencedTypes = mutableMapOf<Feature, String>()
    private var modelDescriptions = mutableListOf<ObjectDescription>()
    private val factory = EntityFactory.eINSTANCE


    fun createModel(psiDomainmodel: EntityDomainmodel): Domainmodel? {
        visitDomainmodel(psiDomainmodel)
        completeRawModel()
        return emfRoot
    }


    fun visitDomainmodel(psiDomainmodel: EntityDomainmodel) {
        val domainModel = factory.createDomainmodel()
        psiDomainmodel.typeList.forEach {
            visitType(it, domainModel)
        }
        emfRoot = domainModel
    }

    fun visitEntity(psiEntity: EntityEntity, emfDomainmodel: Domainmodel) {
        val entity = factory.createEntity()
        psiEntity.name?.let { entity.name = it }
        psiEntity.referenceEntityID?.let { visitREFERENCEEntityID(it, entity) }
        psiEntity.featureList.forEach {
            visitFeature(it, entity)
        }
        emfDomainmodel.elements.add(entity)
        modelDescriptions.add(ObjectDescriptionImpl(entity, entity.name))
    }

    fun visitFeature(psiFeature: EntityFeature, emfEntity: Entity) {
        val feature = factory.createFeature()
        feature.name = psiFeature.id.text
        psiFeature.referenceTypeID?.let { visitREFERENCETypeID(it, feature) }
        emfEntity.features.add(feature)
    }

    fun visitDataType(psiDataType: EntityDataType, emfDomainmodel: Domainmodel) {
        val dataType = factory.createDataType()
        dataType.name = psiDataType.id.text
        emfDomainmodel.elements.add(dataType)
        modelDescriptions.add(ObjectDescriptionImpl(dataType, dataType.name))
    }

    fun visitType(psiType: EntityType, emfDomainmodel: Domainmodel) {
        psiType.dataType?.let {
            visitDataType(it, emfDomainmodel)
        }
        psiType.entity?.let {
            visitEntity(it, emfDomainmodel)
        }
    }

    fun visitREFERENCEEntityID(psiReferenceEntity: EntityREFERENCEEntityID, emfEntity: Entity) {
        referencedEntities.put(emfEntity, psiReferenceEntity.text)
    }

    fun visitREFERENCETypeID(psiReferenceType: EntityREFERENCETypeID, emfFeature: Feature) {
        referencedTypes.put(emfFeature, psiReferenceType.text)
    }


    private fun completeRawModel() {
        val scope = EntityScope(modelDescriptions)
        referencedEntities.forEach {
            val container = it.key
            val resolvedEntity = scope.getSingleElement(it.value)?.obj
            resolvedEntity?.let { container.superType = it as Entity }
        }

        referencedTypes.forEach {
            val container = it.key
            val resolvedType = scope.getSingleElement(it.value)?.obj
            resolvedType?.let { container.type = it as Type }

        }
    }
}
