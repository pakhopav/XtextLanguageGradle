package com.intellij.entityLanguage.entity.emf

import com.intellij.entityLanguage.entity.psi.*
import org.xtext.example.entity.entity.Domainmodel
import org.xtext.example.entity.entity.Entity
import org.xtext.example.entity.entity.EntityFactory
import org.xtext.example.entity.entity.Feature

class EntityEmfVisitor {
    var emfRoot: Domainmodel? = null
    val factory = EntityFactory.eINSTANCE


    companion object {
        fun getRawEmfModel(o: EntityDomainmodel): Domainmodel? {

            val visitor = EntityEmfVisitor()
            visitor.visitDomainmodel(o)
            return visitor.emfRoot


        }
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
        val proxy = EntityProxy()
        proxy.name = o.text
        p.superType = proxy
    }

    fun visitREFERENCETypeID(o: EntityREFERENCETypeID, p: Feature) {
        val proxy = TypeProxy()
        proxy.name = o.text
        p.type = proxy
    }
}
