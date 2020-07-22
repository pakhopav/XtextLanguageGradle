package com.intellij.xtext.emf

import arithmetics.ArithmeticsPackage
import arithmetics.Module
import com.intellij.calcLanguage.calc.emf.CalcEmfBridge
import com.intellij.calcLanguage.calc.psi.CalcFile
import com.intellij.entityLanguage.entity.emf.EntityEmfBridge
import com.intellij.entityLanguage.entity.psi.EntityFile
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
import java.nio.file.Files
import java.nio.file.Paths

open class EntityEmfTestsBase(val myDataFolder: String) : BasePlatformTestCase() {

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

    fun getCalcEmfModel(): Module? {
        val fileName = getCurrentInputFileName("calc")
        fileName?.let {
            val file = myFixture.configureByFile(it) as CalcFile
            val bridge = CalcEmfBridge()
            return bridge.createEmfModel(file) as Module
        }
        return null
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


    fun assertEqualXmi(compareWith: String, extention: String) {
        val testFileName = getCurrentInputFileName(extention)
        val myFileContent = Files.readAllLines(Paths.get("$basePath/$testFileName"))
        val expectedContent = Files.readAllLines(Paths.get("$basePath/$compareWith"))
        TestCase.assertEquals(myFileContent, expectedContent)
    }

}