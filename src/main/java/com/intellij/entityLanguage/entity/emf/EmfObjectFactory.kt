//package com.intellij.entityLanguage.entity.emf
//
//import com.intellij.entityLanguage.entity.psi.*
//import org.example.domainmodel.domainmodel.*
//
//class EmfObjectFactory {
//    var unfinishedEntities = mutableMapOf<Entity, String>()
//    var unfinishedFeatures = mutableMapOf<Feature, String>()
//    fun createEmfModel(domainmodel: EntityDomainmodel): Domainmodel {
//        val visitor = EObjectVisitor()
//        val model = visitor.visitDomainmodel(domainmodel)
//        unfinishedEntities = visitor.unfinishedEntities
//        unfinishedFeatures = visitor.unfinishedFeatures
//        val resolver = EObjectResolver(model)
//        unfinishedEntities.forEach {
//            it.key.superType = resolver.findEntity(it.value)
//        }
//        unfinishedFeatures.forEach {
//            it.key.type = resolver.findType(it.value)
//        }
//
//        return model
//    }
//
//
//    class EObjectVisitor {
//        val unfinishedEntities = mutableMapOf<Entity, String>()
//        val unfinishedFeatures = mutableMapOf<Feature, String>()
//
//        companion object {
//
//        }
//
//
//        val factory = DomainmodelFactory.eINSTANCE
//        fun visitDataType(o: EntityDataType): DataType {
//            val dataType = factory.createDataType()
//            dataType.name = o.id.text
//            return dataType
//        }
//
//        fun visitDomainmodel(o: EntityDomainmodel): Domainmodel {
//            val domainmodel = factory.createDomainmodel()
//            val elements = domainmodel.elements
//            o.typeList.forEach {
//                elements.add(visitType(it))
//            }
//
//            return domainmodel
//        }
//
//        fun visitEntity(o: EntityEntity): Entity {
//            val entity = factory.createEntity()
//            entity.name = o.id.text
//            o.referenceEntityID?.let {
//                unfinishedEntities.put(entity, it.text)
//            }
//            val features = entity.features
//            o.featureList.forEach {
//                features.add(visitFeature(it))
//            }
//            return entity
//        }
//
//        fun visitFeature(o: EntityFeature): Feature {
//            val feature = factory.createFeature()
//            feature.isMany = o.manyKeyword != null
//            feature.name = o.id.text
//            o.referenceTypeID?.let {
//                unfinishedFeatures.put(feature, it.text)
//            }
//            return feature
//        }
//
//
//        fun visitType(o: EntityType): Type {
//            var type: Type = factory.createType()
//            o.entity?.let {
//                type = visitEntity(it)
//
//            }
//            o.dataType?.let {
//                type = visitDataType(it)
//            }
//            return type
//
//        }
//    }
//}