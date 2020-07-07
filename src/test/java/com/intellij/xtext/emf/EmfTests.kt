package com.intellij.xtext.emf

import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl
import org.junit.Test
import org.xtext.example.entity.entity.EntityPackage

class EmfTests {


    @Test
    fun testLoadEcoreModelXmi() {
        EntityPackage.eINSTANCE.eClass()

        val reg = Resource.Factory.Registry.INSTANCE;
        val m = reg.getExtensionToFactoryMap();
        m.put("entity", XMIResourceFactoryImpl());

        val resSet = ResourceSetImpl();
        val resource = resSet.getResource(URI.createURI("/Users/pavel/work/xtextGradle/XtextLanguageGradle/src/test/resources/testData/emf/test.entity"), true);

        val result = resource.getContents().get(0);
        print("sad")
    }
}