//package com.intellij.entityLanguage.entity.emf
//
//import org.example.domainmodel.domainmodel.*
//
//open class EmfVisitor() {
//
//    open fun visitDomainmodel(o: Domainmodel) {
//        o.elements.forEach {
//            visitType(it)
//        }
//    }
//
//    open fun visitEntity(o: Entity) {
//        o.features.forEach {
//            visitFeature(it)
//        }
//    }
//
//    open fun visitFeature(o: Feature) {
//
//    }
//
//
//    open fun visitType(o: Type) {
//        if (o is Entity) {
//            visitEntity(o)
//        }
//        if (o is DataType) {
//            visitDataType(o)
//        }
//
//    }
//
//    open fun visitDataType(o: DataType) {
//
//    }
//
//
//}