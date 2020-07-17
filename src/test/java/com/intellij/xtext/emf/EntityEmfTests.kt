package com.intellij.xtext.emf

class EntityEmfTests : EntityEmfTestsBase("/emf") {


//    fun testSimpleEntityEmfInstance() {
//        val fileName = getCurrentInputFileName()
//        fileName?.let {
//            val file = myFixture.configureByFile(it)
//            val dm = PsiTreeUtil.findChildOfAnyType(file, EntityDomainmodel::class.java)
//            dm?.let {
//                val myRes = EntityEmfVisitor.getEmfModel(it)
//                val eclRes = getModelFromXmi("test.entity")
//                print("sds")
//            }
//
//        }
//
//    }

    fun testSimpleEntityEmfInstance() {
        val myRes = getEntityEmfModel()
        myRes?.let { persistEntityEmfModel(it) }
        assertEqualXmi("test.entity", "entity")
    }

    fun testSimpleEntityWithExtends() {
        val myRes = getEntityEmfModel()
        myRes?.let { persistEntityEmfModel(it) }
        assertEqualXmi("simpleWithExtends.entity", "entity")

    }

    fun testSimpleEntityWithDatatypes() {
        val myRes = getEntityEmfModel()
        myRes?.let { persistEntityEmfModel(it) }
        assertEqualXmi("simpleWithDatatypes.entity", "entity")

    }

    fun testEntityAll() {
        val myRes = getEntityEmfModel()
        myRes?.let { persistEntityEmfModel(it) }
        assertEqualXmi("entityAllExpected.entity", "entity")

    }

    fun testCalcAll() {
        val myRes = getCalcEmfModel()
        myRes?.let { persistEntityEmfModel(it) }
    }

    fun testCalcMultiPluses() {
        val myRes = getCalcEmfModel()
        myRes?.let { persistCalcEmfModel(it) }
        assertEqualXmi("calcMultiPlusesExpected.calc", "calc")
    }

    fun testCalcWithDefinitions() {
        val myRes = getCalcEmfModel()
        myRes?.let { persistCalcEmfModel(it) }
        assertEqualXmi("calcWithDefinitionsExpected.calc", "calc")
    }
}