package com.intellij.xtext.emf

import com.intellij.testFramework.fixtures.BasePlatformTestCase
import com.intellij.xtext.AllTests
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl
import org.xtext.example.entity.entity.EntityPackage

open class EntityEmfTestsBase(val myDataFolder: String) : BasePlatformTestCase() {

    open fun getCurrentInputFileName(): String? {
        return getTestName(true) + ".entity"
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
        val resource = resSet.getResource(URI.createURI("/Users/pavel/work/xtextGradle/XtextLanguageGradle/src/test/resources/testData/emf/" + fileName), true);

        return resource.getContents().get(0);
    }

}