package com.intellij.entityLanguage.entity.emf

import com.intellij.entityLanguage.entity.psi.TypeProxy
import org.xtext.example.entity.entity.Domainmodel
import org.xtext.example.entity.entity.Entity
import org.xtext.example.entity.entity.Feature

class EntityEmfTypeProxyFinder {
    val proxyList = mutableMapOf<Feature, TypeProxy>()
    val s = 2

    companion object {
        fun getMapOfFeaturesAndItsTypes(domainmodel: Domainmodel): MutableMap<Feature, TypeProxy> {
            val visitor = EntityEmfTypeProxyFinder()
            visitor.visitDomainmodel(domainmodel)
            return visitor.proxyList

        }
    }

    fun visitDomainmodel(domainmodel: Domainmodel) {
        domainmodel.elements.forEach {
            if (it is Entity) visitEntity(it)
        }
    }

    fun visitEntity(entity: Entity) {
        entity.features.forEach { visitFeature(it) }

    }

    fun visitFeature(feature: Feature) {
        proxyList.put(feature, feature.type as TypeProxy)
    }
}