package com.intellij.xtext.emf

//import com.intellij.statLanguage.stat.psi.StatTypes
import com.intellij.openapi.vfs.LocalFileSystem
import java.io.File

class EmfTests : EmfTestsBase("/emf") {


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

    //    fun testSimpleEntityEmfInstance() {
//        val myRes = getEntityEmfModel()
//        myRes?.let { persistEntityEmfModel(it) }
//        assertEqualXmi("test.entity", "entity")
//    }
//
//    fun testSimpleEntityWithExtends() {
//        val myRes = getEntityEmfModel()
//        myRes?.let { persistEntityEmfModel(it) }
//        assertEqualXmi("simpleWithExtends.entity", "entity")
//
//    }
//
//    fun testSimpleEntityWithDatatypes() {
//        val myRes = getEntityEmfModel()
//        myRes?.let { persistEntityEmfModel(it) }
//        assertEqualXmi("simpleWithDatatypes.entity", "entity")
//
//    }
//
//    fun testEntityAll() {
//        val myRes = getEntityEmfModel()
//        myRes?.let { persistEntityEmfModel(it) }
//        assertEqualXmi("entityAllExpected.entity", "entity")
//
//    }

    //========================== Arithmetics

//    fun testCalcAll() {
//        val myRes = getCalcEmfModel()
//        myRes?.let { persistEntityEmfModel(it) }
//    }

    //    fun testCalcMultiPluses() {
//        val myRes = getCalcEmfModel()
//        myRes?.let { persistCalcEmfModel(it as EObject) }
//        assertEqualXmi("calcMultiPlusesExpected.calc", "calc")
//    }
//
//    fun testCalcWithDefinitions() {
//        val myRes = getCalcEmfModel()
//        myRes?.let { persistCalcEmfModel(it) }
//        assertEqualXmi("calcWithDefinitionsExpected.calc", "calc")
//    }
//
//    fun testCalcWithDefinitionsDifficult() {
//        val myRes = getCalcEmfModel()
//        myRes?.let { persistCalcEmfModel(it) }
//        assertEqualXmi("calcWithDefinitionsDifficultExpected.calc", "calc")
//    }
//
//   ========================== Calc2
//    fun testCalc2WithDefinitionsDifficult() {
//        val myRes = getCalc2EmfModel()
//        myRes?.let { persistCalc2EmfModel(it) }
//        assertEqualXmi("calcWithDefinitionsDifficultExpected.calc", "calc2")
//    }
//    //========================== Statemachine
//
//    fun testStatSimple() {
//        val leaf = LeafPsiElement(StatTypes.ID, "ASD")
//        val myRes = getStatEmfModel()
//        myRes?.let { persistStatEmfModel(it as EObject) }
//        assertEqualXmi("statSimpleExpected.stat", "stat")
//    }


    //=============================== Small Java

    fun testsss() {
        val f = LocalFileSystem.getInstance().findFileByIoFile(File("src/main/resources/grammars/Terminals.xtext"))
        println("sda")
    }

//    fun testSmallJava() {
//        val myRes = getSmalljavaEmfModel()
//        myRes?.let { persistSmalljavaEmfModel(it as EObject) }
//        assertEqualXmi("smalljavaExpected.smalljava", "smalljava")
//    }

    //=========================== Xtext

//    fun testXtextArithmetics() {
//        val myRes = getXtextEmfModel()
//        myRes?.let { persistXtexttEmfModel(it as EObject) }
//        assertEqualXmi("xtextArithmeticsExpected.xtextt", "xtextt")
//    }

}