package com.intellij.entityLanguage.entity.emf

import com.intellij.entityLanguage.entity.psi.EntityProxy
import org.xtext.example.entity.entity.Domainmodel
import org.xtext.example.entity.entity.Entity

class EntityEmfEntityProxyFinder {
    val proxyList = mutableMapOf<Entity, EntityProxy>()
    val s = 2

    companion object {
        fun getMapOfEntityAndItsSupertype(domainmodel: Domainmodel): MutableMap<Entity, EntityProxy> {
            val visitor = EntityEmfEntityProxyFinder()
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
        val supertype = entity.superType
        supertype?.let {
            if (it is EntityProxy) proxyList.put(entity, it)
        }
    }


}