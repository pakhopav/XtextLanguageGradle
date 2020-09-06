package com.intellij.xtextLanguage.xtext

import arithmetics.ArithmeticsPackage
import com.intellij.openapi.components.ServiceManager
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.EcorePackage
import statemachine.StatemachinePackage

interface EcorePackageRegistry {

    fun getPackage(nsUri: String): EPackage?

    fun contains(nsUri: String) = getPackage(nsUri) != null

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
    }

    class ListBasedRegistry : EcorePackageRegistry {
        private val packages = mutableSetOf<EPackage>()


        init {
            packages.add(EcorePackage.eINSTANCE)
            packages.add(ArithmeticsPackage.eINSTANCE)
            packages.add(StatemachinePackage.eINSTANCE)
        }

        override fun getPackage(nsUri: String): EPackage? {
            return packages.firstOrNull { it.nsURI == nsUri }
        }


        fun registerPackage(ePackage: EPackage) {
            packages.add(ePackage)
        }
    }


}
