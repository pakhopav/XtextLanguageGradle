package com.intellij.xtext.emf

//import com.intellij.calcLanguage.calc.emf.CalcEmfBridge
//import com.intellij.xtexttLanguage.xtextt.emf.XtexttEmfBridge
//import com.intellij.xtexttLanguage.xtextt.psi.XtexttFile
//import com.intellij.calc2Language.calc2.emf.Calc2EmfBridge
//import com.intellij.calc2Language.calc2.psi.Calc2File
//import com.intellij.smalljavaLanguage.smalljava.emf.SmalljavaEmfBridge
//import com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaFile
import arithmetics.ArithmeticsPackage
import com.intellij.entityLanguage.entity.emf.EntityEmfBridge
import com.intellij.entityLanguage.entity.psi.EntityFile
import com.intellij.statLanguage.stat.emf.StatEmfBridge
import com.intellij.statLanguage.stat.psi.StatFile
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import com.intellij.xtext.AllTests
import junit.framework.TestCase
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl
import org.xtext.example.entity.entity.Domainmodel
import org.xtext.example.entity.entity.EntityPackage
import org.xtext.example.xtext.xtext.XtextPackage
import smallJava.SmallJavaPackage
import statemachine.Statemachine
import statemachine.StatemachinePackage
import java.nio.file.Files
import java.nio.file.Paths

open class EmfTestsBase(val myDataFolder: String) : BasePlatformTestCase() {

    open fun getCurrentInputFileName(extention: String): String? {
        return getTestName(true) + ".$extention"
    }

    override fun getTestDataPath(): String {
        return getBasePath()
    }

    override fun getBasePath(): String {
        return AllTests.getTestDataRoot() + myDataFolder
    }

    fun getModelFromXmi(fileName: String): EObject {
        EntityPackage.eINSTANCE.eClass()

        val reg = Resource.Factory.Registry.INSTANCE;
        val m = reg.getExtensionToFactoryMap();
        m.put("entity", XMIResourceFactoryImpl());

        val resSet = ResourceSetImpl();
        val resource = resSet.getResource(URI.createURI("$basePath/$fileName"), true);

        return resource.getContents().get(0);
    }

    fun getEntityEmfModel(): Domainmodel? {
        val fileName = getCurrentInputFileName("entity")
        fileName?.let {
            val file = myFixture.configureByFile(it) as EntityFile
            val bridge = EntityEmfBridge()
            return bridge.createEmfModel(file) as Domainmodel
        }
        return null
    }
//
//    fun getCalcEmfModel(): Module? {
//        val fileName = getCurrentInputFileName("calc")
//        fileName?.let {
//            val file = myFixture.configureByFile(it) as CalcFile
//            val bridge = CalcEmfBridge()
//            return bridge.createEmfModel(file) as Module
//        }
//        return null
//    }

    //    fun getCalc2EmfModel(): Module? {
//        val fileName = getCurrentInputFileName("calc2")
//        fileName?.let {
//            val file = myFixture.configureByFile(it) as Calc2File
//            val bridge = Calc2EmfBridge()
//            return bridge.createEmfModel(file) as Module
//        }
//        return null
//    }
//
    fun getStatEmfModel(): Statemachine? {
        val fileName = getCurrentInputFileName("stat")
        fileName?.let {
            val file = myFixture.configureByFile(it) as StatFile
            val bridge = StatEmfBridge()
            return bridge.createEmfModel(file) as Statemachine
        }
        return null
    }

