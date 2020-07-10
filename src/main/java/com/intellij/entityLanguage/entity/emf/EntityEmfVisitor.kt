package com.intellij.entityLanguage.entity.emf

import com.intellij.entityLanguage.entity.psi.*
import com.intellij.xtextLanguage.xtext.emf.ObjectDescription
import com.intellij.xtextLanguage.xtext.emf.impl.ObjectDescriptionImpl
import org.xtext.example.entity.entity.Domainmodel
import org.xtext.example.entity.entity.Entity
import org.xtext.example.entity.entity.EntityFactory
import org.xtext.example.entity.entity.Feature

class EntityEmfVisitor {
    var emfRoot: Domainmodel? = null
    var referencedEntities = mutableMapOf<Entity, String>()
    var referencedTypes = mutableMapOf<Feature, String>()
    var modelDescriptions = mutableListOf<ObjectDescription>()
    val factory = EntityFactory.eINSTANCE


    fun createModel(o: EntityDomainmodel) {
        visitDomainmodel(o)
    }


    fun visitDomainmodel(o: EntityDomainmodel) {
        val domainModel = factory.createDomainmodel()
        o.typeList.forEach {
            visitType(it, domainModel)
        }
        emfRoot = domainModel
    }

    fun visitEntity(o: EntityEntity, p: Domainmodel) {
        val entity = factory.createEntity()
        o.name?.let { entity.name = it }
        o.referenceEntityID?.let { visitREFERENCEEntityID(it, entity) }
        o.featureList.forEach {
            visitFeature(it, entity)
        }
        p.elements.add(entity)
        modelDescriptions.add(ObjectDescriptionImpl(entity, entity.name))
    }

    fun visitFeature(o: EntityFeature, p: Entity) {
        val feature = factory.createFeature()
        feature.name = o.id.text
        o.referenceTypeID?.let { visitREFERENCETypeID(it, feature) }
        p.features.add(feature)
    }

    fun visitDataType(o: EntityDataType, p: Domainmodel) {
        val dataType = factory.createDataType()
        dataType.name = o.id.text
        p.elements.add(dataType)
        modelDescriptions.add(ObjectDescriptionImpl(dataType, dataType.name))
    }

    fun visitType(o: EntityType, p: Domainmodel) {
        o.dataType?.let {
            visitDataType(it, p)
        }
        o.entity?.let {
            visitEntity(it, p)
        }
    }

    fun visitREFERENCEEntityID(o: EntityREFERENCEEntityID, p: Entity) {
        referencedEntities.put(p, o.text)
    }

    fun visitREFERENCETypeID(o: EntityREFERENCETypeID, p: Feature) {
        referencedTypes.put(p, o.text)
    }
}
