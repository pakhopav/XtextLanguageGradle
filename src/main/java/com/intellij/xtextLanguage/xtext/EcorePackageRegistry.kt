package com.intellij.xtextLanguage.xtext

import arithmetics.ArithmeticsPackage
import com.intellij.openapi.components.ServiceManager
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.EcorePackage
import org.xtext.example.entity.entity.EntityPackage
import org.xtext.example.xtext.xtext.XtextPackage
import simple.SimplePackage
import smallJava.SmallJavaPackage
import statemachine.StatemachinePackage

interface EcorePackageRegistry {

    fun getPackage(nsUri: String): EPackage?

    fun contains(nsUri: String) = getPackage(nsUri) != null

    fun registerPackage(ePackage: EPackage)

    companion object {
        @JvmStatic
        val instance: EcorePackageRegistry = ServiceManager.getService(EcorePackageRegistry::class.java)
    }


    class Dummy : EcorePackageRegistry {
        override fun getPackage(nsUri: String): EPackage? {
            if (nsUri == EcorePackage.eNS_URI) {
                return EcorePackage.eINSTANCE
            }
            return null
        }

        override fun registerPackage(ePackage: EPackage) {

        }
    }

    class ListBasedRegistry : EcorePackageRegistry {
        private val packages = mutableSetOf<EPackage>()


        init {
            packages.add(EcorePackage.eINSTANCE)
            packages.add(XtextPackage.eINSTANCE)
            packages.add(ArithmeticsPackage.eINSTANCE)
            packages.add(StatemachinePackage.eINSTANCE)
            packages.add(EntityPackage.eINSTANCE)
            packages.add(SimplePackage.eINSTANCE)
            packages.add(SmallJavaPackage.eINSTANCE)
        }

        override fun getPackage(nsUri: String): EPackage? {
            return packages.firstOrNull { it.nsURI == nsUri }
        }


        override fun registerPackage(ePackage: EPackage) {
            packages.add(ePackage)
        }
    }


}
