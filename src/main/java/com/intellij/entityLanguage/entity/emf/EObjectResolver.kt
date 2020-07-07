//package com.intellij.entityLanguage.entity.emf
//
//import org.example.domainmodel.domainmodel.Domainmodel
//import org.example.domainmodel.domainmodel.Entity
//import org.example.domainmodel.domainmodel.Type
//
//class EObjectResolver(val domainmodel: Domainmodel) {
//
//    fun findEntity(name: String): Entity? {
//        return EntityFinder.findEntityByNameInModel(domainmodel, name)
//    }
//
//    fun findType(name: String): Type? {
//        return TypeFinder.findTypeByNameInModel(domainmodel, name)
//    }
//
//    class EntityFinder(val name: String) : EmfVisitor() {
//        var ent: Entity? = null
//
//        companion object {
//            fun findEntityByNameInModel(model: Domainmodel, name: String): Entity? {
//                val visitor = EntityFinder(name)
//                visitor.visitDomainmodel(model)
//                return visitor.ent
//            }
//        }
//
//        override fun visitEntity(o: Entity) {
//            if (o.name == name) {
//                ent = o
//            }
//        }
//    }
//
//    class TypeFinder(val name: String) : EmfVisitor() {
//        var type: Type? = null
//
//        companion object {
//            fun findTypeByNameInModel(model: Domainmodel, name: String): Type? {
//                val visitor = TypeFinder(name)
//                visitor.visitDomainmodel(model)
//                return visitor.type
//            }
//        }
//
//        override fun visitType(o: Type) {
//            if (o.name == name) {
//                type = o
//            }
//        }
//    }
//}