    //    fun getXtextEmfModel(): Grammar? {
//        val fileName = getCurrentInputFileName("xtextt")
//        fileName?.let {
//            val file = myFixture.configureByFile(it) as XtexttFile
//            val bridge = XtexttEmfBridge()
//            return bridge.createEmfModel(file) as Grammar
//        }
//        return null
//    }
//    fun getSmalljavaEmfModel(): SJProgram? {
//        val fileName = getCurrentInputFileName("smalljava")
//        fileName?.let {
//            val file = myFixture.configureByFile(it) as SmalljavaFile
//            val bridge = SmalljavaEmfBridge()
//            return bridge.createEmfModel(file) as SJProgram
//        }
//        return null
//    }
    fun persistSmalljavaEmfModel(model: EObject) {
        val testFileName = getCurrentInputFileName("smalljava")
        SmallJavaPackage.eINSTANCE.eClass()
        val reg = Resource.Factory.Registry.INSTANCE
        val m = reg.getExtensionToFactoryMap()
        m.put("smalljava", XMIResourceFactoryImpl())
        val resSet = ResourceSetImpl()

        val resource = resSet.createResource(
                URI.createFileURI(
                        "$basePath/$testFileName"))

        resource.getContents().add(model);
        resource.save(null);
    }

    fun persistEntityEmfModel(model: EObject) {
        val testFileName = getCurrentInputFileName("entity")
        EntityPackage.eINSTANCE.eClass()
        val reg = Resource.Factory.Registry.INSTANCE
        val m = reg.getExtensionToFactoryMap()
        m.put("entity", XMIResourceFactoryImpl())
        val resSet = ResourceSetImpl()

        val resource = resSet.createResource(
                URI.createFileURI(
                        "$basePath/$testFileName"))

        resource.getContents().add(model);
        resource.save(null);
    }

    fun persistCalc2EmfModel(model: EObject) {
        val testFileName = getCurrentInputFileName("calc2")
        ArithmeticsPackage.eINSTANCE.eClass()
        val reg = Resource.Factory.Registry.INSTANCE
        val m = reg.getExtensionToFactoryMap()
        m.put("calc2", XMIResourceFactoryImpl())
        val resSet = ResourceSetImpl()

        val resource = resSet.createResource(
                URI.createFileURI(
                        "$basePath/$testFileName"))

        resource.getContents().add(model);
        resource.save(null);
    }

    fun persistCalcEmfModel(model: EObject) {
        val testFileName = getCurrentInputFileName("calc")
        ArithmeticsPackage.eINSTANCE.eClass()
        val reg = Resource.Factory.Registry.INSTANCE
        val m = reg.getExtensionToFactoryMap()
        m.put("calc", XMIResourceFactoryImpl())
        val resSet = ResourceSetImpl()

        val resource = resSet.createResource(
                URI.createFileURI(
                        "$basePath/$testFileName"))

        resource.getContents().add(model);
        resource.save(null);
    }

    fun persistStatEmfModel(model: EObject) {
        val testFileName = getCurrentInputFileName("stat")
        StatemachinePackage.eINSTANCE.eClass()
        val reg = Resource.Factory.Registry.INSTANCE
        val m = reg.getExtensionToFactoryMap()
        m.put("stat", XMIResourceFactoryImpl())
        val resSet = ResourceSetImpl()

        val resource = resSet.createResource(
                URI.createFileURI(
                        "$basePath/$testFileName"))

        resource.getContents().add(model)
        resource.save(null)
    }

    fun persistXtexttEmfModel(model: EObject) {
        val testFileName = getCurrentInputFileName("xtextt")
        XtextPackage.eINSTANCE.eClass()
        val reg = Resource.Factory.Registry.INSTANCE
        val m = reg.getExtensionToFactoryMap()
        m.put("xtextt", XMIResourceFactoryImpl())
        val resSet = ResourceSetImpl()

        val resource = resSet.createResource(
                URI.createFileURI(
                        "$basePath/$testFileName"))

        resource.getContents().add(model)
        resource.save(null)
    }

    fun assertEqualXmi(compareWith: String, extention: String) {
        val testFileName = getCurrentInputFileName(extention)
        val myFileContent = Files.readAllLines(Paths.get("$basePath/$testFileName"))
        val expectedContent = Files.readAllLines(Paths.get("$basePath/$compareWith"))
        TestCase.assertEquals(myFileContent, expectedContent)
    }

}