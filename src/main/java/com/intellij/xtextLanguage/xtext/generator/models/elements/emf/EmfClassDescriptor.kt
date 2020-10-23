package com.intellij.xtextLanguage.xtext.generator.models.elements.emf

import org.eclipse.emf.ecore.EClassifier
import org.eclipse.emf.ecore.EcorePackage

data class EmfClassDescriptor(val classifier: EClassifier) {
    val classPath: String = classifier.instanceTypeName
    val className = classifier.name
    val packagePrefix: String = classifier.ePackage.nsPrefix
    val packagePath = classifier.ePackage.javaClass.interfaces.first().packageName


    companion object {
        val STRING = EmfClassDescriptor(EcorePackage.eINSTANCE.eString)
    }
}